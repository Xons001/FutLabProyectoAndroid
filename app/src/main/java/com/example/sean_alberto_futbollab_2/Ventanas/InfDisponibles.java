package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sean_alberto_futbollab_2.Objetos.Grado;
import com.example.sean_alberto_futbollab_2.Objetos.Master;
import com.example.sean_alberto_futbollab_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.example.sean_alberto_futbollab_2.Ventanas.CursosDisponibles.cursoClickado;
import static com.example.sean_alberto_futbollab_2.Ventanas.Login.clienteLogin;

public class InfDisponibles extends AppCompatActivity {

    private String TAG = InfDisponibles.class.getSimpleName();

    TextView mostrarNombre, mostrarHoras, mostrarFecha, mostrarDescripcion, mostrarPlazas, mostrarPrecio;
    Button btnComprar, btnVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inf_disponibles);

        btnVolver = findViewById(R.id.btnVolver);
        btnComprar = findViewById(R.id.btnComprarCurso);

        mostrarNombre = findViewById(R.id.mostrarNombreCurso);
        mostrarNombre.setText(cursoClickado.getNombre());

        mostrarPlazas = findViewById(R.id.mostrarPlazas);
        mostrarPlazas.setText(cursoClickado.getPlazas());

        mostrarHoras = findViewById(R.id.mostrarHoras);
        mostrarFecha = findViewById(R.id.mostrarFechaInicio);
        mostrarDescripcion = findViewById(R.id.mostrarDescCurso);
        mostrarPrecio = findViewById(R.id.mostrarPrecio);

        if (cursoClickado.getMaster_id().equals("null")) {
            new getGrado().execute();
            Log.e(TAG, "Ha entrado en el master");
        } else if (cursoClickado.getGrado_id().equals("null")) {
            new getMaster().execute();
            Log.e(TAG, "Ha entrado en el grado");
        } else {
            Log.e(TAG, "No ha entrado en ninguno");
        }

        btnComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Integer.parseInt(cursoClickado.getPlazas()) > 0) {
                    new insertarInscripcion().execute();
                    new updatePlazas().execute();
                } else {
                    Toast.makeText(InfDisponibles.this, "No hay plazas suficientes", Toast.LENGTH_SHORT).show();

                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volverDisponibles = new Intent(getApplicationContext(), CursosDisponibles.class);
                startActivity(volverDisponibles);
            }
        });
    }

    private class getMaster extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/cursos/master/" + cursoClickado.getMaster_id());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    result = stringBuilder.toString();
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("master");

                Master master = new Master();

                for (int i = 0; i < array.length(); i++) {

                    JSONObject c = array.getJSONObject(i);
                    String id = c.getString("master_id");
                    String descripcion = c.getString("descripcion");
                    String fechaIncio = c.getString("fecha_inicio");
                    String precio = c.getString("precio");
                    String horasTotales = c.getString("horas_totales");


                    master.setMaster_id(id);
                    master.setMasterDescripcion(descripcion);
                    master.setMasterFechaInicio(fechaIncio);
                    master.setMasterPrecio(precio);
                    master.setMasterHorasTotales(horasTotales);

                }

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(master.getMasterFechaInicio(), inputFormatter);
                String fechaInicioCorrecto = outputFormatter.format(date);

                mostrarDescripcion.setText(master.getMasterDescripcion());
                mostrarHoras.setText(master.getMasterHorasTotales());
                mostrarFecha.setText(fechaInicioCorrecto);
                mostrarPrecio.setText(master.getMasterPrecio());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class getGrado extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/cursos/grado/" + cursoClickado.getGrado_id());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    result = stringBuilder.toString();
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("grado");

                Grado grado = new Grado();

                for (int i = 0; i < array.length(); i++) {

                    JSONObject c = array.getJSONObject(i);
                    String id = c.getString("grado_id");
                    String descripcion = c.getString("descripcion");
                    String horasTotales = c.getString("horas_totales");
                    String fechaIncio = c.getString("fecha_inicio");
                    String precio = c.getString("precio");


                    grado.setGrado_id(id);
                    grado.setGradoDescripcion(descripcion);
                    grado.setGradoHorasTotales(horasTotales);
                    grado.setGradoFechaInicio(fechaIncio);
                    grado.setGradoPrecio(precio);

                }

                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy", Locale.ENGLISH);
                LocalDate date = LocalDate.parse(grado.getGradoFechaInicio(), inputFormatter);
                String fechaInicioCorrecto = outputFormatter.format(date);

                mostrarDescripcion.setText(grado.getGradoDescripcion());
                mostrarHoras.setText(grado.getGradoHorasTotales());
                mostrarFecha.setText(fechaInicioCorrecto);
                mostrarPrecio.setText(grado.getGradoPrecio());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class insertarInscripcion extends AsyncTask<Void, Void, Void> {
        boolean insertado = false;
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/inscripcion");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                jsonParam.put("curso_id", Integer.parseInt(cursoClickado.getCurso_id()));
                jsonParam.put("cliente_id", clienteLogin.getCliente_id());

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS prueba", String.valueOf(conn.getResponseCode()));
                Log.i("MSG supremo", conn.getResponseMessage());

                insertado = true;

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(InfDisponibles.this, "El cliente no ha podido comprar el curso!", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(insertado == true){
                Toast.makeText(InfDisponibles.this, "El cliente ha comprado el curso", Toast.LENGTH_SHORT).show();
                Intent volverDisponibles = new Intent(getApplicationContext(), CursosDisponibles.class);
                startActivity(volverDisponibles);
            } else {
                Toast.makeText(InfDisponibles.this, "El cliente no ha podido comprar el curso", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class updatePlazas extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/restarplazas/" + Integer.parseInt(cursoClickado.getCurso_id()));
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("PUT");
                conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.setDoOutput(true);
                conn.setDoInput(true);

                JSONObject jsonParam = new JSONObject();
                //jsonParam.put("curso_id", Integer.parseInt(cursoClickado.getCurso_id()));
                jsonParam.put("plazas", Integer.parseInt(cursoClickado.getPlazas()));

                Log.i("JSON", jsonParam.toString());
                DataOutputStream os = new DataOutputStream(conn.getOutputStream());
                os.writeBytes(jsonParam.toString());

                os.flush();
                os.close();

                Log.i("STATUS prueba", String.valueOf(conn.getResponseCode()));
                Log.i("MSG supremo", conn.getResponseMessage());

                conn.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(InfDisponibles.this, "El cliente no ha podido comprar el curso porque no se ha podido hacer el update!", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

    }
}

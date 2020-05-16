package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.sean_alberto_futbollab_2.Adapter.CustomAdapterTemas;
import com.example.sean_alberto_futbollab_2.Adapter.CustomAdpaterUf;
import com.example.sean_alberto_futbollab_2.Objetos.Cursos;
import com.example.sean_alberto_futbollab_2.Objetos.Temas;
import com.example.sean_alberto_futbollab_2.Objetos.UnidadFormativa;
import com.example.sean_alberto_futbollab_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.sean_alberto_futbollab_2.Ventanas.CursosComprados.cursoClickadoComprado;

public class TitulosAsignaturas extends AppCompatActivity {

    private String TAG = TitulosAsignaturas.class.getSimpleName();

    ListView listViewAsignaturas;
    ArrayList<Temas> arrayListTemas;
    ArrayList<UnidadFormativa> arrayListUF;
    SwipeRefreshLayout swipeRefreshAsignaturas;
    Button volverMenuListaComprados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.titulos_asignaturas);

        swipeRefreshAsignaturas = findViewById(R.id.swipeRefreshComprados);
        listViewAsignaturas = findViewById(R.id.listaAsignaturas);
        volverMenuListaComprados = findViewById(R.id.btnVolverListaComprados);

        arrayListTemas = new ArrayList<>();
        arrayListUF = new ArrayList<>();

        Log.e(TAG, "Grado: " + cursoClickadoComprado.getGrado_id());
        Log.e(TAG, "Master: " + cursoClickadoComprado.getMaster_id());

        if (cursoClickadoComprado.getMaster_id().equals("null")) {
            new getUF().execute();
            swipeRefreshAsignaturas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new getUF().execute();
                }
            });
        } else if (cursoClickadoComprado.getGrado_id().equals("null")) {
            new getTemas().execute();
            swipeRefreshAsignaturas.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new getTemas().execute();
                }
            });
        } else {
            Log.e(TAG, "No ha entrado en ninguno");
        }
        volverMenuListaComprados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenuListaComprados = new Intent(getApplicationContext(), CursosComprados.class);
                startActivity(intentMenuListaComprados);
            }
        });
    }

    public class getTemas extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            swipeRefreshAsignaturas.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            arrayListTemas.clear();
            String result = null;
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/asignaturasmaster/" + cursoClickadoComprado.getMaster_id());
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
            swipeRefreshAsignaturas.setRefreshing(false);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("temas");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject c = array.getJSONObject(i);
                    String id = c.getString("tema_id");
                    String nombre = c.getString("nombre");
                    String master_id = c.getString("master_id");


                    Temas tema = new Temas();
                    tema.setTema_id(id);
                    tema.setNombre_tema(nombre);
                    tema.setMaster_id(master_id);
                    arrayListTemas.add(tema);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomAdapterTemas adapter = new CustomAdapterTemas(TitulosAsignaturas.this, arrayListTemas);
            listViewAsignaturas.setAdapter(adapter);

        }
    }

    public class getUF extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            swipeRefreshAsignaturas.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            arrayListUF.clear();
            String result = null;
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/asignaturasgrado/" + cursoClickadoComprado.getGrado_id());
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
            swipeRefreshAsignaturas.setRefreshing(false);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("uf");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject c = array.getJSONObject(i);
                    String id = c.getString("uf_id");
                    String nombre = c.getString("nombre");
                    String master_id = c.getString("grado_id");


                    UnidadFormativa uf = new UnidadFormativa();
                    uf.setUf_id(id);
                    uf.setNombre_uf(nombre);
                    uf.setGrado_id(master_id);
                    arrayListUF.add(uf);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomAdpaterUf adapter = new CustomAdpaterUf(TitulosAsignaturas.this, arrayListUF);
            listViewAsignaturas.setAdapter(adapter);

        }
    }
}

package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sean_alberto_futbollab_2.Adapter.CustomAdapterComprados;
import com.example.sean_alberto_futbollab_2.Objetos.Cursos;
import com.example.sean_alberto_futbollab_2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.sean_alberto_futbollab_2.Ventanas.Login.clienteLogin;

public class CursosComprados extends AppCompatActivity {

    ListView listViewComprados;
    ArrayList<Cursos> arrayListComprados;
    SwipeRefreshLayout swipeRefreshComprados;
    Button volverMenuComprados;

    static Cursos cursoClickado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cursos_comprados);

        swipeRefreshComprados = findViewById(R.id.swipeRefreshComprados);
        listViewComprados = findViewById(R.id.listaComprados);
        volverMenuComprados = findViewById(R.id.btnVolverMenuComprados);

        arrayListComprados = new ArrayList<>();

        new getCursosComprados().execute();

        swipeRefreshComprados.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new getCursosComprados().execute();
            }
        });


        volverMenuComprados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentMenuComprados = new Intent(getApplicationContext(), VentanaCliente.class);
                startActivity(intentMenuComprados);
            }
        });
    }

    public class getCursosComprados extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            swipeRefreshComprados.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            arrayListComprados.clear();
            String result = null;
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/inscripcioncliente/" + clienteLogin.getCliente_id());
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
            swipeRefreshComprados.setRefreshing(false);
            try {
                JSONObject object = new JSONObject(s);
                JSONArray array = object.getJSONArray("cursos");

                for (int i = 0; i < array.length(); i++) {

                    JSONObject c = array.getJSONObject(i);
                    String id = c.getString("curso_id");
                    String nombre = c.getString("nombre");
                    String plazas = c.getString("plazas");
                    String grado_id = c.getString("grado_id");
                    String master_id = c.getString("master_id");


                    Cursos curso = new Cursos();
                    curso.setCurso_id(id);
                    curso.setNombre(nombre);
                    curso.setPlazas(plazas);
                    curso.setGrado_id(grado_id);
                    curso.setMaster_id(master_id);
                    arrayListComprados.add(curso);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            CustomAdapterComprados adapter = new CustomAdapterComprados(CursosComprados.this, arrayListComprados);
            listViewComprados.setAdapter(adapter);

            listViewComprados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(CursosComprados.this, "Entrando en el curso ", Toast.LENGTH_SHORT).show();
                    cursoClickado = new Cursos();
                    cursoClickado = arrayListComprados.get(i);
                    Intent ventanaAsignaturas = new Intent(getApplicationContext(), TitulosAsignaturas.class);
                    startActivity(ventanaAsignaturas);
                }
            });

        }
    }

}

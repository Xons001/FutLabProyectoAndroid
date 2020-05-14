package com.example.sean_alberto_futbollab_2.Ventanas;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.sean_alberto_futbollab_2.Adapter.CustomAdapter;
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

public class CursosDisponibles extends Activity {

    private String TAG = CursosDisponibles.class.getSimpleName();

    ListView listView;
    ArrayList<Cursos> arrayList;
    SwipeRefreshLayout swipeRefresh;

    static Cursos cursoClickado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cursos_disponibles);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        listView = findViewById(R.id.listaDisponibles);

        arrayList = new ArrayList<>();

        new getCursos().execute();

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new getCursos().execute();
            }
        });
    }

    public class getCursos extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();
            swipeRefresh.setRefreshing(true);
        }

        @Override
        protected String doInBackground(String... params) {
            arrayList.clear();
            String result = null;
            try {
                URL url = new URL("https://futlab-credito-sintesis.herokuapp.com/cursos");
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
            swipeRefresh.setRefreshing(false);
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
                    arrayList.add(curso);

                }

            } catch (JSONException  e) {
                e.printStackTrace();
            }

            CustomAdapter adapter = new CustomAdapter(CursosDisponibles.this, arrayList);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Toast.makeText(CursosDisponibles.this, "Entrando en el curso ", Toast.LENGTH_SHORT).show();
                    cursoClickado = new Cursos();
                    cursoClickado = arrayList.get(i);
                    Intent ventanaInfo = new Intent(getApplicationContext(), InfDisponibles.class);
                    startActivity(ventanaInfo);
                }
            });

        }
    }

}

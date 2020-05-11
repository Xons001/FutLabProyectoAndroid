package com.example.sean_alberto_futbollab_2.Ventanas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.sean_alberto_futbollab_2.Conexion.ConexionPostgresSQL;
import com.example.sean_alberto_futbollab_2.Frames.CursosDisponibles;
import com.example.sean_alberto_futbollab_2.R;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class VentanaCliente extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ventana_cliente);

        tabLayout = findViewById(R.id.tabLayout);
        viewPager= findViewById(R.id.viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                new getCursos().execute();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    public ArrayList<HashMap<String, String>> cursosArray = new ArrayList<>();

    private class getCursos extends AsyncTask<Void, Void, Void> {
        CursosDisponibles cd = new CursosDisponibles();

        protected Void doInBackground(Void... arg0) {
            ConexionPostgresSQL conn = new ConexionPostgresSQL();
            // Making a request to url and getting response
            String jsonStr = conn.makeServiceCall("https://futlab-credito-sintesis.herokuapp.com/cursos");
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                Log.e(TAG, "Ha entrado en el if.");
                try {
                    Log.e(TAG, "Ha entrado en el try.");
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    // Getting JSON Array node
                    JSONArray cursosJS = jsonObj.getJSONArray("cursos");
                    // looping through All Courses
                    for (int i = 0; i < cursosJS.length(); i++) {
                        JSONObject c = cursosJS.getJSONObject(i);
                        String id = c.getString("curso_id");
                        String nombre = c.getString("nombre");
                        String plazas = c.getString("plazas");
                        // tmp hash map for single course
                        HashMap<String, String> cursosHM = new HashMap<>();
                        // adding each child node to HashMap key => value
                        cursosHM.put("curso_id", id);
                        cursosHM.put("nombre", nombre);
                        cursosHM.put("plazas", plazas);
                        // adding contact to contact list
                        cursosArray.add(cursosHM);
                        Log.e(TAG, "Ha entrado en el for.");

                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.e(TAG, "Ha entrado en el catch.");
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.e(TAG, "Ha entrado en el else.");
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            ListAdapter adapter = new SimpleAdapter(VentanaCliente.this, cursosArray, R.layout.fragment_cursos_disponibles, new String[]{"nombre", "plazas"}, new int[]{R.id.nombre, R.id.plazas});
            cd.lv = findViewById(R.id.listaDisponibles);
            cd.lv.setAdapter(adapter);
        }
    }
}

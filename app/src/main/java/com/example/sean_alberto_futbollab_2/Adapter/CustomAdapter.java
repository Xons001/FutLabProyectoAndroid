package com.example.sean_alberto_futbollab_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sean_alberto_futbollab_2.Objetos.Cursos;
import com.example.sean_alberto_futbollab_2.R;

import java.util.ArrayList;


public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Cursos> arrayList;

    public CustomAdapter(Context context, ArrayList<Cursos> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_cursos_disponibles, parent, false);
        }
        TextView nombre, plazas;
        nombre = convertView.findViewById(R.id.nombre);
        plazas = convertView.findViewById(R.id.plazas);
        nombre.setText(arrayList.get(position).getNombre());
        plazas.setText(arrayList.get(position).getPlazas());

        return convertView;    }
}

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

public class CustomAdapterComprados extends BaseAdapter {

    Context contextComprados;
    ArrayList<Cursos> arrayListComprados;

    public CustomAdapterComprados(Context contextComprados, ArrayList<Cursos> arrayListComprados) {
        this.contextComprados = contextComprados;
        this.arrayListComprados = arrayListComprados;
    }

    @Override
    public int getCount() {
        return arrayListComprados.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListComprados.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(contextComprados).inflate(R.layout.list_item_cursos_comprados, parent, false);
        }
        TextView nombre;
        nombre = convertView.findViewById(R.id.nombreComprado);
        nombre.setText(arrayListComprados.get(position).getNombre());

        return convertView;
    }
}

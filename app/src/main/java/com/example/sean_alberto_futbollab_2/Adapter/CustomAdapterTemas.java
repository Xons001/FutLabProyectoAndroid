package com.example.sean_alberto_futbollab_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sean_alberto_futbollab_2.Objetos.Temas;
import com.example.sean_alberto_futbollab_2.R;

import java.util.ArrayList;

public class CustomAdapterTemas extends BaseAdapter {

    Context contextTemas;
    ArrayList<Temas> arrayListTemas;

    public CustomAdapterTemas(Context contextTemas, ArrayList<Temas> arrayListTemas) {
        this.contextTemas = contextTemas;
        this.arrayListTemas = arrayListTemas;
    }

    @Override
    public int getCount() {
        return arrayListTemas.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListTemas.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(contextTemas).inflate(R.layout.list_item_asignaturas, parent, false);
        }
        TextView nombre;
        nombre = convertView.findViewById(R.id.nombreAsignatura);
        nombre.setText(arrayListTemas.get(position).getNombre_tema());

        return convertView;
    }
}

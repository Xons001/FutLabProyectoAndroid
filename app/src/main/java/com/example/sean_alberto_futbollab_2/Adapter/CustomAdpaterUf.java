package com.example.sean_alberto_futbollab_2.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sean_alberto_futbollab_2.Objetos.UnidadFormativa;
import com.example.sean_alberto_futbollab_2.R;

import java.util.ArrayList;

public class CustomAdpaterUf extends BaseAdapter {

    Context contextUF;
    ArrayList<UnidadFormativa> arrayListUF;

    public CustomAdpaterUf(Context contextTemas, ArrayList<UnidadFormativa> arrayListUF) {
        this.contextUF = contextTemas;
        this.arrayListUF = arrayListUF;
    }

    @Override
    public int getCount() {
        return arrayListUF.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListUF.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView ==  null) {
            convertView = LayoutInflater.from(contextUF).inflate(R.layout.list_item_asignaturas, parent, false);
        }
        TextView nombre;
        nombre = convertView.findViewById(R.id.nombreAsignatura);
        nombre.setText(arrayListUF.get(position).getNombre_uf());

        return convertView;
    }
}

package com.example.sean_alberto_futbollab_2.Frames;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sean_alberto_futbollab_2.R;

public class CursosDisponibles extends Fragment {

    public ListView lv;
    public ViewPager viewPager;

    public String TAG = CursosDisponibles.class.getSimpleName();

    public CursosDisponibles() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_item_cursos_disponibles, container, false);

        //cursosArray = new ArrayList<>();
        viewPager = view.findViewById(R.id.viewPager);

        lv = view.findViewById(R.id.listaDisponibles);

        return view;
    }

}

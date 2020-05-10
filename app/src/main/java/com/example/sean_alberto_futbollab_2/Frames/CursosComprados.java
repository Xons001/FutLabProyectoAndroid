package com.example.sean_alberto_futbollab_2.Frames;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sean_alberto_futbollab_2.R;

public class CursosComprados extends Fragment {

    public CursosComprados() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.ventana_cliente, container, false);
    }
}

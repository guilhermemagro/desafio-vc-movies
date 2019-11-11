package br.com.guilhermerm.desafiovcmovies.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import br.com.guilhermerm.desafiovcmovies.R;
import br.com.guilhermerm.desafiovcmovies.activities.MainActivity;
import br.com.guilhermerm.desafiovcmovies.adapters.ResultsAdapter;
import br.com.guilhermerm.desafiovcmovies.domain.ObjetoResultado;
import br.com.guilhermerm.desafiovcmovies.domain.Search;

public class ListaFragment extends Fragment {

    private int paginaAtual;

    public ListaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_fragment_layout, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity activity = (MainActivity) getActivity();
        ListView listResultados = activity.findViewById(R.id.list_lista_resultados);
        TextView txtPaginas = activity.findViewById(R.id.lista_txt_paginas);
        TextView txtResultados = activity.findViewById(R.id.lista_txt_resultados);
        paginaAtual = 1;
        ObjetoResultado objetoResultado = activity.obterResultado();

        if (objetoResultado != null) {
            ArrayAdapter arrayAdapter = new ResultsAdapter(
                    this.getContext(),(ArrayList<Search>) objetoResultado.getSearch());
            listResultados.setAdapter(arrayAdapter);

            if (objetoResultado.getTotalResults() != null) {
                Integer numResultadosInt = Integer.parseInt(objetoResultado.getTotalResults());
                String textoResultados = numResultadosInt > 1 ?
                        numResultadosInt + " resultados" :
                        numResultadosInt + " resultado";

                Integer numTotalPaginas = ((numResultadosInt - 1) / 10) + 1;
                String textoPaginas = paginaAtual + "/" + numTotalPaginas;

                txtResultados.setText(textoResultados);
                txtPaginas.setText(textoPaginas);
            }
        }
    }
}

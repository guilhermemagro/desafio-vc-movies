package br.com.guilhermerm.desafiovcmovies.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Map;

import br.com.guilhermerm.desafiovcmovies.R;
import br.com.guilhermerm.desafiovcmovies.activities.MainActivity;
import br.com.guilhermerm.desafiovcmovies.adapters.ResultsAdapter;
import br.com.guilhermerm.desafiovcmovies.domain.ObjetoResultado;
import br.com.guilhermerm.desafiovcmovies.domain.Search;
import br.com.guilhermerm.desafiovcmovies.services.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListaFragment extends Fragment {

    private ListView listResultados;
    private TextView txtPaginas;
    private TextView txtResultados;
    private Button btnVoltar;
    private Button btnAvancar;
    private Integer paginaAtual;
    private int totalDePaginas;
    private ObjetoResultado objetoResultado;
    private Map<String, String> parametros;

    public ListaFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.lista_fragment_layout, container, false);
        paginaAtual = 1;
        totalDePaginas = 1;
        listResultados = view.findViewById(R.id.list_lista_resultados);
        txtPaginas = view.findViewById(R.id.lista_txt_paginas);
        txtResultados = view.findViewById(R.id.lista_txt_resultados);
        btnVoltar = view.findViewById(R.id.lista_btn_voltar);
        btnAvancar = view.findViewById(R.id.lista_btn_avancar);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parametros != null) {
                    paginaAtual = Integer.parseInt(parametros.get("page"));
                    paginaAtual++;
                    parametros.put("page", paginaAtual.toString());
                    realizarNovaPesquisa();
                    atualizarDadosDaNovaPesquisa();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (parametros != null) {
                    paginaAtual = Integer.parseInt(parametros.get("page"));
                    paginaAtual--;
                    parametros.put("page", paginaAtual.toString());
                    realizarNovaPesquisa();
                    atualizarDadosDaNovaPesquisa();
                }
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void pesquisaInicialListener(){
        MainActivity activity = (MainActivity) getActivity();
        objetoResultado = activity.getObjetoResultado();
        parametros = activity.getParametros();
        atualizarDadosDaNovaPesquisa();
    }

    public void atualizarDadosDaNovaPesquisa() {
        atualizarLista();
        atualizarBotoes();
    }

    public void realizarNovaPesquisa(){
        Call<ObjetoResultado> call = new RetrofitConfig().
                getMoviesService().buscarFilmes(parametros);
        call.enqueue(new Callback<ObjetoResultado>() {
            @Override
            public void onResponse(Call<ObjetoResultado> call, Response<ObjetoResultado> response) {
                objetoResultado = response.body();
            }

            @Override
            public void onFailure(Call<ObjetoResultado> call, Throwable t) {
                Toast.makeText(getContext(), "Ocorreu um erro, por favor, confira a sua internet!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void atualizarLista() {
        if (objetoResultado != null) {
            ArrayAdapter arrayAdapter = new ResultsAdapter(
                    this.getContext(),(ArrayList<Search>) objetoResultado.getSearch());
            listResultados.setAdapter(arrayAdapter);

            if (objetoResultado.getTotalResults() != null) {
                Integer numResultadosInt = Integer.parseInt(objetoResultado.getTotalResults());
                String textoResultados = numResultadosInt > 1 ?
                        numResultadosInt + " resultados" :
                        numResultadosInt + " resultado";

                totalDePaginas = ((numResultadosInt - 1) / 10) + 1;
                String textoPaginas = paginaAtual + "/" + totalDePaginas;

                txtResultados.setText(textoResultados);
                txtPaginas.setText(textoPaginas);
            }
        }
    }

    private void atualizarBotoes(){
        if(paginaAtual == 1) {
            btnVoltar.setEnabled(false);
            btnVoltar.setAlpha(.5f);
        } else {
            btnVoltar.setEnabled(true);
            btnVoltar.setAlpha(1.0f);
        }

        if(paginaAtual == totalDePaginas) {
            btnAvancar.setEnabled(false);
            btnAvancar.setAlpha(.5f);
        } else {
            btnAvancar.setEnabled(true);
            btnAvancar.setAlpha(1.0f);
        }
    }
}

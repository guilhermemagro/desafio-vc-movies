package br.com.guilhermerm.desafiovcmovies.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

import br.com.guilhermerm.desafiovcmovies.R;
import br.com.guilhermerm.desafiovcmovies.domain.ObjetoResultado;
import br.com.guilhermerm.desafiovcmovies.services.RetrofitConfig;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PesquisarFragment extends Fragment {

    public PesquisarFragment() {
    }

    public interface PassarDadosEventListener {
        public void passarDados(ObjetoResultado objetoResultado);
    }

    PassarDadosEventListener passarDadosEventListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try{
            passarDadosEventListener = (PassarDadosEventListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " precisa implementar PassarDadosEventListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.pesquisar_fragment_layout, container, false);

        final Spinner spnCategoria = view.findViewById(R.id.spn_categoria_id);
        final EditText edtTitulo = view.findViewById(R.id.edt_titulo_id);
        final EditText edtAno = view.findViewById(R.id.edt_ano_id);
        Button btnPesquisar = view.findViewById(R.id.btn_pesquisar_id);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.array_cidades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapter);

        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String categoria = spnCategoria.getSelectedItem().toString();
                String titulo = edtTitulo.getText().toString();
                String ano = edtAno.getText().toString();
                Map<String, String> parametros = obterParametros(categoria, titulo, ano);

                Call<ObjetoResultado> call = new RetrofitConfig().
                        getMoviesService().buscarFilmes(parametros);
                call.enqueue(new Callback<ObjetoResultado>() {
                    @Override
                    public void onResponse(Call<ObjetoResultado> call, Response<ObjetoResultado> response) {
                        ObjetoResultado objetoResultado = response.body();
                        imprimirMensagensDeErros(objetoResultado);
                        passarDadosEventListener.passarDados(objetoResultado);

                        TabLayout tabLayout = getActivity().findViewById(R.id.tablayout_id);
                        TabLayout.Tab tab = tabLayout.getTabAt(1);
                        tab.select();
                    }

                    @Override
                    public void onFailure(Call<ObjetoResultado> call, Throwable t) {
                        Toast.makeText(getContext(), "Ocorreu um erro, por favor, confira a sua internet!", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return view;
    }

    public Map<String, String> obterParametros (String categoria, String titulo, String ano) {
        Map<String, String> parametros = new HashMap<>();
        // ApiKey
        parametros.put("apikey", "5cced8c2");
        // Titulo
        parametros.put("s", titulo);
        // Categoria
        switch (categoria) {
            case "Filme":
                parametros.put("type", "movie");
                break;
            case "Série":
                parametros.put("type", "series");
                break;
            case "Episódio":
                parametros.put("type", "episode");
                break;
            case "Jogo":
                parametros.put("type", "game");
                break;
        }
        // Ano
        if(!ano.equals("")) {
            parametros.put("y", ano);
        }
        return parametros;
    }

    public void imprimirMensagensDeErros(ObjetoResultado objetoResultado) {
        if (objetoResultado.getError() != null) {
            switch (objetoResultado.getError()) {
                case "Movie not found!":
                    Toast.makeText(getContext(), R.string.titulo_nao_encontrado, Toast.LENGTH_LONG).show();
                    break;
                case "Too many results.":
                    Toast.makeText(getContext(), R.string.muitos_resultados_encontrados, Toast.LENGTH_LONG).show();
                    break;
                case "Series not found!":
                    Toast.makeText(getContext(), R.string.serie_nao_encontrada, Toast.LENGTH_LONG).show();
                    break;
                case "Something went wrong.":
                    Toast.makeText(getContext(), R.string.um_erro_inesperado_ocorreu, Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}

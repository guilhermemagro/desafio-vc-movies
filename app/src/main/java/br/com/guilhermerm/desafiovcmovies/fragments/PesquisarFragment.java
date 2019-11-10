package br.com.guilhermerm.desafiovcmovies.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pesquisar_fragment_layout, container, false);

        final Spinner spnCategoria = view.findViewById(R.id.spn_categoria_id);
        final EditText edtTitulo = view.findViewById(R.id.edt_titulo_id);
        final EditText edtAno = view.findViewById(R.id.edt_ano_id);
        Button btnPesquisar = view.findViewById(R.id.btn_pesquisar_id);
        final TextView txtTeste = view.findViewById(R.id.text_teste);

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

                        if (objetoResultado.getError().equals("Movie not found!")){
                            Toast.makeText(getContext(), "Filme não encontrado!", Toast.LENGTH_LONG).show();
                            return;
                        } else if(objetoResultado.getError().equals("Too many results.")){
                            Toast.makeText(getContext(), "Muitos resultados encontrados, por favor, refinar a pesquisa!", Toast.LENGTH_LONG).show();
                            return;
                        } else if(objetoResultado.getError().equals("Something went wrong.")){
                            Toast.makeText(getContext(), "Um erro inesperado ocorreu!", Toast.LENGTH_LONG).show();
                            return;
                        }

                        txtTeste.setText(objetoResultado.toString());
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
}

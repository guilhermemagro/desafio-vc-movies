package br.com.guilhermerm.desafiovcmovies.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.guilhermerm.desafiovcmovies.R;

public class PesquisarFragment extends Fragment {

    public PesquisarFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pesquisar_fragment_layout, container, false);

        Spinner spnCategoria = view.findViewById(R.id.spn_categoria_id);
        EditText edtTitulo = view.findViewById(R.id.edt_titulo_id);
        EditText edtAno = view.findViewById(R.id.edt_ano_id);
        Button btnPesquisar = view.findViewById(R.id.btn_pesquisar_id);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getContext(),
                R.array.array_cidades, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCategoria.setAdapter(adapter);
        btnPesquisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}

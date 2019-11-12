package br.com.guilhermerm.desafiovcmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import br.com.guilhermerm.desafiovcmovies.R;
import br.com.guilhermerm.desafiovcmovies.domain.Search;

public class ResultsAdapter extends ArrayAdapter<Search> {

    private final Context context;
    private final ArrayList<Search> resultados;

    public ResultsAdapter(Context context, ArrayList<Search> resultados) {
        super(context, R.layout.linha_resultado ,resultados);
        this.context = context;
        this.resultados = resultados;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Search resultado = resultados.get(position);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_resultado, null);

        TextView txtImdbID = rowView.findViewById(R.id.txt_imdbid);
        TextView txtTitulo = rowView.findViewById(R.id.txt_titulo);
        TextView txtCategoria = rowView.findViewById(R.id.txt_categoria);
        TextView txtAno = rowView.findViewById(R.id.txt_ano);
        ImageView imgPoster = rowView.findViewById(R.id.res_imagem);

        txtImdbID.setText(resultado.getImdbID());
        txtTitulo.setText(resultado.getTitle());
        txtCategoria.setText(resultado.getType());
        txtAno.setText(resultado.getYear());

        if (("N/A").equals(resultado.getPoster())){
            imgPoster.setImageResource(R.drawable.sem_imagem);
        } else {
            Picasso.get().load(resultado.getPoster()).into(imgPoster);
        }

        return rowView;
    }
}

package br.com.guilhermerm.desafiovcmovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.Map;

import br.com.guilhermerm.desafiovcmovies.R;
import br.com.guilhermerm.desafiovcmovies.adapters.ViewPagerAdapter;
import br.com.guilhermerm.desafiovcmovies.domain.ObjetoResultado;
import br.com.guilhermerm.desafiovcmovies.fragments.InformacoesFragment;
import br.com.guilhermerm.desafiovcmovies.fragments.ListaFragment;
import br.com.guilhermerm.desafiovcmovies.fragments.PesquisarFragment;

public class MainActivity extends AppCompatActivity implements
        PesquisarFragment.PassarDadosEventListener {

    private TabLayout mTabLayout;
    private ViewPagerAdapter adapter;
    private ObjetoResultado objetoResultado;
    private Map<String, String> parametros;
    private int[] tabIcons = {
            R.drawable.baseline_search_white_24dp,
            R.drawable.baseline_list_white_24dp,
            R.drawable.baseline_help_white_24dp
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = findViewById(R.id.tablayout_id);
        ViewPager mViewPager = findViewById(R.id.viewpager_id);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PesquisarFragment(), getString(R.string.pesquisar));
        adapter.addFragment(new ListaFragment(), getString(R.string.lista));
        adapter.addFragment(new InformacoesFragment(), getString(R.string.informacoes));

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);

        adicionarIcones();
    }

    @Override
    public void passarDados(ObjetoResultado objetoResultado, Map<String, String> parametros) {
        this.objetoResultado = objetoResultado;
        this.parametros = parametros;
        ((ListaFragment) adapter.getItem(1)).passarDadosPesquisaInicial();
    }

    private void adicionarIcones(){
        mTabLayout.getTabAt(0).setIcon(tabIcons[0]);
        mTabLayout.getTabAt(1).setIcon(tabIcons[1]);
        mTabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    public ObjetoResultado getObjetoResultado() {
        return objetoResultado;
    }

    public Map<String, String> getParametros() {
        return parametros;
    }
}

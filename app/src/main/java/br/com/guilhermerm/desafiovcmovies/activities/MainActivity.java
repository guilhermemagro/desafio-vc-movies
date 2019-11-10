package br.com.guilhermerm.desafiovcmovies.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

import br.com.guilhermerm.desafiovcmovies.R;
import br.com.guilhermerm.desafiovcmovies.adapter.ViewPagerAdapter;
import br.com.guilhermerm.desafiovcmovies.fragments.InformacoesFragment;
import br.com.guilhermerm.desafiovcmovies.fragments.ListaFragment;
import br.com.guilhermerm.desafiovcmovies.fragments.PesquisarFragment;


public class MainActivity extends AppCompatActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        mViewPager = (ViewPager) findViewById(R.id.viewpager_id);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new PesquisarFragment(), "Pesquisar");
        adapter.addFragment(new ListaFragment(), "Lista");
        adapter.addFragment(new InformacoesFragment(), "Informações");

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}

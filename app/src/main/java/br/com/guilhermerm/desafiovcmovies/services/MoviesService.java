package br.com.guilhermerm.desafiovcmovies.services;

import java.util.Map;

import br.com.guilhermerm.desafiovcmovies.domain.ObjetoResultado;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MoviesService {

    @GET("/")
    Call<ObjetoResultado> buscarFilmes(@QueryMap Map<String, String> paramsMap);
}

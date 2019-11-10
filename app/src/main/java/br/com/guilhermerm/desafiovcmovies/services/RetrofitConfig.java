package br.com.guilhermerm.desafiovcmovies.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private Retrofit retrofit;
    private static final String BASE_URL = "https://www.omdbapi.com/";

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public MoviesService getMoviesService() {
        return this.retrofit.create(MoviesService.class);
    }
}

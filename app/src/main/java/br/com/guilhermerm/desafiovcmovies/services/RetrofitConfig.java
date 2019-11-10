package br.com.guilhermerm.desafiovcmovies.services;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {

    private static Retrofit retrofit;
    private static final String BASE_URL = "http://www.omdbapi.com/";

    public static Retrofit getRetrofitInstance() {
        if(retrofit == null) {
            RetrofitConfig.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public MoviesService getMoviesService() {
        return RetrofitConfig.retrofit.create(MoviesService.class);
    }
}

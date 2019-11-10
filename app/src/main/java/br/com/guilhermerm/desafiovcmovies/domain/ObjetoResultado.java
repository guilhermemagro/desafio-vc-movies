package br.com.guilhermerm.desafiovcmovies.domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ObjetoResultado {

    @SerializedName("Search")
    @Expose
    private List<Search> search = null;

    @SerializedName("totalResults")
    @Expose
    private String totalResults;

    @SerializedName("Response")
    @Expose
    private String response;

    @SerializedName("Error")
    @Expose
    private String error;

    public ObjetoResultado(List<Search> search, String totalResults, String response) {
        this.search = search;
        this.totalResults = totalResults;
        this.response = response;
    }

    public List<Search> getSearch() {
        return search;
    }

    public void setSearch(List<Search> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

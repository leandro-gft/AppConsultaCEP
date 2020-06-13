package br.com.gft.enderecocep.api;

import br.com.gft.enderecocep.model.Cep;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CepService {

    @GET(".")
    Call<Cep> recuperarCep();
}

package br.com.gft.enderecocep.api;

import java.util.List;

import br.com.gft.enderecocep.model.Cep;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CepService {

    @GET("{cep}/json/")
    Call<Cep> recuperarCep(@Path("cep") String cep);

    @GET("{uf}/{localidade}/{logradouro}/json/")
    Call<List<Cep>> recuperarListaCep(@Path("uf") String uf, @Path("localidade") String localidade, @Path("logradouro") String logradouro);
}

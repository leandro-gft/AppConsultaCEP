package br.com.gft.enderecocep.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.gft.enderecocep.R;
import br.com.gft.enderecocep.api.CepService;
import br.com.gft.enderecocep.model.Cep;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class CepPorEndereco extends Fragment {

    private EditText etLogradouro, etUF, etLocalidade;
    private Button btBuscar;
    private TextView txCEP;
    private Retrofit retrofit;
    private String logradouro, uf, localidade;

    public CepPorEndereco() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cep_por_endereco, container, false);

        etLocalidade = view.findViewById(R.id.etLocalidade);
        etLogradouro = view.findViewById(R.id.etLogradouro);
        etUF = view.findViewById(R.id.etUF);
        txCEP = view.findViewById(R.id.txtCEP);
        btBuscar = view.findViewById(R.id.btnBuscar);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logradouro = etLogradouro.getText().toString();
                localidade = etLocalidade.getText().toString();
                uf = etUF.getText().toString();

                retrofit = new Retrofit.Builder()
                        .baseUrl("https://viacep.com.br/ws/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                recuperarCepRetrofit();
            }
        });

        return view;
    }

    private void recuperarCepRetrofit() {
        CepService cepService = retrofit.create(CepService.class);
        Call<List<Cep>> call = cepService.recuperarListaCep(uf, localidade, logradouro);

        call.enqueue(new Callback<List<Cep>>() {
            @Override
            public void onResponse(Call<List<Cep>> call, Response<List<Cep>> response) {
                if (response.isSuccessful()) {
                    List<Cep> ceps = response.body();
                    if (ceps.size() > 0) {
                        txCEP.setText("CEP: " + ceps.get(0).getCep());
                    } else {
                        txCEP.setText("A pesquisa não retornou resultados");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Cep>> call, Throwable t) {
                txCEP.setText("Erro: " + t.getMessage());
            }
        });
    }
}

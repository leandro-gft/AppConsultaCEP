package br.com.gft.enderecocep.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.gft.enderecocep.MainActivity;
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
public class EnderecoPorCep extends Fragment {

    private EditText etCep;
    private TextView txLogradouro, txCep, txBairro, txCidade, txUf;
    private Button btBuscar;
    private Retrofit retrofit;
    private String cep;

    public EnderecoPorCep() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_endereco_por_cep, container, false);

        etCep = view.findViewById(R.id.editText);
        txBairro = view.findViewById(R.id.txtBairro);
        txCep = view.findViewById(R.id.txtCep);
        txCidade = view.findViewById(R.id.txtCidade);
        txLogradouro = view.findViewById(R.id.txtLogradouro);
        txUf = view.findViewById(R.id.txtUf);
        btBuscar = view.findViewById(R.id.button);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cep = etCep.getText().toString();
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
        Call<Cep> call = cepService.recuperarCep(cep);

        call.enqueue(new Callback<Cep>() { //criado tarefa assíncrona
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()) {
                    Cep cep = response.body();
                    txBairro.setText("Bairro: " + cep.getBairro());
                    txLogradouro.setText("Logradouro: " + cep.getLogradouro());
                    txCep.setText("CEP: " + cep.getCep());
                    txCidade.setText("Localidade: " + cep.getLocalidade());
                    txUf.setText("UF: " + cep.getUf());
                } else {
                    txCep.setText("Erro: "+response.message());
                }
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {

            }
        });

    }

}

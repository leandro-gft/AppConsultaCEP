package br.com.gft.enderecocep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.gft.enderecocep.api.CepService;
import br.com.gft.enderecocep.model.Cep;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etCep;
    private TextView txLogradouro, txCep, txBairro, txCidade, txUf;
    private Button btBuscar;
    private Retrofit retrofit;
    private String cep, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCep = findViewById(R.id.editText);
        txBairro = findViewById(R.id.txtBairro);
        txCep = findViewById(R.id.txtCep);
        txCidade = findViewById(R.id.txtCidade);
        txLogradouro = findViewById(R.id.txtLogradouro);
        txUf = findViewById(R.id.txtUf);
        btBuscar = findViewById(R.id.button);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cep = etCep.getText().toString();
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://viacep.com.br/ws/"+cep+"/json/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                recuperarCepRetrofit();
            }
        });

    }

    private void recuperarCepRetrofit() {

        CepService cepService = retrofit.create(CepService.class);
        Call<Cep> call = cepService.recuperarCep();

        call.enqueue(new Callback<Cep>() { //criado tarefa assíncrona
            @Override
            public void onResponse(Call<Cep> call, Response<Cep> response) {
                if (response.isSuccessful()){
                    Cep cep = response.body();
                    txBairro.setText("Bairro: "+cep.getBairro());
                    txLogradouro.setText("Logradouro: "+cep.getLogradouro());
                    txCep.setText("CEP: "+cep.getCep());
                    txCidade.setText("Localidade: "+cep.getLocalidade());
                    txUf.setText("UF: "+cep.getUf());
                } else {
                    Toast.makeText(MainActivity.this, "Erro ao buscar o CEP: "+response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cep> call, Throwable t) {

            }
        });

    }
}

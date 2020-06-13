package br.com.gft.enderecocep;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText etCep;
    private TextView txLogradouro, txCep, txBairro, txCidade, txUf;
    private Button btBuscar;
    private Retrofit retrofit;


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
                String cep = etCep.getText().toString();
                retrofit = new Retrofit.Builder()
                        .baseUrl("https://viacep.com.br/ws/"+cep+"/json/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        });

    }
}

package com.ssolutionsdeveloper.ecocycle.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ssolutionsdeveloper.ecocycle.R;
import com.ssolutionsdeveloper.ecocycle.api.CEPService;
import com.ssolutionsdeveloper.ecocycle.model.CEP;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroEnderecoActivity extends AppCompatActivity {
    Vibrator vibrator;
    private Button btBuscarCEP, btSalvarEndereco;
    private TextView textoResultado;
    private EditText campoCEP, campoLogradouro, campoComplemento,
            campoBairro, campoLocalidade, campoUF;
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_endereco);

        btBuscarCEP = findViewById(R.id.btPesquisaCEP);
        btSalvarEndereco = findViewById(R.id.btSalvarEndereço);
        campoCEP = findViewById(R.id.editCEP);
        campoLogradouro = findViewById(R.id.editLogradouro);
        campoComplemento = findViewById(R.id.editComplemento);
        campoBairro = findViewById(R.id.editBairro);
        campoLocalidade = findViewById(R.id.editLocalidade);
        campoUF = findViewById(R.id.editUF);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://viacep.com.br/ws/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        btBuscarCEP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recuperarCEPRetrofit();

                MyTask task = new MyTask();
                String cep = campoCEP.getText().toString();
                String urlCep = "https://viacep.com.br/ws/" + cep + "/json/";
                task.execute(urlCep);
            }
        });
    }
    //**********************BUSCAR CEP*******************************************
    private void recuperarCEPRetrofit() {
        String cep = campoCEP.getText().toString();
        CEPService cepService = retrofit.create(CEPService.class);
        Call<CEP> call = cepService.recuperarCEP(cep);

        call.enqueue(new Callback<CEP>() {
            @Override
            public void onResponse(Call<CEP> call, Response<CEP> response) {
                if (response.isSuccessful()) {
                    CEP cep = response.body();
                    textoResultado.setText(
                            cep.getCep() + "/"+
                                    cep.getLogradrouro() + " / "+
                                    //cep.getComplemento() + " / "+
                                    cep.getBairro()+"/"+
                                    cep.getLocalidade()+"/"+
                                    cep.getUf()
                    );
                }
            }
            @Override
            public void onFailure(Call<CEP> call, Throwable t) {
            }
        });
    }
    class MyTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(String... strings) {

            String stringUrl = strings[0];
            InputStream inputStream = null;
            InputStreamReader inputStreamReader = null;
            StringBuffer buffer = null;
            startVibrate();//Vibrar botão

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                inputStream = conexao.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";

                while ((linha = reader.readLine()) != null) {
                    buffer.append(linha);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return buffer.toString();
        }
        @Override
        protected void onPostExecute(String resultado) {
            super.onPostExecute(resultado);

            String logradouro = null;
            String cep = null;
            String complemento = null;
            String bairro = null;
            String localidade = null;
            String uf = null;
            startVibrate();
            try {
                JSONObject jsonObject = new JSONObject(resultado);
                logradouro = jsonObject.getString("logradouro");
                cep = jsonObject.getString("cep");
                complemento = jsonObject.getString("complemento");
                bairro = jsonObject.getString("bairro");
                localidade = jsonObject.getString("localidade");
                uf = jsonObject.getString("uf");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            campoLogradouro.setText(logradouro);
            campoComplemento.setText(complemento);
            campoBairro.setText(bairro);
            campoLocalidade.setText(localidade);
            campoUF.setText(uf);
        }
        public void startVibrate() {
            long pattern[] = { 0, 100, 200 };
            vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(pattern, -1);
        }
    }
}
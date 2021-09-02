package com.ssolutionsdeveloper.ecocycle.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ssolutionsdeveloper.ecocycle.R;
import com.ssolutionsdeveloper.ecocycle.api.CNPJService;
import com.ssolutionsdeveloper.ecocycle.model.CNPJ;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CadastroEmpresaActivity extends AppCompatActivity {
    Vibrator vibrator;
    private Retrofit retrofit;
    private Button btBuscarCnpj;
    private EditText campoCNPJ,campoRazaoSocial, campoEmail,
            campoDDD,campoTelefone,campoFantasia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_empresa);
        campoCNPJ = findViewById(R.id.editCNPJ);
        campoRazaoSocial = findViewById(R.id.editNomeEmpresarial);
        campoFantasia = findViewById(R.id.editNomeFantasia);
        campoEmail = findViewById(R.id.editEmail);
      //  campoDDD = findViewById(R.id.editDDD);
        campoTelefone = findViewById(R.id.editTelefone);

        //Configura o botão de buscar Cnpj com Retrofit e AsyncTask
        btBuscarCnpj = findViewById(R.id.btPesquisaCNPJ);
        //Configuração do retrofit
        retrofit = new Retrofit.Builder()
               // .baseUrl("https://www.receitaws.com.br/v1/cnpj/[cnpj]")
                .baseUrl("https://www.receitaws.com.br/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        btBuscarCnpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVibrate();
                recuperarCNPJRetrofit();

             //Configuração do AsynTask
            /* MyTask task = new MyTask();
                String cnpj = campoCNPJ.getText().toString(); //Digite o CNPJ no campo do aplicativo
                String urlCnpj = "https://api-publica.speedio.com.br/buscarcnpj?cnpj="+cnpj;
                //String urlCnpj = "https://www.receitaws.com.br/v1/cnpj/"+cnpj;
                task.execute(urlCnpj);*/
            }
        });
    }
    private void recuperarCNPJRetrofit(){
       String cnpj = campoCNPJ.getText().toString();
        CNPJService cnpjService = retrofit.create(CNPJService.class);
        Call <CNPJ> call = cnpjService.recuperarCNPJ(cnpj);

        call.enqueue(new Callback<CNPJ>() {
            @Override
            public void onResponse(Call<CNPJ> call, Response<CNPJ> response) {
                if(response.isSuccessful() ){
                    CNPJ cnpj =response.body();

                    campoCNPJ.setText(cnpj.getCnpj());
                    campoRazaoSocial.setText(cnpj.getNome());
                    campoFantasia.setText(cnpj.getFantasia());
                    campoEmail.setText(cnpj.getEmail());
                    campoTelefone.setText(cnpj.getTelefone());
                }
            }
            @Override
            public void onFailure(Call<CNPJ> call, Throwable t) {

            }
        });
    }

    public void startVibrate() {
        long pattern[] = { 0, 100, 200 };
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, -1);
    }

    class  MyTask extends AsyncTask<String, Void, String>{
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

            try {
                URL url = new URL(stringUrl);
                HttpURLConnection conexao = (HttpURLConnection) url.openConnection();

                inputStream = conexao.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader= new BufferedReader(inputStreamReader);
                buffer = new StringBuffer();
                String linha = "";
                while ((linha = reader.readLine())!=null){
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

            String cnpj = null;
            String fantasia = null;
            String nomeEmpresarial= null;
            String email = null;
            String ddd = null;
            String telefone = null;

            try {
                /*JSONObject jsonObject =  new JSONObject(resultado);
                cnpj = jsonObject.getString("cnpj");
                nomeEmpresarial = jsonObject.getString("nome");
                fantasia =jsonObject.getString("fantasia");
                email = jsonObject.getString("email");
                telefone = jsonObject.getString("telefone");*/

                JSONObject jsonObject =  new JSONObject(resultado);
                cnpj = jsonObject.getString("CNPJ");
                nomeEmpresarial = jsonObject.getString("RAZAO SOCIAL");
                fantasia =jsonObject.getString("NOME FANTASIA");
                email = jsonObject.getString("EMAIL");
                ddd = jsonObject.getString("DDD");
                telefone = jsonObject.getString("TELEFONE");

            } catch (JSONException e) {
                e.printStackTrace();
            }
                      /* campoCNPJ.setText(cnpj);
            campoRazaoSocial.setText(nomeEmpresarial); //textoResultado
            campoFantasia.setText(fantasia); //textoResultado
            campoEmail.setText(email);
            campoDDD.setText(ddd);
            campoTelefone.setText(telefone);*/

            campoCNPJ.setText(cnpj);
            campoRazaoSocial.setText(nomeEmpresarial); //textoResultado
            campoFantasia.setText(fantasia);
            campoEmail.setText(email);
            campoDDD.setText(ddd);
            campoTelefone.setText(telefone);
        }
    }
}
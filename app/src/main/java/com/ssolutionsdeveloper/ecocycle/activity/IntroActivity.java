package com.ssolutionsdeveloper.ecocycle.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.ssolutionsdeveloper.ecocycle.R;
import com.ssolutionsdeveloper.ecocycle.adapter.ImagemIntroAdapter;
import com.ssolutionsdeveloper.ecocycle.config.ConfiguracaoFirebase;
import com.ssolutionsdeveloper.ecocycle.model.Introducoes;
import com.ssolutionsdeveloper.ecocycle.model.Usuario;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.HORIZONTAL;

public class IntroActivity extends AppCompatActivity {

    private RecyclerView recyclerIntroducao;
    private List<Introducoes>introducoes = new ArrayList<>();
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private Button btEntrar;
    private Button btCadastre_se;
    Vibrator vibrator;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        recyclerIntroducao = findViewById(R.id.idRecycleIntroducao);

        //***********DEFINIR LAYOUT de introducao para lateral **********
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(HORIZONTAL);
        recyclerIntroducao.setLayoutManager(layoutManager);

        //***********DEFINIR ADAPTER *********
        this.preparaIntroducoes();
        ImagemIntroAdapter adapter = new ImagemIntroAdapter(introducoes);
        recyclerIntroducao.setAdapter(adapter);
    }
    public void preparaIntroducoes(){

        Introducoes introd = new Introducoes("",R.drawable.img_back_intro1);
        this.introducoes.add(introd);
         introd = new Introducoes("",R.drawable.imagem1);
        this.introducoes.add(introd);
         introd = new Introducoes("",R.drawable.imagem2);
        this.introducoes.add(introd);
         introd = new Introducoes("",R.drawable.imagem3);
        this.introducoes.add(introd);
        introd = new Introducoes("",R.drawable.imagem4);
        this.introducoes.add(introd);
    }

    @Override
    protected void onStart() {
        super.onStart();
        verificarUsuarioLogado();
    }
    //Tela Intro_cadastro vai para Tela deLogin, Tela de cadastro e se logado, para tela principal
    public void irEntrar(View view){ startActivity(new Intent(this,
            LoginActivity.class));
        startVibrate();
    }
   public void irCadastrar(View view){ startActivity(new Intent(this,
            CadastroActivity.class));
            startVibrate();
    }
    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        //autenticacao.signOut(); por enquanto definir onde ficar o botão Sair
        if (autenticacao.getCurrentUser()!=null){
            abrirTelaPrincipal();
        }
    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,
                PrincipalActivity.class));
    }
    public void startVibrate() {
        long pattern[] = { 0, 100, 200 };
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, -1);
    }
}

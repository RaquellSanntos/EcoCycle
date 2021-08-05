package com.ssolutionsdeveloper.ecocycle.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.ssolutionsdeveloper.ecocycle.R;
import com.ssolutionsdeveloper.ecocycle.config.ConfiguracaoFirebase;
import com.ssolutionsdeveloper.ecocycle.model.Usuario;

public class LoginActivity extends AppCompatActivity {
    private AppCompatEditText campoEmail, campoSenha;
    private TextInputLayout textInputLayoutEmail, textInputLayoutSenha;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    private Button botaoEntrar;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputLayoutEmail = findViewById(R.id.txt_layoutEmail);
        textInputLayoutSenha = findViewById(R.id.txt_layoutSenha);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);
        botaoEntrar = findViewById(R.id.buttonEntrar);

        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();
                validateForm();
                startVibrate();
                botaoEntrar.setEnabled(false); // DESATIVA O BOTÃO ENTRAR DEPOIS QUE CLICADO NA PRIMEIRA VEZ ;)

                //VALIDAR CAMPOS DE LOGIN PREENCHIDOS
                if (!textEmail.isEmpty()){
                    if (!textSenha.isEmpty()){
                        usuario = new Usuario();
                        usuario.setEmail(textEmail);
                        usuario.setSenha(textSenha);
                        validarLogin();
                        startVibrate();
                    }else {
                        Toast.makeText(LoginActivity.this,
                                "Preencha sua senha",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(LoginActivity.this,
                            "Preencha seu e-mail",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void validateForm(){
        if(campoEmail.getText().toString().isEmpty()){//editEmail
            textInputLayoutEmail.setErrorEnabled(true);
            textInputLayoutEmail.setError("Preencha seu e-mail");
            startVibrate();
        }else {
            textInputLayoutEmail.setErrorEnabled(false);
            startVibrate();
        }
        if(campoSenha.getText().toString().isEmpty()){//editSenha
            textInputLayoutSenha.setErrorEnabled(true);
            textInputLayoutSenha.setError("Preencha sua senha");
            startVibrate();
        }else {
            textInputLayoutSenha.setErrorEnabled(false);
            startVibrate();
        }
    }
    public void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(), usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this,
                            "Sucesso ao fazer Login",
                            Toast.LENGTH_SHORT).show();
                }else{
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email e senha não corresponde ao usuario cadastrado";
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao= "Usuário não está cadatrado";
                        startVibrate();

                    }catch (Exception e){
                        excecao = "Erro ao fazer login: "+ e.getMessage();
                        e.printStackTrace();
                        startVibrate();
                    }
                    Toast.makeText(LoginActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                    startVibrate();
                }
            }

        });
    }
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this,PrincipalActivity.class));
        finish();
    }
    public void startVibrate() {
        long pattern[] = { 0, 100, 200 };
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, -1);
    }
}

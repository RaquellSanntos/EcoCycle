package com.ssolutionsdeveloper.ecocycle.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Context;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.ssolutionsdeveloper.ecocycle.R;
import com.ssolutionsdeveloper.ecocycle.config.ConfiguracaoFirebase;
import com.ssolutionsdeveloper.ecocycle.model.Usuario;

public class CadastroActivity extends AppCompatActivity {
    private AppCompatEditText campoNome, campoEmail, campoSenha;
    private TextInputLayout textInputLayoutNome, textInputLayoutEmail, textInputLayoutSenha;
    private Button botaoCadastrar;
    private Usuario usuario;
    private FirebaseAuth autenticacao;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        campoNome = findViewById(R.id.editNome);
        campoEmail = findViewById(R.id.editEmail);
        campoSenha = findViewById(R.id.editSenha);

        textInputLayoutNome = findViewById(R.id.txt_layoutNome);
        textInputLayoutEmail = findViewById(R.id.txt_layoutEmail);
        textInputLayoutSenha = findViewById(R.id.txt_layoutSenha);

        botaoCadastrar = findViewById(R.id.btCadastrar);
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textoNome = campoNome.getText().toString();
                String textEmail = campoEmail.getText().toString();
                String textSenha = campoSenha.getText().toString();
                startVibrate();
                validateForm();
                //VALIDAR CAMPOS DE CADASTRO PREENCHIDOS
                if (!textoNome.isEmpty()){
                    if (!textEmail.isEmpty()){
                        if (!textSenha.isEmpty()){
                            usuario= new Usuario();
                            usuario.setNome(textoNome);
                            usuario.setEmail(textEmail);
                            usuario.setSenha(textSenha);
                            cadastrarUsuario();
                        }else {
                            Toast.makeText(CadastroActivity.this, "Preencha sua senha",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(CadastroActivity.this, "Preencha seu e-mail",
                                Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(CadastroActivity.this, "Preencha seu nome",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void validateForm(){
        if(campoNome.getText().toString().isEmpty()){//editNome
            textInputLayoutNome.setErrorEnabled(true);
            textInputLayoutNome.setError("Preencha seu nome");
        }else {
            textInputLayoutNome.setErrorEnabled(false);
        }
        if(campoEmail.getText().toString().isEmpty()){//editEmail
            textInputLayoutEmail.setError("Preencha seu e-mail");
        }else {
            textInputLayoutEmail.setErrorEnabled(false);
        }
        if(campoSenha.getText().toString().isEmpty()){//editSenha
            textInputLayoutSenha.setErrorEnabled(true);
            textInputLayoutSenha.setError("Preencha sua senha");
        }else {
            textInputLayoutSenha.setErrorEnabled(false);
        }
    }
    public void cadastrarUsuario(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroActivity.this,
                            "Sucesso ao cadastrar usuário",
                            Toast.LENGTH_SHORT).show();
                    finish();
                    //abrirTelaPrincipal();
                }else {
                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e ){
                        excecao= "Digite uma senha mais forte ";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Digite um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "Esta conta já foi cadastrada";
                    }catch (Exception e){
                        excecao = "Erro ao cadastrar usuário: "+ e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroActivity.this,
                            excecao,
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void startVibrate() {
        long pattern[] = { 0, 100, 200 };
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, -1);
    }
}
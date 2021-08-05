package com.ssolutionsdeveloper.ecocycle.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.ssolutionsdeveloper.ecocycle.R;
import com.ssolutionsdeveloper.ecocycle.config.ConfiguracaoFirebase;

public class PrincipalActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView navigationView;
    private FirebaseAuth autenticacao;
    Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        navigationView =  findViewById(R.id.bottomNavigationView);
        navigationView.setOnItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home:{
                startVibrate();
                startActivity(new Intent(this,PrincipalActivity.class));
                break;
            }
            case R.id.navigation_mensagens:{
                startVibrate();
                startActivity(new Intent(this,MessageActivity.class));
                break;
            }
            case R.id.navigation_opcoes:{
                startVibrate();
                startActivity(new Intent(this,OpcoesActivity.class));
                break;
            }
            case R.id.navigation_exit:{
                autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
                autenticacao.signOut();
                startActivity(new Intent(this,IntroActivity.class));
                startVibrate();
                finish();
                break;
            }
        }
        return true;
    }
    public void startVibrate() {
        long pattern[] = { 0, 100, 200 };
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(pattern, -1);
    }
}

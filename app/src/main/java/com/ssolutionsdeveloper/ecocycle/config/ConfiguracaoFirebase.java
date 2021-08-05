package com.ssolutionsdeveloper.ecocycle.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFirebase {

    private static FirebaseAuth autenticacao;

    //retorna Instancia Firebase
    public static FirebaseAuth getFirebaseAutenticacao() {
        if (autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return autenticacao;
    }
}

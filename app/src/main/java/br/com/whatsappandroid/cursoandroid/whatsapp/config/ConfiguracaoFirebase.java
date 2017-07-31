package br.com.whatsappandroid.cursoandroid.whatsapp.config;

import com.firebase.client.Firebase;


public final class ConfiguracaoFirebase {

    private static Firebase firebase;
    private static final String ULR_FIREBASE = "https://whatsapp-curso-andro.firebaseio.com/";

    public static Firebase getFirebase(){

        if(firebase==null){
            firebase = new Firebase( ULR_FIREBASE );
        }

        return firebase;
    }

}

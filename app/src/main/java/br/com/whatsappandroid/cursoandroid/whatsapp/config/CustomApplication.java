package br.com.whatsappandroid.cursoandroid.whatsapp.config;


import android.app.Application;

import com.firebase.client.Firebase;

public class CustomApplication extends Application {

    public void onCreate (){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}

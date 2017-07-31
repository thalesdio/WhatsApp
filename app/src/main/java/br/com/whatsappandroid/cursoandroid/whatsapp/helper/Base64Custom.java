package br.com.whatsappandroid.cursoandroid.whatsapp.helper;


import android.util.Base64;


public class Base64Custom {

    //Codificar para Firebase
    public static String codificarBase64(String texto){
        return Base64.encodeToString(texto.getBytes(), Base64.DEFAULT);
    }

    //Decodificar para Firebase
    public static String decodificarBase64 (String textcCodificado){
        return new String(Base64.decode(textcCodificado, Base64.DEFAULT));
    }

}

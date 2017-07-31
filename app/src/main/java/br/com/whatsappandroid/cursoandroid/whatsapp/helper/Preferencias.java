package br.com.whatsappandroid.cursoandroid.whatsapp.helper;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context contexto;
    private SharedPreferences preferences;
    private final String NOME_ARQUIVO = "wpp.preferencias";
    private final int MODE = 0;
    private SharedPreferences.Editor editor;
    private final String CHAVE_IDENTIFICADOR = "identificadorUsuario";
    private final String CHAVE_NOME = "nome";

  public Preferencias (Context contextoParamentro){
      contexto = contextoParamentro;
      preferences = contexto.getSharedPreferences(NOME_ARQUIVO, MODE);
      editor = preferences.edit();
  }

    public void salvarDados (String identificador, String nome){
        editor.putString(CHAVE_IDENTIFICADOR, identificador);
        editor.putString(CHAVE_NOME, nome);
        editor.commit();
    }

    public String getNome(){
        return preferences.getString(CHAVE_NOME, null);
    }

    public String getIdentificador(){
        return preferences.getString(CHAVE_IDENTIFICADOR, null);
    }

}

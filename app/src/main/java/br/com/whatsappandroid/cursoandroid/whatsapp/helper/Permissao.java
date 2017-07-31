package br.com.whatsappandroid.cursoandroid.whatsapp.helper;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thales on 02/02/2017.
 */

public class Permissao {

    public static boolean validadaPermissoes(int requestCode ,Activity activity, String[] permissoes) {
        if (Build.VERSION.SDK_INT >= 23) {

            List<String> listaPermissoes = new ArrayList<String>();

            for (String permissao : permissoes) {

                //verifica se a activity tem a permissao necessaria
                boolean validaPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

                if (!validaPermissao) listaPermissoes.add(permissao);
            }
            //Caso nao seja necessario pedir permissoes
            if (listaPermissoes.isEmpty()) return true;
            String[] novasPermissoes = new String[listaPermissoes.size()];
            listaPermissoes.toArray(novasPermissoes);

            //Solicita permissão
            ActivityCompat.requestPermissions(activity, novasPermissoes, requestCode);
        }
        return true;

    }
}


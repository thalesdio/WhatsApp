package br.com.whatsappandroid.cursoandroid.whatsapp.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Mensagem;

public class MensagemAdapter extends ArrayAdapter<Mensagem> {

    private Context context;
    private ArrayList<Mensagem> mensagens;

    public MensagemAdapter(Context c, ArrayList<Mensagem> objects) {
        super(c, 0, objects);
        this.context = c;
        this.mensagens = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view=null;

        //Verifica se a lista esta preenchida
        if (mensagens != null){
            Mensagem mensagem = mensagens.get(position);  // Recupera mensagens

            //Recuperar usuario logado
            Preferencias preferencias = new Preferencias(context);
            String idUsuarioLogado = preferencias.getIdentificador();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE); //Inicia objeto para montar layout

            if (idUsuarioLogado.equals(mensagem.getIdUsuario())){
                view = layoutInflater.inflate(R.layout.item_mensagem_direita, parent, false); //Monta a view a partir do XML
            }else {
            view = layoutInflater.inflate(R.layout.item_mensagem_esquerda, parent, false);
                 }

            //Recuepera elementos no Firebase para exibição
            TextView textView = (TextView) view.findViewById(R.id.tv_mensagem);
            textView.setText(mensagem.getMensagem());
        }

        return view;
    }
}

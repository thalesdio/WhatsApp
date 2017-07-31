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
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Conversa;

public class ConversaAdapter extends ArrayAdapter<Conversa>{

    private ArrayList<Conversa> conversas;
    private Context context;
    private Conversa conversa;


    public ConversaAdapter(Context c, ArrayList<Conversa> objects) {
        super(c, 0, objects);
        this.context = c;
        this.conversas = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        if (conversas != null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE); //Inicia objeto para montagem do layout
            view = inflater.inflate(R.layout.lista_conversas, parent, false); //Monta view a partir do XML

            //Recupera elementos na tela
            TextView nome = (TextView) view.findViewById(R.id.text_nome);
            TextView ultimaMensagem = (TextView) view.findViewById(R.id.text_ultima_conversa);

            //Setar valores nos componentes de tela
            conversa = conversas.get(position);
            nome.setText(conversa.getNome());
            ultimaMensagem.setText(conversa.getMensagem());


        }

        return view;
    }
}

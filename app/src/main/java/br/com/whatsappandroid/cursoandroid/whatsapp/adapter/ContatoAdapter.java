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
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Contato;

public class ContatoAdapter extends ArrayAdapter<Contato> {

    private Context context;
    private ArrayList<Contato> contatos;

    public ContatoAdapter(Context c, ArrayList<Contato> objects) {
        super(c, 0, objects);
        this.context = c;
        this.contatos = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        //Verificar se lista de contatos não esta vazia
        if (contatos != null){

            //Inicia montagem do layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            //Montar a View a partir do XML
            view = inflater.inflate(R.layout.lista_ontatos, parent, false);

            //Recuperar elementos para visualização
            TextView textView = (TextView) view.findViewById(R.id.tv_nome);
            Contato contato = contatos.get(position);
            textView.setText(contato.getNome());
        }

        return view;
    }
}

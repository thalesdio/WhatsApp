package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.adapter.MensagemAdapter;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Conversa;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Mensagem;

public class ConversaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editMensagem;
    private ImageButton btMensagem;
    private Firebase firebase;
    private ListView listView;
    private ArrayAdapter<Mensagem> arrayAdapter;
    private ArrayList<Mensagem> mensagens;
    private ValueEventListener valueEventListenerConversa;
    private Conversa conversa;

    //Destinatario
    private String nomeUsuarioDestinatario;
    private String idUsuarioDestinatario;

    //Remetente
    private String idUsuarioLogado;
    private String nomeUsuarioLogado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversa);

        toolbar = (Toolbar) findViewById(R.id.tb_conversa);
        editMensagem = (EditText) findViewById(R.id.edit_mensagem);
        btMensagem = (ImageButton) findViewById(R.id.bt_enviar);
        listView = (ListView) findViewById(R.id.lv_conversa);

        //Recuperar dados do usuario logado
        Preferencias preferencias = new Preferencias(ConversaActivity.this);
        idUsuarioLogado = preferencias.getIdentificador();
        nomeUsuarioLogado = preferencias.getNome();

        //Recuperar dados enviados na intent
        Bundle extra = getIntent().getExtras();
        if (extra != null){

            //Recuperar dados do destinatario
            nomeUsuarioDestinatario = extra.getString("nome");
            idUsuarioDestinatario = Base64Custom.converterBase64(extra.getString("email"));
        }

        //Configurar toolbar
        toolbar.setTitle(nomeUsuarioDestinatario);
        toolbar.setNavigationIcon(R.drawable.ic_action_arrow_left);
        setSupportActionBar(toolbar);

        //Montagem ListView e adapter
        mensagens = new ArrayList<>();
        //arrayAdapter = new ArrayAdapter<String>(ConversaActivity.this, android.R.layout.simple_list_item_1, mensagens);
        arrayAdapter = new MensagemAdapter(ConversaActivity.this, mensagens);
        listView.setAdapter(arrayAdapter);

        //Recuperar as mensagens do Firebase
        firebase = ConfiguracaoFirebase.getFirebase().child("Mensagens").child(idUsuarioLogado).child(idUsuarioDestinatario);

        //Listener para mensagens
        valueEventListenerConversa = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mensagens.clear(); //Limpar ArrayList de mensagens

                //Recuperar mensagens
                for (DataSnapshot dados : dataSnapshot.getChildren()){
                    Mensagem mensagem = dados.getValue(Mensagem.class); //Recuperar mensagem individual
                    mensagens.add(mensagem); //Adicionar na conversa
                }

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        firebase.addValueEventListener(valueEventListenerConversa);

        //Envio da mensagem
        btMensagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textoMensagem = editMensagem.getText().toString();

                //Testar se a mensagem foi preenchida
                if (textoMensagem.isEmpty()){
                }else {
                    //Salvar mensagem no Firebase
                    Mensagem mensagem = new Mensagem();
                    mensagem.setIdUsuario(idUsuarioLogado);
                    mensagem.setMensagem(textoMensagem);

                    //Salvar mensagem para o remetente
                    Boolean retornoRemetente = salvarMensagemFirebase(idUsuarioLogado, idUsuarioDestinatario, mensagem);
                    if (!retornoRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem, tente novamente", Toast.LENGTH_LONG).show();
                    }

                    //Salvar mensagem para destinatario
                    Boolean retornoDestinatario = salvarMensagemFirebase(idUsuarioDestinatario, idUsuarioLogado, mensagem);
                    if (!retornoDestinatario){
                        Toast.makeText(ConversaActivity.this, "Problema ao enviar mensagem, tente novamente", Toast.LENGTH_LONG).show();
                    }

                    //Salvar conversas para remetente
                    conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioDestinatario);
                    conversa.setNome(nomeUsuarioDestinatario);
                    conversa.setMensagem(textoMensagem);
                    Boolean retornoConversaRemetente = salvarConversaFirebase(idUsuarioLogado, idUsuarioDestinatario, conversa);
                    if (!retornoConversaRemetente){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa, tente novamente", Toast.LENGTH_LONG).show();
                    }

                    //Salvar conversas para destinatario
                    conversa = new Conversa();
                    conversa.setIdUsuario(idUsuarioLogado);
                    conversa.setNome(nomeUsuarioLogado);
                    conversa.setMensagem(textoMensagem);
                    Boolean retornoConversaDestinatario = salvarConversaFirebase(idUsuarioDestinatario, idUsuarioLogado, conversa);
                    if (!retornoConversaDestinatario){
                        Toast.makeText(ConversaActivity.this, "Problema ao salvar conversa, tente novamente", Toast.LENGTH_LONG).show();
                    }

                    editMensagem.setText(""); //apagar texto digitado apos enviar
                }
            }
        });
    }

    private boolean salvarMensagemFirebase(String idRemetente, String idDestinatario, Mensagem mensagem){
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("Mensagens");
            firebase.child(idRemetente).child(idDestinatario).push().setValue(mensagem);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    private boolean salvarConversaFirebase(String idRemetente, String idDestinatario, Conversa conversa){
        try {
            firebase = ConfiguracaoFirebase.getFirebase().child("Conversas");
            firebase.child(idRemetente).child(idDestinatario).setValue(conversa);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        firebase.addValueEventListener(valueEventListenerConversa);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerConversa);
    }

}

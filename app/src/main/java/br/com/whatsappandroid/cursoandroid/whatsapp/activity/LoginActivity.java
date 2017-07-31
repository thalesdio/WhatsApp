package br.com.whatsappandroid.cursoandroid.whatsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import br.com.whatsappandroid.cursoandroid.whatsapp.R;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Base64Custom;
import br.com.whatsappandroid.cursoandroid.whatsapp.helper.Preferencias;
import br.com.whatsappandroid.cursoandroid.whatsapp.model.Usuario;


public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText senha;
    private Button botaoLogar;
    private Usuario usuario;
    private String idUsuarioLogado;
    private Firebase firebase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verificarUsuarioLogado();

        email = (EditText) findViewById(R.id.editLoginEmail);
        senha = (EditText) findViewById(R.id.editLoginSenha);
        botaoLogar = (Button) findViewById(R.id.btLogar);

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Login do usuario
                usuario = new Usuario();
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                validarLogin();
            }
        });
    }

    private void verificarUsuarioLogado(){
        if (firebase.getAuth() != null){
            abrirTelaPrincipal();
        }
    }

    //Realizar login
    private void validarLogin(){

          firebase.authWithPassword(usuario.getEmail(), usuario.getSenha(), new Firebase.AuthResultHandler() {
              @Override
              public void onAuthenticated(AuthData authData) {

                  //Recupera dados do usuario
                  idUsuarioLogado = Base64Custom.converterBase64(usuario.getEmail());
                  firebase = ConfiguracaoFirebase.getFirebase().child("usuarios").child(idUsuarioLogado);
                  firebase.addListenerForSingleValueEvent(new ValueEventListener() {
                      @Override
                      public void onDataChange(DataSnapshot dataSnapshot) {

                          Usuario usuario = dataSnapshot.getValue(Usuario.class);

                          //Salvar email nas preferencias
                          String identificadorUsuarioLogado = Base64Custom.converterBase64(usuario.getEmail());
                          Preferencias preferencias = new Preferencias(LoginActivity.this);
                          preferencias.salvarDados(identificadorUsuarioLogado, usuario.getNome());
                          abrirTelaPrincipal();
                      }

                      @Override
                      public void onCancelled(FirebaseError firebaseError) {

                      }
                  });
              }

              @Override
              public void onAuthenticationError(FirebaseError firebaseError) {
                  Toast.makeText(LoginActivity.this, firebaseError.getMessage(), Toast.LENGTH_LONG).show();
              }
           }
          );
    }

    //Logar o ir para tela principal
    private void abrirTelaPrincipal(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void abrirCadastroUsuario(View view){  //trocar activity ao clicar para se cadastrar
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

}

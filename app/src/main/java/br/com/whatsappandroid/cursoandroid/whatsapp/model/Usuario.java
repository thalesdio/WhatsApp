package br.com.whatsappandroid.cursoandroid.whatsapp.model;

import com.firebase.client.Firebase;
import br.com.whatsappandroid.cursoandroid.whatsapp.config.ConfiguracaoFirebase;


public class Usuario {

    private String id;
    private String nome;
    private String email;
    private String senha;

    public Usuario (){
    }

    //Construtor para salvar dados do usuario
    public void Salvar(){
        Firebase firebase = ConfiguracaoFirebase.getFirebase();
        firebase.child("usuarios").child(getId()).setValue(this);  //criar Nós de usuarios e dentro um nó de Id
        firebase.setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}

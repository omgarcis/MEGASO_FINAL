package com.example.megaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class authActivity extends AppCompatActivity {
    //creacion de objetos tipo edit text
    private EditText txtema;
    private EditText txtpas;
    private Button btnregauth;
    private Button btnlog;
    private Button btnrespasauth;
    private ProgressDialog mDialog;

    //variables para el registro
    private String email= "";
    private String password= "";

    //objeto del paquete de autenticacion de Firebase
    FirebaseAuth Auth;
    DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        //creando instance
        Auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mDialog= new ProgressDialog(this);

        //relacionando objetos con el xml
        btnregauth = (Button) findViewById(R.id.btnregistrarauth);
        btnlog = (Button) findViewById(R.id.btnlogin);
        btnrespasauth= (Button) findViewById(R.id.btn_authresetpas);

        txtema = (EditText) findViewById(R.id.txt_nombre);
        txtpas = (EditText) findViewById(R.id.txt_passwauth);

        //creando funcion para el boton registrar
        btnregauth.setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View view){
            startActivity(new Intent(authActivity.this, Register.class));
        }
        });

        btnrespasauth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(authActivity.this, ResetPass.class));
            }
        });

        btnlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = txtema.getText().toString().trim();
                password = txtpas.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {
                    mDialog.setMessage("espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    loginUser();
                }else{
                    Toast.makeText(authActivity.this,"ingrese los datos para acceder", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    //metodo login
    public void loginUser(){
        Auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(authActivity.this,MenuActivity.class));
                    mDialog.dismiss();
                    finish();

                }else{
                    Toast.makeText(authActivity.this, "no se pudo iniciar sesion compruebe los datos",Toast.LENGTH_SHORT).show();
                    mDialog.dismiss();
                }
            }
        });
    }



    //metodo registrarse

    //mantener sesion iniciada al cerrar la aplicacion (escribir onstar para llamarlo)
    @Override
    public void onStart() {
        super.onStart();
        if(Auth.getCurrentUser() != null) {
            startActivity(new Intent(authActivity.this, MenuActivity.class));
            finish();
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = Auth.getCurrentUser();
    }

}
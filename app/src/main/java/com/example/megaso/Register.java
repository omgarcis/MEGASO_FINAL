package com.example.megaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText txtema;
    private EditText txtpas;
    private EditText txtnam;
    private EditText txttel;

    private Button btnreg;
    //variables para el registro
    private String email= "";
    private String password= "";
    private String name= "";
    private String telefono= "";


    FirebaseAuth Auth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Auth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //relacionando objetos con el xml
        txtema = (EditText)findViewById(R.id.txt_emailauth2);
        txtpas = (EditText)findViewById(R.id.txt_passwauth);
        txtnam = (EditText)findViewById(R.id.txt_nombre);
        btnreg = (Button) findViewById(R.id.btnregistrarauth);
        txttel = (EditText) findViewById(R.id.txt_telefono);


        //creando funcion para el boton registrar
        btnreg.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                email = txtema.getText().toString().trim();
                password = txtpas.getText().toString();
                name = txtnam.getText().toString().trim();
                telefono = txttel.getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty() && !name.isEmpty()) {
                    if (password.length() >= 6) {
                        //ejecutando un metodo
                        registerUser();
                    } else {
                        Toast.makeText(Register.this, "la contraseña debe tener minimo 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Register.this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser(){
        Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Map<String, Object> map = new HashMap<>();
                    map.put( "nombre", name);
                    map.put( "email", email);
                    map.put( "telefono", telefono);
                    map.put("password", password);
                    String id = Auth.getCurrentUser().getUid();

                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>(){


                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if(task2.isSuccessful()){
                                startActivity(new Intent(Register.this, MenuActivity.class));
                                Toast.makeText(Register.this, "Bienvenido!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(Register.this, "no se pudieron crear los datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else{
                    Toast.makeText(Register.this, "no se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
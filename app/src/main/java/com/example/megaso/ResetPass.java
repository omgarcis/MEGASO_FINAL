package com.example.megaso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPass extends AppCompatActivity {

    private EditText txtresetpas;
    private Button btnrespas;
    private String email= " ";
    private FirebaseAuth mAuth;
    private ProgressDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        mAuth= FirebaseAuth.getInstance();
        mDialog= new ProgressDialog(this);


        txtresetpas = (EditText) findViewById(R.id.txt_emailreset);
        btnrespas = (Button) findViewById(R.id.btn_resetpas);


        btnrespas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email= txtresetpas.getText().toString().trim();

                if(!email.isEmpty()){
                    mDialog.setMessage("espere un momento...");
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.show();
                    resetpassword();
                }else{
                    Toast.makeText(ResetPass.this, "ingrese su email para restablecer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetpassword(){
           mAuth.setLanguageCode("es");
           mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
               @Override
               public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(ResetPass.this, "Se ha enviado un correo de restablecimiento", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ResetPass.this, authActivity.class));
                    }else{
                        Toast.makeText(ResetPass.this, "no se pudo enviar el correo de restablecer contarseña", Toast.LENGTH_SHORT).show();
                    }
                    mDialog.dismiss();
               }
           });
    }
}
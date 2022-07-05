package com.example.textdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class LogginActivity extends AppCompatActivity {

    Button button_registrar, button_iniciar, button_recuperar;
    EditText et_mail, et_passwd;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            iraHome();
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.editTextTextPassword, ".{6,}",R.string.invalid_mail);

        button_registrar = findViewById(R.id.btn_crear);
        button_iniciar = findViewById(R.id.btn_register);
        button_recuperar = findViewById(R.id.btn_recuperar);

        et_mail= findViewById(R.id.editTextTextEmailAddress);
        et_passwd = findViewById(R.id.editTextTextPassword);

        button_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LogginActivity.this, "Registro el boton.", Toast.LENGTH_SHORT).show();
                if(awesomeValidation.validate()){
                    Toast.makeText(LogginActivity.this, "Se valido bien.", Toast.LENGTH_SHORT).show();

                    String mail = et_mail.getText().toString();
                    String passwd = et_passwd.getText().toString();
                    Toast.makeText(LogginActivity.this, "email: "+ mail, Toast.LENGTH_SHORT).show();
                    Toast.makeText(LogginActivity.this, "email: "+ passwd, Toast.LENGTH_SHORT).show();
                    firebaseAuth.signInWithEmailAndPassword(mail,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogginActivity.this, "On Complete.", Toast.LENGTH_SHORT).show();
                                iraHome();
                            }else {
                                String error = ((FirebaseAuthException) task.getException()).getErrorCode();
                                Toast.makeText(LogginActivity.this, "Ha ocurrido un problema.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{
                    Toast.makeText(LogginActivity.this, "No se valido.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        button_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LogginActivity.this,RegistrarActivity.class);
                startActivity(i);
            }
        });


    }

    private void iraHome() {
        Toast.makeText(LogginActivity.this, "Llego a ir a home.", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
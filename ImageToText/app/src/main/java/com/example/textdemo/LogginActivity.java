package com.example.textdemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
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

    Button button_registrar, button_iniciar;
    EditText et_mail, et_passwd;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;
    FirebaseAuth mAuth;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);
        mDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            iraHome();
        }

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.editTextTextPassword, ".{6,}",R.string.invalid_mail);

        button_registrar = findViewById(R.id.btn_crear);
        button_iniciar = findViewById(R.id.btn_register);

        et_mail= findViewById(R.id.editTextTextEmailAddress);
        et_passwd = findViewById(R.id.editTextTextPassword);

        button_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(awesomeValidation.validate()){

                    String mail = et_mail.getText().toString();
                    String passwd = et_passwd.getText().toString();
                    firebaseAuth.signInWithEmailAndPassword(mail,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogginActivity.this, "Se inició Sesión correctamente.", Toast.LENGTH_SHORT).show();
                                iraHome();
                            }else {
                                String error = ((FirebaseAuthException) task.getException()).getErrorCode();
                                Toast.makeText(LogginActivity.this, "Ha ocurrido un problema.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

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
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
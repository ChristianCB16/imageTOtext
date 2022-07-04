package com.example.imagetotext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializar();
        if(revisarSesion() == true){
            startActivity(new Intent(this, CameraActivity.class));
        }else{

        }
    }

    public boolean revisarSesion(){

        return  this.preferences.getBoolean("sesion",false);
    }

    public boolean verificar(){
        String email = findViewById(R.id.editTexttEmailAddress).toString();
        String password = findViewById(R.id.editTextPassword).toString();
        if(email !=""){
            return true;
        }else{
            return false;
        }
    }
    public void enviar(View view){
        if( verificar() == true){
            editor.putBoolean("sesion",true);
            editor.apply();

        }
        else{
            Toast.makeText(this, "El usuario o contraseña ingresada son incorrectos.", Toast.LENGTH_LONG).show();
        }
    }


    private void inicializar(){
        preferences = this.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();

    }


}
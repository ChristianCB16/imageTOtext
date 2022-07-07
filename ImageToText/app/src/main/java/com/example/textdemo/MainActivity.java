package com.example.textdemo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.languageid.IdentifiedLanguage;
import com.google.mlkit.nl.languageid.LanguageIdentification;
import com.google.mlkit.nl.languageid.LanguageIdentificationOptions;
import com.google.mlkit.nl.languageid.LanguageIdentifier;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    EditText textView;
    TextView translatedView;
    //botón dropdown que contiene los posibles lenguajes detectados en la foto
    Spinner spinnerLanguages;
    //botón dropdown que contiene las opciones de lenguje al cúal se traduce el texto
    Spinner spinner ;
    Button btn_insert;
    private static final String TAG = "LangID";
    //cadena donde se guarda el texto recuperado de la foto
    String s = "placeholder";
    //cadena donde se guarda el texto traducido
    String finalTranslatedText = "placeholder";
    //arreglo donde se guardan los posibles lenguajes reconodcidos en la foto
    List<String> items = new ArrayList<>();
    FirebaseFirestore mfirestore;
    String email;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        items.add("N/F");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        email = user.getEmail();
        //Solucionando error de cierre de app
        btn_insert = findViewById(R.id.btn_guardar);
        mfirestore = FirebaseFirestore.getInstance();
        //implementación del dropwdown que contiene las opciones de lenguaje para la traducción
        spinner = (Spinner) findViewById(R.id.spinner_languages_obj);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Target_lenguages, android.R.layout.simple_spinner_item);
            // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        //find imageview
        imageView = findViewById(R.id.imageId);
        //find textview
        textView = findViewById(R.id.textId);
        translatedView = findViewById(R.id.texTranslated);

        //spinner para lenguajes detectados
        spinnerLanguages = (Spinner)findViewById(R.id.spinner_languages);
        //check app level permission is granted for Camera
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //grant the permission
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 101);
        }
        //modificamos la precisión del indentificador de lenguajes
        setConfidence();
    }

    //ir al historial de traducciones
    public  void goHistorial (View view) {
        Intent remember = new Intent(MainActivity.this, RememberActivity.class);
        //remember.putExtra("email_key", email);
        startActivity(remember);
    }

    //proceso que ocurre al hacer clic sobre boptón take pic
    public void doProcess(View view) {
        //open the camera => create an Intent object
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 101);
    }

    //función para traducir el texto reconocido
    public void translate(View view) {
        //traducimos según la opción seleccionada en el dropdown
        if(items.get(0)!="N/F"){
            int day = spinner.getSelectedItemPosition();
            switch (day) {
                case 0:
                    translateText(textView.getText().toString(), spinnerLanguages.getSelectedItem().toString(), "es");
                    break;
                case 1:
                    translateText(textView.getText().toString(), spinnerLanguages.getSelectedItem().toString(), "en");
                    break;
                case 2:
                    translateText(textView.getText().toString(), spinnerLanguages.getSelectedItem().toString(), "fr");
                    break;
                case 3:
                    translateText(textView.getText().toString(), spinnerLanguages.getSelectedItem().toString(), "pt");
                    break;
            }
            Log.i("TEST ", finalTranslatedText);
        }else {
            Toast.makeText(MainActivity.this, "Se necesita una nueva foto.", Toast.LENGTH_SHORT).show();
        }
    }

    //manejo de las ocurrencias luego de una traducción
    public void postTraduction(View view) {

        if(textView.getText().toString() !="" &&translatedView.getText().toString() !="" ){
            Map<String, Object> map = new HashMap<>();
            map.put("Email",email);
            map.put("Texto",textView.getText().toString());
            map.put("Traduccion",translatedView.getText().toString());

            mfirestore.collection("Traduccion").add(map).
                    addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Se ha guardado con Exito.", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "Ha ocurrido un error.", Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(this, "Necesita llenar los campos ante de guardar.", Toast.LENGTH_SHORT).show();
        }
        
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle bundle = data.getExtras();
        //from bundle, extract the image
        Bitmap bitmap = (Bitmap) bundle.get("data");
        //set image in imageview
        imageView.setImageBitmap(bitmap);
        //process the image
        //1. create a FirebaseVisionImage object from a Bitmap object
        FirebaseVisionImage firebaseVisionImage = FirebaseVisionImage.fromBitmap(bitmap);
        //2. Get an instance of FirebaseVision
        FirebaseVision firebaseVision = FirebaseVision.getInstance();
        //3. Create an instance of FirebaseVisionTextRecognizer
        FirebaseVisionTextRecognizer firebaseVisionTextRecognizer = firebaseVision.getOnDeviceTextRecognizer();
        //4. Create a task to process the image
        Task<FirebaseVisionText> task = firebaseVisionTextRecognizer.processImage(firebaseVisionImage);
        //5. if task is success
        task.addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                s = firebaseVisionText.getText();
                textView.setText(s);
            }

        });
        //6. if task is failure
        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        //7. detectar los posibles idiomas en los que podría estar el texto analizado
        getPossibleLanguuages(s);
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set the spinners adapter to the previously created one.
        spinnerLanguages.setAdapter(adapter);
    }

    //función con la que cambiamos el parámetro de precisión de la identifiación del lenguaje
    //si este valor es muy alto, a veces no aparece el lenguaje que esperamos
    private void setConfidence() {
        // [START set_confidence]
        LanguageIdentifier languageIdentifier = LanguageIdentification.getClient(
                new LanguageIdentificationOptions.Builder()
                        .setConfidenceThreshold(0.30f)
                        .build());
        // [END set_confidence]
    }

    //función que utiliza los modelos de lenguaje del cliente de google para identificar qué lenguaje utiliza el texto
    //reconocido en la imagen
    private void getPossibleLanguuages(String text) {
        // [START get_possible_languages]
        LanguageIdentifier languageIdentifier =
                LanguageIdentification.getClient();
        languageIdentifier.identifyPossibleLanguages(text)
                .addOnSuccessListener(new OnSuccessListener<List<IdentifiedLanguage>>() {
                    @Override
                    public void onSuccess(List<IdentifiedLanguage> identifiedLanguages) {
                        items.clear();
                        //el siguiente paso recupera cada posible lenguaje y lo guarda en el dropdown de lenguajes reconocidos
                        for (IdentifiedLanguage identifiedLanguage : identifiedLanguages) {
                            String language = identifiedLanguage.getLanguageTag();
                            float confidence = identifiedLanguage.getConfidence();
                            Log.i(TAG, language + " (" + confidence + ")");
                            items.add(language);
                        }

                    }
                })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be loaded or other internal error.
                                // ...
                            }
                        });
        // [END get_possible_languages]
    }

    //traduce el texto, primero creando un objeto traductor a partir del lenguaje de ingreso que seleccionamos en el dropdown de posibles lenguajes
    // y a partir de lenguaje de salida que elejimos en el dropdown de opciones de traducción
    private void translateText(String text, String inputLanguage, String outputLanguage) {
        // Create a translator
        TranslatorOptions options =
                new TranslatorOptions.Builder()
                        .setSourceLanguage(TranslateLanguage.fromLanguageTag(inputLanguage))
                        .setTargetLanguage(TranslateLanguage.fromLanguageTag(outputLanguage))
                        .build();
        final Translator dynamicTranslator =
                Translation.getClient(options);

        //la sección siguiente descarga los modelos de lenguaje escogidos si no se cuenta con ellos en el dispositivo
        //cada modelo de lenguaje necesita alrededor de 30MB
        //los modelos utilizados forman parte de la tecnología de google translate
        DownloadConditions conditions = new DownloadConditions.Builder()
                .build();
        dynamicTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                //sección para traducir el texto una vez se hayan descargado los modelos
                                dynamicTranslator.translate(text)
                                        .addOnSuccessListener(
                                                new OnSuccessListener() {
                                                    @Override
                                                    public void onSuccess(Object o) {
                                                        Log.i("SUCCESS", o.toString());
                                                        translatedView.setText(o.toString());
                                                    }
                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Error.
                                                        // ...
                                                    }
                                                });
                            }
                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                            }
                        });
    }
}

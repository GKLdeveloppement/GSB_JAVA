package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

    private Button btnConnexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.btnConnexion = (Button) findViewById(R.id.btnConnexion);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //Lorsque l'on clique sur le bouton
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //On récupère la valeur dans l'EditText pour les affecter dans la variable str
                EditText login = (EditText) findViewById(R.id.inputLogin);
                String loginGSB = login.getText().toString();

                //On récupère la valeur dans l'EditText pour les affecter dans la variable pwd
                EditText password = (EditText) findViewById(R.id.inputPwd);
                String pwd = password.getText().toString();

                HttpClientGsb httpClientTest = new HttpClientGsb();
                String API = "";

                Map<String, Object> testParam = new HashMap<String, Object>(); //Cette Map contient les paramètres à envoyer en GET, POST, ou autres
                testParam.put("login", loginGSB);
                testParam.put("password", pwd);
                //System.out.println(testParam);
                try {
                    API = httpClientTest.sendPost("POST", "http://198.50.173.145:8003/java_connexion", testParam);
                    System.out.println(API);
                    //Si le retour est true aors on donne l'accès en envoyant l'utilisateur sur l'accueil
                    if (API.equals("true")) {
                        //On génère un intent pour changer de fenetre
                        Intent otherActivity = new Intent(getApplicationContext(), Dashboard.class);
                        //On demande à notre application de faire passer le login dans l'autre activité.
                        otherActivity.putExtra("login", loginGSB);
                        //On change de fenetre
                        startActivity(otherActivity);
                    } else {
                        //Afficher un message d'erreur
                        final TextView msgError = (TextView) findViewById(R.id.msgError);
                        msgError.setText("Login et/ou mot de passe incorrect");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

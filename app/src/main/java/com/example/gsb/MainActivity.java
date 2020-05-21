package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
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

            // Evenement déclenché lors du clique sur connexion
        btnConnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    //On récupère les valeurs de connexion dans l'EditText pour les affecter dans la variable str
                EditText login = (EditText) findViewById(R.id.inputLogin);
                EditText password = (EditText) findViewById(R.id.inputPwd);
                String loginGSB = login.getText().toString();
                String pwdGSB = password.getText().toString();

                    // Création de la requète HTTP
                HttpClientGsb httpClientTest = new HttpClientGsb();
                String idUser = "-1"; // Initialisation à -1, valeur de refus de connexion
                Map<String, Object> paramsConnexion = new HashMap<String, Object>(); // Paramètres pour la requete
                paramsConnexion.put("login", loginGSB);
                paramsConnexion.put("password", pwdGSB);
                try {
                    idUser = httpClientTest.sendHttpRequest("POST", "http://198.50.173.145:8003/java_connexion", paramsConnexion);
                        // Si l'id du user est  different de -1 ça veut dire qu'il a pas été trouvé dans la BDD
                    if (!idUser.equals("-1")) {
                            //  On génère un intent pour changer de fenetre
                        Intent otherActivity = new Intent(getApplicationContext(), Dashboard.class);
                            //  Passage de paramètres dans la nouvelle fenetre
                        otherActivity.putExtra("login", loginGSB);
                        otherActivity.putExtra("id", idUser);
                        startActivity(otherActivity);
                        // Sinon, si l'id vaut -1 c'est que l'utiisateur n'a pas été trouvé dans la BDD
                    } else {
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

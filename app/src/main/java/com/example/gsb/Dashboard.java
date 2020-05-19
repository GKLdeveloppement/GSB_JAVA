package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Dashboard extends AppCompatActivity {

    private Button check_tableau;
    private Button btnConsulteFiche;
    private Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        this.btnConsulteFiche = (Button) findViewById(R.id.btnConsulteFiche);

        //On récupère l'intent de la page principale
        Intent intent = getIntent();
        //On vérifie que l'intent n'est pas null par précaution
        if (intent != null) {
            //On définit les variables qui vont accueillir nos data
            String str = "";

            //Si dans notre intent on a des extras du nom de login et password alors
            if ((intent.hasExtra("login"))){
                //On affecte leur contenus dans les variables créé à cet effet
                str = intent.getStringExtra("login");
            }

            if ((intent.hasExtra("id"))){
                //On affecte leur contenus dans les variables créé à cet effet
                idUser = intent.getIntExtra("id", 0);
            }

            //On initialise les valeurs sur les champs créé pour afin de les afficher
            TextView login = (TextView) findViewById(R.id.login_);
            login.setText(str);
        }

        btnConsulteFiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity = new Intent(getApplicationContext(), consulteFiche.class);
                otherActivity.putExtra("id", idUser);
                startActivity(otherActivity);
            }
        });

    }
}




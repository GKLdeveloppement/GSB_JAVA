package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Profil extends AppCompatActivity {

    private JSONObject infos_user;
    private String idUser;
    private TextView nom;
    private TextView prenom;
    private TextView num_tel;
    private TextView adresse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        Intent intent = getIntent();
        if (intent != null) {
            if ((intent.hasExtra("id"))){
                idUser = intent.getStringExtra("id");
            }
        }
        this.nom = (TextView) findViewById(R.id.profil_nom);
        this.prenom = (TextView) findViewById(R.id.profil_prenom);
        this.num_tel = (TextView) findViewById(R.id.profil_num_tel);
        this.adresse = (TextView) findViewById(R.id.profil_adresse);

        HttpClientGsb httpClientProfil = new HttpClientGsb();

        Map<String, Object> paramGET = new HashMap<String, Object>();
        try {
            String retourGet = httpClientProfil.sendHttpRequest("GET", "http://198.50.173.145:8003/api/profil?id=" + idUser, paramGET);
            this.infos_user = new JSONObject(retourGet);
            nom.setText(infos_user.getString("nom"));
            prenom.setText(infos_user.getString("prenom"));
            num_tel.setText(infos_user.getString("num_tel"));
            adresse.setText(infos_user.getString("adresse"));


        } catch (Exception e) {
            e.printStackTrace();
        }




    }


}

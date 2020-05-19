package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class consulteFiche extends AppCompatActivity {

    private JSONArray listeNoteFrais;
    private Button btnModifFiche;
    private Integer idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulte_fiche);
        this.btnModifFiche = (Button) findViewById(R.id.btnModifFiche);

        Intent intent = getIntent();
        //On vérifie que l'intent n'est pas null par précaution
        if (intent != null) {
            if ((intent.hasExtra("id"))){
                //On affecte leur contenus dans les variables créé à cet effet
                idUser = intent.getIntExtra("id", 0);
            }
        }

        HttpClientGsb httpClientTest = new HttpClientGsb();

        Map<String, Object> paramGET = new HashMap<String, Object>();
        try {

            String retourGet = httpClientTest.sendHttpRequest("GET", "http://198.50.173.145:8003/api/ficheuser?id=" + idUser.toString(), paramGET);
            this.listeNoteFrais = new JSONArray(retourGet);
            for (int i = 0; i < listeNoteFrais.length(); i++) {
                System.out.println(listeNoteFrais.getJSONObject(i).getInt("quantite"));
                System.out.println(listeNoteFrais.getJSONObject(i).getJSONObject("fk_FraisForfait").getString("libelle"));
                System.out.println(listeNoteFrais.getJSONObject(i).getJSONObject("fk_FraisForfait").getInt("montant"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnModifFiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> paramPUT = new HashMap<String, Object>();
                HttpClientGsb httpClientPost = new HttpClientGsb();
                try {
                    String retourPost = httpClientPost.sendHttpRequest("PUT", "http://198.50.173.145:8003/api/modification/ficheuser", paramPUT);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }
}

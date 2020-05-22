package com.example.gsb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConsulteFiche extends AppCompatActivity {

    private JSONArray listeNoteFrais;
    private Button btnModifFiche;
    private String idUser;
    private RecyclerView recyclerViewNotes;
    private TextView moisEnCours;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulte_fiche);
        this.btnModifFiche = (Button) findViewById(R.id.btnModifFiche);
        this.moisEnCours = (TextView) findViewById(R.id.titreFicheFrais);
        Intent intent = getIntent();
        //On vérifie que l'intent n'est pas null par précaution
        if (intent != null) {
            if ((intent.hasExtra("id"))){
                //On affecte leur contenus dans les variables créé à cet effet
                idUser = intent.getStringExtra("id");
            }
        }

        String[] nomMois= {"de Janvier","de Février","de Mars","d\'Avril","de Mai","de Juin","de Juillet","d\'Aout","de Septembre","de Octobre","de Novembre","de Décembre"};
        String titreFiche = getString(R.string.titre_fiche_frais, nomMois[Calendar.getInstance().get(Calendar.MONTH)]);
        moisEnCours.setText(titreFiche);

        HttpClientGsb httpClientTest = new HttpClientGsb();

        Map<String, Object> paramGET = new HashMap<String, Object>();
        try {
            String retourGet = httpClientTest.sendHttpRequest("GET", "http://198.50.173.145:8003/api/ficheuser?id=" + idUser, paramGET);
            this.listeNoteFrais = new JSONArray(retourGet);
            List<String> arrayLibelle = new ArrayList<>();
            List<Double> arrayMontant = new ArrayList<>();
            final List<Integer> arrayQuantite = new ArrayList<>();
            final List<Integer> arrayIds = new ArrayList<>();

            for (int i = 0; i < listeNoteFrais.length(); i++) {
                arrayLibelle.add(listeNoteFrais.getJSONObject(i).getJSONObject("fk_FraisForfait").getString("libelle"));
                arrayMontant.add(listeNoteFrais.getJSONObject(i).getJSONObject("fk_FraisForfait").getDouble("montant"));
                arrayQuantite.add(listeNoteFrais.getJSONObject(i).getInt("quantite"));
                arrayIds.add(listeNoteFrais.getJSONObject(i).getInt("id"));
            }
                // Traitement du recycleur view
            recyclerViewNotes = findViewById(R.id.listeNotes);
            final NotesAdapteur notesAdapteur = new NotesAdapteur(this, arrayLibelle, arrayMontant, arrayQuantite);
            recyclerViewNotes.setAdapter(notesAdapteur);
            recyclerViewNotes.setLayoutManager(new LinearLayoutManager(this));

            btnModifFiche.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Map<String, Object> paramPUT = new HashMap<String, Object>();
                    HttpClientGsb httpClientPost = new HttpClientGsb();

                        // Formatage des données en string pour les nouvelles quantités et les IDs
                    String nouvellesQuantitePUT = "";
                    String listeIdsString = "";
                    Integer[] nouvellesQuantiteArray = notesAdapteur.nouvellesQuantite();
                    for (int i = 0; i < nouvellesQuantiteArray.length; i++) {
                        nouvellesQuantitePUT += nouvellesQuantiteArray[i].toString() + ",";
                    }
                    nouvellesQuantitePUT = nouvellesQuantitePUT.substring(0, nouvellesQuantitePUT.length() - 1); // enlève la derniere virgule

                    for (int i = 0; i < arrayIds.size(); i++) {
                        listeIdsString += arrayIds.get(i).toString() + ",";
                    }
                    listeIdsString = listeIdsString.substring(0, listeIdsString.length() - 1);

                    try {
                        Map<String, Object> paramsModificationQuantite = new HashMap<String, Object>(); // Paramètres pour la requete HTTP
                        paramsModificationQuantite.put("nouvellesQuantite", nouvellesQuantitePUT);
                        paramsModificationQuantite.put("ids", listeIdsString);
                        String retourPost = httpClientPost.sendHttpRequest("PUT", "http://198.50.173.145:8003/api/modification/ficheuser", paramsModificationQuantite);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

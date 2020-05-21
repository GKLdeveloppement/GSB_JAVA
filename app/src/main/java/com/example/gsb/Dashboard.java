package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    private Button btnConsulteFiche;
    private String idUser;

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
                idUser = intent.getStringExtra("id");
            }

            //On initialise les valeurs sur les champs créé pour afin de les afficher
            TextView login = (TextView) findViewById(R.id.login_);
            login.setText(str);
        }

        btnConsulteFiche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity = new Intent(getApplicationContext(), ConsulteFiche.class);
                otherActivity.putExtra("id", idUser);
                startActivity(otherActivity);
            }
        });

    }
}




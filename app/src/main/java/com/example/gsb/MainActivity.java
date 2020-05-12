package com.example.gsb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.button = (Button) findViewById(R.id.button);

        //Lorsque l'on clique sur le bouton
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        //tableau process = new tableau();
                        //process.execute();

                try{
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httpPost= new HttpPost("http://198.50.173.145:8003/java_connexion");
                        List<NameValuePair> params = new ArrayList<NameValuePair>();
                        params.add(new BasicNameValuePair("login", "gsbComptbaleNEW"));
                        params.add(new BasicNameValuePair("password", "123456789"));
                        Log.d("etape1", "ok");
                        httpPost.setEntity(new UrlEncodedFormEntity(params));

                        HttpResponse response = httpclient.execute(httpPost);
                        Log.d("etape2", "ok");
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Log.d("etape1", "pas ok");
                    } catch (ClientProtocolException e) {
                        e.printStackTrace();
                    Log.d("etape1", "pas ok");
                    } catch (IOException e) {
                        e.printStackTrace();
                    Log.d("etape1", "pas ok");
                    }

                /*
                //On récupère la valeur dans l'EditText pour les affecter dans la variable str
                EditText login = (EditText) findViewById(R.id.id_login);
                String str = login.getText().toString();

                //On récupère la valeur dans l'EditText pour les affecter dans la variable pwd
                EditText password = (EditText) findViewById(R.id.password);
                String pwd = password.getText().toString();

                //On fait appel à un controller Symfony afin d'incrémenter la bdd
                    try {
                        URL url = new URL("http://198.50.173.145:8003/java_connexion");
                        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                        httpURLConnection.setRequestMethod("GET");
                        int responseCode = httpURLConnection.getResponseCode();
                        if (responseCode == HttpURLConnection.HTTP_OK) { // success
                            Log.d("1","success");
                            }
                        else {
                            Log.d("2", "echec");
                        }
                    } catch (IOException e) {
                        Log.d("3","catch");
                        e.printStackTrace();
                    }

                //On génère un intent
                Intent otherActivity = new Intent(getApplicationContext(), Dashboard.class);

                //On demande à notre application de faire passer les données entrées dans l'autre activité.
                otherActivity.putExtra("login", str);
                otherActivity.putExtra("password", pwd);
                startActivity(otherActivity);
*/
                //finish();
            }
        });
    }
}

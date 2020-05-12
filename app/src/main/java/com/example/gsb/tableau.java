package com.example.gsb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;


public class tableau extends AsyncTask<Void,Void,Void> {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected Void doInBackground(Void... voids) {

        try {

            String urlParameters  = "login=gsbComptable&password=123456";
            byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );

            URL url = new URL("http://198.50.173.145:8003/java_connexion");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput( true );
            httpURLConnection.setInstanceFollowRedirects( false );
            httpURLConnection.setRequestMethod( "POST" );
            httpURLConnection.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            httpURLConnection.setRequestProperty( "charset", "utf-8");
            httpURLConnection.setUseCaches( false );
            try( DataOutputStream wr = new DataOutputStream( httpURLConnection.getOutputStream())) {
                wr.write( postData );
                Log.d("ok","try2");
            }
            //InputStream inputStream = httpURLConnection.getInputStream();
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Log.d("coucou", "test");

        }
        catch (IOException e)
        {
            Log.d("1", "echec",e);
        }

        return null;
    }
}
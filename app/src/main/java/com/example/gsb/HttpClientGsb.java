package com.example.gsb;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpClientGsb {
    private final String USER_AGENT = "Mozilla/5.0";

    private String dataSend="";

    // requete HTTP
    public String sendHttpRequest(String method, String url, Map<String, Object> parameters) throws Exception {
        System.out.println("Début requete HTTP");
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        //add request header
        con.setRequestMethod(method);
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");


        JSONObject params = new JSONObject();

        String urlParameters = "";
        int i = 1;
        for (Map.Entry<String, Object> entry : parameters.entrySet()) {
            urlParameters += entry.getKey()+"="+entry.getValue();
            if (i < parameters.size()) {
                urlParameters += "&";
            }
            i++;
        }

            // Traitement selon le type de requete
        System.out.println("Methode : " + method);
        if (method == "GET") {
            con.setDoOutput(false);
        } else {
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());

            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
        }

        int responseCode = con.getResponseCode();

        BufferedReader in = null;
        if (responseCode != HttpURLConnection.HTTP_OK) {
            System.out.println("HTTP FAILED, CODE " + responseCode);
            in = new BufferedReader(
                    new InputStreamReader(con.getErrorStream()));
        } else {
            System.out.println("HTTP OK, CODE " + responseCode);
            in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
        }

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        dataSend = response.toString();

        System.out.println("Valeur retour requete : ");
        System.out.println(dataSend);
        System.out.println("Fin requete HTTP");

        return dataSend;

    }
}

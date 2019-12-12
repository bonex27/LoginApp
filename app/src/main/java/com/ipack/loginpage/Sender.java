package com.ipack.loginpage;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

/**
 * 1.SEND DATA FROM EDITTEXT OVER THE NETWORK
 * 2.DO IT IN BACKGROUND THREAD
 * 3.READ RESPONSE FROM A SERVER
 */
public class Sender extends AsyncTask<Void,Void,String> {

    Context c;
    String urlAddress;
    EditText user,pass;

    String userT,passT;

    ProgressDialog pd;

    /*
            1.OUR CONSTRUCTOR
    2.RECEIVE CONTEXT,URL ADDRESS AND EDITTEXTS FROM OUR MAINACTIVITY
    */


    public Sender(Context c, String urlAddress,EditText...editTexts) {
        this.c = c;
        this.urlAddress = urlAddress;

        //INPUT EDITTEXTS
        this.user=editTexts[0];
        this.pass=editTexts[1];

        //GET TEXTS FROM EDITEXTS
        userT=user.getText().toString();
        passT = pass.getText().toString();


    }
    /*
   1.SHOW PROGRESS DIALOG WHILE DOWNLOADING DATA
    */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Send");
        pd.setMessage("Sending..Please wait");
        pd.show();
    }

    /*
    1.WHERE WE SEND DATA TO NETWORK
    2.RETURNS FOR US A STRING
     */
    @Override
    protected String doInBackground(Void... params) {
        JSONObject postDataParams = new JSONObject();
        try {
            //GET Request
            //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

            // POST Request

            postDataParams.put("user", this.userT);
            postDataParams.put("pass", this.passT);

            return send(postDataParams);
        } catch (Exception e) {
            return "Exception: " + e.getMessage();
        }
        // return this.send(postDataParams);
    }

    /*
  1. CALLED WHEN JOB IS OVER
  2. WE DISMISS OUR PD
  3.RECEIVE A STRING FROM DOINBACKGROUND
   */
    @Override
    protected void onPostExecute(String response) {
        super.onPostExecute(response);

        pd.dismiss();
        try {
            JSONObject reader = new JSONObject(response);
            String sys = reader.getString("status");
            //String[] arr = response.split("@");
            if (reader.getString("status").equals("1")) {
                //SUCCESS
                Toast.makeText(c, "Nome: " + reader.getString("name") + " Cognome:" + reader.getString("sname"), Toast.LENGTH_LONG).show();
                this.c.startActivity(new Intent(this.c, LoginSuccess.class));

            } else if (reader.getString("status").equals("0")) {
                //NO SUCCESS
                Toast.makeText(c, "Utente non trovato!", Toast.LENGTH_LONG).show();
            } else if (reader.getString("status").equals("2")) {
                //NO SUCCESS
                Toast.makeText(c, "Password errata!", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /* Invia e riceve i dati dalla connessione http, l' invio viene tramite post della stringa json ottenuta */
    private String send(JSONObject postDataParams)
    {
        //CONNECT
        //HttpURLConnection con = Connector.connect(urlAddress + "?user=" + userT + "&pass=" + passT);
        HttpURLConnection con = Connector.connect(urlAddress);


        try {
            OutputStream os = con.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, StandardCharsets.UTF_8));
            writer.write(postDataParams.toString());
            writer.flush();
            writer.close();
            os.close();

            int responseCode = con.getResponseCode(); // To Check for 200
            if (responseCode == HttpURLConnection.HTTP_OK) {

                try (BufferedReader br = new BufferedReader(
                        new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8))) {
                    StringBuilder response = new StringBuilder();
                    String responseLine = null;
                    while ((responseLine = br.readLine()) != null) {
                        response.append(responseLine.trim());
                    }
                    return response.toString();
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }

}
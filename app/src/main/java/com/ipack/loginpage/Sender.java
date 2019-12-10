package com.ipack.loginpage;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

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
        passT=pass.getText().toString();


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
        return this.send();
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

        String[] arr = response.split("@");
        if (arr[0].equals("1"))
        {
            //SUCCESS
            Toast.makeText(c, "Nome: " + arr[2] + " Cognome:" + arr[3], Toast.LENGTH_LONG).show();

        } else if (arr[0].equals("0"))
        {
            //NO SUCCESS
            Toast.makeText(c, "Errore " + response, Toast.LENGTH_LONG).show();
        }
    }

    /*
    SEND DATA OVER THE NETWORK
    RECEIVE AND RETURN A RESPONSE
     */
    private String send()
    {
        //CONNECT
        HttpURLConnection con = Connector.connect(urlAddress + "?user=" + userT + "&pass=" + passT);

        if(con==null)
        {
            return null;
        }

        try
        {


            //HAS IT BEEN SUCCESSFUL?
            int responseCode=con.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK)
            {
                //GET EXACT RESPONSE
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuffer response=new StringBuffer();

                String line;

                //READ LINE BY LINE
                while ((line=br.readLine()) != null)
                {
                    response.append(line);
                }

                //RELEASE RES
                br.close();

                return response.toString();

            }else
            {

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
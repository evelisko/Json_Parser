package com.example.json_parser;

import android.widget.Toast;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;



public class HttpHandler {

    private static final String TAG = HttpHandler.class.getSimpleName();

    public HttpHandler() {
    }

    public String makeServiceCall(String reqUrl) {
        String response = null;
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            Log.e(TAG, "setRequestMethod-GET");
            // read the response
            InputStream in = new BufferedInputStream(conn.getInputStream());
            Log.e(TAG, "getInputStream");
            response = convertStreamToString(in);
        } catch (MalformedURLException e) {
            Log.e(TAG, "makeServiceCall MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "makeServiceCall ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "makeServiceCall IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "makeServiceCall Exception: " + e.getMessage());
        }
        return response;
    }

    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
       return sb.toString();
    }

//    private static String readAll(Reader rd) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        int cp;
//        while ((cp = rd.read()) != -1) {
//            sb.append((char) cp);
//        }
//        return sb.toString();
//    }
//
//    private String readText(int resId) throws IOException {
//        InputStream is = this.getResources().openRawResource(resId);
//        BufferedReader br= new BufferedReader(new InputStreamReader(is));
//        StringBuilder sb= new StringBuilder();
//        String s= null;
//        while((s = br.readLine())!= null){
//            sb.append(s);
//            sb.append("\n");
//        }
//        return sb.toString();
//    }


}

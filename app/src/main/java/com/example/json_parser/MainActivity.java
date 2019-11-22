package com.example.json_parser;

import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;
import java.util.stream.Collectors;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME = "https://www.mocky.io/v2/56fa31e0110000f920a72134";
    ListView lvCompanies;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCompanies = (ListView) findViewById(R.id.listViewCompanies);
        textView = (TextView) findViewById(R.id.textView);

          //  if (!isOnline())
          //  {
          //    Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_LONG).show();
          //  }
          //  else
          //  {
          //     textView.append("\n Устройство подключено");
                try {
        //           List<Company> companies = importFromJSON("company");
                     importFromJSON_URL(FILE_NAME);

                } catch (Exception e) {
                    textView.append("\n Ошибка подключения "+e.getMessage());
                    e.printStackTrace();
                }
        //  }
   }

//    public boolean isOnline() {
//        //  String cs = this.CONNECTIVITY_SERVICE;
//        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo netInfo = cm.getActiveNetworkInfo();
//        if (netInfo != null && netInfo.isConnected())
//        {
//            return true;
//        }
//        else
//        {
//            return false;
//        }
//    }

 // TODO   нет допуска. необходимо установить разрешение на считывание.

 // private static final String TAG = this.getSimpleName();

    private void importFromJSON_URL(String StrUrl) throws MalformedURLException, IOException{

        HttpURLConnection connection = (HttpURLConnection) new URL(StrUrl).openConnection();
   //     connection.setRequestProperty("Content-Type", contentType);
   //     String contentType =conn.getHeaderField("Content-Type").toUpperCase();
   //     conn.setRequestMethod("GET");
        connection.setConnectTimeout(10000);

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("utf-8"))))
        {
      //      return Optional.of(reader.lines().collect(Collectors.joining(System.lineSeparator())));
            textView.append("\n Подключение выполненно");
        }

//        try {
//       //     URL u = new URL(StrUrl);
//       //      con = (HttpsURLConnection) u.openConnection();
//        //     con.connect();
//
//        //    URL сurl = new URL(StrUrl);
//        //    textView.append("\n "+ StrUrl);
////            HttpURLConnection conn = (HttpURLConnection) сurl.openConnection();
////            conn.setRequestMethod("GET");
////            conn.connect();
////        InputStream  inputStream =  conn.getInputStream();
//
//         textView.append("\n Подключение выполненно");
//     // read the response
//     //        ins = new BufferedInputStream(inputStream);
//     //      final URLConnection  con =ins.createURLConnection(url);
//     //       String contentType =conn.getHeaderField("Content-Type").toUpperCase();
//     //       textView.append("\n тип файла"+contentType);
//     //      BufferedReader rd = new BufferedReader(new InputStreamReader(ins, Charset.forName("UTF-8")));
//
//
// //------------------------------------------------------------------------------------------------//
//
//        } catch (IOException ex)
//        {   textView.append("\n Ошибка "+ex.getMessage());
//            ex.printStackTrace();
//  //          return null;
//        } finally
//        {
//
//        }
  // return comps;
    }








}

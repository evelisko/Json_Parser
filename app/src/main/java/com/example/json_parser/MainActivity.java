package com.example.json_parser;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Bundle;
import android.widget.Toast;
import java.util.List;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;
import android.view.View.OnClickListener;
import android.view.View;

// Если программа отказывается запускаться. нужно удалить приложение из эмулятора.
// и перезапустить отладку.
public class MainActivity extends AppCompatActivity {

    ListView lvCompanies;
    TextView textView;
    JsonReader jsonReader;
    Button btnOk;

    private String TAG = MainActivity.class.getSimpleName();
    private static final String FILE_NAME = "http://www.mocky.io/v2/56fa31e0110000f920a72134";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCompanies = (ListView) findViewById(R.id.listViewCompanies);
        textView = (TextView) findViewById(R.id.textView);

        btnOk = (Button) findViewById(R.id.btnOk);
//        // создание обработчика
        OnClickListener oclBtnOk = new OnClickListener() {
            @Override
            public void onClick(View v)
            {
              // TODO Auto-generated method stub
                Log.e("MayTage", "Нажата Ok 1");
            }
        };
        btnOk.setOnClickListener(oclBtnOk);

//        public interface CommandDataAvailiableListener {
//            public void commandDataAvailiable(CommandDataAvailiableEvent e);
//        }

       jsonReader = new JsonReader();
//
      JsonReader.CreateCompanyListListener createCompanyListListener = new JsonReader.CreateCompanyListListener() {
         public void  onCreateCompanyListListener(List<Company> companies){
             CreateCompanyList(companies);
         }
        };
        jsonReader.setCompanyListListener(createCompanyListListener);

        if (!isOnline())
        {
            Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_LONG).show();
            Log.e("MayTage", "Нет подключения к сети");
        }
        else
        {
            Log.e("MayTage", "Устройство подключено");

            textView.append("\n Устройство подключено");
            try {
                jsonReader.readCompanyJSONFile();

                //   new GetCompanies().execute();

            } catch (Exception e) {
                textView.append("\n Ошибка подключения "+e.getMessage());
                Log.e("MayTage", "Ошибка подключения");
                e.printStackTrace();
            }
        }
  }

    public boolean isOnline() {
//  String cs = this.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

 private void CreateCompanyList(List<Company>companyList)
 {
   try {
         if (companyList != null) {

             CompanyAdapter adapter = new CompanyAdapter(this, R.layout.company_layout, companyList);
             lvCompanies.setAdapter(adapter);
             textView.setText(companyList.get(0).getName());

             Toast.makeText(this, "Данные считанны", Toast.LENGTH_LONG).show(); //аналог massegeBox
         } else {
             Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
             textView.append("\n Не удалось открыть данные");
         }
     } catch (Exception e) {
         textView.append("\n Ошибка подключения "+e.getMessage());
         e.printStackTrace();
     }
 }

///////////////////////////////////////////////////////////////////////////////////////////////////






}

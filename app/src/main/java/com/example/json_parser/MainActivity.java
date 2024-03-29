package com.example.json_parser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

// Если программа отказывается запускаться. нужно удалить приложение из эмулятора.
// и перезапустить отладку.
public class MainActivity extends AppCompatActivity {

    ListView lvCompanies;
    JsonReader jsonReader;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private boolean dataIsReaded;

    private String TAG = MainActivity.class.getSimpleName();
    private static final String FILE_NAME = "http://www.mocky.io/v2/56fa31e0110000f920a72134";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataIsReaded = true;
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeСontainer);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("MayTage", "Swipe ");
                readDataFromJSON();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        lvCompanies = (ListView) findViewById(R.id.lvCompanies);

        lvCompanies.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {  }

             // Делаем так чтобы Swipe срабатывал только тогда,
             // когда ролик прокрутки находится в самом начале списка.
             // Чтобы не мешать пермещаться по списку вверх-вниз.
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (lvCompanies.getChildAt(0) != null) {
                   mSwipeRefreshLayout.setEnabled(lvCompanies.getFirstVisiblePosition() == 0 && lvCompanies.getChildAt(0).getTop() == 0);
                }
            }
        });

        jsonReader = new JsonReader();

        JsonReader.createCompanyListListener createCompanyListListener = new JsonReader.createCompanyListListener() {
            public void onCreateCompanyListListener(List<Company> companies) {

                dataIsReaded = true;
                createCompanyList(companies);
           }
        };
        jsonReader.setCompanyListListener(createCompanyListListener);

        readDataFromJSON();
    }


    private void readDataFromJSON() {
        if (dataIsReaded == true) {
            if (!isOnline()) {
                Toast.makeText(this, "Нет подключения к сети", Toast.LENGTH_LONG).show();
                Log.e("MayTage", "Нет подключения к сети");
            } else {
                Log.e("MayTage", "Устройство подключено");
                try {
                    dataIsReaded = false;
                    jsonReader.readCompanyJSONFile();
                } catch (Exception e) {
                    Toast.makeText(this, "Ошибка подключения " + e.getMessage(), Toast.LENGTH_LONG).show();
                    Log.e("MayTage", "Ошибка подключения");
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    private void createCompanyList(List<Company> companies) {
        try {
            if (companies != null) {
                mSwipeRefreshLayout.setEnabled(true);
                CompanyAdapter adapter = new CompanyAdapter(this, R.layout.company_layout, companies);
                lvCompanies.setAdapter(adapter);

                Toast.makeText(this, "Данные считаны", Toast.LENGTH_LONG).show(); //аналог massegeBox
            } else {
                Toast.makeText(this, "Не удалось открыть данные", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Ошибка подключения " + e.getMessage(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}

package com.example.json_parser;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;
import android.widget.Toast;
import java.util.List;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// Если программа отказывается запускаться. нужно удалить приложение из эмулятора.
// и перезапустить отладку.
public class MainActivity extends AppCompatActivity {

    ListView lvCompanies;
    TextView textView;
    JsonReader jsonReader;
    private String TAG = MainActivity.class.getSimpleName();
    private static final String FILE_NAME = "http://www.mocky.io/v2/56fa31e0110000f920a72134";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvCompanies = (ListView) findViewById(R.id.listViewCompanies);
        textView = (TextView) findViewById(R.id.textView);

        jsonReader = new JsonReader();

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



    /**
     * Async task class to get json by making HTTP call
     */
    private class GetCompanies extends AsyncTask<Void, Void, List<Company>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
//            pDialog = new ProgressDialog(MainActivity.this);
//            pDialog.setMessage("Please wait...");
//            pDialog.setCancelable(false);
//            pDialog.show();
        }

        @Override
        protected List<Company>  doInBackground(Void... arg0) {

            HttpHandler sh = new HttpHandler();
//          Making a request to url and getting response
            Log.e(TAG, "File Name url: " + FILE_NAME);
            String jsonStr = sh.makeServiceCall(FILE_NAME);
//
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                Log.e(TAG, "doInBackground JSON-str not NULL");
//              Выполняем анализ полученной строки.
                try {
                    return importFromJSON(jsonStr);
                }catch (JSONException ex)
                {
                    return null;
                }
//                try {
//                    JSONObject jsonObj = new JSONObject(jsonStr);
//
//                    // Getting JSON Array node
//                    JSONArray contacts = jsonObj.getJSONArray("contacts");
//
//                    // looping through All Contacts
//                    for (int i = 0; i < contacts.length(); i++) {
//                        JSONObject c = contacts.getJSONObject(i);
//
//                        String id = c.getString("id");
//                        String name = c.getString("name");
//                        String email = c.getString("email");
//                        String address = c.getString("address");
//                        String gender = c.getString("gender");
//
//                        // Phone node is JSON Object
//                        JSONObject phone = c.getJSONObject("phone");
//                        String mobile = phone.getString("mobile");
//                        String home = phone.getString("home");
//                        String office = phone.getString("office");
//
//                        // tmp hash map for single contact
//                        HashMap<String, String> contact = new HashMap<>();
//
//                        // adding each child node to HashMap key => value
//                        contact.put("id", id);
//                        contact.put("name", name);
//                        contact.put("email", email);
//                        contact.put("mobile", mobile);
//
//                        // adding contact to contact list
//                        contactList.add(contact);
//                    }
//                } catch (final JSONException e) {
//                    Log.e(TAG, "Json parsing error: " + e.getMessage());
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getApplicationContext(),
//                                    "Json parsing error: " + e.getMessage(),
//                                    Toast.LENGTH_LONG)
//                                    .show();
//                        }
//                    });

//                }
//            } else {
///////////////////////////////////////////////////////////////////////////////////////////////////
//                Log.e(TAG, "Couldn't get json from server.");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(),
//                                "Couldn't get json from server. Check LogCat for possible errors!",
//                                Toast.LENGTH_LONG)
//                                .show();
//        //            }
//        //        });
////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            else
            {
                return null;
            }

        }

        @Override
        protected void onPostExecute(List<Company> result) {
            super.onPostExecute(result);
            Log.e(TAG, "Asinch is finish");
        }

//            И вот сюда же добавим обработку запроса.

//////////////////////////////////////////////////////////////////////////////////////////

        private List<Company> importFromJSON(String jString) throws JSONException{

            InputStreamReader streamReader = null;
            FileInputStream fileInputStream = null;
            List<Company> comps;

            try {
                comps = new ArrayList<>();

                JSONObject jsonRoot = new JSONObject(jString);

                for(int s = 0; s <  jsonRoot.length();s++)
                {
                    // считываем пока не будет достигнут конец файла.
                    JSONObject jcmp = jsonRoot.getJSONObject("company");

                    JSONArray jsonArray = jcmp.getJSONArray("competences");

                    String[] competences = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++)
                    {
                        competences[i] = jsonArray.getString(i);
                    }

                    JSONArray jsonArray1 = jcmp.getJSONArray("employees");
                    Employee[] employees = new Employee[jsonArray1.length()];

                    for (int i = 0; i < jsonArray1.length(); i++)
                    {
                        Employee emp = new Employee();

                        JSONObject jEmployee = jsonArray1.getJSONObject(i);
                        String str = jEmployee.getString("name");
                        emp.setName(str);

                        emp.setPhone_number(jEmployee.getString("phone_number"));
                        JSONArray jSkills = jEmployee.getJSONArray("skills");

                        String[] skills = new String[jSkills.length()];
                        for (int j = 0; j < jSkills.length(); j++) {
                            skills[j] = jSkills.getString(j);
                        }
                        emp.setSkills(skills);

                        employees[i] = emp;
                    }

                    Company cmp = new Company(jcmp.getString("name"),jcmp.getString("age"),competences);
                    cmp.setEmployee(employees);
                    comps.add(cmp);
                }
//------------------------------------------------------------------------------------------------//

                //       } catch (IOException ex)
                //      {
                //           ex.printStackTrace();
                //           return null;
            } finally {
                if (streamReader != null) {
                    try {
                        streamReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return comps;
        }
///////////////////////////////////////////////////////////////////////////////////////////////////

    }
///////////////////////////////////////////////////////////////////////////////////////////////////






}

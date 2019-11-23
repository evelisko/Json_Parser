package com.example.json_parser;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import android.util.Log;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

public class JsonReader {

  private static final String FILE_NAME = "http://www.mocky.io/v2/56fa31e0110000f920a72134";

    private String TAG = JsonReader.class.getSimpleName();


// Выполняет считывание информации из - JSON
    public void readCompanyJSONFile() throws IOException
    {
//   Toast.makeText(getApplicationContext(), "Form Data has been sent to server", Toast.LENGTH_LONG).show();

//   необходимо  передвать Activivty;

     new GetCompanies().execute();
//  Запуск потока на выполнение.
    }

// необходмо создать событие сигнализирующее о завершении считывания файла.
    public void  getCompanyList(List<Company> companies)
    {
    // необходимо  передвать Activivty;
    }




///////////////////////////////////////////////////////////////////////////////////////////////////
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
//                    }
//                });
////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Company> result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
//            if (pDialog.isShowing())
//                pDialog.dismiss();
//            /**
//             * Updating parsed JSON data into ListView
//             * */
//            ListAdapter adapter = new SimpleAdapter(
//                    MainActivity.this, contactList,
//                    R.layout.list_item, new String[]{"name", "email",
//                    "mobile"}, new int[]{R.id.name,
//                    R.id.email, R.id.mobile});
//
//            lv.setAdapter(adapter);
         // Создаем событие сигнализирующее о завершении считывания информации.

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
      Log.e(TAG, "importFromJSON Удачно");
         return comps;
      }
///////////////////////////////////////////////////////////////////////////////////////////////////
    }
///////////////////////////////////////////////////////////////////////////////////////////////////

}

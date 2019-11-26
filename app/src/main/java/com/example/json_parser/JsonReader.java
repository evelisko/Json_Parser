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

import java.util.EventObject;

public class JsonReader {

    private static final String FILE_NAME = "http://www.mocky.io/v2/56fa31e0110000f920a72134";

    private String TAG = JsonReader.class.getSimpleName();

    // Выполняет считывание информации из - JSON
    public void readCompanyJSONFile() throws IOException {
        new GetCompanies().execute();//  Запуск потока на выполнение.
    }
///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Async task class to get json by making HTTP call
     */
    private class GetCompanies extends AsyncTask<Void, Void, List<Company>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<Company> doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            Log.e(TAG, "File Name url: " + FILE_NAME);
            String jsonStr = sh.makeServiceCall(FILE_NAME);

            if (jsonStr != null) {
                Log.e(TAG, "doInBackground JSON-str not NULL");
//              Выполняем анализ полученной строки.
                try {
                    return importFromJSON(jsonStr);
                } catch (JSONException ex) {
                    return null;
                }
            } else return null;
        }

        @Override
        protected void onPostExecute(List<Company> result) {
            super.onPostExecute(result);
            Log.e(TAG, "onPostExecute");
//              Выполняем анализ полученной строки.
            creatingList.onCreateCompanyListListener(result);
        }
//////////////////////////////////////////////////////////////////////////////////////////

        private List<Company> importFromJSON(String jString) throws JSONException {

            InputStreamReader streamReader = null;
            FileInputStream fileInputStream = null;
            List<Company> comps;

            try {
                comps = new ArrayList<>();

                JSONObject jsonRoot = new JSONObject(jString);

                for (int s = 0; s < jsonRoot.length(); s++) {
                    // считываем пока не будет достигнут конец файла.
                    JSONObject jcmp = jsonRoot.getJSONObject("company");

                    JSONArray jsonArray = jcmp.getJSONArray("competences");

                    String[] competences = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        competences[i] = jsonArray.getString(i);
                    }

                    JSONArray jsonArray1 = jcmp.getJSONArray("employees");
                    Employee[] employees = new Employee[jsonArray1.length()];

                    for (int i = 0; i < jsonArray1.length(); i++) {
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

                    Company cmp = new Company(jcmp.getString("name"), jcmp.getString("age"), competences);
                    cmp.setEmployee(employees);
                    comps.add(cmp);
                }
//------------------------------------------------------------------------------------------------//
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

    public interface CreateCompanyListListener {
        public void onCreateCompanyListListener(List<Company> companies);
    }

    private List<CreateCompanyListListener> listeners = new ArrayList<CreateCompanyListListener>();

    private CreateCompanyListListener creatingList;

    public void addListener(CreateCompanyListListener toAdd) {
        listeners.add(toAdd);
    }

    public void setCompanyListListener(CreateCompanyListListener companyList) {
        this.creatingList = companyList;
    }
//-----------------------------------------------------------------------------------------------//

    public class CommandDataAvailiableEvent extends EventObject {

        private String message;

        public CommandDataAvailiableEvent(Object source, String message) {
            super(source);
            this.message = message;
        }

        public CommandDataAvailiableEvent(Object source) {
            this(source, "");
        }

        public CommandDataAvailiableEvent(String s) {
            this(null, s);
        }

        public String getMessage() {
            return message;
        }

        @Override
        public String toString() {
            return getClass().getName() + "[source = " + getSource() + ", message = " + message + "]";
        }
    }
//------------------------------------------------------------------------------------------------//

}

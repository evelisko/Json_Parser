package com.example.json_parser;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;


public class JsonReader {

    private static final String FILE_NAME = "http://www.mocky.io/v2/56fa31e0110000f920a72134";

    private static String TAG = JsonReader.class.getSimpleName();

//------------------------------------------------------------------------------------------------//
    // region Создание события обработчика(Listener)события завершения считывания данных

    public interface createCompanyListListener {
        public void onCreateCompanyListListener(List<Company> companies);
    }

    private List<createCompanyListListener> listeners = new ArrayList<createCompanyListListener>();

    private createCompanyListListener creatingList;

    public void addListener(createCompanyListListener toAdd) {
        listeners.add(toAdd);
    }

    public void setCompanyListListener(createCompanyListListener companyList) {
        this.creatingList = companyList;
    }
// endregion
//------------------------------------------------------------------------------------------------//

    // Выполняет считывание информации из - JSON
    public void readCompanyJSONFile() throws IOException {
        new getCompanies().execute(); //  Запуск потока на выполнение.
    }

///////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Async task class to get json by making HTTP call
     */
    private class getCompanies extends AsyncTask<Void, Void, List<Company>> {

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
//      Геренрируем событие завершения считывания данных.
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
                // считываем пока не будет достигнут конец файла.
                for (int indCompany = 0; indCompany < jsonRoot.length(); indCompany++) {

                    JSONObject jcmp = jsonRoot.getJSONObject("company");

                    JSONArray jsonArray = jcmp.getJSONArray("competences");

                    String[] competences = new String[jsonArray.length()];

                    for (int i = 0; i < jsonArray.length(); i++) {
                        competences[i] = jsonArray.getString(i);
                    }

                    JSONArray jsonArray1 = jcmp.getJSONArray("employees");
                    Employee[] employees = new Employee[jsonArray1.length()];

                    for (int indEmployee = 0; indEmployee < jsonArray1.length(); indEmployee++) {
                        Employee emp = new Employee();

                        JSONObject jEmployee = jsonArray1.getJSONObject(indEmployee);
                        String str = jEmployee.getString("name");
                        emp.setName(str);

                        emp.setPhone_number(jEmployee.getString("phone_number"));
                        JSONArray jSkills = jEmployee.getJSONArray("skills");

                        String[] skills = new String[jSkills.length()];
                        for (int indSkille = 0; indSkille < jSkills.length(); indSkille++) {
                            skills[indSkille] = jSkills.getString(indSkille);
                        }
                        emp.setSkills(skills);

                        employees[indEmployee] = emp;
                    }

                    // Упорядочивание сотрудников компанни по алфавиту.
                    // (Сортировка по методу пузырька)
                    for (int indExtern = employees.length - 1; indExtern > 0; indExtern--) {
                        for (int indInner = 0; indInner < indExtern; indInner++) {
                            if (employees[indInner].getName().compareTo(employees[indInner + 1].getName()) > 0) {
                                Employee emp = employees[indInner];
                                employees[indInner] = employees[indInner + 1];
                                employees[indInner + 1] = emp;
                            }
                        }
                    }
                    Company cmp = new Company(jcmp.getString("name"), jcmp.getString("age"), competences,employees);
                    comps.add(cmp);
                }
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
    }
}

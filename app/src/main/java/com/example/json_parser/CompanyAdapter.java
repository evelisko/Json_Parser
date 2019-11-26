package com.example.json_parser;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CompanyAdapter extends ArrayAdapter<Company> {

    private LayoutInflater inflater;
    private int layout;
    private List<Company> companies;


    public CompanyAdapter(Context context, int resource, List<Company> companies) {
        super(context, resource, companies);
        this.companies = companies;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Company company = companies.get(position);
        viewHolder.companyNameView.setText(company.getName());
        viewHolder.companyAgeView.setText("Возраст компанни: " + company.getAge());
        viewHolder.companyCompetencesView.setText("Область деятельности: \n    " + company.getCompetencesString());

        viewHolder.employeeView.setText(company.getEmployeeString());
        return convertView;
    }

    private class ViewHolder {
        final TextView companyNameView, companyAgeView, companyCompetencesView;
        final TextView employeeView;

        ViewHolder(View view) {
            companyNameView = (TextView) view.findViewById(R.id.company_name);
            companyAgeView = (TextView) view.findViewById(R.id.company_age);
            companyCompetencesView = (TextView) view.findViewById(R.id.company_competences);
            employeeView = (TextView) view.findViewById(R.id.employees);
        }
    }

}

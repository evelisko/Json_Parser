package com.example.json_parser;

import java.util.List;

// TODO: Настроить стили отображения для разных строк.

public class Company {

    private String name;
    private String[] competences;
    private String age;
//  private Employee employees;  // Здесь нужно организовать список.
    private Employee[] employees;

    public Company(String name, String age, String[] competences){

        this.name=name;
        this.age= age;
        this.competences = competences;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCompetences() {
        return this.competences;
    }

    public String getCompetencesString() {
       String CompetencesStr ="";
       for (int index=0; index< competences.length;index++)
       {
         CompetencesStr+= competences[index];
           if (index < competences.length-1)  CompetencesStr += ", ";
       }
        return CompetencesStr;
    }

    public void setCompetences(String[] competences) {
        this.competences = competences;
    }

    public String getAge() {
        return this.age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Employee[] getEmployees() {
        return employees;
    }

    public String[] getEmployeeStringArray() {

        String[] employeesStr = new String[employees.length];

      for (int index = 0; index< employees.length;index++)
        {
            employeesStr[index] = this.employees[index].getEmployeeString();
          //  if (index < employees.length-1) {
          //      employeesStr[index] += ", ";
        }
       return employeesStr;
    }

    public String getEmployeeString() {

        String employeesStr = new String();
        String[] employeesArray = getEmployeeStringArray();
        for (int index = 0; index < employeesArray.length;index++)
        {
            employeesStr +=  employeesArray[index]+"\n\n";
        }
        return employeesStr;
    }

    void setEmployee(Employee[] employee) {
        this.employees = employee;
    }



}


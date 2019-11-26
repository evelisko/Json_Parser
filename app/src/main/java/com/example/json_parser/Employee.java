package com.example.json_parser;

public final class Employee {


    private String name;
    private String phone_number;
    private String[] skills;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String[] getSkills() {
        return skills;
    }

    public void setSkills(String[] skills) {
        this.skills = skills;
    }

    public String getEmployeeString() {
        String employeeStr;
        employeeStr = "   Имя:  " + name + "\n";
        employeeStr += "   Номер телефона:  " + phone_number + "\n";
        employeeStr += "   Компетенции: [ ";

        for (int index = 0; index < skills.length; index++) {
            employeeStr += skills[index];
            if (index < skills.length - 1) employeeStr += ", ";
        }
        employeeStr += " ]";
        return employeeStr;
    }

}

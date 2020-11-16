package org.hbrs.ia.contract.entities;

public class SalesMan {

    private int employeeID;
    private String name, department, jobTitle;

    public SalesMan(String name, String department, String jobTitle) {
        this.name = name;
        this.department = department;
        this.jobTitle = jobTitle;
        this.employeeID = -1;
    }

    public SalesMan(String name, String department, String jobTitle, int employeeID) {
        this.name = name;
        this.department = department;
        this.jobTitle = jobTitle;
        this.employeeID = employeeID;
    }

    public String getName() {
        return name;
    }

    public String getDepartment() {
        return department;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public Integer getEmployeeID() { return employeeID; }

}

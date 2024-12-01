package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    private Employee employee;
    private int salary;
    private Date effectiveDate;

    public Compensation(Employee employee, int salary, Date effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Date getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    @Override
    public String toString() {
        return "Compensation [employee=" + employee + ", salary=" + salary + ", effectiveDate=" + effectiveDate + ", getEmployee()="
                + getEmployee() + ", getSalary()=" + getSalary() + ", getEffectiveDate()=" + getEffectiveDate() + ", getClass()=" + getClass()
                + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Compensation that = (Compensation) o;
        return salary == that.salary && effectiveDate.equals(that.effectiveDate) && employee.equals(that.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, salary, effectiveDate);
    }
}
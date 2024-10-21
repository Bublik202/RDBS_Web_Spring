package com.epam.rd.autocode.dao;

public class DaoFactory {
    public EmployeeDao employeeDAO() {
        return new EmployeeDAOImpl();
    }

    public DepartmentDao departmentDAO() {
        return new DepartmentDAOImpl();
    }
}

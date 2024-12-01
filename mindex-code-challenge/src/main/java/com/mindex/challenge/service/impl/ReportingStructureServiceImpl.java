package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.service.ReportingStructureService;
import com.mindex.challenge.exception.EmployeeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ReportingStructureServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    @Transactional
    public ReportingStructure getReportingStructure(String employeeId) {
        LOG.debug("Getting reporting structure for employeeId [{}]", employeeId);

        validateEmployeeId(employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new EmployeeNotFoundException("Employee not found for employeeId: " + employeeId);
        }

        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setEmployee(employee);

        Set<String> directReports = new HashSet<>();
        populateDirectReports(employee, directReports);
        reportingStructure.setNumberOfReports(directReports.size());

        return reportingStructure;
    }

    private void validateEmployeeId(String employeeId) {
        if (employeeId == null || employeeId.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid employeeId");
        }
    }

    private void populateDirectReports(Employee employee, Set<String> directReports) {
        if (employee.getDirectReports() != null) {
            for (Employee directReport : employee.getDirectReports()) {
                directReports.add(directReport.getEmployeeId());
                populateDirectReports(directReport, directReports);
            }
        }
    }
}
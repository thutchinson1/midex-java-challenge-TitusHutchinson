package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.exception.EmployeeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportingStructureServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private ReportingStructureServiceImpl reportingStructureService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReportingStructure_Success() {
        String employeeId = "123456";
        Employee employee = new Employee();
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(employee);

        ReportingStructure reportingStructure = reportingStructureService.getReportingStructure(employeeId);

        assertNotNull(reportingStructure);
        assertEquals(employee, reportingStructure.getEmployee());
        verify(employeeRepository, times(1)).findByEmployeeId(employeeId);
    }

    @Test
    void testGetReportingStructure_EmployeeNotFound() {
        String employeeId = "123456";
        when(employeeRepository.findByEmployeeId(employeeId)).thenReturn(null);

        assertThrows(EmployeeNotFoundException.class, () -> reportingStructureService.getReportingStructure(employeeId));
    }

    @Test
    void testGetReportingStructure_InvalidEmployeeId() {
        String employeeId = "";

        assertThrows(IllegalArgumentException.class, () -> reportingStructureService.getReportingStructure(employeeId));
    }
}
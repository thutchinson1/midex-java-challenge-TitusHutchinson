package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.exception.CompensationNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CompensationServiceImplTest {

    @Mock
    private CompensationRepository compensationRepository;

    @InjectMocks
    private CompensationServiceImpl compensationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() {
        Compensation compensation = new Compensation();
        compensation.setEmployee(new Employee());
        compensation.setSalary(100000);
        compensation.setEffectiveDate(new Date());

        when(compensationRepository.insert(compensation)).thenReturn(compensation);

        Compensation createdCompensation = compensationService.create(compensation);

        assertNotNull(createdCompensation);
        verify(compensationRepository, times(1)).insert(compensation);
    }

    @Test
    void testCreate_ValidationFailure() {
        Compensation compensation = new Compensation();

        assertThrows(IllegalArgumentException.class, () -> compensationService.create(compensation));
    }

    @Test
    void testRead_Success() {
        String employeeId = "123456";
        Compensation compensation = new Compensation();
        when(compensationRepository.findByEmployeeEmployeeId(employeeId)).thenReturn(compensation);

        Compensation readCompensation = compensationService.read(employeeId);

        assertNotNull(readCompensation);
        verify(compensationRepository, times(1)).findByEmployeeEmployeeId(employeeId);
    }

    @Test
    void testRead_NotFound() {
        String employeeId = "123456";
        when(compensationRepository.findByEmployeeEmployeeId(employeeId)).thenReturn(null);

        assertThrows(CompensationNotFoundException.class, () -> compensationService.read(employeeId));
    }

    @Test
    void testUpdate_Success() {
        String employeeId = "123456";
        Compensation existingCompensation = new Compensation();
        Compensation newCompensation = new Compensation();
        newCompensation.setEmployee(new Employee());
        newCompensation.setSalary(120000);
        newCompensation.setEffectiveDate(new Date());

        when(compensationRepository.findByEmployeeEmployeeId(employeeId)).thenReturn(existingCompensation);
        when(compensationRepository.save(newCompensation)).thenReturn(newCompensation);

        Compensation updatedCompensation = compensationService.update(employeeId, newCompensation);

        assertNotNull(updatedCompensation);
        verify(compensationRepository, times(1)).save(newCompensation);
    }

    @Test
    void testUpdate_ValidationFailure() {
        String employeeId = "123456";
        Compensation newCompensation = new Compensation();

        assertThrows(IllegalArgumentException.class, () -> compensationService.update(employeeId, newCompensation));
    }
}
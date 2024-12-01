package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.service.CompensationService;
import com.mindex.challenge.exception.CompensationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CompensationServiceImpl implements CompensationService {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);

    private final CompensationRepository compensationRepository;

    @Autowired
    public CompensationServiceImpl(CompensationRepository compensationRepository) {
        this.compensationRepository = compensationRepository;
    }

    @Override
    @Transactional
    public Compensation create(Compensation compensation) {
        LOG.debug("Creating compensation [{}]", compensation);

        validateCompensation(compensation);
        compensationRepository.insert(compensation);
        return compensation;
    }

    @Override
    public Compensation read(String id) {
        LOG.debug("Reading compensation for employeeId [{}]", id);

        Compensation compensation = compensationRepository.findByEmployeeEmployeeId(id);
        if (compensation == null) {
            throw new CompensationNotFoundException("Compensation not found for employeeId: " + id);
        }
        return compensation;
    }

    @Override
    @Transactional
    public Compensation update(String id, Compensation compensation) {
        LOG.debug("Updating compensation for employeeId [{}] with [{}]", id, compensation);

        validateCompensation(compensation);
        Compensation existingCompensation = read(id);
        compensation.setId(existingCompensation.getId());
        compensationRepository.save(compensation);
        return compensation;
    }

    private void validateCompensation(Compensation compensation) {
        if (compensation.getEmployee() == null || compensation.getSalary() <= 0 || compensation.getEffectiveDate() == null) {
            throw new IllegalArgumentException("Invalid compensation data");
        }
    }
}
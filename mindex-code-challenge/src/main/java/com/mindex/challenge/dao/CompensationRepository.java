package com.mindex.challenge.dao;

import com.mindex.challenge.data.Compensation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompensationRepository extends MongoRepository<Compensation, String> {
    Compensation findByEmployeeId(String employeeId);

    // These methods are not needed for the challenge, but it is good to have it for future use to find salary by different means
    Compensation findBySalary(int salary);

    Compensation findByEffectiveDate(String effectiveDate);

    Compensation findByEmployeeIdAndSalaryAndEffectiveDate(String employeeId, int salary, String effectiveDate);
}
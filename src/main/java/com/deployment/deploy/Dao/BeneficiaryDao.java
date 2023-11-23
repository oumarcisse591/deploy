package com.deployment.deploy.Dao;

import com.deployment.deploy.Entity.Beneficiary;

import java.util.List;

public interface BeneficiaryDao {

    List<Beneficiary> findAllBeneficiary();

    Beneficiary findFirstByOrderByUdDesc();
    Beneficiary findByBeneficiaryName(String userName);

    Beneficiary findBeneficiaryById(int theId);

    void deleteBeneficiaryById(int theId);

    Beneficiary saveBeneficiary(Beneficiary theBeneficiary);
}

package com.deployment.deploy.Dao;

import com.deployment.deploy.Entity.Beneficiary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class BeneficiaryDaoImpl implements BeneficiaryDao{

    EntityManager entityManager;

    @Autowired
    public BeneficiaryDaoImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<Beneficiary> findAllBeneficiary() {
        TypedQuery<Beneficiary> theQuery = entityManager.createQuery("SELECT c FROM Beneficiary c ORDER BY c.id DESC", Beneficiary.class);
        return theQuery.getResultList();
    }

    @Override
    public Beneficiary findFirstByOrderByUdDesc() {
        return null;
    }

    @Override
    public Beneficiary findByBeneficiaryName(String userName) {
        return null;
    }

    @Override
    public Beneficiary findBeneficiaryById(int theId) {
        Beneficiary theBeneficiary = entityManager.find(Beneficiary.class, theId);
        return theBeneficiary;
    }

    @Override
    public void deleteBeneficiaryById(int theId) {

    }

    @Transactional
    @Override
    public Beneficiary saveBeneficiary(Beneficiary theBeneficiary) {
        Beneficiary dbBeneficiary =  entityManager.merge(theBeneficiary);
        return dbBeneficiary;
    }
}

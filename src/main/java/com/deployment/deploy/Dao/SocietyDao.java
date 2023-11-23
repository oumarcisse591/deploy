package com.deployment.deploy.Dao;

import com.deployment.deploy.Entity.Society;

import java.util.List;

public interface SocietyDao {

    List<Society> findAllSociety();
    Society findBySocietyName(String userName);

    Society findSocietyById(int theId);

    void deleteSocietyById(int theId);

    Society saveSociety(Society theSociety);
}

package com.deployment.deploy.Dao;

import com.deployment.deploy.Entity.Role;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class RoleDaoImpl implements RoleDao{
    private EntityManager entityManager;

    @Autowired
    public RoleDaoImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public Role findRoleByName(String theRoleName) {
        TypedQuery<Role> theQuery = entityManager.createQuery("from Role where name=:roleName", Role.class);
        theQuery.setParameter("roleName", theRoleName);

        Role theRole = null;

        try{
            theRole = theQuery.getSingleResult();
        } catch (Exception e) {
            theRole = null;
        }
        return theRole;
    }
}

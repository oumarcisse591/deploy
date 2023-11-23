package com.deployment.deploy.Dao;

import com.deployment.deploy.Entity.Role;

public interface RoleDao {
    public Role findRoleByName(String theRoleName);
}

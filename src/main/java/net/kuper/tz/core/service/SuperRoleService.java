package net.kuper.tz.core.service;

public interface SuperRoleService {

    boolean isSuperRoleByUser(Object userId);

    boolean isSuperRole(Object roleId);

    Object superRoleId();
}

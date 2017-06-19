package com.auto.myte.mapper;

import java.util.List;

import com.auto.myte.entity.Permission;

public interface PermissionMapper {
	public List<Permission> findAll();
	public List<Permission> findByAdminUserId(int eid);
}
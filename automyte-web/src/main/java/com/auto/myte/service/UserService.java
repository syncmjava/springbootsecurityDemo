package com.auto.myte.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.auto.myte.common.filter.MyGrantedAuthority;
import com.auto.myte.entity.Permission;
import com.auto.myte.entity.User;
import com.auto.myte.mapper.PermissionMapper;
import com.auto.myte.mapper.UserMapper;

@Component
public class UserService implements UserDetailsService {
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private PermissionMapper permissionMapper;

	public UserDetails loadUserByUsername(String eName) {

		User user = userMapper.findByUserName(eName);
		if (user != null) {
			List<Permission> permissions = permissionMapper.findByAdminUserId(user.getEid());
			List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
			for (Permission permission : permissions) {
				if (permission != null && permission.getPname() != null) {

					GrantedAuthority grantedAuthority = new MyGrantedAuthority(permission.getUrl(), permission.getPname());
					// 1：此处将权限信息添加到 GrantedAuthority
					// 对象中，在后面进行全权限验证时会使用GrantedAuthority 对象。
					grantedAuthorities.add(grantedAuthority);
				}
			}
			return new org.springframework.security.core.userdetails.User(user.getEname(), user.getPassword(), grantedAuthorities);
		}
		else {
			 throw new UsernameNotFoundException("admin: " + eName + " do not exist!");
		}
	}

}

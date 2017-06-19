package com.auto.myte.common.filter;

import org.springframework.security.core.GrantedAuthority;

public class MyGrantedAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4281455902098710916L;
	private String url;
	private String method;
	private String authority;

	public String getPermissionUrl() {
		return url;
	}

	public void setPermissionUrl(String permissionUrl) {
		this.url = permissionUrl;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public MyGrantedAuthority(String url, String authority) {
		this.url = url;
		this.authority = authority;
	}

	@Override
	public String getAuthority() {
		return this.authority;
	}
}

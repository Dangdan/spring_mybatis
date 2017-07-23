package com.ddd.mybatis.pojo;

import java.util.List;

/**
 * 根据特殊需求自定义的包装类
 * @author Dan
 *
 */
public class UserQueryVo {
	//传入多个id
	private List<Integer> ids;
	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

	private UserCustom userCustom;

	public UserCustom getUserCustom() {
		return userCustom;
	}

	public void setUserCustom(UserCustom userCustom) {
		this.userCustom = userCustom;
	}
	
}

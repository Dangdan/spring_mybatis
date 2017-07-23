package com.ddd.mybatis.dao;

import com.ddd.mybatis.pojo.User;

/**
 * 用户的dao接口
 * @author Dan
 *
 */
public interface UserDao {
	//根据id查询一个user
	public User findUserById(int id);
	//添加用户
	public void insertUser(User u);
	//删除用户
	public void deleteUserById(int id);
	//更新用户
	public void updateUser(User u);

}

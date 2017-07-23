package com.ddd.mybatis.mapper;

import java.util.List;

import com.ddd.mybatis.pojo.User;
import com.ddd.mybatis.pojo.UserCustom;
import com.ddd.mybatis.pojo.UserQueryVo;

/**
 * 使用mapper接口相当于dao接口
 * 
 * @author Dan
 * mapper.xml的namespace需要和mapper.java的接口地址一致
 *mapper.java中的方法的方法名要和mapper.xml中的statement的id一致
 *输入参数要和mapper.xml中的statement的parameterType指定类型一致
 *方法的返回值要和mapper.xml中的statement的resultType指定类型一致
 */
public interface UserMapper {
	//综合查询用户信息
	public List<UserCustom> findUserList(UserQueryVo userQueryVo);
	//综合查询用户信息总数
	public int findUserCount(UserQueryVo userQueryVo);

	// 根据id查询一个user
	public User findUserById(int id);
	//返回值为ResultMap的查询
	public User findUserByIdResultMap(int id);
	//根据名字查询user
	public List<User> findUserByName(String name);
	// 添加用户
	public void insertUser(User u);

	// 删除用户
	public void deleteUserById(int id);

	// 更新用户
	public void updateUser(User u);

}

package com.ddd.mybatis.daoImplTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Date;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.ddd.mybatis.dao.UserDao;
import com.ddd.mybatis.daoImpl.UserDaoImpl;
import com.ddd.mybatis.pojo.User;
/**
 * 接口测试
 * @author Dan
 *
 */
public class UserDaoImplTest {
	private SqlSessionFactory sqlSessionFactory;
	@Before
	public void setUp() throws IOException{
		String fileSource="SqlMapConfig.xml";
		//获取文件流
		InputStream inputStream=Resources.getResourceAsStream(fileSource);
		//根据加载的配置文件信息创建SqlSessionFactory
		sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testFindUserById() {
		UserDao userDao=new UserDaoImpl(sqlSessionFactory);
		User u=userDao.findUserById(13);
		System.out.println(u);
	}
	@Test
	public void testInsertUserd() {
		UserDao userDao=new UserDaoImpl(sqlSessionFactory);
		User u=new User();
		u.setUsername("杰克");
		u.setSex("男");
		u.setBirthday(new Date());
		u.setAddress("美国纽约");
		userDao.insertUser(u);
	}
	@Test
	public void testDeleteUserById() {
		UserDao userDao=new UserDaoImpl(sqlSessionFactory);
		userDao.deleteUserById(5);
	}
	@Test
	public void testUpdateUser() {
		UserDao userDao=new UserDaoImpl(sqlSessionFactory);
		User u=new User();
		u.setId(7);
		u.setUsername("小七");
		u.setBirthday(new Date());
		u.setSex("女");
		u.setAddress("英国伦敦");
		userDao.updateUser(u);
	}

}

package com.ddd.mybatis.mapperTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.ddd.mybatis.mapper.UserMapper;
import com.ddd.mybatis.pojo.User;
import com.ddd.mybatis.pojo.UserCustom;
import com.ddd.mybatis.pojo.UserQueryVo;

public class UserMapperTest {
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
		//由于之前是在UserDaoImpl中每次获取sqlSession，所以这里需要我们手动写
		SqlSession sqlSession=sqlSessionFactory.openSession();
		//mybatis自动生成mapper代理对象，代理对象内部调用selectOne或者selectList
		UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
		//调用userMapper的方法
		User u=userMapper.findUserById(1);
		System.out.println(u);
	}
	@Test
	public void testFindUserByIdResultMap() {
		//由于之前是在UserDaoImpl中每次获取sqlSession，所以这里需要我们手动写
		SqlSession sqlSession=sqlSessionFactory.openSession();
		//mybatis自动生成mapper代理对象，代理对象内部调用selectOne或者selectList
		UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
		//调用userMapper的方法
		User u=userMapper.findUserByIdResultMap(13);
		System.out.println(u);
	}
	@Test
	public void testFindUserByName() {
		//由于之前是在UserDaoImpl中每次获取sqlSession，所以这里需要我们手动写
		SqlSession sqlSession=sqlSessionFactory.openSession();
		//mybatis自动生成mapper代理对象，代理对象内部调用selectOne或者selectList
		UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
		//调用userMapper的方法,但这里如果不小心用一个单个user来接收，那会报错
		List<User> list=userMapper.findUserByName("小明");
		System.out.println(list);
	}
	
	@Test
	public void testFindUserList() {
		//由于之前是在UserDaoImpl中每次获取sqlSession，所以这里需要我们手动写
		SqlSession sqlSession=sqlSessionFactory.openSession();
		//mybatis自动生成mapper代理对象，代理对象内部调用selectOne或者selectList
		UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
		//调用userMapper的方法,但这里如果不小心用一个单个user来接收，那会报错
		UserQueryVo userQueryVo=new UserQueryVo();
		UserCustom userCustom=new UserCustom();
		List<Integer> ids=new ArrayList<>();
		ids.add(1);
		ids.add(3);
		userQueryVo.setIds(ids);
		userCustom.setSex("男");
		//userCustom.setUsername("张伟");
		userQueryVo.setUserCustom(userCustom);
		List<UserCustom> list=userMapper.findUserList(userQueryVo);
		System.out.println(list);
	}
	
	@Test
	public void testFindUserCount() {
		//由于之前是在UserDaoImpl中每次获取sqlSession，所以这里需要我们手动写
		SqlSession sqlSession=sqlSessionFactory.openSession();
		//mybatis自动生成mapper代理对象，代理对象内部调用selectOne或者selectList
		UserMapper userMapper=sqlSession.getMapper(UserMapper.class);
		//调用userMapper的方法,但这里如果不小心用一个单个user来接收，那会报错
		UserQueryVo userQueryVo=new UserQueryVo();
		UserCustom userCustom=new UserCustom();
		//userCustom.setSex("男");
		//userCustom.setUsername("张伟");
		userQueryVo.setUserCustom(userCustom);
		int count=userMapper.findUserCount(userQueryVo);
		System.out.println(count);
	}
}

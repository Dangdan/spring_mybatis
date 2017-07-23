package com.ddd.mybatis.mapperTest;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import com.ddd.mybatis.mapper.OrdersMapperCustom;
import com.ddd.mybatis.mapper.UserMapper;
import com.ddd.mybatis.pojo.Orders;
import com.ddd.mybatis.pojo.OrdersCustom;
import com.ddd.mybatis.pojo.User;

public class OrdersMapperCustomTest {
	private SqlSessionFactory sqlSessionFactory;

	@Before
	public void setUp() throws IOException {
		String fileSource = "SqlMapConfig.xml";
		// 获取文件流
		InputStream inputStream = Resources.getResourceAsStream(fileSource);
		// 根据加载的配置文件信息创建SqlSessionFactory
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}

	@Test
	public void testFindOrdersUser() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 获取代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		List<OrdersCustom> list = ordersMapperCustom.findOrdersUser();
		System.out.println(list);

		sqlSession.close();
	}

	@Test
	public void testFindOrdersUserResultMap() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 获取代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		List<Orders> list = ordersMapperCustom.findOrdersUserResultMap();
		for (Orders orders : list) {
			System.out.println(orders);
		}
		sqlSession.close();
	}

	@Test
	public void testFindOrdersAndOrderDetailResultMap() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 获取代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		List<Orders> list = ordersMapperCustom.findOrdersAndOrderDetailResultMap();
		System.out.println(list.size());
		for (Orders orders : list) {
			System.out.println(orders);
		}
		sqlSession.close();
	}

	@Test
	public void testFindUserAndItemsResultMap() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 获取代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		List<User> list = ordersMapperCustom.findUserAndItemsResultMap();
		System.out.println(list.size());
		for (User u : list) {
			System.out.println(u);
		}
		sqlSession.close();
	}

	@Test
	public void testFindOrdersUserLazyLoading() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 获取代理对象
		OrdersMapperCustom ordersMapperCustom = sqlSession.getMapper(OrdersMapperCustom.class);
		List<Orders> list = ordersMapperCustom.findOrdersUserLazyLoading();
		System.out.println(list.size());
		System.out.println("-----------11111111-----------");
		for (Orders order : list) {
			User user = order.getUser();
			System.out.println(user);
		}
		sqlSession.close();
	}

	// 测试一级缓存
	@Test
	public void testCache1() throws Exception {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		// 获取代理对象
		UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
		// 使用一个sqlSession
		// 第一次发起请求查询用户id为1的用户
		User u = userMapper.findUserById(1);
		System.out.println(u);

		// 如果sqlSession去执行commit操作（插入，更新，删除），清空sqlSession的一级缓存，这样是为了让缓存中存储的是最新的信息，避免脏读
		u.setUsername("林更新");
		u.setBirthday(new Date());
		u.setSex("男");

		userMapper.updateUser(u);
		sqlSession.commit();

		// 第二次发起请求查询用户id为1的用户
		User u2 = userMapper.findUserById(1);
		System.out.println(u2);
		sqlSession.close();
	}

	// 测试二级缓存
	@Test
	public void testCache2() throws Exception {
		SqlSession sqlSession1 = sqlSessionFactory.openSession();
		SqlSession sqlSession2 = sqlSessionFactory.openSession();
		SqlSession sqlSession3 = sqlSessionFactory.openSession();

		// 获取代理对象
		UserMapper userMapper1 = sqlSession1.getMapper(UserMapper.class);
		// 使用sqlSession1
		// 第一次发起请求查询用户id为1的用户
		User u = userMapper1.findUserById(1);
		System.out.println(u);

		// 这里执行关闭操作，将sqlSession1中的数据写到二级缓存
		sqlSession1.close();

		// 获取代理对象
		UserMapper userMapper3 = sqlSession3.getMapper(UserMapper.class);
		// 使用sqlSession3
		// 第2次发起请求查询用户id为1的用户
		User u3 = userMapper3.findUserById(1);
		u3.setUsername("王思聪");
		userMapper3.updateUser(u3);
		//提交清空二级缓存
		sqlSession3.commit();
		System.out.println(u3);

		// 这里执行关闭操作，将sqlSession3中的数据写到二级缓存
		sqlSession3.close();

		UserMapper userMapper2 = sqlSession2.getMapper(UserMapper.class);
		// 使用sqlSession2
		// 第3次发起请求查询用户id为1的用户，将是最新的数据
		User u2 = userMapper2.findUserById(1);
	
		System.out.println(u2);

		sqlSession2.close();

	}
}

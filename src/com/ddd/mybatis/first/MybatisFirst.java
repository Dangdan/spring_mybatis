package com.ddd.mybatis.first;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.ddd.mybatis.pojo.User;
/**
 * 测试mybatisdemo
 * @author Dan
 *
 */
public class MybatisFirst {
	//根据id查询用户信息得到一条结果记录
	@Test
	public void findUserByIdTest() throws IOException{
		//加载mybatis文件
		String fileSource="SqlMapConfig.xml";
		//获取文件流
		InputStream inputStream=Resources.getResourceAsStream(fileSource);
		//根据加载的配置文件信息创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		//得到sqlsession
		SqlSession sqlSession=sqlSessionFactory.openSession();
		
		//sqlSession操作数据库
		//第一个参数：映射文件中的statement的id：也就是namespace+"."+statement的id
		//第二个参数：指定和映射文件中所匹配的parameterType类型的参数
		//接受参数就是resultType中指定的对象类型
		User u=sqlSession.selectOne("test.findUserById", 1);
		System.out.println(u);
		sqlSession.close();
	}
	
	@Test
	public void findUserByNameTest() throws IOException{
		//加载mybatis文件
		String fileSource="SqlMapConfig.xml";
		//获取文件流
		InputStream inputStream=Resources.getResourceAsStream(fileSource);
		//根据加载的配置文件信息创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		//得到sqlsession
		SqlSession sqlSession=sqlSessionFactory.openSession();
		
		//sqlSession操作数据库
		//这里list中的要指定是user，和resultType指定的类型保持一致，selectList表示查询多条记录
		List<User> list=sqlSession.selectList("test.findUserByName", "小明");
		for (User u:list) {
			System.out.println(u);
		}
		sqlSession.close();
	}
	
	
	@Test
	public void insertUserTest() throws IOException{
		//加载mybatis文件
		String fileSource="SqlMapConfig.xml";
		//获取文件流
		InputStream inputStream=Resources.getResourceAsStream(fileSource);
		//根据加载的配置文件信息创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		//得到sqlsession
		SqlSession sqlSession=sqlSessionFactory.openSession();
		
		User u=new User();
		u.setUsername("大张伟");
		u.setSex("男");
		u.setBirthday(new Date());
		u.setAddress("广州佛山");
		sqlSession.insert("test.insertUser", u);
		//这里需要提交，在使用spring后就不用提交了
		sqlSession.commit();
		
		
		/**1.
		 * 获取用户主键(自增主键的返回)
		 * mysql自增主键，执行insert提交之前会自动生成一个自增主键
		 * 自增主键可以通过mysql函数获取到：SELECT LAST_INSERT_ID()，但是这里是0
		 * System.out.println(u.getId());结果是0
		 * 所以应该在insert之后执行,所以要在mapper中设置返回主键
		 * 2.
		 * 获取用户主键(非自增的主键的返回)
		 * mysql的uuid()函数生成主键，需要修改表中id字段类型为String，长度35
		 * 先通过uuid()查询到主键，将主键输入到sql中
		 * 执行uuid()在insert语句之前执行
		 */
		//关闭会话
		sqlSession.close();
	}
	//根据id删除一条用户记录
	@Test
	public void deleteUserByIdTest() throws IOException{
		//加载mybatis文件
		String fileSource="SqlMapConfig.xml";
		//获取文件流
		InputStream inputStream=Resources.getResourceAsStream(fileSource);
		//根据加载的配置文件信息创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		//得到sqlsession
		SqlSession sqlSession=sqlSessionFactory.openSession();
		
		//sqlSession操作数据库
		sqlSession.delete("test.deleteUserById", 2);
		sqlSession.commit();
		sqlSession.close();
	}
	//更新用户
	@Test
	public void updateUserTest() throws IOException{
		//加载mybatis文件
		String fileSource="SqlMapConfig.xml";
		//获取文件流
		InputStream inputStream=Resources.getResourceAsStream(fileSource);
		//根据加载的配置文件信息创建SqlSessionFactory
		SqlSessionFactory sqlSessionFactory=new SqlSessionFactoryBuilder().build(inputStream);
		//得到sqlsession
		SqlSession sqlSession=sqlSessionFactory.openSession();
		
		//sqlSession操作数据库
		User u=new User();
		u.setId(3);
		u.setUsername("huauhua");
		u.setSex("女");
		u.setBirthday(new Date());
		u.setAddress("河南郑州");
		sqlSession.update("test.updateUser", u);
		sqlSession.commit();
		sqlSession.close();
	}

}

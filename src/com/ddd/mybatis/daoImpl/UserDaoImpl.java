package com.ddd.mybatis.daoImpl;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import com.ddd.mybatis.dao.UserDao;
import com.ddd.mybatis.pojo.User;
/**
 * 接口实现
 * @author Dan
 *
 */
public class UserDaoImpl implements UserDao {
	private SqlSessionFactory sqlSessionFactory;

	public UserDaoImpl(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Override
	public User findUserById(int id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		User user = sqlSession.selectOne("test.findUserById", id);
		
		sqlSession.close();
		return user;
	}

	@Override
	public void insertUser(User u) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.insert("test.insertUser", u);
		sqlSession.commit();
		sqlSession.close();
	}

	@Override
	public void deleteUserById(int id) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.delete("test.deleteUserById", id);
		sqlSession.commit();
		sqlSession.close();

	}

	@Override
	public void updateUser(User u) {
		SqlSession sqlSession = sqlSessionFactory.openSession();
		sqlSession.update("test.updateUser", u);
		sqlSession.commit();
		sqlSession.close();
		
	}

}

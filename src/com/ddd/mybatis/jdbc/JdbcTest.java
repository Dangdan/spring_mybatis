package com.ddd.mybatis.jdbc;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Dan
 *原生态的jdbc
 */
public class JdbcTest {
	public static void main(String[] args) {
		//数据库连接
		Connection conn=null;
		//预编译(如果下次执行sql相同则不用编译，直接执行)的statement，使用它可以提高sql的执行效率
		PreparedStatement ps=null;
		//结果集
		ResultSet rs=null;
		try {
			//加载数据库驱动
			Class.forName("com.mysql.jdbc.Driver");
			//获取数据库连接
			conn=DriverManager.getConnection
					("jdbc:mysql://localhost:3306/mybatis?characterEncoding=utf-8","root", "1234");
			//定义sql语句
			String sql="select * from user where username=?";
			//获取预处理的statement
			ps=conn.prepareStatement(sql);
			//设置参数，(序号，值),其中序号从1开始
			ps.setString(1, "小明");
			//执行结果
			rs=ps.executeQuery();
			//遍历结果集打印输出
			while(rs.next()){
				System.out.println(rs.getString("id")+rs.getString("username"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{//释放资源
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}

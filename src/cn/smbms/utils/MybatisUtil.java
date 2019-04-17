package cn.smbms.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {
	private static SqlSessionFactory sqlSessionFactory;//会话创建的工厂(单例)
	static{
		try {
			//获取mybatis-config.xml文件的输入流
			InputStream is = Resources.getResourceAsStream("mybatis-config.xml");
			//创建SqlSessionFactory 对象，此对象可以完成对配置文件的读取
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 创建SqlSession 对象
	 * @return
	 */
	public static SqlSession createSqlSession(){
		return sqlSessionFactory.openSession(false);//false关闭事务自动提交即开启事务控制（默认为true）
	}
	/**
	 * 关闭SqlSession
	 * @param sqlSession
	 */
	public static void closeSqlSession(SqlSession sqlSession){
		if(null != sqlSession){
			sqlSession.close();
		}
	}
}

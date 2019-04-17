package cn.smbms.service.user;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.smbms.dao.user.UserDao;
import cn.smbms.pojo.User;
import cn.smbms.utils.MybatisUtil;

/**
 * service层捕获异常，进行事务处理
 * 事务处理：调用不同dao的多个方法，必须使用同一个connection（connection作为参数传递）
 * 事务完成之后，需要在service层进行connection的关闭，在dao层关闭（PreparedStatement和ResultSet对象）
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService{
	
	@Override
	public boolean add(User user) {
		boolean flag = false;
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		try {
			int updateRows = sqlSession.getMapper(UserDao.class).add(user);
			sqlSession.commit();
			if(updateRows > 0){
				flag = true;
				System.out.println("add success!");
			}else{
				System.out.println("add failed!");
			}
			
		} catch (Exception e) { 
			e.printStackTrace();
			System.out.println("rollback==================");
			sqlSession.rollback();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}
	@Override
	public User login(String userCode, String userPassword) {
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		User user = null;
		try {
			user = sqlSession.getMapper(UserDao.class).getLoginUser(userCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//匹配密码
		if(null != user){
			if(!user.getUserPassword().equals(userPassword))
				user = null;
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return user;
	}
	@Override
	public List<User> getUserList(String queryUserName,int queryUserRole,int currentPageNo, int pageSize) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		List<User> userList = null;
		System.out.println("queryUserName ---- > " + queryUserName);
		System.out.println("queryUserRole ---- > " + queryUserRole);
		System.out.println("currentPageNo ---- > " + currentPageNo);
		System.out.println("pageSize ---- > " + pageSize);
		try {
			currentPageNo=(currentPageNo-1)*pageSize;
			userList = sqlSession.getMapper(UserDao.class).getUserList(queryUserName,queryUserRole,currentPageNo,pageSize);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return userList;
	}
	@Override
	public User selectUserCodeExist(String userCode) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		User user = null;
		try {
			user = sqlSession.getMapper(UserDao.class).getLoginUser(userCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return user;
	}
	@Override
	public boolean deleteUserById(Integer delId) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		boolean flag = false;
		try {
			if(sqlSession.getMapper(UserDao.class).deleteUserById(delId) > 0)
				flag = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}
	@Override
	public User getUserById(String id) {
		// TODO Auto-generated method stub
		User user = null;
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		try{
			user = sqlSession.getMapper(UserDao.class).getUserById(id);
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			user = null;
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return user;
	}
	@Override
	public boolean modify(User user) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		boolean flag = false;
		try {
			if(sqlSession.getMapper(UserDao.class).modify(user) > 0)
				flag = true;
			sqlSession.commit();
		} catch (Exception e) { 
			e.printStackTrace();
			sqlSession.rollback();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}
	@Override
	public boolean updatePwd(int id, String pwd) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		try{
			if(sqlSession.getMapper(UserDao.class).updatePwd(id,pwd) > 0)
				flag = true;
			sqlSession.commit();
		}catch (Exception e) {
			e.printStackTrace();
			sqlSession.rollback();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}
	@Override
	public int getUserCount(String queryUserName, int queryUserRole) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		int count = 0;
		System.out.println("queryUserName ---- > " + queryUserName);
		System.out.println("queryUserRole ---- > " + queryUserRole);
		try {
			count = sqlSession.getMapper(UserDao.class).getUserCount(queryUserName,queryUserRole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return count;
	}
	
}

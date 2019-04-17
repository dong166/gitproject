package cn.smbms.service.role;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.smbms.dao.role.RoleDao;
import cn.smbms.pojo.Role;
import cn.smbms.utils.MybatisUtil;

public class RoleServiceImpl implements RoleService{
	
	/*private RoleDao roleDao;
	
	public RoleServiceImpl(){
		roleDao = new RoleDaoImpl();
	}*/
	
	/*@Override
	public List<Role> getRoleList() {
		// TODO Auto-generated method stub
		Connection connection = null;
		List<Role> roleList = null;
		try {
			connection = BaseDao.getConnection();
			roleList = roleDao.getRoleList(connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			BaseDao.closeResource(connection, null, null);
		}
		return roleList;
	}*/
	@Override
	public List<Role> getRoleList() {
		//1. 获取SqlSession对象
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		//2.调用mapper接口方法
		List<Role> roleList = sqlSession.getMapper(RoleDao.class).getRoleList();
		//3.关闭SqlSession对象
		MybatisUtil.closeSqlSession(sqlSession);
		return roleList;
	}
}

package cn.smbms.dao.role;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import cn.smbms.pojo.Role;

public interface RoleDao {
	/**
	 * 获取角色列表
	 * @return
	 * @throws Exception
	 */
	@Select("select * from smbms_role")
	public List<Role> getRoleList();
}

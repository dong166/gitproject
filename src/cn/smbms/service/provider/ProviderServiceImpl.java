package cn.smbms.service.provider;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import cn.smbms.dao.bill.BillDao;
import cn.smbms.dao.provider.ProviderDao;
import cn.smbms.pojo.Provider;
import cn.smbms.utils.MybatisUtil;

public class ProviderServiceImpl implements ProviderService {
	
	@Override
	public boolean add(Provider provider) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		try {
			if(sqlSession.getMapper(ProviderDao.class).add(provider) > 0)
				flag = true;
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("rollback==================");
			sqlSession.rollback();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}

	@Override
	public List<Provider> getProviderList(String proName,String proCode) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		List<Provider> providerList = null;
		System.out.println("query proName ---- > " + proName);
		System.out.println("query proCode ---- > " + proCode);
		try {
			providerList = sqlSession.getMapper(ProviderDao.class).getProviderList(proName,proCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return providerList;
	}

	/**
	 * 业务：根据ID删除供应商表的数据之前，需要先去订单表里进行查询操作
	 * 若订单表中无该供应商的订单数据，则可以删除
	 * 若有该供应商的订单数据，则不可以删除
	 * 返回值billCount
	 * 1> billCount == 0  删除---1 成功 （0） 2 不成功 （-1）
	 * 2> billCount > 0    不能删除 查询成功（0）查询不成功（-1）
	 * 
	 * ---判断
	 * 如果billCount = -1 失败
	 * 若billCount >= 0 成功
	 */
	@Override
	public int deleteProviderById(String delId) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		int billCount = -1;
		try {
			billCount = sqlSession.getMapper(BillDao.class).getBillCountByProviderId(delId);
			if(billCount == 0){
				sqlSession.getMapper(ProviderDao.class).deleteProviderById(delId);
			}
			sqlSession.commit();
		} catch (Exception e) {
			e.printStackTrace();
			billCount = -1;
			sqlSession.rollback();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return billCount;
	}

	@Override
	public Provider getProviderById(String id) {
		// TODO Auto-generated method stub
		Provider provider = null;
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		try{
			provider = sqlSession.getMapper(ProviderDao.class).getProviderById(id);
		}catch (Exception e) {
			e.printStackTrace();
			provider = null;
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return provider;
	}

	@Override
	public boolean modify(Provider provider) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		boolean flag = false;
		try {
			if(sqlSession.getMapper(ProviderDao.class).modify(provider) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}

}

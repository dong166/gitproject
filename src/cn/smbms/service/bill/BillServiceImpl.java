package cn.smbms.service.bill;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import cn.smbms.dao.bill.BillDao;
import cn.smbms.pojo.Bill;
import cn.smbms.utils.MybatisUtil;

public class BillServiceImpl implements BillService {
	
	@Override
	public boolean add(Bill bill) {
		// TODO Auto-generated method stub
		boolean flag = false;
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		try {
			if(sqlSession.getMapper(BillDao.class).add(bill) > 0){
				flag = true;
				sqlSession.commit();
			}else{
				System.out.println("rollback==================");
				sqlSession.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}

	@Override
	public List<Bill> getBillList(Bill bill) {
		// TODO Auto-generated method stub
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		List<Bill> billList = null;
		System.out.println("query productName ---- > " + bill.getProductName());
		System.out.println("query providerId ---- > " + bill.getProviderId());
		System.out.println("query isPayment ---- > " + bill.getIsPayment());
		try {
			billList = sqlSession.getMapper(BillDao.class).getBillList(bill);
		} catch (Exception e) {
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return billList;
	}

	@Override
	public boolean deleteBillById(String delId) {
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		boolean flag = false;
		try {
			if(sqlSession.getMapper(BillDao.class).deleteBillById(delId) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}

	@Override
	public Bill getBillById(String id) {
		Bill bill = null;
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		try{
			bill = sqlSession.getMapper(BillDao.class).getBillById(id);
		}catch (Exception e) {
			e.printStackTrace();
			bill = null;
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return bill;
	}

	@Override
	public boolean modify(Bill bill) {
		SqlSession sqlSession = MybatisUtil.createSqlSession();
		boolean flag = false;
		BillDao billDao = sqlSession.getMapper(BillDao.class);
		try {
			if(billDao.modify(bill) > 0)
				flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		MybatisUtil.closeSqlSession(sqlSession);
		return flag;
	}
}

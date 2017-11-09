
package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.*;

public class PurchaseDAO {

	// ����Ʈ ������
	public PurchaseDAO() {

	}

	//
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction " + " (tran_no, prod_no, buyer_id, payment_option, "
				+ " receiver_name, receiver_phone, dlvy_addr," 
				+ " dlvy_request, dlvy_date, order_date) "
				+ "VALUES(seq_transaction_tran_no.nextval,?," 
				+ " ?,?,?,?,?,?,TO_DATE(?,'YYYY/MM/DD'), sysdate)";

		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("PurchaseDAO ::" + sql);

		// tran_no �������� �޾ƿ�
		stmt.setString(2, purchaseVO.getBuyer().getUserId());//
		System.out.println("�� ���̵�" + purchaseVO.getBuyer().getUserId());
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		System.out.println("�� �ѹ�" + purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getDivyDate());
		// �ֹ��� sysdate ���
		stmt.executeQuery();

		con.close();

	}// end of addPurchase

	// ���Ż����� ��û
	public PurchaseVO getPurcahse(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT prod_no, buyer_id, payment_option, " + 
		" receiver_name, receiver_phone, dlvy_addr, "
				+ " dlvy_request, dlvy_date, order_date  where prod_no=? ";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();

		ProductService productService = new ProductServiceImpl();
		ProductVO productVO = productService.getProduct(prodNo);

		UserService userservice = new UserServiceImpl();
		

		PurchaseVO purchaseVO = new PurchaseVO();

		while (rs.next()) {
			
			purchaseVO.setPurchaseProd(productVO);
			purchaseVO.setBuyer(userservice.getUser(rs.getString("buyer_id")));
			purchaseVO.setPaymentOption(rs.getString("paymentOption"));
			purchaseVO.setReceiverName(rs.getString("receiverName"));
			purchaseVO.setReceiverPhone(rs.getString("receiverPhone"));
			purchaseVO.setDivyAddr(rs.getString("receiverAddr"));
			purchaseVO.setDivyRequest(rs.getString("receiverRequest"));
			purchaseVO.setDivyDate(rs.getString("receiverDate"));

			System.out.println("PurchaseDAO getPurchase :" + purchaseVO);

		}
		con.close();

		return purchaseVO;
	}// end of getPurchase

	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		
		
		String sql="select tran_no, receiver_name, receiver_phone, tran_status_code from transaction ";
		
		//��ǰ�˻�
		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE Prod_No LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE receiver_name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if(search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")){
				sql +=" WHERE tran_no LIKE '%"+search.getSearchKeyword() +"%'";
			}
		}
		sql += " ORDER BY prod_NO";
		System.out.println("PurchaseDAO :: SQL  : "+sql);
		
		//��ü �Խù��� 
		int totalCount= this.getTotalCount(sql);
		
		//���� �������� �Խù� �޵��� ���� �ٽ�
		sql=makeCurrentPageSql(sql,search);
		PreparedStatement stmt=con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println("���� ��ġ==> "+search);
		
		
		List<PurchaseVO> list=new ArrayList<PurchaseVO>();
		
		ProductService productservice = new ProductServiceImpl();
		UserService userservice= new UserServiceImpl();
		
		
		while(rs.next()) {
			PurchaseVO purchaseVO= new PurchaseVO();
			
			purchaseVO.setPurchaseProd(productservice.getProduct(rs.getInt("prodNo")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("buyer_id")));
			purchaseVO.setReceiverName(rs.getString("receiverName"));
			purchaseVO.setReceiverPhone(rs.getString("receiverPhone"));
			list.add(purchaseVO);
			
		}
		map.put("list", list);
		map.put("totalCount", totalCount);
		
		
		rs.close();
		stmt.close();
		con.close();
		
		return map;
	}// end of getPurchaseList

	private String makeCurrentPageSql(String sql, Search search) throws Exception{
		
		sql = "SELECT * " + " FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ " WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("purchaseDAO :: make SQL :: " + sql);

		return sql;
	}

	private int getTotalCount(String sql) throws Exception{
		//total ī��Ʈ ���� ���� ����
				sql ="SELECT COUNT(*) "+"FROM ( " + sql + " ) countable ";
				
				
				Connection con =DBUtil.getConnection();
				PreparedStatement Stmt = con.prepareStatement(sql);
				ResultSet rs = Stmt.executeQuery();

				int totalCount = 0;
				if (rs.next()) {
					totalCount = rs.getInt(1);
				}

				Stmt.close();
				con.close();
				rs.close();
				
				return totalCount;
	}

}// end of class

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
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.Product;

public class PurchaseDAO {

	// 디폴트 생성자
	public PurchaseDAO() {

	}

	//
	public void insertPurchase(Purchase purchase) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction " + " (tran_no, prod_no, buyer_id, payment_option, "
				+ " receiver_name, receiver_phone, dlvy_addr," 
				+ " dlvy_request, dlvy_date, order_date) "
				+ "VALUES(seq_transaction_tran_no.nextval,?," 
				+ " ?,?,?,?,?,?,TO_DATE(?,'YYYY/MM/DD'), sysdate)";

		PreparedStatement stmt = con.prepareStatement(sql);
		//System.out.println("PurchaseDAO ::" + sql);

		// tran_no 시퀀스로 받아옴
		stmt.setString(2, purchase.getBuyer().getUserId());//
		//System.out.println("난 아이디" + purchaseVO.getBuyer().getUserId());
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		//System.out.println("난 넘버" + purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getDivyDate());
		// 주문일 sysdate 사용
		stmt.executeQuery();

		con.close();

	}// end of addPurchase
	
	// 구매상세정보 요청
	public Purchase findPurcahse(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT TRAN_NO, PROD_NO, BUYER_ID, PAYMENT_OPTION, " + 
		" RECEIVER_NAME, RECEIVER_PHONE, DLVY_ADDR, "
				+ " DLVY_REQUEST, DLVY_DATE, ORDER_DATE "
				+ " FROM transaction WHERE PROD_NO=? ";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductService productService = new ProductServiceImpl();
		Product product = productService.getProduct(prodNo);

		UserService userservice = new UserServiceImpl();
		
		
		Purchase purchase= new Purchase();

		while (rs.next()) {
			purchase.setTranNo(rs.getInt("tran_no"));
			purchase.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));

			System.out.println("PurchaseDAO getPurchase :" + purchase);

		}
		con.close();

		return purchase;
	}// end of getPurchase
	
	public Purchase findPurcahse2(int tranNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT TRAN_NO, PROD_NO, BUYER_ID, PAYMENT_OPTION, " + 
		" RECEIVER_NAME, RECEIVER_PHONE, DLVY_ADDR, "
				+ " DLVY_REQUEST, DLVY_DATE, ORDER_DATE "
				+ " FROM transaction WHERE TRAN_NO=? ";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductService productService = new ProductServiceImpl();
		

		UserService userservice = new UserServiceImpl();
		
		
		Purchase purchase = new Purchase();

		while (rs.next()) {
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			purchase.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
			purchase.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchase.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchase.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchase.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchase.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchase.setDivyDate(rs.getString("DLVY_DATE"));

			System.out.println("PurchaseDAO getPurchase :" + purchase);

		}
		con.close();

		return purchase;
	}// end of getPurchase
	
	
	//구매목록 조회 No, 회원ID, 회원명, 전화번호, 배송현황, 정보수정
	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		
		
		String sql="select  tran_no, prod_no, buyer_id, receiver_name, receiver_phone  from transaction ";
		
		
		//전체 게시물수 
		int totalCount= this.getTotalCount(sql);
		
		//현재 페이지만 게시물 받도록 쿼리 다시
		sql=makeCurrentPageSql(sql,search);
		PreparedStatement stmt=con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println("나는 서치==> "+search);
		
		
		List<Purchase> list=new ArrayList<Purchase>();
		
		ProductService productservice = new ProductServiceImpl();
		UserService userservice= new UserServiceImpl();
		
		
		while(rs.next()) {
			Purchase purchase= new Purchase();
			
			purchase.setTranNo(rs.getInt("tran_No"));
			purchase.setPurchaseProd(productservice.getProduct(rs.getInt("prod_No")));
			purchase.setBuyer(userservice.getUser(rs.getString("buyer_Id")));
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			list.add(purchase);
			//System.out.println("purchaseVO==>"+purchaseVO);
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
		//total 카운트 개수 새는 쿼리
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
	
	
	public void updatePurchase(Purchase purchase) throws Exception{
		
		Connection con=DBUtil.getConnection();
		
		System.out.println("여기는 purchase업데이트 메소드");
		
		String sql="update transaction set PAYMENT_OPTION=?, RECEIVER_NAME=?,"
				+ " RECEIVER_PHONE=?, DLVY_ADDR=?, DLVY_REQUEST=?, DLVY_DATE=? WHERE tran_no=?";
		
		System.out.println("PurchaseDAO Update ==>"+purchase);
		PreparedStatement stmt= con.prepareStatement(sql);
		
		
		//stmt.setString(1, purchaseVO.getBuyer().getUserId());
		stmt.setString(2, purchase.getPaymentOption());
		stmt.setString(3, purchase.getReceiverName());
		stmt.setString(4, purchase.getReceiverPhone());
		stmt.setString(5, purchase.getDivyAddr());
		stmt.setString(6, purchase.getDivyRequest());
		stmt.setString(7, purchase.getDivyDate());
		stmt.executeUpdate();
		
		
		con.close();
		
		//return purchaseVO;
	}
	public void updateTranCode(Purchase purchase)throws Exception{

		Connection con=DBUtil.getConnection();
		System.out.println("TranCode 메소드");
		
		String sql="update transaction set tran_status_code=?"
				+ "WHERE prod_no=?";
				
		
		System.out.println("PurchaseDAO TranCode ==>"+purchase);
		PreparedStatement stmt= con.prepareStatement(sql);
		
		stmt.setString(1, purchase.getTranCode());
		stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
		
		stmt.executeUpdate();
		
		con.close();
		
	}
}// end of class
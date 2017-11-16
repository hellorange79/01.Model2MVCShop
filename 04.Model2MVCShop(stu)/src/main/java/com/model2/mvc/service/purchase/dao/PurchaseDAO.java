
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
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.domain.*;

public class PurchaseDAO {

	// 디폴트 생성자
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
		//System.out.println("PurchaseDAO ::" + sql);

		// tran_no 시퀀스로 받아옴
		stmt.setString(2, purchaseVO.getBuyer().getUserId());//
		//System.out.println("난 아이디" + purchaseVO.getBuyer().getUserId());
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		//System.out.println("난 넘버" + purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getDivyDate());
		// 주문일 sysdate 사용
		stmt.executeQuery();

		con.close();

	}// end of addPurchase
	
	// 구매상세정보 요청
	public PurchaseVO findPurcahse(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT TRAN_NO, PROD_NO, BUYER_ID, PAYMENT_OPTION, " + 
		" RECEIVER_NAME, RECEIVER_PHONE, DLVY_ADDR, "
				+ " DLVY_REQUEST, DLVY_DATE, ORDER_DATE "
				+ " FROM transaction WHERE PROD_NO=? ";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductService productService = new ProductServiceImpl();
		Product productVO = productService.getProduct(prodNo);

		UserService userservice = new UserServiceImpl();
		
		
		PurchaseVO purchaseVO = new PurchaseVO();

		while (rs.next()) {
			purchaseVO.setTranNo(rs.getInt("tran_no"));
			purchaseVO.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));

			System.out.println("PurchaseDAO getPurchase :" + purchaseVO);

		}
		con.close();

		return purchaseVO;
	}// end of getPurchase
	
	public PurchaseVO findPurcahse2(int tranNo) throws Exception {

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
		
		
		PurchaseVO purchaseVO = new PurchaseVO();

		while (rs.next()) {
			purchaseVO.setTranNo(rs.getInt("TRAN_NO"));
			purchaseVO.setPurchaseProd(productService.getProduct(rs.getInt("PROD_NO")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("BUYER_ID")));
			purchaseVO.setPaymentOption(rs.getString("PAYMENT_OPTION"));
			purchaseVO.setReceiverName(rs.getString("RECEIVER_NAME"));
			purchaseVO.setReceiverPhone(rs.getString("RECEIVER_PHONE"));
			purchaseVO.setDivyAddr(rs.getString("DLVY_ADDR"));
			purchaseVO.setDivyRequest(rs.getString("DLVY_REQUEST"));
			purchaseVO.setDivyDate(rs.getString("DLVY_DATE"));

			System.out.println("PurchaseDAO getPurchase :" + purchaseVO);

		}
		con.close();

		return purchaseVO;
	}// end of getPurchase
	
	
	//구매목록 조회 No, 회원ID, 회원명, 전화번호, 배송현황, 정보수정
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();
		
		
		String sql="select  tran_no, prod_no, buyer_id, receiver_name, receiver_phone  from transaction ";
		
		
		//전체 게시물수 
		int totalCount= this.getTotalCount(sql);
		
		//현재 페이지만 게시물 받도록 쿼리 다시
		sql=makeCurrentPageSql(sql,search);
		PreparedStatement stmt=con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println("나는 서치==> "+search);
		
		
		List<PurchaseVO> list=new ArrayList<PurchaseVO>();
		
		ProductService productservice = new ProductServiceImpl();
		UserService userservice= new UserServiceImpl();
		
		
		while(rs.next()) {
			PurchaseVO purchaseVO= new PurchaseVO();
			
			purchaseVO.setTranNo(rs.getInt("tran_No"));
			purchaseVO.setPurchaseProd(productservice.getProduct(rs.getInt("prod_No")));
			purchaseVO.setBuyer(userservice.getUser(rs.getString("buyer_Id")));
			purchaseVO.setReceiverName(rs.getString("receiver_name"));
			purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
			list.add(purchaseVO);
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
	
	
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception{
		
		Connection con=DBUtil.getConnection();
		
		System.out.println("여기는 purchase업데이트 메소드");
		
		String sql="update transaction set PAYMENT_OPTION=?, RECEIVER_NAME=?,"
				+ " RECEIVER_PHONE=?, DLVY_ADDR=?, DLVY_REQUEST=?, DLVY_DATE=? WHERE tran_no=?";
		
		System.out.println("PurchaseDAO Update ==>"+purchaseVO);
		PreparedStatement stmt= con.prepareStatement(sql);
		
		
		//stmt.setString(1, purchaseVO.getBuyer().getUserId());
		stmt.setString(2, purchaseVO.getPaymentOption());
		stmt.setString(3, purchaseVO.getReceiverName());
		stmt.setString(4, purchaseVO.getReceiverPhone());
		stmt.setString(5, purchaseVO.getDivyAddr());
		stmt.setString(6, purchaseVO.getDivyRequest());
		stmt.setString(7, purchaseVO.getDivyDate());
		stmt.executeUpdate();
		
		
		con.close();
		
		//return purchaseVO;
	}

}// end of class
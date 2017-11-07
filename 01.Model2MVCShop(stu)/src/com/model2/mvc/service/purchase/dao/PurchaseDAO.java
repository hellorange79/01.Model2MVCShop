package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.domain.*;
public class PurchaseDAO {

	// 디폴트 생성자
	public PurchaseDAO() {

	}

	//
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction "
				+ " (tran_no, prod_no, buyer_id, payment_option, "
				+ " receiver_name, receiver_phone, dlvy_addr,"
				+ " dlvy_request, dlvy_date) "+ 
				"VALUES(seq_transaction_tran_no.nextval,seq_product_prod_no.nextval,"
				+ " ?,?,?,?,?,?,TO_DATE(?,'YYYY/MM/DD'))";

		PreparedStatement stmt = con.prepareStatement(sql);
		
		
		User user = new User();
		stmt.setString(1, user.getUserId());
		stmt.setString(2, purchaseVO.getPaymentOption());
		stmt.setString(3, purchaseVO.getReceiverName());
		stmt.setString(4, purchaseVO.getReceiverPhone());
		stmt.setString(5, purchaseVO.getDivyAddr());
		stmt.setString(6, purchaseVO.getDivyRequest());
		stmt.setString(7, purchaseVO.getDivyDate());
		stmt.executeQuery();
		
		con.close();
		
		
	}

}
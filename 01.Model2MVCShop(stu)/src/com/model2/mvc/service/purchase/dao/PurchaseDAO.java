
package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.domain.*;
public class PurchaseDAO {

	// ����Ʈ ������
	public PurchaseDAO() {
		
	}

	//
	public void insertPurchase(PurchaseVO purchaseVO) throws Exception {
		
		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO transaction "
				+ " (tran_no, prod_no, buyer_id, payment_option, "
				+ " receiver_name, receiver_phone, dlvy_addr,"
				+ " dlvy_request, dlvy_date) "+ 
				"VALUES(seq_transaction_tran_no.nextval,?,"
				+ " ?,?,?,?,?,?,TO_DATE(?,'YYYY/MM/DD'))";

		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("PurchaseDAO ::"+sql);
		
		//tran_no �������� �޾ƿ�
		
		stmt.setString(2, purchaseVO.getBuyer().getUserId());//
		System.out.println("�� ���̵�"+ purchaseVO.getBuyer().getUserId());
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		System.out.println("�� �ѹ�"+purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(3, purchaseVO.getPaymentOption());
		stmt.setString(4, purchaseVO.getReceiverName());
		stmt.setString(5, purchaseVO.getReceiverPhone());
		stmt.setString(6, purchaseVO.getDivyAddr());
		stmt.setString(7, purchaseVO.getDivyRequest());
		stmt.setString(8, purchaseVO.getDivyDate());
		stmt.executeQuery();
		
		con.close();
		
		
	}

}
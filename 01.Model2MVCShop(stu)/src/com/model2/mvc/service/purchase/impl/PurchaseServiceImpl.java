package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO = new PurchaseDAO();
	}
	
	//±¸¸Å
	public void addPurchase(PurchaseVO purchaseVO)throws Exception{
		purchaseDAO.insertPurchase(purchaseVO);
		
	}

	@Override
	public PurchaseVO getPurchase2(int tranNo) throws Exception {
		
		
		return purchaseDAO.findPurcahse2(tranNo);
	}

	@Override
	public PurchaseVO getPurchase(int ProdNo) throws Exception {
		
		
		return purchaseDAO.findPurcahse(ProdNo);
	}

	@Override
	public HashMap<String, Object> getPurchaseList(Search search, String buyerId) throws Exception {
		
		return (HashMap<String, Object>)purchaseDAO.getPurchaseList(search, buyerId);
	}

	@Override
	public HashMap<String, Object> getSaleList(Search search) throws Exception {
		
		return null;
	}

	@Override
	public void updatePurchase(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.updatePurchase(purchaseVO);
	}

	@Override
	public void updateTranCode(PurchaseVO purchaseVO) throws Exception {
		purchaseDAO.updateTranCode(purchaseVO);
		
	}
	

	
	

}

package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeAction extends Action{

	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		System.out.println(":::UpdateTranCodeAction ::");
		
		int tranNo =Integer.parseInt(request.getParameter("tranNo"));
	
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO= purchaseService.getPurchase2(tranNo);
		
		purchaseVO.setTranCode("2");
		purchaseService.updateTranCode(purchaseVO);

		request.setAttribute("purchaseVO", purchaseVO);

		return "redirect:/listProduct.do?";
	}

}

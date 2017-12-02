package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;



public class UpdatePurchaseAction extends Action {
	
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
	
		
		int tranNo =Integer.parseInt(request.getParameter("tranNo"));
		
		
		
		Purchase purchase= new Purchase();
		
		
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("divyAddr"));
		purchase.setDivyRequest(request.getParameter("divyRequest"));
		purchase.setDivyDate(request.getParameter("divyDate"));
		
		System.out.println("updatePurchase :::::::::::"+purchase );
		
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.updatePurchase(purchase);
		
		
		request.setAttribute("purchaseVO", purchase);
		
		
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}
	

}

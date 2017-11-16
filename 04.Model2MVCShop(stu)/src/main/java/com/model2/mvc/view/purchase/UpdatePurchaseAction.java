package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;


public class UpdatePurchaseAction extends Action {
	
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
	
		
		int tranNo =Integer.parseInt(request.getParameter("tranNo"));
		
		
		
		PurchaseVO purchaseVO= new PurchaseVO();
		
		
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		
		System.out.println("updatePurchase :::::::::::"+purchaseVO );
		
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.updatePurcahse(purchaseVO);
		
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}
	

}

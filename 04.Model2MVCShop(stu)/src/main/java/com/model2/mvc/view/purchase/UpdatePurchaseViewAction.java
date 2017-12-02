package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;


public class UpdatePurchaseViewAction extends Action {
	public String execute(HttpServletRequest request, 
			HttpServletResponse response)throws Exception{
		
		PurchaseService purchaseService= new PurchaseServiceImpl();

		int tranNo =Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchase=purchaseService.getPurchase2(tranNo);
		System.out.println(purchaseService.getPurchase2(tranNo));
		
		
		request.setAttribute("purchaseVO", purchase);
		
		return "forward:/purchase/updatePurchaseView.jsp?tranNo="+tranNo;
		
	}

}

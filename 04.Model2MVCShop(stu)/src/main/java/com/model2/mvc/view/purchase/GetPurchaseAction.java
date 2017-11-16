package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class GetPurchaseAction extends Action{
	
	public String execute(HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
		int tranNo =Integer.parseInt(request.getParameter("tranNo"));
		
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		PurchaseVO purchaseVO= purchaseService.getPurchase2(tranNo);
		System.out.println("나는 무야:"+purchaseVO);
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/getPurchase.jsp?tranNo="+tranNo;
		
	}

}

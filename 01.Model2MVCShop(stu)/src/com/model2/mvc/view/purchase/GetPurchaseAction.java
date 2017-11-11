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
		
		int prodNo =Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService productService=new ProductServiceImpl();
		ProductVO productVO=productService.getProduct(prodNo);
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		PurchaseVO purchaseVO= purchaseService.getPurchase(prodNo);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		return "forward:/purchase/getPurchase.jsp?=prodNo="+prodNo;
		
	}

}

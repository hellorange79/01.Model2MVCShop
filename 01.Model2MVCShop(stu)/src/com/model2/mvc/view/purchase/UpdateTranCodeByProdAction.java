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

public class UpdateTranCodeByProdAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		
		System.out.println(":: UpdateTranCodeByProdAcction :: ");
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		String proTranCode = request.getParameter("proTranCode");
		
		ProductService service = new ProductServiceImpl();
		ProductVO productVO = service.getProduct(prodNo);
		productVO.setProTranCode(proTranCode);
		System.out.println("proTranCode====> "+proTranCode);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO=purchaseService.getPurchase(prodNo);
		purchaseVO.setTranCode(proTranCode);
		
		purchaseService.updateTranCode(purchaseVO);
		
		request.setAttribute("proTranCode", proTranCode);
		
		
		
	return "redirect:/listProduct.do?";
	}
}

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
		
		String proTranCode = request.getParameter("proTranCode");
		
		ProductService productservice = new ProductServiceImpl();
		
		ProductVO productVO= new ProductVO();
		productVO.setProTranCode("2");
		System.out.println("proTranCode ===>"+proTranCode);
		
		String tranCode= request.getParameter("tranCode"); 
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranCode("2");
		
		//proTranCode update
		
	return "redirect:/listProduct.do?";
	}
}

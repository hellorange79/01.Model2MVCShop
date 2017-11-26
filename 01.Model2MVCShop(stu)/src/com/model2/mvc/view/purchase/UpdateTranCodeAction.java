package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class UpdateTranCodeAction {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		int tranNo = Integer.parseInt(request.getParameter("tranNo"));

		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setTranNo(tranNo);
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		System.out.println("updateTranCode :::::" + purchaseVO);

		PurchaseService purchaseService = new PurchaseServiceImpl();
		purchaseService.updateTranCode(purchaseVO);

		request.setAttribute("purchaseVO", purchaseVO);

		return "redirect:/updateTranCode.do?tranNo=" + tranNo;
	}

}

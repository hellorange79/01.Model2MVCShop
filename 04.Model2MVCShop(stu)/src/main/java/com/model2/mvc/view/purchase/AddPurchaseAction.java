package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		// buyerId를 userId로 받아오기
		HttpSession session = request.getSession();
		
		String userId = ((User) session.getAttribute("user")).getUserId();
		
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		// user정보 받아오기
		UserService userService = new UserServiceImpl();
		User user = userService.getUser(userId);
		
		ProductService productservice = new ProductServiceImpl();
		Product product = productservice.getProduct(prodNo);

		Purchase purchase = new Purchase();
		
		purchase.setBuyer(((User) session.getAttribute("user")));
		//purchaseVO.setPurchaseProd((ProductVO)request.getAttribute("prodNo"));
		purchase.setPurchaseProd(product);
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setReceiverName(request.getParameter("receiverName"));
		purchase.setReceiverPhone(request.getParameter("receiverPhone"));
		purchase.setDivyAddr(request.getParameter("receiverAddr"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setDivyDate(request.getParameter("receiverDate"));

		System.out.println(purchase);
		// purchase 에 정보 넣기
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchase);

		
		request.setAttribute("product", product);
		request.setAttribute("prodNo", prodNo);
		request.setAttribute("purchaseVO", purchase);
		return "forward:/purchase/addPurchaseAction.jsp";

	}
}

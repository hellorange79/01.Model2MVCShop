package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
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

		PurchaseVO purchaseVO = new PurchaseVO();
		
		purchaseVO.setBuyer(((User) session.getAttribute("user")));
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("receiverAddr"));
		purchaseVO.setDivyRequest(request.getParameter("receiverRequest"));
		purchaseVO.setDivyDate(request.getParameter("receiverDate"));

		System.out.println(purchaseVO);
		// purchase 에 정보 넣기
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchaseVO);

		ProductService productservice = new ProductServiceImpl();
		ProductVO productVO = productservice.getProduct(prodNo);

		request.setAttribute("productVO", productVO);
		request.setAttribute("prodNo", prodNo);
		request.setAttribute("purchaseVO", purchaseVO);
		return "forward:/purchase/addPurchaseAction.jsp";

	}
}

package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseAction extends Action{
	public String execute(HttpServletRequest request, 
			HttpServletResponse response) throws Exception{
		
		HttpSession session=request.getSession();
		
		
		PurchaseVO purchaseVO=new PurchaseVO();
		User user=new User();
		//buyerId를 userId로 받아오기
		user.setUserId(request.getParameter("userid"));
		purchaseVO.setPaymentOption(request.getParameter("payment_option"));
		purchaseVO.setReceiverName(request.getParameter("receiver_name"));
		purchaseVO.setReceiverPhone(request.getParameter("receiver_phone"));
		purchaseVO.setDivyAddr(request.getParameter("dlvy_addr"));
		purchaseVO.setDivyRequest(request.getParameter("dlvy_request"));
		purchaseVO.setDivyDate(request.getParameter("dlvy_date"));
		
		System.out.println(purchaseVO);
		
		
		PurchaseService service = new PurchaseServiceImpl();
		service.addPurchase(purchaseVO);
		//session으로 받아올거임
		UserService userService=new UserServiceImpl();
		User dbUser=userService.loginUser(user);
		
		
		return "redirect:/product/addPurchaseAction.jsp";
		
	}
}

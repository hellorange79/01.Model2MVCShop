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

import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class AddPurchaseViewAction extends Action{
	
	public String execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		//���� ����
		HttpSession session=request.getSession();
		
		//session�� �ִ� userId ��������
		String userId = ((User)session.getAttribute("user")).getUserId();
		System.out.println(userId+"<=== userId ");
		
		//request�� prodNo��������
		int prodNo = Integer.parseInt(request.getParameter("prodNo"));
		System.out.println(prodNo+"<=== prodNo ");
		
		
		//��ǰ��ȣ�� ��ǰ���� ��������
		ProductService productservice = new ProductServiceImpl();
		Product product = productservice.getProduct(prodNo);
		
		//���� ���̵�� �������� ��������
		UserService userservice = new UserServiceImpl();
		User user = userservice.getUser(userId);
		
		
		
		//request.setAttribute("prodNo", prodNo);
		request.setAttribute("product", product);
		System.out.println(product+"<==product");
		
		
		
		return "forward:/purchase/addPurchaseViewAction.jsp?prodNo ="+prodNo;
		
	}
	
	

}

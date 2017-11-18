package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

public class ListPurchaseAction extends Action {

	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Search search = new Search();
		
		HttpSession session = request.getSession();

		String userId = ((User) session.getAttribute("user")).getUserId();
		System.out.println(userId+"11");
		
		int currentPage = 1;
		if (request.getParameter("currentPage") != null)
			currentPage = Integer.parseInt(request.getParameter("currentPage"));

		search.setCurrentPage(currentPage);

		int pageSize = Integer.parseInt(getServletContext().getInitParameter("pageSize"));
		int pageUnit = Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);

		PurchaseService purchaseService = new PurchaseServiceImpl();
		HashMap<String, Object> map = purchaseService.getPurchaseList(search, userId);

		Page resultPage = new Page(currentPage, ((Integer) map.get("totalCount")).intValue(), pageUnit, pageSize);

		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);

		return "forward:/purchase/listPurchase.jsp";
	}

}

package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO = new ProductDAO(); 
	}
	
	@Override
	//상품등록
	public void addProduct(ProductVO productVO) throws Exception {
		
		productDAO.insertProduct(productVO);
		
	}

	@Override
	//상세정보
	public ProductVO getProduct(int prodNo) throws Exception {
		//System.out.println(prodNo);
		return productDAO.findProduct(prodNo);
	}

	@Override
	//상품목록
	public HashMap<String, Object> getProductList(Search search) throws Exception {
		// TODO Auto-generated method stub
		return (HashMap<String, Object>)productDAO.getProductList(search);
	}

	@Override
	//상품수정
	public void updateProduct(ProductVO productVO) throws Exception {
		System.out.println(productVO);
		productDAO.updateProduct(productVO);
		
	}
	
	

}

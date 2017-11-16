package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.ProductDao;;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService{
	
	//field
	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
	public void setProductDao(ProductDao productDao) {
		this.productDao=productDao;
		
	}
	
	//constructor
	public ProductServiceImpl() {
		System.out.println(this.getClass());
	}
	
	@Override
	//상품등록
	public void addProduct(Product product) throws Exception {
		productDao.addProduct(product);
	}

	@Override
	//상세정보
	public Product getProduct(int prodNo) throws Exception {
		//System.out.println(prodNo);
		return productDao.getProduct(prodNo);
	}

	@Override
	//상품목록
	public HashMap<String, Object> getProductList(Search search) throws Exception {
		List<Product> list = productDao.getProductList(search);
		int totalCount= productDao.getTotalCount(search);
		
		HashMap<String, Object> map= new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		return map;
	}

	@Override
	//상품수정
	public void updateProduct(Product product) throws Exception {
		System.out.println(product);
		productDao.updateProduct(product);
		
	}
	
	
	

}

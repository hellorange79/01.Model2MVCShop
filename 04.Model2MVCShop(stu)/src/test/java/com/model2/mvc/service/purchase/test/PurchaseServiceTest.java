package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;




import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;

import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

import com.model2.mvc.service.purchase.PurchaseService;

import com.model2.mvc.service.user.UserService;


/*
 *	FileName :  UserServiceTest.java
 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
 * ㅇ @ContextConfiguration : Meta-data location 지정
 * ㅇ @Test : 테스트 실행 소스 지정
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
public class PurchaseServiceTest {

	// @RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	// @Test
	public void testAddPurchase() throws Exception {

		Purchase purchase = new Purchase();

		purchase.setPurchaseProd(productService.getProduct(10002));
		System.out.println(":: prodNo ::" + productService.getProduct(10002));

		purchase.setBuyer(userService.getUser("user02"));
		System.out.println("나난나ㅏㄴ" + userService.getUser("user02"));

		purchase.setPaymentOption("1");
		purchase.setReceiverName("구매자이름");
		purchase.setReceiverPhone("4567");
		purchase.setDivyAddr("서울");
		purchase.setDivyRequest("배송요쳥");
		purchase.setDivyDate("20171205");

		purchaseService.addPurchase(purchase);

		purchase = purchaseService.getPurchase(10002);

		System.out.println(purchase);

	}

	// @Test
	public void testGetPurchase() throws Exception {

		Purchase purchase = new Purchase();

		purchase = purchaseService.getPurchase(10000);

		System.out.println(purchase);
	}

	// @Test
	public void testGetPurchase2() throws Exception {

		Purchase purchase = new Purchase();

		purchase = purchaseService.getPurchase2(10000);

		System.out.println(purchase);
	}

	// @Test
	public void testUpdatePurchase() throws Exception {

		Purchase purchase = purchaseService.getPurchase2(10020);
		System.out.println(":: UpdatePurchase :: "+purchase);
		
		purchase.setPaymentOption("2");
		purchase.setReceiverName("이정은");
		purchase.setReceiverPhone("1111");
		purchase.setDivyAddr("제주도");
		purchase.setDivyRequest("요청변경");
		purchase.setDivyDate("20111205");
		
		purchaseService.updatePurchase(purchase);
		
		purchase =purchaseService.getPurchase2(10020);
		System.out.println(purchase);
	}
	 @Test
		public void testUpdateTranCode() throws Exception {

			Purchase purchase = purchaseService.getPurchase(10000);
			System.out.println(":: UpdatetranCode :: "+purchase);
			
			purchase.setTranCode("2");
			purchase.setPurchaseProd(productService.getProduct(10000));
			
			purchaseService.updateTranCode(purchase);
			
			
			purchase =purchaseService.getPurchase(10000);
			System.out.println("TranCode변경됨 ====> "+purchase);
		} 
	 

	// @Test
	public void testGetPurchaseListAll() throws Exception {

		Search search = new Search();
		search.setCurrentPage(1);
		search.setPageSize(3);
		Map<String, Object> map = purchaseService.getPurchaseList(search);

		List<Object> list = (List<Object>) map.get("list");
		// Assert.assertEquals(3, list.size());
		System.out.println(list);

		Integer totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

		System.out.println("=======================================");

		search.setCurrentPage(1);
		search.setPageSize(3);
		search.setSearchCondition("0");
		search.setSearchKeyword("");
		map = purchaseService.getPurchaseList(search);

		list = (List<Object>) map.get("list");

		totalCount = (Integer) map.get("totalCount");
		System.out.println(totalCount);

	}

}
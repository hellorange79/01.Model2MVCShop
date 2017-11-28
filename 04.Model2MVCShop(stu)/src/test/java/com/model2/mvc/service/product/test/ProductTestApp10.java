package com.model2.mvc.service.product.test;

import java.util.ArrayList;

import com.model2.mvc.common.*;
import com.model2.mvc.service.domain.*;

import org.apache.ibatis.session.SqlSession;


/*
 * FileName : MyBatisTestApp101.java
  */
public class ProductTestApp10 {
	
	///main method
	public static void main(String[] args) throws Exception{
	
		//==> SqlSessionFactoryBean.getSqlSession()을 이용 SqlSession instance GET
		SqlSession sqlSession = SqlSessionFactoryBean.getSqlSession();
		System.out.println("\n");
		
		//==> Test 용 Product instance 생성  
		Product product = new Product("오징어","못생김","17/11/12",100,"ac.jpg");
		
		//1. UserMapper10.addUser Test  :: users table age/grade/redDate 입력값 확인할것 : OK 
		System.out.println(":: 1. addProduct(INSERT)  ? ");
		System.out.println(":: "+ sqlSession.insert("ProductMapper.addProduct",product));
		System.out.println("\n");
		
		//2. UserMapper10.getUser Test :: 주몽 inert 확인 
		product.setProdNo(10000);
		System.out.println(":: 2. getProduct(SELECT)  ? ");
		System.out.println(":: "+sqlSession.selectOne("ProductMapper.getProduct",product.getProdNo()));
		System.out.println("\n");
		
		//3. UserMapper10.uadateUser Test  :: users table age/grade/redDate 입력값 확인할것 : OK
		//                                                    :: 주몽 ==> 온달 수정
		product.setProdName("징징이");
		product.setProdDetail("싱싱한오징어");
		product.setPrice(333);
		product.setFileName("qwe.jpg");
		System.out.println(":: 3. update(UPDATE)  ? ");
		System.out.println(":: "+ sqlSession.update("ProductMapper.updateProduct",product));
		System.out.println("\n");
		
		//4. UserMapper10.getUserList Test  :: Dynamic Query 확인 id=user04/name=온달 검색
		
		System.out.println(":: 4. getProduct(SELECT)  ? ");
		System.out.println(":: "+sqlSession.selectOne("ProductMapper.getProduct",product.getProdNo()) );
		System.out.println("\n");
		
		//5. UserMapper10.removeUser Test
		//System.out.println(":: 5. Use10.removeUser (DELETE)  ? ");
		//System.out.println(":: "+sqlSession.delete("UserMapper10.removeUser", 
		//																				   user.getUserId()) );
		//System.out.println("\n");
		//System.out.println("/////////////////////////////////////////////////////////////////////////////////////////////////");
		//System.out.println("\n");
		
		//SqlSessionFactoryBean.printList( sqlSession.selectList("UserMapper10.getProductList",search) );
		
		
	}//end of main
}//end of class
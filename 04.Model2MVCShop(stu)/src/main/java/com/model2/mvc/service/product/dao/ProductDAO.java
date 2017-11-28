package com.model2.mvc.service.product.dao;

import java.sql.*;
import java.util.*;


import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.*;

public class ProductDAO {

	// 디폴트 생성자
	public ProductDAO() {
	}

	// 상품등록 메서드
	public void insertProduct(Product product) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "insert into PRODUCT values(seq_product_prod_no.nextval,?,?,to_date(?, 'YYYY/MM/DD'),?,?,sysdate)";

		PreparedStatement stmt = con.prepareStatement(sql);
		// stmt.setInt(1, Product.getProdNo());//상품번호(시퀀스 사용)
		stmt.setString(1, product.getProdName());// 상품이름
		stmt.setString(2, product.getProdDetail());// 상세정보
		stmt.setString(3, product.getManuDate());// 제조일자
		stmt.setInt(4, product.getPrice());// 상품가격
		stmt.setString(5, product.getFileName());// 상품 이미지파일이름
		// stmt.setDate(7,Product.getRegDate());//상품등록일자 sysdate이용??
		stmt.executeQuery();

		con.close();
	}

	// 상품상세정보 조회 메서드
	public Product findProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "select  prod_no, prod_name, prod_detail, image_file,"
				+ " Manufacture_Day, Price, Reg_date from product  where PROD_NO=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		//System.out.println("DAO"+prodNo);
		ResultSet rs = stmt.executeQuery();

		Product product = new Product();

		while (rs.next()) {
			//System.out.println("Prod_NO");
			product.setProdNo(rs.getInt("prod_no"));// 상품번호
			product.setProdName(rs.getString("prod_name"));// 상품명
			product.setFileName(rs.getString("image_file"));// 이미지파일
			product.setProdDetail(rs.getString("Prod_Detail"));
			product.setManuDate(rs.getString("Manufacture_Day"));
			product.setPrice(rs.getInt("Price"));
			product.setRegDate(rs.getDate("Reg_Date"));

		}
		
		con.close();
		
		return product;
	}

	// 상품 목록조회 메서드
	public Map<String, Object> getProductList(Search search) throws Exception {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Connection con = DBUtil.getConnection();

		String sql = "select prod_no, prod_name, price, reg_date from product";

		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE Prod_No LIKE '%" + search.getSearchKeyword() + "%'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE Prod_Name LIKE '%" + search.getSearchKeyword() + "%'";
			} else if(search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")){
				sql +=" WHERE Price LIKE '%"+search.getSearchKeyword() +"%'";
			}
		}
		sql += " ORDER BY prod_NO";
		System.out.println("ProductDAO :: SQL  : "+sql);
		
		//전체 게시물수 
		int totalCount= this.getTotalCount(sql);
		
		//현재 페이지만 게시물 받도록 쿼리 다시
		sql=makeCurrentPageSql(sql,search);
		PreparedStatement stmt=con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		System.out.println("나는 서치==> "+search);
		
		
		List<Product> list=new ArrayList<Product>();
		
		while(rs.next()) {
				Product product = new Product();
				product.setProdNo(rs.getInt("PROD_NO"));// 싱품번호
				product.setProdName(rs.getString("PROD_NAME"));// 상품명
				product.setPrice(rs.getInt("PRICE"));// 가격
				product.setRegDate(rs.getDate("REG_DATE"));// 등록일
				product.setProTranCode(rs.getString("prod_name"));
				list.add(product);
				
			}
		//totalCount 정보 저장
		map.put("list", list);
		//current page정보저장
		map.put("totalCount", totalCount);
		System.out.println("list.size() : " + list.size());
		System.out.println("totalCount: "+totalCount);
		
		
		
		rs.close();
		stmt.close();
		con.close();

		return map;
	}

	private int getTotalCount(String sql)throws Exception {
		
		//total 카운트 개수 새는 쿼리
		sql ="SELECT COUNT(*) "+"FROM ( " + sql + " ) countable ";
		
		
		Connection con =DBUtil.getConnection();
		PreparedStatement Stmt = con.prepareStatement(sql);
		ResultSet rs = Stmt.executeQuery();

		int totalCount = 0;
		if (rs.next()) {
			totalCount = rs.getInt(1);
		}

		Stmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}

	private String makeCurrentPageSql(String sql, Search search) {
		sql = "SELECT * " + " FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " + " 	FROM (	" + sql
				+ " ) inner_table " + "	WHERE ROWNUM <=" + search.getCurrentPage() * search.getPageSize() + " ) "
				+ " WHERE row_seq BETWEEN " + ((search.getCurrentPage() - 1) * search.getPageSize() + 1) + " AND "
				+ search.getCurrentPage() * search.getPageSize();

		System.out.println("UserDAO :: make SQL :: " + sql);

		return sql;
	}

	// 상품정보 수정 메서드
	public void updateProduct(Product product) throws Exception {
		
		Connection con=DBUtil.getConnection();
		System.out.println("여기는 업데이트 메소드");
		String sql ="update product set prod_name=?, prod_detail=?, "
				+ "REPLACE(MANUFACTURE_DAY,'-',''), price=?, image_file=? where prod_no=?";
		
		PreparedStatement stmt=con.prepareStatement(sql);
		
		
		
		stmt.setString(1, product.getProdName());
		stmt.setString(2, product.getProdDetail());
		stmt.setString(3, product.getManuDate());
		stmt.setInt(4, product.getPrice());
		stmt.setString(5, product.getFileName());
		stmt.setInt(6, product.getProdNo());
		
		
		stmt.executeUpdate();
		con.close();
	}

}

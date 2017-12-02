<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td>${productVO.getProdNo()}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td>${purchaseVO.getBuyer().getUserId()}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		<c:if test="${purchaseVO.getPaymentOption() eq '1'}">현금구매</c:if>
		<c:if test="${purchaseVO.getPaymentOption() eq '2'}">신용구매</c:if>
	
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td>${purchaseVO.getReceiverName()}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td>${purchaseVO.getReceiverPhone()}</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td>${purchaseVO.getDivyAddr()}</td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td>${purchaseVO.getDivyRequest()}</td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td>${purchaseVO.getDivyDate()}</td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>
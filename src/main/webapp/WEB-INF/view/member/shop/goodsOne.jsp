<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>goodsOne</title>
</head>
<body>
	<!-- 맨위에 include  -->
	<h3>goodsOne</h3>
	<!-- 가격, 수량조절, 장바구니로 갈지 결제창으로 갈지, 제품소개 등등.... -->
	<c:forEach var="m" items="${list}">
		<table border="1">
			<tr>
				<td colspan="2">
					<img src="${pageContext.request.contextPath}/upload/${m.fileName}" width="200" height="200">
				</td>
			</tr>
			<tr>
				<td>상품 이름</td>
				<td>${m.goodsName}</td>
			</tr>
			<tr>
				<td>상품 가격</td>
				<td>${m.goodsPrice}</td>
			</tr>
			<tr>
			<!--  수량이 변경되면 상품가격이 변경되야 함 -->
				<td>수량</td>
				<td><input type="number" value="1"></td>
			</tr>
			<!-- 제품설명? 개별적인 설명이 나오게 하려면 어떻게?? -->
			<tr>
				<td>제품 설명</td>
				<td>대중적인 향으로 모두에게 어필할 수 있는 매력적인 향수입니다.</td>
			</tr>
			<!-- 또 추가할게 있으면 추가 -->
		</table>
		<!-- 장바구니 아니면 결제창 이동 버튼 -->
		<a href='${pageContext.request.contextPath}/member/cart'>장바구니</a>
		<a href='${pageContext.request.contextPath}/member/goodsPayMent'>구매하기</a>
	</c:forEach>
</body>
</html>
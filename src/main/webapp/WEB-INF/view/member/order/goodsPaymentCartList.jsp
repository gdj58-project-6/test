<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>goodsPaymentList</title>
		<link rel="icon" type="image/png" href="../images/icons/favicon.png"/>
		<link rel="stylesheet" type="text/css" href="../vendor/bootstrap/css/bootstrap.min.css">
		<link rel="stylesheet" type="text/css" href="../fonts/font-awesome-4.7.0/css/font-awesome.min.css">
		<link rel="stylesheet" type="text/css" href="../fonts/iconic/css/material-design-iconic-font.min.css">
		<link rel="stylesheet" type="text/css" href="../fonts/linearicons-v1.0.0/icon-font.min.css">
		<link rel="stylesheet" type="text/css" href="../vendor/select2/select2.min.css">
		<link rel="stylesheet" type="text/css" href="../vendor/perfect-scrollbar/perfect-scrollbar.css">
		<link rel="stylesheet" type="text/css" href="../css/util.css">
		<link rel="stylesheet" type="text/css" href="../css/main.css">
		<style>
			table, th, td {
				text-align: center;
			}
			
			td {
				height: 150px;
			}
			
			textarea {
				resize: none;
			}
		</style>
	</head>
	<body>
		<jsp:include page="/inc/homeMenu.jsp"></jsp:include>
		<div class="container p-t-80">
			<div class="bread-crumb flex-w p-l-25 p-r-15 p-t-30 p-lr-0-lg">
				<a href="${pageContext.request.contextPath}/home" class="stext-109 cl8 hov-cl1 trans-04"> Home <i class="fa fa-angle-right m-l-9 m-r-10" aria-hidden="true"></i></a>
				<span class="stext-109 cl4"> Order </span>
			</div>
		</div>
		<form action="${pageContext.request.contextPath}/member/goodsPayMentCart" method="post">
			<div class="container m-t-20">
				<div class="row">
					<div class="col-lg-10 col-xl-7 m-lr-auto m-b-50">
						<div class="m-l-25 m-r--38 m-lr-0-xl">
							<div class="wrap-table-shopping-cart">
								<table class="table-shopping-cart">
									<tr class="table_head">
										<th style="width: 550px;" colspan="2">상품</th>
										<th style="width: 100px;">상품 가격</th>
										<th style="width: 50px;">개수</th>
										<th style="width: 200px;">총 가격</th>
									</tr>
									<c:forEach var="g" items="${list}">
										<tr>
											<td style="width: 150px;"><img src="${pageContext.request.contextPath}/upload/${g.fileName}" width="150" height="150"></td>
											<td style="width: 400px;"><input type="hidden" name="goodsCode" value="${g.goodsCode}"> ${g.goodsName}</td>
											<td style="width: 100px;"><fmt:formatNumber value="${g.goodsPrice}" pattern="###,###,###" /></td>
											<td style="width: 50px;">${g.cartQuantity}</td>
											<td style="width: 200px;">
												<fmt:formatNumber value="${g.goodsPrice * g.cartQuantity}" pattern="###,###,###" />
											</td>
											<c:set var="totalPrice"  value="${totalPrice + (g.goodsPrice * g.cartQuantity)}" ></c:set>
										</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
					<div class="col-sm-10 col-lg-7 col-xl-5 m-lr-auto m-b-30">
						<div class="bor10 p-lr-50 p-t-30 p-b-30 m-l-45 m-r-10 m-lr-0-xl p-lr-15-sm">
							<h4 class="mtext-109 cl2 p-b-30">주문자 정보</h4>
							<div class="flex-w flex-t bor12 p-t-5 p-b-30">
								<div class="size-208 w-full-ssm p-t-10">
									<span class="stext-110 cl2"> ID </span>
								</div>
								<div class="size-209 p-r-18 p-r-0-sm w-full-ssm p-t-10">
									<p class="stext-111 cl6 p-t-2"><input class="size-111 bor8 stext-102 cl2 p-lr-20" name="id" value="${customerOne.customerId}" readonly></p>
								</div>
								<div class="size-208 w-full-ssm p-t-10">
									<span class="stext-110 cl2"> Name </span>
								</div>
								<div class="size-209 p-r-18 p-r-0-sm w-full-ssm p-t-10">
									<p class="stext-111 cl6 p-t-2"><input class="size-111 bor8 stext-102 cl2 p-lr-20" value="${customerOne.customerName}" readonly></p>
								</div>
								<div class="size-208 w-full-ssm p-t-10">
									<span class="stext-110 cl2"> Phone </span>
								</div>
								<div class="size-209 p-r-18 p-r-0-sm w-full-ssm p-t-10">
									<c:set var="phone" value="${customerOne.customerPhone}"></c:set>
									<input class="size-111 bor8 stext-102 cl2 p-lr-20" value="${fn:substring(phone, 0, 3)}-${fn:substring(phone, 3, 7)}-${fn:substring(phone, 7, 11)}" readonly="readonly">
								</div>
								<div class="size-208 w-full-ssm p-t-10">
									<span class="stext-110 cl2"> Address </span>
								</div>
								<div class="size-209 p-r-18 p-r-0-sm w-full-ssm p-t-10">
									<p class="stext-111 cl6 p-t-2">
										<input type="hidden" name="addressCode" value="${customerOne.addressCode}">
										<input class="size-111 bor8 stext-102 cl2 p-lr-20" value="${customerOne.address}" readonly="readonly">
									</p>
								</div>
								<div class="size-208 w-full-ssm p-t-10">
									<span class="stext-110 cl2"> Memo </span>
								</div>
								<div class="size-209 p-r-18 p-r-0-sm w-full-ssm p-t-10">
									<p class="stext-111 cl6 p-t-2">
										<textarea class="size-110 bor8 stext-102" name="orderMemo"></textarea>
									</p>
								</div>
							</div>
							<div class="flex-w flex-t p-t-27 p-b-33">
								<div class="size-208 w-full-ssm p-t-10">
									<span class="stext-110 cl2"> Point </span>
								</div>
								<div class="size-209 p-r-18 p-r-0-sm w-full-ssm p-t-10">
									<input class="size-111 bor8 stext-102 cl2 p-lr-20" name="point" value="0">
									<p class="stext-111 cl6 p-t-2">사용 가능 포인트 : ${customerOne.point}</p>
								</div>
								<div class="size-208 p-t-10">
									<span class="mtext-101 cl2"> Total Price </span>
								</div>
								<div class="size-208 m-l-20 p-t-10">
									<input type="hidden" name="orderPrice" value="${totalPrice}">
									<span class="mtext-110 cl2"> <fmt:formatNumber value="${totalPrice}" pattern="###,###,###" /> </span>
								</div>
							</div>
							<button class="flex-c-m stext-101 cl0 size-116 bg3 bor14 hov-btn3 p-lr-15 trans-04 pointer">
								place an order
							</button>
						</div>
					</div>
				</div>
			</div>
		</form>
		<div class="btn-back-to-top" id="myBtn">
			<span class="symbol-btn-back-to-top"> <i class="zmdi zmdi-chevron-up"></i>
			</span>
		</div>
		<script src="../vendor/jquery/jquery-3.2.1.min.js"></script>
		<script src="../vendor/animsition/js/animsition.min.js"></script>
		<script src="../vendor/bootstrap/js/popper.js"></script>
		<script src="../vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="../vendor/select2/select2.min.js"></script>
		<script>
			$(".js-select2").each(function() {
				$(this).select2({
					minimumResultsForSearch : 20,
					dropdownParent : $(this).next('.dropDownSelect2')
				});
			})
		</script>
		<script src="../vendor/MagnificPopup/jquery.magnific-popup.min.js"></script>
		<script src="../vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
		<script src="../js/main.js"></script>
	</body>
</html>
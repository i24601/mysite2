<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@page import="com.javaex.dao.GuestBookDao"%>
<%@page import="com.javaex.vo.GuestBookVo"%>
<%@page import="java.util.List"%>

<%
List<GuestBookVo> gList = (List<GuestBookVo>)request.getAttribute("gList");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet"
	type="text/css">
<link href="/mysite2/assets/css/guestbook.css" rel="stylesheet"
	type="text/css">

</head>

<body>
	<div id="wrap">

		<!-- 헤더분리 -->
		<jsp:include page="/WEB-INF/views/include/header.jsp" />
		<!-- 헤더분리 -->
		<!-- //header -->

		<jsp:include page="/WEB-INF/views/include/nav.jsp" />

		<!-- //nav -->

		<jsp:include page="/WEB-INF/views/include/aSideUser.jsp"></jsp:include>

		<!-- //aside -->


		<div id="content">

			<div id="content-head">
				<h3>일반방명록</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>방명록</li>
						<li class="last">일반방명록</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="guestbook">
				<form action="/mysite2/gbc" method="get">
					<table id="guestAdd">
						<colgroup>
							<col style="width: 70px;">
							<col>
							<col style="width: 70px;">
							<col>
						</colgroup>

						<tbody>
							<tr>
								<th><label class="form-text" for="input-uname">이름</label>
								</td>
								<td><input id="input-uname" type="text" name="name"></td>
								<th><label class="form-text" for="input-pass">패스워드</label>
								</td>
								<td><input id="input-pass" type="password" name="pass"></td>
							</tr>
							<tr>
								<td colspan="4"><textarea name="content" cols="72" rows="5"></textarea></td>
							</tr>
							<tr class="button-area">
								<td colspan="4"><button type="submit">등록</button></td>
							</tr>
						</tbody>

					</table>
					<!-- //guestWrite -->
					<input type="hidden" name="action" value="add">

				</form>

				<table class="guestRead">
					<colgroup>
						<col style="width: 10%;">
						<col style="width: 40%;">
						<col style="width: 40%;">
						<col style="width: 10%;">
					</colgroup>
					<%for(GuestBookVo vo : gList) {%>
					<tr>
						<td><%=vo.getNumber()%></td>
						<td><%=vo.getName()%></td>
						<td><%=vo.getReg_date()%></td>
						<td><a href="./gbc?action=deleteForm&no=<%=vo.getNumber()%>">삭제</a>
					</tr>
					<tr>
						<td colspan=4 class="text-left"><%=vo.getContent()%></td>
					</tr>
					<%} %>
				</table>
				<!-- //guestRead -->



			</div>
			<!-- //guestbook -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- 푸터푼리 -->
		<jsp:include page="/WEB-INF/views/include/footer.jsp" />
		<!-- 푸터푼리 -->
		<!-- //footer -->

	</div>
	<!-- //wrap -->

</body>

</html>
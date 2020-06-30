<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>


<body>
	<div id="wrap">

		<!-- 헤더분리 -->
		<c:import url="/WEB-INF/views/include/header.jsp">
		</c:import>
		<!-- 헤더분리 -->
		<!-- //header -->

		<c:import url="/WEB-INF/views/include/nav.jsp">
		</c:import>

		<!-- //nav -->

		<c:import url="/WEB-INF/views/include/aSideUser.jsp">
		</c:import>

		<!-- //aside -->

		<div id="content">

			<div id="content-head">
				<h3>게시판</h3>
				<div id="location">
					<ul>
						<li>홈</li>
						<li>게시판</li>
						<li class="last">일반게시판</li>
					</ul>
				</div>
				<div class="clear"></div>
			</div>
			<!-- //content-head -->

			<div id="board">
				<div id="read">
					<form action="#" method="get">
						<!-- 작성자 -->
						<div class="form-group">
							<span class="form-text">작성자</span>
							<span class="form-value">${bVo.name}</span>
						</div>
						
						<!-- 조회수 -->
						<div class="form-group">
							<span class="form-text">조회수</span>
							<span class="form-value">${bVo.hit}</span>
						</div>
						
						<!-- 작성일 -->
						<div class="form-group">
							<span class="form-text">작성일</span>
							<span class="form-value">${bVo.reg_date}</span>
						</div>
						
						<!-- 제목 -->
						<div class="form-group">
							<span class="form-text">제 목</span>
							<span class="form-value">${bVo.title}</span>
						</div>
					
						<!-- 내용 -->
						<div id="txt-content">
							<span class="form-value" >
								${bVo.content}<br>
							</span>
						</div>
						
						<c:if test="${sessionScope.authUser.no eq bVo.user_no}">								
						<a id="btn_modify" href="/mysite2/bc?action=modifyForm&no=${param.no}">수정</a>
									</c:if>
						<a id="btn_modify" href="/mysite2/bc?action=list">목록</a>
						
					</form>
	                <!-- //form -->
				</div>
				<!-- //read -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<!-- 푸터푼리 -->
		<c:import url="/WEB-INF/views/include/footer.jsp">
		</c:import>
		<!-- 푸터푼리 -->
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>

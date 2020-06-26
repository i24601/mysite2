<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String result = request.getParameter("result");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/user.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div id="wrap">

		<!-- 헤더분리 -->
		<jsp:include page="/WEB-INF/views/include/header.jsp" />
		<!-- 헤더분리 -->
		<!-- //header -->

		<jsp:include page="/WEB-INF/views/include/nav.jsp" />
		<!-- //nav -->

		
		<!-- //aside -->
		<jsp:include page="/WEB-INF/views/include/aSideUser.jsp"></jsp:include>

		<div id="content">
			
			<div id="content-head">
            	<h3>로그인</h3>
            	<div id="location">
            		<ul>
            			<li>홈</li>
            			<li>회원</li>
            			<li class="last">로그인</li>
            		</ul>
            	</div>
                <div class="clear"></div>
            </div>
             <!-- //content-head -->

			<div id="user">
				<div id="loginForm">
					<form action="/mysite2/user" method="get">

						<!-- 아이디 -->
						<div class="form-group">
							<label class="form-text" for="input-uid">아이디</label> 
							<input type="text" id="input-uid" name="id" value="" placeholder="아이디를 입력하세요">
						</div>

						<!-- 비밀번호 -->
						<div class="form-group">
							<label class="form-text" for="input-pass">비밀번호</label> 
							<input type="text" id="input-pass" name="password" value="" placeholder="비밀번호를 입력하세요"	>
						</div>
						
						<%if("fail".equals(result)) {%>
						<p>
							로그인에 실패했습니다.
							아이디와 비밀번호를 확인해주세요.
						</p>
						<% } %>
						<!-- 버튼영역 -->
		                <div class="button-area">
		                    <button type="submit" id="btn-submit">로그인</button>
		                </div>

						<input type="hidden" name="action" value="login">
						
					</form>
				</div>
				<!-- //loginForm -->
			</div>
			<!-- //user -->
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
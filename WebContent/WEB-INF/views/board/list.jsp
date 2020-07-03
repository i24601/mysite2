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
				<div id="list">
					<form action="/mysite2/bc" method="get">
						<div class="form-group text-right">
							<input type="text" name="str">
							<button type="submit" id=btn_search>검색</button>
							<input type="hidden" name="action" value="list">
							<input type="hidden" name="page" value="1">
						</div>
					</form>
					<table >
						<thead>
							<tr>
								<th>번호</th>
								<th>제목</th>
								<th>글쓴이</th>
								<th>조회수</th>
								<th>작성일</th>
								<th>관리</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${bList}" var="vo">
							<tr>
								<td>${vo.no}</td>
								<td class="text-left"><a href="/mysite2/bc?action=read&page=${param.page}&no=${vo.no}&str=${param.str}">${vo.title}</a></td>
								<td>${vo.name}</td>
								<td>${vo.hit}</td>
								<td>${vo.reg_date}</td>
								<td>
								<c:if test="${sessionScope.authUser.no eq vo.user_no}">								
								<a href="/mysite2/bc?action=delete&no=${vo.no}&page=${param.page}&str=${param.str}">[삭제]</a>
									</c:if>
								</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
		
					<div id="paging">
						<ul>
							<li><a href="/mysite2/bc?action=list&page=${btn_left}&str=${param.str}">◀</a></li>
								<c:forEach begin="${start}" end="${end}" var="x">
									<c:choose>
										<c:when test="${param.page eq x}">
										<li class="active"><a href="/mysite2/bc?action=list&page=${x}&str=${param.str}">${x}</a></li>
										</c:when>
										<c:otherwise>
										<li><a href="/mysite2/bc?action=list&page=${x}&str=${param.str}">${x}</a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>	
							<li><a href="/mysite2/bc?action=list&page=${btn_right}&str=${param.str}">▶</a></li>
							
						</ul>
						
						
						<div class="clear"></div>
					</div>
					
					<c:if test="${sessionScope.authUser.no ne null}">
						<a id="btn_write" href="/mysite2/bc?action=writeForm&page=${param.page}&str=${param.str}">글쓰기</a>
					</c:if>
				</div>
				<!-- //list -->
			</div>
			<!-- //board -->
		</div>
		<!-- //content  -->
		<div class="clear"></div>

		<c:import url="/WEB-INF/views/include/footer.jsp">
		</c:import>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>

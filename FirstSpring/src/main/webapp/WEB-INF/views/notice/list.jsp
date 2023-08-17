<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>
<title>공지게시판 목록</title>
</head>
<body>
	<div class="jumbotron">
		<div class="container">
			<h1 class="display-3">공지게시판 목록</h1>
		</div>
	</div>
	<div class="container">
			<div>
				<div class="text-right">
					<span>전체 건	</span>
				</div>
			</div>
			<div style="padding-top: 50px">
				<div class="row" style="padding-bottom: 20px">
					<div class="col-md-7"></div>
					<div class="col-md-5">
						<div class="row">
							<div class="col-md-2"></div>
							<div class="col-md-10">
								<form action="" method="post" id="searchForm" >
									<input type="hidden" name="page" id="page"/>
									<select class="form-control"name="searchType">
										<option value="title" <c:if test="${searchType == 'title' }"><c:out value="selected"/></c:if>>제목</option>
										<option value="writer" <c:if test="${searchType == 'writer' }"><c:out value="selected"/></c:if>>작성자</option>
										<option value="both" <c:if test="${searchType == 'both' }"><c:out value="selected"/></c:if>>제목+작성자</option>
									</select>
									<input type="text" name="searchWord" class="form-control float-right" value="${searchWord }" placeholder="Search"/>
									<input type="submit" value="검색"/>
								</form>
							</div>
						</div>
					</div>
				</div>	
				<table class="table">
					<thead class="table-dark">
						<tr>
							<th>번호</th>
							<th>제목</th>
							<th>작성일</th>
							<th>작성자</th>
							<th>조회수</th>
						</tr>
					</thead>
					<tbody>
						<c:set value="${pagingVO.dataList }" var="noticeList"/>
						<c:choose>
							<c:when test="${empty noticeList }">
								<tr>
									<td colspan="5">조회하신 게시글이 존재하지 않습니다.</td>
								</tr>
							</c:when>
							<c:otherwise>
								<c:forEach items="${noticeList }" var="notice">
									<tr class="nolist">
										<td>${notice.boNo }</td>
										<td>${notice.boTitle }</td>
										<td>${notice.boDate }</td>
										<td>${notice.boWriter }</td>
										<td>${notice.boHit }</td>
									</tr>
									<!-- </a> -->
								</c:forEach>
							</c:otherwise>
						</c:choose>
					</tbody>
				</table>
			</div>
			<div align="left">
				<a href="/notice/form.do" onclick="checkForm(); return false;" class="btn btn-primary">&laquo;글쓰기</a>
			</div>
		<div class="card-footer clearfix" id="pagingArea">
			${pagingVO.pagingHTML }
		</div>
		<hr>
	</div>
</body>
<script type="text/javascript">
$(function(){
	var pagingArea = $('#pagingArea');
	var searchForm = $('#searchForm');
	
	pagingArea.on("click", "a", function(event){
		event.preventDefault();
		var pageNo = $(this).data("page");
		searchForm.find('#page').val(pageNo);
		searchForm.submit();
	})
	
	$('.nolist').on("click", function(){
		var boNo = $(this).find('td:first-child').html();
		location.href="/notice/detail.do?boNo="+boNo+"";
	})
	
	$('tr').hover(function(){
		$(this).css('background', '#FDEBC8');
	},function(){
		$(this).css('background', 'none');
	}) 
})
</script>
</html>






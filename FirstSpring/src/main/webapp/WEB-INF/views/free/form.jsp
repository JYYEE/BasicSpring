<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" href="${pageContext.request.contextPath }/resources/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/resources/plugins/jquery/jquery.min.js"></script>
<title>자유게시판 등록 /수정</title>
</head>
<body>
	<c:set value="등록" var="name"/>
		<c:if test="${status eq 'u' }">
			<c:set value="수정" var="name"/>
		</c:if>
	<div class="jumbotron">
		<div class="container">
			<h3 class="display-3">자유 게시판 ${name }</h3>
		</div>
	</div>
	<div class="container">
		<form name="newWrite" action="/free/insert.do" class="form-horizontal" method="post" id="freeForm">
			<c:if test="${status eq 'u' }">
				<input name="boNo" type="hidden" class="form-control" value="${free.boNo }">
			</c:if>
			<div class="form-group row">
				<label class="col-sm-2 control-label" >제목</label>
				<div class="col-sm-5">
					<input maxlength="133" name="boTitle" id="boTitle" type="text" class="form-control" placeholder="title" value="${free.boTitle }">
				</div>
			</div>
			<div class="form-group row">
				<label class="col-sm-2 control-label" >내용</label>
				<div class="col-sm-8">
					<textarea name="boContent" id="boContent" cols="50" rows="5" class="form-control" placeholder="content">${free.boContent }</textarea>
				</div>
			</div>
			<div class="form-group row">
				<div class="col-sm-offset-2 col-sm-10 ">
					<input type="button" id="formBtn" class="btn btn-primary " value="${name }">
					<c:if test="${status eq 'u' }">
						<a href = "/free/detail.do?boNo=${free.boNo }">
							<input type="button" class="btn btn-danger " value="취소 ">
						</a>						
					</c:if>				
					<c:if test="${status ne 'u' }">
						<a href = "/free/list.do">
							<input type="button" class="btn btn-success float-right" value="목록">
						</a>						
					</c:if>				
					
				</div>
			</div>
		</form>
		<hr>
	</div>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
<script type="text/javascript">
$(function(){
	CKEDITOR.replace("boContent");
	CKEDITOR.config.allowedContent = true;
	var freeForm = $('#freeForm');
	var formBtn = $('#formBtn');
	
	formBtn.on("click", function(){
		var title = $('#boTitle').val().trim();
		var content = CKEDITOR.instances.boContent.getData().trim();
		
		if(title == ""){
			alert("제목을 입력해주세요.");
			return false;
		}
		if(content == ""){
			alert("내용을 입력해주세요.");
			return false;
		}
		if($(this).val() == "수정"){
			freeForm.attr("action", "/free/update.do"); // 수정을 하러 가기 위해 url경로를 틀어준다.
		}
	
		
		freeForm.submit();
	})
	
})
</script>
</html>




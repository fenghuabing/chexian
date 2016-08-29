<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ include file="/common/meta.jsp"%>
<style type="text/css">
.logo_top_left {
	background-color: #428bca;
	width: auto;
	height: 46px;
	margin: 0 5px;
	border-radius: 3px;
	float: left;
	box-sizing: border-box;
	display: block;
	padding-left: 5px;
	padding-right: 5px;
}

.border-bottom {
	border-bottom: 1px solid #eee;
}

.padding-top-15 {
	padding-top: 15px;
}

.padding-15-tb {
	padding: 15px 0px 15px 0px;
}

.line-height-34 {
	line-height: 34px;
}

div.dataTables_info {
	float: left;
	padding-top: 4px;
}

select.form-control {
	width: auto;
}

.p0 {
	padding: 0px;
}
</style>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/add_user_validate.js"></script>
<script type="text/javascript">
	function provinceChange(){
		var prov = $("#selectProvince").val();
		if (prov != '00'){
			$.post("city",{"prov":prov},function(result){
				$("#selectCity").children("option").detach().append();
				$("#selectCity").append("<option>--请选择城市--</option>");
				$.each(result.cities, function(key) {
					$("#selectCity").append('<option value="'+result.cities[key]["id"]+'">'+result.cities[key]["name"]+'</option>');
				});
			});
		}
	}
</script>
</head>

<body>
	<c:set var="currentNav" value="search"></c:set>
	<%@ include file="/common/nav.jsp"%>
	<main>
	<div class="container-fluid">
		<div class="row border-bottom padding-top-15 ">
			<div class="col-xs-4 text-left">
				<ol class="breadcrumb">
					<li><a href="#">用户</a></li>
					<li><a href="#" class="active">添加</a></li>
				</ol>
			</div>
		</div>
		<div class="row padding-15-tb">
			<div class="col-xs-12">
				<form id="addUserForm" class="form-horizontal" action="create">					
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">用户名称</label>
						<div class="col-xs-7">
							<input name="username" type="text" class="form-control"
								 placeholder="用户名称" required="required">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">密码</label>
						<div class="col-xs-7">
							<input type="password" class="form-control" 
								name="password" placeholder="密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">确认密码</label>
						<div class="col-xs-7">
							<input type="password" class="form-control" 
								name="comfirmpassword" placeholder="确认密码">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">联系人</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" 
								name="linkman" placeholder="联系人">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">公司</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" 
								name="company_name" placeholder="公司">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">电话</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" 
								name="phone" placeholder="电话">
						</div>
					</div>
					<c:if test="${admin.level == 1}">
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">省份</label>
						<div class="col-xs-7">
							<select id="selectProvince" name="province" onChange="javascript:provinceChange()">
								<option value="00">--请选择省份--</option>
								<c:forEach items="${provinces }" var="p">
									<option value="${p.vc2areaGuid }">${p.vc2areaName }</option>
								</c:forEach>
							</select>
							<select id="selectCity" name="cityId">
								<option>--请选择城市--</option>
							</select>
						</div>
					</div>
					</c:if>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">备注</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" 
								name="comment" placeholder="备注">
						</div>
					</div>

					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-8 text-center">
							<button type="submit" class="btn btn-primary" id="btn_add">创建</button>
							<button type="reset" class="btn btn-default">重置</button>
							<a href="list" type="reset" class="btn btn-default">返回</a>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	</main>

</body>
</html>

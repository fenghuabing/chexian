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
</style>
</head>

<body>
	<%@ include file="/common/nav.jsp"%>
	<main>
	<div class="container-fluid">
		<div class="row border-bottom padding-top-15 ">
			<div class="col-xs-4 text-left">
				<ol class="breadcrumb">
					<li><a href="#">用户</a></li>
					<li>
						<a href="#" class="active">
							<c:if test="${user.level == 1}">地市管理员</c:if>
							<c:if test="${user.level == 2}">采集人员</c:if>
						</a>
					</li>
				</ol>
			</div>
			<div class="col-xs-8 text-right">
				<a class="btn btn-success" href="./add">
				<c:if test="${user.level == 1}">添加地市管理员</c:if>
				<c:if test="${user.level == 2}">添加采集人员</c:if>
				</a>
			</div>
		</div>

		<div class="row padding-15-tb">
			<div class="col-xs-12">
				<table id="datalist"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>ID</th>
							<th>username</th>
							<th>联系人</th>
							<th>电话</th>
							<th>城市</th>
							<th>创建时间</th>
							<th width="20"></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(document).ready(function() {
			var table = $('#datalist').dataTable({
				"bAutoWidth" : false,
				"bPaginate" : false, //是否分页。
				"bServerSide" : true,
				"bProcessing" : true,
				"searching" : true,
				"bLengthChange" : true,
				"scrollCollapse" : true,
				"order" : [ [ 0, "desc" ] ],
				//"scrollY": "400px",
				"pagingType" : "full_numbers",
				"aLengthMenu" : [ 20, 50, 100 ],
				// "dom": '<"toolbar">frtip',
				// "sAjaxSource": "Handler.ashx?actionname=getdatalist2",
				"ajax" : {
					"url" : "${pageContext.request.contextPath}/user/load",
					"dataType" : "json",
					"method" : "POST"
				},
				"columns" : [
					{
						"data" : "id"
					},
					{
						"data" : "username"
					},
					{
						"data" : "linkman"
					},
					{
						"data" : "phone"
					},
					{
						"data" : "cityName"
					},
					{
						"data" : "createTime"
					},
					{

						"render" : function(data, type, row) {
							return '--';
						}
					}
				],
				"oLanguage" : {
					"sUrl" : "${pageContext.request.contextPath}/thirdpart_framework/jquery-datatable/language/zh_CN.json"
				}
			});
		});
	</script> </main>
</body>
</html>

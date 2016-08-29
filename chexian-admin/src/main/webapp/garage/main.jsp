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
ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:500px;overflow-y:scroll;overflow-x:auto;}

</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/thirdpart_framework/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/thirdpart_framework/ztree/js/jquery.ztree.core.min.js"></script>
	<SCRIPT type="text/javascript">
		var setting = {
			data: {
				key: {
					title:"t"
				},
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeClick: beforeClick,
				onClick: onClick
			}
		};
		
		
		var zNodes = ${tree};

		var cityId = '';
		var className = "dark";
		function beforeClick(treeId, treeNode, clickFlag) {
			className = (className === "dark" ? "":"dark");
			return (treeNode.click != false);
		}
		function onClick(event, treeId, treeNode, clickFlag) {
			console.log('onClick: '+treeNode.id);
			if (cityId != treeNode.id){
				cityId = treeNode.id;
				search();
			}
		}		

		function search() {
			$('#datalist').dataTable().fnDraw();
		}

		$(document).ready(function(){
			$.fn.zTree.init($("#treeDemo"), setting, zNodes);
			var table = $('#datalist').dataTable({
				"bAutoWidth" : false,
				"bPaginate" : true, //是否分页。
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
					"url" : "${pageContext.request.contextPath}/garage/load",
					"data" : function(d) {
						d.start_date = $("#start").val();
						d.end_date = $("#end").val();
						d.city_id = cityId;
						d.status = $("#status").val();
					},
					"dataType" : "json",
					"method" : "POST"
				},
				"columns" : [
						{
							"data" : "id"
						},
						{
							"data" : "name"
						},
						{
							"data" : "legalPerson"
						},
						{
							"data" : "province"
						},
						{
							"data" : "city"
						},
						{
							"data" : "regFund"
						},
						{
							"render" : function(data, type, row) {
								console.log('status: '+row.status);
								if (row.status == 'SUBMIT'){
									return '待审批';
								}
								else if (row.status == 'AUDIT'){
									return '已审批';
								}
								else{
									return '待补充';
								}
							}
						},
						{
							"render" : function(data, type, row) {
								return '<a type="button" class="btn btn-primary btn-xs" href="${pageContext.request.contextPath}/garage/info?id=' + row.id + '" target="_blank">详情</a>';
							}
						}

				],
				"oLanguage" : {
					"sUrl" : "${pageContext.request.contextPath}/thirdpart_framework/jquery-datatable/language/zh_CN.json"
				}
			});
		});

	</SCRIPT>
</head>

<body>
	<%@ include file="/common/nav.jsp"%>
	<main>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-2 zTreeDemoBackground left">
				<ul id="treeDemo" class="ztree"></ul>
			</div>
			<div class="col-xs-10">
				<div class="form-inline text-right" style="margin-top: 10px;" role="form">
					<input type="hidden" id="city_id" />
					<div class="form-group col-xs-2">
						<div class="input-group" >
							<div class="input-group-addon">状态</div>
							<div style="position: relative;">
								<select id="status" name="status" onchange="search();" class="selectpicker show-menu-arrow">
									<option value="all">全部</option>
					            	<c:forEach items="${status }" var="item">
						                <option value="${item }">${item.key }</option>
					            	</c:forEach>
								</select>
							</div>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">从</div>
							<input id="start" name="start" class="form-control "
								placeholder="请选择开始日期" type="text"
								onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,onpicked:search,oncleared:search})" />
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">到</div>
							<input id="end" name="end" class="form-control" type="text"
								placeholder="请选择结束日期"
								onFocus="WdatePicker({startDate:'%y-%M-01 00:00:00',dateFmt:'yyyy-MM-dd',alwaysUseStartDate:true,onpicked:search,oncleared:search})" />
						</div>
					</div>
				</div>
				<table id="datalist"
					class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>id</th>
							<th>名称</th>
							<th>法人</th>
							<th>省</th>
							<th>市</th>
							<th>注册资金</th>
							<th>状态</th>
							<th width="40"></th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</main>
</body>
</html>
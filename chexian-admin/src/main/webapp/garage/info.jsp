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

</head>

<body>
	<c:set var="currentNav" value="search"></c:set>
	<%@ include file="/common/nav.jsp"%>
	<main>
	<div class="container-fluid">
		<div class="row border-bottom padding-top-15 ">
			<div class="col-xs-4 text-left">
				<ol class="breadcrumb">
					<li><a href="#">审核</a></li>
					<li><a href="#">维修企业详情</a></li>
				</ol>
			</div>
		</div>
		<div class="row padding-15-tb">
			<div class="col-xs-12">
				<form id="form_task" class="form-horizontal"
					action="${pageContext.request.contextPath}/garage/check">
					<input type="hidden" name="id" value="${fac.id}" />

					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">名称</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.name}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">省</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.province}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">市</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.city}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">区县</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.district}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">注册地址</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.regAddress}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">经营地址</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.serviceAddress}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">经度</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.lon}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">纬度</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.lat}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">法人</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.legalPerson}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">维修资质</label>
						<div class="col-xs-7">
							<select disabled>
				            	<c:forEach items="${qualif }" var="item">
					                <option value="${item }"<c:if test="${item == fac.qualif }">selected</c:if>>${item.key }</option>
				            	</c:forEach>
				            </select>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">企业类型</label>
						<div class="col-xs-7">
							<select disabled>
				            	<c:forEach items="${category }" var="item">
					                <option value="${item }" <c:if test="${item == fac.category }">selected</c:if>>${item.key }</option>
				            	</c:forEach>
				            </select>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">注册资金</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.regFund}" readOnly="true">							
						</div>
					</div>

					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">18位社会信用代码</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.creditCode}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">工商营业执照注册号</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.businessLisenceCode}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">组织机构代码</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.orgCode}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">经营范围</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.businessScope}" readOnly="true">							
						</div>
					</div>


					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">营业期限</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" value="${fac.expire}" readOnly="true">							
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">工商营业执照副本</label>
						<div class="col-xs-7">
							<c:if test="${not empty fac.imgBusinessLisence}">
								<img alt="" src="/static/${fac.imgBusinessLisence}">
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">组织机构代码证</label>
						<div class="col-xs-7">
							<c:if test="${not empty fac.imgOrgCode}">
								<img alt="" src="/static/${fac.imgOrgCode}">
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">道路运输经营许可证</label>
						<div class="col-xs-7">
							<c:if test="${not empty fac.imgBusinessCert}">
								<img alt="" src="/static/${fac.imgBusinessCert}">
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">维修企业照片</label>
						<div class="col-xs-7">
							<c:if test="${fac.imgFactoryList.size()>0 }">
								<c:forEach var="item" items="${fac.imgFactoryList }">
									<img alt="" src="/static/${item}">
								</c:forEach>
							</c:if>
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">审批结果备注</label>
						<div class="col-xs-7">
							<textarea id="comment" name="comment" rows="3" cols="80">${fac.comment }</textarea>
						</div>
					</div>
					<c:choose>
						<c:when test="${fac.status == 'SUBMIT'}">
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-8 text-center">
									<button type="button" class="btn btn-default" onclick="javascript:check(1)">审批</button>
									<button type="button" class="btn btn-default" onclick="javascript:check(-1)">拒绝</button>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-8 text-center">
									<label for="inputEmail" class="col-xs-3 control-label">
										<c:if test="${fac.status == 'AUDIT'}">审核通过</c:if>
										<c:if test="${fac.status == 'PENDING'}">审核拒绝</c:if>
									</label>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</form>
			</div>
		</div>
	</div>

	</main>
	<script type="text/javascript">
		function check(status){
			console.log('ll: '+$('#comment').val());
			var data = {"id":'${fac.id}',"status":status,"comment":$('#comment').val()};
			$.post('check', data).success(function(e){
				if (e.result == 1){
					alert(e.msg);
				}
    	    	window.location = '${pageContext.request.contextPath}/garage/info?id='+id;
    	    }).error(function(err){
    	    	console.log(err);
    	    });
		}
	</script>

</body>
</html>

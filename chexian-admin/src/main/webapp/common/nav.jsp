<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="com.picc.chexian.admin.util.security.SpringSecurityUtils"%>
<div class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target=".navbar-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand">人保车险</a>
		</div>
		<div class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">审核<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<security:authorize ifAnyGranted="R_ADMIN,R_CITY_ADMIN">
								<li class=""><a
									href="${pageContext.request.contextPath}/garage/main">维修企业</a></li>
							</security:authorize>
						</ul></li>
					<li class="dropdown"><a class="dropdown-toggle"
						data-toggle="dropdown" href="#">用户<span class="caret"></span></a>
						<ul class="dropdown-menu">
							<security:authorize ifAnyGranted="R_ADMIN">
								<li class=""><a
									href="${pageContext.request.contextPath}/user/list">地市管理员</a></li>
							</security:authorize>
							<security:authorize ifAnyGranted="R_CITY_ADMIN">
								<li class=""><a
									href="${pageContext.request.contextPath}/user/list">采集人员</a></li>
							</security:authorize>
						</ul></li>
			</ul>
			
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown"><a class="dropdown-toggle"
					data-toggle="dropdown"><%=SpringSecurityUtils.getCurrentUserName()%>
						<b class="caret"></b></a>
					<ul class="dropdown-menu">
						<li><a
							href="${pageContext.request.contextPath}/user/passwd">修改密码</a></li>
						<li><a
							href="${pageContext.request.contextPath}/j_spring_security_logout">退出</a></li>
					</ul></li>
			</ul>
		</div>
	</div>
</div>

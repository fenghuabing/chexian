<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>人保车险</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="styles/weui-0.4.2.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
    </head>
    <body>
        <div class="weui_cells_title"><h2>登录</h2></div>

        <form id="form" method="POST" action="j_spring_security_check">
			<div class="weui_cells weui_cells_form">
				<c:if test="${not empty errorMessage}">
				<div class="weui_msg">
				    <div class="weui_icon_area"><i class="weui_icon_info weui_icon_msg"></i></div>
				    <div class="weui_text_area">
				        <h2 class="weui_msg_title">${errorMessage}</h2>
				    </div>
				</div>
				</c:if>
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">用户名</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="username" class="weui_input" type="text" placeholder="请输入用户名">
			        </div>
			    </div>
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">密码</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="password" class="weui_input" type="password" placeholder="请输入密码">
			        </div>
			    </div>
			</div>
        </form>
		
        <div class="weui_btn_area">
		    <a class="weui_btn weui_btn_primary" href="javascript:" id="btn_ensure">确定</a>
		</div>
		
    </body>
    <script type="text/javascript">
    $(function () {  
    	$('#btn_ensure').click(function(){
    		$('form').submit();
    	});
	});  
    </script>
</html>
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
        <div class="weui_cells_title"><h2>修改密码</h2></div>

        <form id="form" method="POST" action="passwd">
			<div class="weui_cells weui_cells_form">
				<c:if test="${not empty message}">
				<div class="weui_msg">
				    <div class="weui_icon_area"><i class="weui_icon_info weui_icon_msg"></i></div>
				    <div class="weui_text_area">
				        <h2 class="weui_msg_title">${message}</h2>
				    </div>
				</div>
				</c:if>
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">密码</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="password" class="weui_input" type="password" placeholder="请输入密码">
			        </div>
			    </div>
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">确认密码</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="repassword" class="weui_input" type="password" placeholder="请输入确认密码">
			        </div>
			    </div>
			</div>
        </form>
		
        <div class="weui_btn_area">
		    <a class="weui_btn weui_btn_primary" href="javascript:" id="btn_ensure">确定</a>
		</div>
		
		<div class="weui_dialog_alert" style="display: none;">
		    <div class="weui_mask"></div>
		    <div class="weui_dialog">
		      <div class="weui_dialog_hd"> <strong class="weui_dialog_title">警告</strong>
		      </div>
		      <div class="weui_dialog_bd">弹窗内容，告知当前页面信息等</div>
		      <div class="weui_dialog_ft">
		        <a href="javascript:;" class="weui_btn_dialog primary">确定</a>
		      </div>
		    </div>
		  </div>

    </body>
    <script type="text/javascript">
    $.weui = {};  
    $.weui.alert = function(options){  
    	options = $.extend({title: '警告', text: '警告内容'}, options);  
	    var $alert = $('.weui_dialog_alert');  
	    $alert.find('.weui_dialog_title').text(options.title);  
	    $alert.find('.weui_dialog_bd').text(options.text);  
	    $alert.on('touchend click', '.weui_btn_dialog', function(){  
	      $alert.hide();  
	    });  
	    $alert.show();  
	};
	
	$(function () {  
    	$('#btn_ensure').click(function(){
    		if (!$("input[name='password']").val() || !$("input[name='repassword']").val() ){
    			$.weui.alert({title:'提示', text: '请输入完整'});
    			return;
    		}
    		if ($("input[name='password']").val() != $("input[name='repassword']").val() ){
    			$.weui.alert({title:'提示', text: '两次输入不一致'});
    			return;
    		}
    		$('form').submit();
    	});
	});  
    </script>
</html>
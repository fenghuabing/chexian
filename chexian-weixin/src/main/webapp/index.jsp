<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width,initial-scale=1,user-scalable=0">
        <title>人保车险-门店采集信息</title>
        <!-- 引入 WeUI -->
        <link rel="stylesheet" href="styles/weui-0.4.2.min.css"/>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
    </head>
    <body>
        <div class="weui_tab">
		    <div class="weui_navbar">
		        <div id="all" class="weui_navbar_item weui_bar_item_on"> 全部 </div>
		        <div id="sub1" class="weui_navbar_item">  已提交 </div>
		        <div id="sub2" class="weui_navbar_item">  待补充 </div>
		    </div>
		    <div class="weui_tab_bd">
		    	<div id="all_content"  class="weui_cells weui_cells_access">
		        	<c:if test="${all.size() >0 }">
		        	<c:forEach var="item" items="${all }">
		            <a class="weui_cell" href="input?id=${item.id }">
		                <div class="weui_cell_bd weui_cell_primary">
		                    <p>${item.id } -- ${item.name }</p>
		                </div>
		            </a>
		        	</c:forEach>
		        	</c:if>
		        </div>
		        <div id="sub1_content" style="display:none" class="weui_cells weui_cells_access">
		        	<c:if test="${submit.size() >0 }">
		        	<c:forEach var="item" items="${submit }">
		            <a class="weui_cell" href="input?id=${item.id }">
		                <div class="weui_cell_bd weui_cell_primary">
		                    <p>${item.id } -- ${item.name }</p>
		                </div>
		            </a>
		        	</c:forEach>
		        	</c:if>
		        </div>
		        <div id="sub2_content"  style="display:none" class="weui_cells weui_cells_access">
		        	<c:if test="${pending.size() >0 }">
		        	<c:forEach var="item" items="${pending }">
		            <a class="weui_cell" href="input?id=${item.id }">
		                <div class="weui_cell_bd weui_cell_primary">
		                    <p>${item.id } -- ${item.name }</p>
		                </div>
		            </a>
		        	</c:forEach>
		        	</c:if>
		        </div>
		    </div>
		</div>

		
        <div class="weui_btn_area">
		    <a class="weui_btn weui_btn_primary" href="input" id="btn_ensure">继续录入</a>
		</div>
        <div class="weui_btn_area">
		    <a class="weui_btn weui_btn_primary" href="passwd" id="btn_passwd">修改密码</a>
		</div>
    </body>
    <script type="text/javascript">
    $(function () {  
    	$(".weui_tab .weui_navbar_item").click(function(){
            $(this).addClass("weui_bar_item_on").siblings().removeClass('weui_bar_item_on');
            $('#'+$(this).attr("id")+'_content').css("display","").siblings().css("display","none");
        });
	});  
    </script>
</html>
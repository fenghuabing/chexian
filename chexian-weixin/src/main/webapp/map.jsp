<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, minimum-scale=1, user-scalable=no, minimal-ui">
<link href="http://cdn.bootcss.com/weui/0.4.2/style/weui.css"
	rel="stylesheet">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/js/jquery-2.1.1.min.js"></script>
<title></title>
</head>
<body>
	<div>
		<h2>地理位置标注</h2>
		<input id="txtAddress" type="text" value="${city.vc2areaName }"/> 
		<a id="btn_search" href="javascript:;" class="weui_btn weui_btn_mini weui_btn_primary">搜索</a>
	</div>
	<div style="height: 2px;"></div>

	<!--百度地图显示的地方-->
	<div class="weui_msg" style="height: 300px;" id="container"></div>
	<!--百度地图显示的地方-->
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.2"></script>
	<script type="text/javascript">
    var map = new BMap.Map("container");//在指定的容器内创建地图实例
    map.setDefaultCursor("crosshair");//设置地图默认的鼠标指针样式
    map.enableScrollWheelZoom();//启用滚轮放大缩小，默认禁用。
    
    //根据经纬度选择默认范围
	<c:choose>
		<c:when test="${not empty fac.lon && not empty fac.lat}">
	    map.centerAndZoom(new BMap.Point('${fac.lon}', '${fac.lat}'), 13);
	    map.addOverlay(new BMap.Marker(new BMap.Point('${fac.lon}', '${fac.lat}')));
		</c:when>
		<c:otherwise>
	    map.centerAndZoom("${city.vc2areaName}", 13);//默认地图的显示范围
		</c:otherwise>
	</c:choose>
    map.addControl(new BMap.NavigationControl());

    map.addEventListener("click", function (e) {//地图右键单击事件，左键为click
        map.clearOverlays();//添加标注前清空以前的所有标注
        $('#lon').val(e.point.lng);
        $('#lat').val(e.point.lat);
        var marker = new BMap.Marker(new BMap.Point(e.point.lng, e.point.lat));        // 创建标注     
        map.addOverlay(marker);
    });

    function sear(result) {//根据地质搜索范围
        var local = new BMap.LocalSearch(map, {
            renderOptions: { map: map }
        });
        local.search(result);
    }
    
    $(function(){
    	$('#btn_search').click(function(){
    		sear($('#txtAddress').val());
    	});

    	$('#btn_ensure').click(function(){
            lon = $('#lon').val();
            lat = $('#lat').val();
            if (!lon || !lat){
            	alert('请先标注位置！');
            }
    		var data = {"id":'${fac.id}',"lon":lon,"lat":lat};
    	    console.log(data);
    	    $.post('location', data).success(function(msg){
    		    console.log(msg);
    		    alert(msg.msg);
    		    window.location="index";
    	    }).error(function(err){
    	    	console.log(err);
    	    });
    	});
    });
</script>
	<div class="weui_cell">
		<div class="weui_cell_hd">
			<label class="weui_label">经度</label>
		</div>
		<div class="weui_cell_bd weui_cell_primary">
			<input id="lon" name="lon" value="${fac.lon }" class="weui_input" type="text" readonly>
		</div>
	</div>
	<div class="weui_cell">
		<div class="weui_cell_hd">
			<label class="weui_label">纬度</label>
		</div>
		<div class="weui_cell_bd weui_cell_primary">
			<input id="lat" name="lat" value="${fac.lat }" class="weui_input" type="text" readonly>
		</div>
	</div>

	<div class="weui_btn_area">
		<a class="weui_btn weui_btn_primary" href="javascript:"
			id="btn_ensure">确定</a>
	</div>
</body>
</html>
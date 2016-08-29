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
	src="${pageContext.request.contextPath}/js/add_taskretain_validate.js"></script>
	
<script type="text/javascript" >
   
	window.onload = function() {
		var retainRate = '${task.retainRate}';
		var retainRateObj = eval(retainRate);;
		$("#createtable").empty();
		var trString = "<table class=\"table table-bordered\" border=\"1\">";
 
		trString += xunTH(retainRateObj, -1, "");

		trString += "</table>";

		$("#createtable").append(trString).show();
	}

	
	function xunTH(retainRateObj, i, trString) {
		trString += "<tr>"
		for (var j = 0; j < retainRateObj.length; j++) {
			
			if (j > i) {
				if('2' == j+1 || '4' == j+1 || '8' == j+1 || '16' == j+1 || '31' == j+1 ){
					trString += "<th><input type = \"text\" class=\"form-control\" style=\"color:red\" name = \"retainRate\" value =\""
						+ retainRateObj[j]
						+ "\" id=\""
						+ (j)
						+ "\" onBlur =\"checkRate('"+(j)+"')\"></th>";
				}else{
					trString += "<th><input type = \"text\" class=\"form-control\" name = \"retainRate\" value =\""
						+ retainRateObj[j]
						+ "\" id=\""
						+ (j)
						+ "\" onBlur =\"checkRate('"+(j)+"')\"></th>";
				}
				
				i = j;
				if ((j + 1) % 10 == 0 && j != 1) {
					trString += "</tr>"
					xunTH(retainRateObj, i, trString);
				}
			}
		}
		return trString;
	}
	function CreateTable() {
		$("#createtable").empty();
		var cellCount = $("#days").val();
		if(cellCount >100 || cellCount<1){
			return;
		}
		var num = 0;
		var trString = "<table class=\"table table-bordered\" border=\"1\">";
		for (var i = 0; i < cellCount / 10; i++) {
			trString += "<tr>";
			if (cellCount - i * 10 >= 10) {
				num = 10;
			} else {
				num = cellCount - i * 10;
			}
			// for(var j=0;j<num;j++)
			// {
			//         trString += "<th>"+(i*10+j+1)+"日</th>"
			// }
			//  trString +="</tr><tr>"
			for (var j = 0; j < num; j++) {
				if ( 0 == j && 0 == i ){
					trString += "<th><input type = \"text\" disabled class=\"form-control\" name = \"retainRate\" id=\""
						+ (i * 10 + j + 1)
						+ "\" value =\"100\" required=\"required\"></th>"
				}else{
				   var day = i * 10 + j + 1;
				   if('2' == day || '4' == day || '8' == day || '16' == day || '31' == day ){
					   trString += "<th><input type = \"text\" class=\"form-control\" style=\"color:red\" name = \"retainRate\" id=\""
							+ (i * 10 + j + 1)
							+ "\" placeholder =\""+ (i * 10 + j + 1) + "日\" required=\"required\" onBlur =\"checkRate('"+(i * 10 + j + 1)+"')\"></th>";
					}else{
					   trString += "<th><input type = \"text\" class=\"form-control\" name = \"retainRate\" id=\""
							+ (i * 10 + j + 1)
							+ "\" placeholder =\""+ (i * 10 + j + 1) + "日\" required=\"required\" onBlur =\"checkRate('"+(i * 10 + j + 1)+"')\"></th>";
					}
				
				}
			}
			trString += "</tr>";
		}
		trString += "</table>";
		$("#createtable").append(trString).show();
	}
	
	function checkRate(id){
		var  rate = $('#'+id).val()
		if(rate <= 0 || isNaN(rate) || rate > 100){
			alert(id+"日留存,请输入有效数字");
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
			<div class="col-xs-3 text-left">
				<ol class="breadcrumb">
					<li><a href="#">管理</a></li>
					<li><a href="#">已发布深度任务</a></li>
					<li><a href="#" class="active">添加</a></li>
				</ol>
			</div>
		</div>
		<div class="row padding-15-tb">
			<div class="col-xs-12">
				<form id="form_task" class="form-horizontal"
					action="${pageContext.request.contextPath}/taskRetain/update">
					<input type="hidden" name="id" value="${task.id}" />
					
					<div class="form-group">
						<label class="col-xs-3  control-label">白名单</label>
						<div class="col-xs-7">
							<label class="radio-inline"> 
								<input type="radio" name="useWhiteList" id="useWhiteList1" value="1"
								<c:if test="${task.useWhiteList == 1}"> checked="checked" </c:if>>特定用户
							</label> 
							<label class="radio-inline"> 
								<input type="radio" name="useWhiteList" id="useWhiteList2" value="0"
								<c:if test="${task.useWhiteList == 0}"> checked="checked" </c:if>>
								所有用户
							</label>

						</div>
					</div>
					
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">任务名称</label>
						<div class="col-xs-7">
							<input name="taskname" type="text" class="form-control"
								value="${task.taskname}" id="taskname" placeholder="任务名称"
								required="required">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">任务描述</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="description"
								value="${task.description}" name="description"
								placeholder="任务描述">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">任务来源</label>
						<div class="col-xs-7">
							<select class="form-control" id="taskSource" name="taskSource">
								<option value="XIGUAMEI"
									<c:if test = "${'SELF' eq task.taskSource }">selected="selected"</c:if>>自有</option>
								<option value="I43"
									<c:if test = "${'I43' eq task.taskSource }">selected="selected"</c:if>>I43</option>
							</select>
						</div>
					</div>

					<div class="form-group">
						<label for="adId" class="col-xs-3  control-label">广告ID</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="adId" name="adId"
								value="${task.adId}" placeholder="渠道商提供，广告的唯一标识（必填）">
						</div>
					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">APPID</label>
						<div class="col-xs-7">
							<input name="appid" type="text" class="form-control" id="appid"
								placeholder="任务名称" required="required" value="${task.appid}">
						</div>
					</div>
					<div class="form-group">
						<label for="size" class="col-xs-3  control-label">应用大小</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="size" name="size"
								value="${task.size}" placeholder="应用大小">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">应用描述</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="appDescription"
								value="${task.appDescription}" name="appDescription"
								placeholder="应用描述">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">激活步骤</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="activeStep"
								value="${task.activeStep}" name="activeStep"
								placeholder="激活步骤">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">留存步骤</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="retainStep"
								value="${task.retainStep}" name="retainStep"
								placeholder="留存步骤">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">应用图标</label>
						<div class="col-xs-7">
							<div>
								<div class="col-sm-10 p0">
									<input class="form-control " type="text" name="img" id="img"
										value="${task.img}" />
								</div>
								<div class="col-sm-2 text-right p0"
									style="padding-right: 0px; padding-left: 5px;">
									<span class="btn btn-success fileinput-button"
										style="width: 70px;"> <i
										class="glyphicon glyphicon-plus"></i> <span>选择</span> <!-- The file input field used as target for the file upload widget -->
										<input id="fileupload" type="file" name="file" />
									</span>
								</div>
							</div>
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">任务类型</label>
						<div class="col-xs-7">
						   
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio1" value="1"
								<c:if test="${task.taskType == 1}"> checked="checked" </c:if>>
								快速任务
							</label> 
							 <label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="2"
								<c:if test="${task.taskType == 2}"> checked="checked" </c:if>>
								快速搜索任务
							</label> 
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="3"
								<c:if test="${task.taskType == 3}"> checked="checked" </c:if>>
								奖励任务
							</label> 
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="4"
								<c:if test="${task.taskType == 4}"> checked="checked" </c:if>>
								朗亿动态任务
							</label> 
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="5"
								<c:if test="${task.taskType == 5}"> checked="checked" </c:if>>
								ASO任务
							</label> 
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="6"
								<c:if test="${task.taskType == 6}"> checked="checked" </c:if>>
								Bat动态任务
							</label> 
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="7"
								<c:if test="${task.taskType == 7}"> checked="checked" </c:if>>
								WanPu动态任务
							</label>
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="9"
								<c:if test="${task.taskType == 9}"> checked="checked" </c:if>>
								点入快速任务
							</label>
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="101"
								<c:if test="${task.taskType == 101}"> checked="checked" </c:if>>
								第三方快速任务
							</label> <label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="102"
								<c:if test="${task.taskType == 102}"> checked="checked" </c:if>>
								第三方快速搜索任务
							</label> 
							<label class="radio-inline"> <input type="radio"
								name="taskType" id="inlineRadio2" value="103"
								<c:if test="${task.taskType == 103}"> checked="checked" </c:if>>
								第三方奖励任务
							</label>
						</div>
					</div>
					<div class="form-group"  id = "keyword_div">
						<label for="inputPassword" class="col-xs-3 control-label">关键词</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="keyWord"
								name="keyWord" placeholder="关键词" value="${task.keyWord}">
						</div>
					</div>
					<div class="form-group" id="searchOrderNum_div"> 
						<label for="inputPassword" class="col-xs-3 control-label">搜索结果排列位置</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="searchOrderNum"
								name="searchOrderNum" placeholder="搜索结果排列位置"
								value="${task.searchOrderNum}">
						</div>
					</div>
					<div class="form-group" id="total_div" >
						<label for="inputPassword" class="col-xs-3  control-label">总份数</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="total" name="total"
								value="${task.total}" placeholder="发布数量">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">优先级</label>
						<div class="col-xs-7">
							<label class="radio-inline"> <input type="radio"
								name="priority" id="inlineRadio1" value="0"
								<c:if test="${task.priority == 0}"> checked="checked" </c:if>>普通
							</label> <label class="radio-inline"> <input type="radio"
								name="priority" id="inlineRadio2" value="1"
								<c:if test="${task.priority == 1}"> checked="checked" </c:if>>
								高级
							</label>

						</div>
					</div>
					<div class="form-group">
						<label class="col-xs-3  control-label">对比方式</label>
						<div class="col-xs-7">
							<label class="radio-inline"> <input type="radio"
								name="compareType" id="compareType1" value="0"
								<c:if test="${task.compareType == 0}"> checked="checked" </c:if>>对比idfa
							</label> <label class="radio-inline"> <input type="radio"
								name="compareType" id="compareType2" value="1"
								<c:if test="${task.compareType == 1}"> checked="checked" </c:if>>
								不对比idfa
							</label>

						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3 control-label">下载地址</label>
						<div class="col-xs-7">
							<input type="text" class="form-control" id="downloadUrl"
								value="${task.downloadUrl}" name="downloadUrl"
								placeholder="下载地址">
						</div>
					</div>
					<div class="form-group">
						<label for="urlscheme" class="col-xs-3 control-label">urlscheme</label>
						<div class="col-xs-7">
							<input name="urlscheme" type="text" class="form-control"
								id="urlscheme" placeholder="urlscheme" 
								value="${task.urlscheme}">
						</div>
					</div>
					<div class="form-group">
						<label for="bundleid" class="col-xs-3 control-label">bundleid</label>
						<div class="col-xs-7">
							<input name="bundleid" type="text" class="form-control"
								id="bundleid" placeholder="bundleid" 
								value="${task.bundleid}">
						</div>
					</div>
					<div class="form-group">
						<label for="processName" class="col-xs-3 control-label">进程名称</label>
						<div class="col-xs-7">
							<input name="processName" type="text" class="form-control"
								id="processName" placeholder="进程名称" 
								value="${task.processName}">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3 control-label">激活奖励</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="award" name="award"
								value="${task.award}" placeholder="激活奖励">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3 control-label">留存奖励</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="awardRetain" name="awardRetain"
								value="${task.awardRetain}" placeholder="留存奖励">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3 control-label">接入价格</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="income" name="income"
								value="${task.income}" placeholder="接入价格">
						</div>
					</div>
					<div class="form-group">
						<label for="limittime" class="col-xs-3 control-label">审核时间</label>
						<div class="col-xs-2 s">
							<div class="input-group">
								<input type="text" class="form-control" name="limittime"
									id="limittime" value="${task.limit}" /> <span
									class="input-group-addon" id="basic-addon2">分</span>
							</div>
						</div>

					</div>
					<div class="form-group">
						<label for="inputEmail" class="col-xs-3 control-label">有效期时间</label>
						<div class="col-xs-3 s">

							<input type="text" class="form-control" name="startTime"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"
								value="<fmt:formatDate value="${task.startTime}" pattern="yyyy-MM-dd HH:mm:ss" />"
								id="startTime">
						</div>
						<div class="col-xs-3 e">
							<input type="text"
								value="<fmt:formatDate value="${task.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								class="form-control" name="endTime" id="endTime"
								onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">

						</div>
					</div>
					
					<div class="form-group">
						<label for="consumeRate" class="col-xs-3 control-label">期望消耗速率</label>
						<div class="col-xs-3 input-group">
							<input type="text" class="form-control" name="consumeRate"  value="${task.consumeRate }"/> <span class="input-group-addon" id="basic-addon2">份/分钟</span>
						</div>
					</div>
					
					<div class="form-group">
						<label for="numorder" class="col-xs-3 control-label">有效期排序</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="numorder"
								value="${task.numorder}" name="numorder" placeholder="有效期排序"
								value="0">
						</div>
					</div>

					<div class="form-group">
						<label for="numorder" class="col-xs-3 control-label">展示期排序</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="showNumorder"
								value="${task.showNumorder}" name="showNumorder"
								placeholder="展示期" value="0">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">显示平台</label>
						<div class="col-xs-2">
							<input type="text" class="form-control" id="displayMode" name="displayMode"
								value="${task.displayMode}" placeholder="默认填0，1代表游戏平台">
						</div>
					</div>
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">是否需要去重</label>
						<div class="col-xs-7">
							<select class="form-control" id="duplicate" name="duplicate">
								<option value="0"
									<c:if test = "${!task.duplicate}">selected="selected"</c:if>>否</option>
								<option value="1"
									<c:if test = "${task.duplicate}">selected="selected"</c:if>>是</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">是否需要激活上报</label>
						<div class="col-xs-7">
							<select class="form-control" id="activeUpload" name="activeUpload">
								<option value="0"
									<c:if test = "${!task.activeUpload}">selected="selected"</c:if>>否</option>
								<option value="1"
									<c:if test = "${task.activeUpload}">selected="selected"</c:if>>是</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
						<label for="inputPassword" class="col-xs-3  control-label">是否测试任务</label>
						<div class="col-xs-7">
							<select class="form-control" id="test" name="test">
								<option value="0"
									<c:if test = "${!task.test}">selected="selected"</c:if>>否</option>
								<option value="1"
									<c:if test = "${task.test}">selected="selected"</c:if>>是</option>
							</select>
						</div>
					</div>
					
					<div class="form-group">
					    <label for="inputPassword" class="col-xs-3  control-label">留存天数</label>
					    <div class="col-xs-2">
							<input type="text" class="form-control" id="days" name="days"
									placeholder="留存天数"  value="${task.days}" onchange = "CreateTable()">
					   </div>
					 </div>
					<div class="form-group">
	                   <label for="inputPassword" class="col-xs-3  control-label">留存率</label>
	                   
	                   <div class="center-block" style = "width:60%" id="createtable" >
	                    
	                   </div>
	                </div>
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-8 text-center">
							<button type="submit" class="btn btn-primary" id="btn_add">保存</button>
							<button type="reset" class="btn btn-default">重置</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	</main>

</body>
</html>

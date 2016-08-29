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
        <div class="weui_cells_title"><h2>维修企业信息录入</h2></div>

        <form id="form" action="${pageContext.request.contextPath}/add">
        	<input type="hidden" name="id" value="${fac.id }"/>
			<div class="weui_cells weui_cells_form">
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">名称</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="name" class="weui_input" type="text" placeholder="企业工商登记名称" value="${fac.name }">
			        </div>
			    </div>
			    <div class="weui_cell weui_cell_select">
			        <div class="weui_cell_hd" style="padding-left: 15px;">省份 </div>
			        <div class="weui_cell_bd weui_cell_primary" style="padding-left: 30px;">
			            <select class="weui_select" name="province">
			                <option value="${province.vc2areaGuid }" selected>${province.vc2areaName }</option>
			            </select>
			        </div>
			    </div>
			    <div class="weui_cell weui_cell_select">
			        <div class="weui_cell_hd" style="padding-left: 15px;">城市 </div>
			        <div class="weui_cell_bd weui_cell_primary" style="padding-left: 30px;">
			            <select class="weui_select" name="city">
			                <option value="${city.vc2areaGuid }" selected>${city.vc2areaName }</option>
			            </select>
			        </div>
			    </div>
			    <div class="weui_cell weui_cell_select">
			        <div class="weui_cell_hd" style="padding-left: 15px;">区县 </div>
			        <div class="weui_cell_bd weui_cell_primary" style="padding-left: 30px;">
			        	<select class="weui_select" name="district">
			        		<c:forEach items="${districts }" var="item">
			        			<option value="${item.vc2areaGuid }"<c:if test="${item.vc2areaGuid == fac.district }">selected</c:if>>${item.vc2areaName }</option>
			        		</c:forEach>
			        	</select>
			        </div>
			    </div>
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">注册地址</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="regAddress" class="weui_input" type="text" placeholder="注册地址" value="${fac.regAddress }">
			        </div>
			    </div>
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">经营地址</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="serviceAddress" class="weui_input" type="text" placeholder="经营地址" value="${fac.serviceAddress }">
			        </div>
			    </div>
			    <div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">法人</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="legalPerson" class="weui_input" type="text" placeholder="法人" value="${fac.legalPerson }">
			        </div>
			    </div>
			    <div class="weui_cell weui_cell_select">
			        <div class="weui_cell_hd" style="padding-left: 15px;">维修资质 </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <select class="weui_select" name="qualif">
			            	<c:forEach items="${qualif }" var="item">
				                <option value="${item }"<c:if test="${item == fac.qualif }">selected</c:if>>${item.key }</option>
			            	</c:forEach>
			            </select>
			        </div>
			    </div>
			    
			    <div class="weui_cell weui_cell_select">
			        <div class="weui_cell_hd" style="padding-left: 15px;">企业类型 </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <select class="weui_select" name="category">
			            	<c:forEach items="${category }" var="item">
			            	
				                <option value="${item }" <c:if test="${item == fac.category }">selected</c:if>>${item.key }</option>
			            	</c:forEach>
			            </select>
			        </div>
			    </div>
			    
			   	<div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">注册资金</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="regFund" class="weui_input" type="number" placeholder="注册资金" value="${fac.regFund }">
			        </div>
			    </div>
			    
			   	<div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">社会信用代码</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="creditCode" class="weui_input" type="text" placeholder="18位社会信用代码" value="${fac.creditCode }">
			        </div>
			    </div>
	
			   	<div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">工商营业执照</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="businessLisenceCode" class="weui_input" type="text" placeholder="工商营业执照注册号" value="${fac.businessLisenceCode }">
			        </div>
			    </div>
			    
			   	<div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">组织机构代码</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="orgCode" class="weui_input" type="text" placeholder="组织机构代码" value="${fac.orgCode }">
			        </div>
			    </div>
	
			   	<div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">经营范围</label>
			        </div>
			    </div>
			    <div class="weui_cell">
			        <div class="weui_cell_bd weui_cell_primary">
			            <textarea name="businessScope" class="weui_textarea" placeholder="经营范围" rows="3">${fac.businessScope }</textarea>
			            <div class="weui_textarea_counter"><span>0</span>/500</div>
			        </div>
			    </div>
	
			   	<div class="weui_cell">
			        <div class="weui_cell_hd">
			            <label class="weui_label">营业期限</label>
			        </div>
			        <div class="weui_cell_bd weui_cell_primary">
			            <input name="expire" class="weui_input" type="date" placeholder="营业期限" value="${fac.expire }">
			        </div>
			    </div>
			    
			    <div class="weui_cell">
			        <div class="weui_cell_bd weui_cell_primary">
			            <div class="weui_uploader">
			                <div class="weui_uploader_hd weui_cell">
			                    <div class="weui_cell_bd weui_cell_primary">营业执照副本</div>
			                    <div class="weui_cell_ft js_counter" id="cBusinessLisence"><c:choose><c:when test="${fac.imgBusinessLisence != null }">1</c:when><c:otherwise>0</c:otherwise></c:choose> /1</div>
			                </div>
			                <div class="weui_uploader_bd">
			                    <ul class="weui_uploader_files" id="BusinessLisence">
			                    <c:if test="${fac.imgBusinessLisence != null }">
			                    	<li class="weui_uploader_file" style="background-image:url('/static/${fac.imgBusinessLisence }')"></li>
			                    </c:if>
			                    </ul>
			                    <div class="weui_uploader_input_wrp">
			                        <input class="weui_uploader_input js_file" type="file" accept="image/*" multiple="" name="BusinessLisence">
				                    <input type="hidden" id="imgBusinessLisence" name="imgBusinessLisence" value="${fac.imgBusinessLisence }">
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>

			    <div class="weui_cell">
			        <div class="weui_cell_bd weui_cell_primary">
			            <div class="weui_uploader">
			                <div class="weui_uploader_hd weui_cell">
			                    <div class="weui_cell_bd weui_cell_primary">组织机构代码证</div>
			                    <div class="weui_cell_ft js_counter" id="cOrgCode"><c:choose><c:when test="${fac.imgOrgCode != null }">1</c:when><c:otherwise>0</c:otherwise></c:choose>/1</div>
			                </div>
			                <div class="weui_uploader_bd">
			                    <ul class="weui_uploader_files" id="OrgCode">
				                    <c:if test="${fac.imgOrgCode != null }">
				                    	<li class="weui_uploader_file" style="background-image:url('/static/${fac.imgOrgCode }')"></li>
				                    </c:if>
			                    </ul>
			                    <div class="weui_uploader_input_wrp">
			                        <input class="weui_uploader_input js_file" type="file" accept="image/*" multiple="" name="OrgCode">
				                    <input type="hidden" id="imgOrgCode" name="imgOrgCode" value="${fac.imgOrgCode }">
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>

			    <div class="weui_cell">
			        <div class="weui_cell_bd weui_cell_primary">
			            <div class="weui_uploader">
			                <div class="weui_uploader_hd weui_cell">
			                    <div class="weui_cell_bd weui_cell_primary">道路运输经营许可证</div>
			                    <div class="weui_cell_ft js_counter" id="cBusinessCert"><c:choose><c:when test="${fac.imgBusinessCert != null }">1</c:when><c:otherwise>0</c:otherwise></c:choose>/1</div>
			                </div>
			                <div class="weui_uploader_bd">
			                    <ul class="weui_uploader_files" id="BusinessCert">
				                    <c:if test="${fac.imgBusinessCert != null }">
				                    	<li class="weui_uploader_file" style="background-image:url('/static/${fac.imgBusinessCert }')"></li>
				                    </c:if>
			                    </ul>
			                    <div class="weui_uploader_input_wrp">
			                        <input class="weui_uploader_input js_file" type="file" accept="image/*" multiple="" name="BusinessCert">
				                    <input type="hidden" id="imgBusinessCert" name="imgBusinessCert" value="${fac.imgBusinessCert }">
			                    </div>
			                </div>
			            </div>
			        </div>
			    </div>

			    <div class="weui_cell">
			        <div class="weui_cell_bd weui_cell_primary">
			            <div class="weui_uploader">
			                <div class="weui_uploader_hd weui_cell">
			                    <div class="weui_cell_bd weui_cell_primary">维修企业照片</div>
			                    <div class="weui_cell_ft js_counter" id="cFactory">${fac.imgFactoryList.size()}/6</div>
			                </div>
			                <div class="weui_uploader_bd">
			                    <ul class="weui_uploader_files" id="Factory">
				                    <c:if test="${fac.imgFactoryList.size() > 0 }">
				                    	<c:forEach var="item" items="${fac.imgFactoryList }">
				                    	<li class="weui_uploader_file" style="background-image:url('/static/${item}')"></li>
				                    	</c:forEach>
				                    </c:if>
			                    </ul>
			                    <div class="weui_uploader_input_wrp">
			                        <input class="weui_uploader_input js_file" type="file" accept="image/*" multiple="" name="Factory">
				                    <input type="hidden" id="imgFactory" name="imgFactory" value="${fac.imgFactory }">
			                    </div>
			                </div>
			            </div>
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
	
	function popMap(){
		var city = $('#city').val();
		console.log('city='+city);
		if (!city){
			$.weui.alert({text: '请选填写省市区信息'});
			return;
		}
		var url = "map.jsp?city="+encodeURI(city);
		console.log('url='+url);
		var city = $('#city').val();
		window.open("map.jsp?city="+encodeURI(city)); 
	}
	
    $(function () {  
        // 允许上传的图片类型  
        var allowTypes = ['image/jpg', 'image/jpeg', 'image/png', 'image/gif'];  
        // 1024KB，也就是 1MB  
        var maxSize = 1024 * 1024;  
        // 图片最大宽度  
        var maxWidth = 300;  
        $('.js_file').on('change', function (event) {
        	var upName = this.name;
          	var files = event.target.files;  

            // 最大上传图片数量  
            var cnt = $('#c'+upName).text();
            var maxCount = cnt.substring(cnt.indexOf('/')+1);
			console.log('upName:'+upName+', maxCount:'+maxCount);            
            // 如果没有选中文件，直接返回  
            if (files.length === 0) {  
            	return;  
            }
            
            for (var i = 0, len = files.length; i < len; i++) {  
            	var file = files[i];  
            	var reader = new FileReader();  
              
                // 如果类型不在允许的类型范围内  
                if (allowTypes.indexOf(file.type) === -1) {  
                  	$.weui.alert({text: '该类型不允许上传'});  
                  	continue;  
                }  
                
                if (file.size > maxSize) {  
                	$.weui.alert({text: '图片太大，不允许上传'});  
                	continue;  
                }
                
                if ($('#'+upName).children().length >= maxCount) {  
                	$.weui.alert({text: '最多只能上传' + maxCount + '张图片'});  
                	return;  
                }  
                
                reader.onload = function (e) {  
                  	var img = new Image();  
                  	img.onload = function () {  
                        // 不要超出最大宽度  
                        var w = Math.min(maxWidth, img.width);  
                        // 高度按比例计算  
                        var h = img.height * (w / img.width);  
                        var canvas = document.createElement('canvas');  
                        var ctx = canvas.getContext('2d');  
                        // 设置 canvas 的宽度和高度  
                        canvas.width = w;  
                        canvas.height = h;  
                        ctx.drawImage(img, 0, 0, w, h);  
                        var base64 = canvas.toDataURL('image/png');  
                        
                        // 插入到预览区  
                        var $preview = $('<li class="weui_uploader_file weui_uploader_status" style="background-image:url(' + base64 + ')"><div class="weui_uploader_status_content">0%</div></li>');  
                        $('#'+upName).append($preview);  
                        var num = $('#'+upName).children().length;  
                        $('#c'+upName).text(num + '/' + maxCount);  
                        
                        // 然后假装在上传，可以post base64格式，也可以构造blob对象上传，也可以用微信JSSDK上传  
	                    $.post('upload/uploadbase64', {'file':base64}).success(function(msg){
	            	    	if (num==1){
		            	    	$('#img'+upName).val(msg);
	            	    	}
	            	    	else{
		            	    	$('#img'+upName).val($('#img'+upName).attr("value")+','+msg);
	            	    	}
	            	    }).error(function(err){
	            	    	console.log(err);
	            	    });
                        
                        var progress = 0;  
                        function uploading() {  
                          	$preview.find('.weui_uploader_status_content').text(++progress + '%');  
                          	if (progress < 100) {  
                            	setTimeout(uploading, 3);  
                          	}  
                          	else {  
                                // 如果是失败，塞一个失败图标  
                                //$preview.find('.weui_uploader_status_content').html('<i class="weui_icon_warn"></i>');  
                                $preview.removeClass('weui_uploader_status').find('.weui_uploader_status_content').remove();  
                            }  
                        }  
                        setTimeout(uploading, 3);
                    };  
                    img.src = e.target.result;
                };
                reader.readAsDataURL(file);
                

            }  
        });

    	$('#btn_ensure').click(function(){
    		var data = $('form').serialize();
    	    console.log(data);
    	    $.post('add', data).success(function(msg){
    			$.weui.alert({title:'提示', text: msg.msg});
    			var city = $('#city').val();
    	    	window.location='map?id='+msg.id;
    	    }).error(function(err){
    	    	console.log(err);
    	    });
    	});
	});  
    </script>
</html>
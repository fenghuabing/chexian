$(function() {
	$('#addUserForm').bootstrapValidator({
		feedbackIcons : {
			valid : 'glyphicon glyphicon-ok',
			invalid : 'glyphicon glyphicon-remove',
			validating : 'glyphicon glyphicon-refresh'
		},
		fields : {
			username : {
				validators : {
					notEmpty : {
						message : '用户名为必填项，不能为空'
					},
					stringLength : {
						min : 6,
						max : 30,
						message : '用户名长度为6到30'
					}
				}
			},
			password : {
				validators : {
					notEmpty : {
						message : '密码为必填项，不能为空'
					},
					stringLength : {
						min : 6,
						max : 15,
						message : '密码长度为长度为6到15'
					},
					regexp : {
						regexp : /^[a-zA-Z0-9_]+$/,
						message : '密码只能使用大小写字母、数字和下划线'
					}
				}
			},
			comfirmpassword:{
				validators:{
					notEmpty:{
						message:'确认密码不能为空'
					},
					stringLength : {
						min : 6,
						max : 15,
						message : '密码长度为长度为6到15'
					},
				    identical: {
                        field: 'password',
                        message: '两次输入密码不一致'
                    }
				}
			},
			linkman : {
				validators : {
					notEmpty : {
						message : '联系人为必填项，不能为空'
					}
				}
			},
			phone : {
				validators : {
					notEmpty : {
						message : '电话为必填项，不能为空'
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
        e.preventDefault();
        var $form = $(e.target);
        var bv = $form.data('formValidation');
        $.ajax({
            type: "POST",
            url:$form.attr('action'),
            data:$form.serialize(),
            error: function(request) {
                console.info(request);
                alert("Connection error");
            },
            success: function(data) {    
            	 console.log(data);
            	   bootbox.alert({
           	        buttons: {
           	            list: {
           	                label: '返回',
           	                className: 'btn-myStyle'
           	            },
           			   ok: {
           				    label: '继续添加',
           				    className: 'btn-myStyle'
           			   }
           		    },
           		    message: data.msg,
           		    callback: function (e) {
           		        if (e == "list") {
           		            window.location="list"
           		        }
           		        if (e == "ok") {
           		            window.location="add"
           		        }
           		    },
           		    title: "提示",
           	    });
            }
        });
	});
});
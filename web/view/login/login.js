
$(function() {
	
	
	// // 验证是否为有效的email的函数，返回boolean值
	// function isValidEmail(str) {
	// 	if(/(^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$)/.test(str)){
	// 	　　return true;
	// 	} else {
	// 		return false;
	// 	}
	// }
	
	// 判断密码是否合法
	// 长度6-20个字符，包括大写字母、小写字母、数字、下划线至少两种
	// function isValidPassword(str) {
	// 	if(str.length<6||str.length>20){
	// 	  return false;
	// 	}
	// 	if(/[^a-zA-Z0-9_]/.test(str)){
	// 	  return false;
	// 	}
	// 	if(/(^[a-z]+$|^[A-Z]+$|^\d+$|^_+$)/.test(str)){
	// 	  return false;
	// 	}
	// 	return true;
	// }
	
	
	// 检查Email并且显示错误提示信息
	function checkEmail(str) {
		if(isValidEmail(str)) {
			email_errorTip.innerText = "";
			return true;
		} else {
			email_errorTip.innerText = "电子邮件不合法";
			return false;
		}
	}
	// 检查password并且显示错误提示信息
	function checkPassword(str) {
		if(isValidPassword(str)) {
			password_errorTip.innerText = "";
			return true;
		} else {
			password_errorTip.innerText = "密码不合法";
			return false;
		}
	}
	
	var $inputs = $("input");
	var email = $inputs[0];
	var password = $inputs[1];
	var button = $inputs[2];
	var $error_tips = $(".error_tip");
	var email_errorTip = $error_tips[0];
	var password_errorTip = $error_tips[1];
	
	


	email.onblur = function() {
		checkEmail(email.value);
	}
	
	password.onblur = function() {
		checkPassword(password.value);
	}
	
	button.onclick = function() {
		if(checkEmail(email.value) & checkPassword(password.value)) {
			// $("form").submit();
			canLogin(email.value, password.value);
		}
	}
	


//	AJAX 判断是否登陆成功：email和password是否匹配
	function canLogin(emailValue,passwordvalue) {
		// 创建对象
		var xhr = new XMLHttpRequest();
		// 注册回调函数
		// 当xhr对象的readyState的值发生改变时时执行此函数
		xhr.onreadystatechange = function(){
			// xhr.readyState == 4时说明服务端响应结束
			if(xhr.readyState == 4) {
				if (xhr.responseText == "-1") {
					email_errorTip.innerText = "用户名或密码错误";
					password_errorTip.innerText = "用户名或密码错误";
				} else {
					window.location = xhr.responseText;
				}
			}
		}
		xhr.open("POST","/MessageWall/login",true);
		// 如果想要使用post提交数据,必须添加此行
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		// 请求体中发送数据
		xhr.send("email="+emailValue+"&password="+passwordvalue);
	}

})
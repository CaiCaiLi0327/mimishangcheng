<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" />
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/addBook.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath }/js/ajaxfileupload.js"></script>
	</head>
    <script type="text/javascript">
		var bool = true;
        function fileChange(pimage){//注意：此处不能使用jQuery中的change事件，因此仅触发一次，因此使用标签的：onchange属性
			if (pimage.length==0){
				bool = true;
				$("#imgDiv").empty();
				alert("没有选择图片！");
				return;
			}
			let formdata = new FormData();
			formdata.append("pimage",pimage[0]);
			bool = false;
			$.ajax({
				url:'${pageContext.request.contextPath}/prod/ajaxImg.action',
				data:formdata,
				type:"post",
				contentType: false,
				processData: false,
				success:function (imgName) {
					alert(imgName);
					$("#imgDiv").empty();  //清空原有数据
					//创建img 标签对象
					var imgObj = $("<img>");
					//给img标签对象追加属性
					imgObj.attr("src","${pageContext.request.contextPath}/image_big/"+imgName);
					imgObj.attr("width","100px");
					imgObj.attr("height","120px");
					//将图片img标签追加到imgDiv末尾
					$("#imgDiv").append(imgObj);
					//将图片的名称（从服务端返回的JSON中取得）赋值给文件本框
					//$("#imgName").html(data.imgName);
				}
			})

        }
		function kon() {
        	if($("[name='pName']").val()=="") {
				alert("请填写商品名称！");
        		return false;
			}
        	if($("[name='pContent']").val()==""){
				alert("请填写商品介绍！");
				return false;
			}
        	if($("[name='pPrice']").val()==""){
				alert("请填写商品价格！");
				return false;
			}
        	if($("[name='pNumber']").val()==""){
				alert("请填写商品数量！");
				return false;
			}
        	if(bool){
				alert("请选择图片！");
				return false;
			}
			return true;
		}
    </script>
	<body>
	<!--取出上一个页面上带来的page的值-->

		<div id="addAll">
			<div id="nav">
				<p>商品管理>新增商品</p>
			</div>

			<div id="table">
				<form action="${pageContext.request.contextPath}/prod/save.action" enctype="multipart/form-data"
					  method="post" id="myform" onsubmit="return kon()">
					<table>
						<tr>
							<td class="one">商品名称</td>
							<td><input type="text" name="pName" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="pnameerr"></span></td>
						</tr>
						<tr>
							<td class="one">商品介绍</td>
							<td><input type="text" name="pContent" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="pcontenterr"></span></td>
						</tr>
						<tr>
							<td class="one">定价</td>
							<td><input type="number" name="pPrice" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="priceerr"></span></td>
						</tr>
						
						<tr>
							<td class="three">图片介绍</td>
                            <td> <br><div id="imgDiv" style="display:block; width: 40px; height: 50px;"></div><br><br><br><br>
								<%--<input type="file" id="pimage" name="pimage" onchange="fileChange()">--%>
                            <input type="file" id="pimage" name="pimage" onchange="fileChange(this.files)" >
                                <span id="imgName" ></span><br>

                            </td>
						</tr>
						<tr class="three">
							<td class="four"></td>
							<td><span></span></td>
						</tr>
						
						<tr>
							<td class="one">总数量</td>
							<td><input type="number" name="pNumber" class="two"></td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span id="numerr"></span></td>
						</tr>
						
						
						<tr>
							<td class="one">类别</td>
							<td>
								<select name="typeId">
									<c:forEach items="${ptlist}" var="type">
										<option value="${type.typeId}">${type.typeName}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<!--错误提示-->
						<tr class="three">
							<td class="four"></td>
							<td><span></span></td>
						</tr>

						<tr>
							<td>
								<input type="submit" value="提交" class="btn btn-success">
							</td>
							<td>
								<input type="reset" value="取消" class="btn btn-default" onclick="myclose()">
								<script type="text/javascript">
									function myclose(ispage) {

										// window.history.go(-1);
										window.location="${pageContext.request.contextPath}/prod/split.action";
									}
								</script>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>

	</body>

</html>
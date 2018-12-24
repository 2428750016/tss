<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" %>
<html>
<head>
        <title>包含百度页面</title>
</head>
<body>
<form id="bdfm" target="_blank" name="bdfm" method="get" action="${pageContext.request.contextPath}/baidu/show">
        <table>
           <tr>
                <td>
                     <a href="#">
                          <img src="img/baidu.jpg"/>
                     </a>
                </td>
                <td><br/><input type="text" id="search1" name="word"/></td>
                <td><br/><input type="submit" value="搜索" /></td>
             </tr>
        </table>
</form>
</body>
</html>

 

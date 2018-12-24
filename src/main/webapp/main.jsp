<%@ page language="java" pageEncoding="utf-8" contentType="text/html;charset=utf-8" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
        <title>搜索页面</title>
</head>
    <body>
    <c:forEach items="${requestScope.scoreDocs}" var="scoreDocs">
        <div>
        <tr>
            <td>
                唐诗编号:${scoreDocs.id}
            </td>
            <td>
                诗题：${scoreDocs.title}
            </td>
            <td>
                诗人：${scoreDocs.poet.name}
            </td>
            <td>
                诗词: ${scoreDocs.content}
            </td>
        </tr>
        </div>
        <td>==================================</td>
    </c:forEach>
    <div>
        <tr>
            <a href="${pageContext.request.contextPath}/baidu/show?nowpage=${requestScope.nowpage- 1} &word=${requestScope.word}">上一页</a>&nbsp;&nbsp;
            <a href="${pageContext.request.contextPath}/baidu/show?nowpage=${requestScope.nowpage+ 1} &word=${requestScope.word}">下一页</a>
        </tr>

    </div>


    </body>
</html>
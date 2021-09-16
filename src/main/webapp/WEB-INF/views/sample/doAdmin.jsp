<%--
  Created by IntelliJ IDEA.
  User: ahyun
  Date: 2021-09-10
  Time: 오전 11:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <h1>DO ADMIN</h1>

  <h2><sec:authentication property="principal"></sec:authentication></h2>
  <h2><sec:authentication property="principal.mname"></sec:authentication></h2>
  <h2><sec:authentication property="principal.mid"></sec:authentication></h2>
  <h2><sec:authentication property="principal.mpw"></sec:authentication></h2>

</body>
</html>

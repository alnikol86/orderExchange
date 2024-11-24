<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Person List</title>
</head>
<body>
<h1>Person List</h1>
<table border="1">
  <thead>
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Email</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="person" items="${persons}">
    <tr>
      <td>${person.person_id}</td>
      <td>${person.name}</td>
      <td>${person.email}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>

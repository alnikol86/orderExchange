<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Person List</title>
</head>
<body>
<h1>Person List</h1>
<a href="/person?action=add">Add New Person</a>
<table border="1">
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>Email</th>
    <th>Role</th>
    <th>Actions</th>
  </tr>
  <c:forEach var="person" items="${persons}">
    <tr>
      <td>${person.person_id}</td>
      <td>${person.name}</td>
      <td>${person.email}</td>
      <td>${person.role}</td>
      <td>
        <a href="/person?action=edit&id=${person.person_id}">Edit</a>
        <form action="/person" method="post" style="display:inline;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="id" value="${person.person_id}">
          <button type="submit" onclick="return confirm('Are you sure?')">Delete</button>
        </form>
      </td>
    </tr>
  </c:forEach>
</table>
</body>
</html>

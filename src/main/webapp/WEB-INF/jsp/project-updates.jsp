<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Project Updates</title>
</head>
<body>
<h1>Project Updates</h1>

<a href="add-project-update.jsp">Add New Update</a>

<table border="1">
  <thead>
  <tr>
    <th>ID</th>
    <th>Project ID</th>
    <th>Update Text</th>
    <th>Created At</th>
    <th>Actions</th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="update" items="${updates}">
    <tr>
      <td>${update.updateId}</td>
      <td>${update.projectId}</td>
      <td>${update.updateText}</td>
      <td>${update.createdAt}</td>
      <td>
        <a href="project-update?id=${update.updateId}">View</a>
        <a href="edit-project-update?id=${update.updateId}">Edit</a>
        <a href="delete-project-update?id=${update.updateId}" onclick="return confirm('Are you sure?');">Delete</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>

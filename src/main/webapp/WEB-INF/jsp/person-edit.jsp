<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Person</title>
</head>
<body>
<h1>Edit Person</h1>
<form action="/person" method="post">
    <input type="hidden" name="action" value="update">
    <input type="hidden" name="id" value="${person.person_id}">
    <label>Name:</label>
    <input type="text" name="name" value="${person.name}" required><br>
    <label>Email:</label>
    <input type="email" name="email" value="${person.email}" required><br>
    <label>Password:</label>
    <input type="password" name="password" value="${person.password}" required><br>
    <label>Role:</label>
    <input type="number" name="roleId" value="${person.role}" required><br>
    <button type="submit">Save Changes</button>
</form>
<a href="/person?action=findAll">Back to List</a>
</body>
</html>

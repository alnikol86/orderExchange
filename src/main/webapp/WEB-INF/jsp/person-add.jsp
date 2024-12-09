<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Person</title>
</head>
<body>
<h1>Add New Person</h1>
<form action="/person" method="post">
    <input type="hidden" name="action" value="save">
    <label>Name:</label>
    <input type="text" name="name" required><br>
    <label>Email:</label>
    <input type="email" name="email" required><br>
    <label>Password:</label>
    <input type="password" name="password" required><br>
    <label>Role:</label>
    <input type="number" name="roleId" required><br>
    <button type="submit">Add Person</button>
</form>
<a href="/person?action=findAll">Back to List</a>
</body>
</html>

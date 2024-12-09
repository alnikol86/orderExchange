<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Project Update</title>
</head>
<body>
<h1>Project Update Details</h1>

<p><strong>ID:</strong> ${update.updateId}</p>
<p><strong>Project ID:</strong> ${update.projectId}</p>
<p><strong>Update Text:</strong> ${update.updateText}</p>
<p><strong>Created At:</strong> ${update.createdAt}</p>

<a href="edit-project-update?id=${update.updateId}">Edit</a>
<a href="delete-project-update?id=${update.updateId}" onclick="return confirm('Are you sure?');">Delete</a>
<a href="project-updates">Back to Updates</a>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Project Update</title>
</head>
<body>
<h1>Add Project Update</h1>

<form action="project-update" method="post">
    <label for="projectId">Project ID:</label>
    <input type="number" id="projectId" name="projectId" required><br>

    <label for="updateText">Update Text:</label><br>
    <textarea id="updateText" name="updateText" rows="5" cols="30" required></textarea><br>

    <input type="submit" value="Save">
</form>

<a href="project-updates">Back to Updates</a>
</body>
</html>

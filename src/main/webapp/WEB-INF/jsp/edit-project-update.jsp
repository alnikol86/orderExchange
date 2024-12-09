<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit Project Update</title>
</head>
<body>
<h1>Edit Project Update</h1>

<form action="project-update" method="post">
  <input type="hidden" name="id" value="${update.updateId}">

  <label for="projectId">Project ID:</label>
  <input type="number" id="projectId" name="projectId" value="${update.projectId}" required><br>

  <label for="updateText">Update Text:</label><br>
  <textarea id="updateText" name="updateText" rows="5" cols="30" required>${update.updateText}</textarea><br>

  <input type="submit" value="Update">
</form>

<a href="project-updates">Back to Updates</a>
</body>
</html>

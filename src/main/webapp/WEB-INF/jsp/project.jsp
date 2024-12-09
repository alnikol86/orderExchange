<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Project Details</title>
</head>
<body>
<h1>Project Details</h1>
<p><strong>ID:</strong> ${project.projectId}</p>
<p><strong>Person ID:</strong> ${project.personId}</p>
<p><strong>Title:</strong> ${project.title}</p>
<p><strong>Description:</strong> ${project.description}</p>
<p><strong>Budget:</strong> ${project.budgetMin} - ${project.budgetMax}</p>
<p><strong>Deadline:</strong> ${project.deadline}</p>
<p><strong>Status:</strong> ${project.status}</p>
<a href="projects">Back to list</a>
</body>
</html>
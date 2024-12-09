<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Projects</title>
</head>
<body>
<h1>Projects</h1>
<a href="projects?action=add">Add New Project</a>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Person ID</th>
        <th>Title</th>
        <th>Budget</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="project" items="${projects}">
        <tr>
            <td>${project.projectId}</td>
            <td>${project.personId}</td>
            <td>${project.title}</td>
            <td>${project.budgetMin} - ${project.budgetMax}</td>
            <td>${project.status}</td>
            <td>
                <a href="projects?action=view&id=${project.projectId}">View</a>
                <a href="projects?action=edit&id=${project.projectId}">Edit</a>
                <a href="projects?action=delete&id=${project.projectId}" onclick="return confirm('Are you sure?')">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
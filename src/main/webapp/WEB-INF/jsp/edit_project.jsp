<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Project</title>
</head>
<body>
<h1>Edit Project</h1>
<form action="projects?action=edit&id=${project.projectId}" method="post">
    <p>
        <label for="personId">Person ID:</label>
        <input type="number" id="personId" name="personId" value="${project.personId}" required>
    </p>
    <p>
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" value="${project.title}" required>
    </p>
    <p>
        <label for="description">Description:</label>
        <textarea id="description" name="description" required>${project.description}</textarea>
    </p>
    <p>
        <label for="budgetMin">Budget Min:</label>
        <input type="number" id="budgetMin" name="budgetMin" step="0.01" value="${project.budgetMin}" required>
    </p>
    <p>
        <label for="budgetMax">Budget Max:</label>
        <input type="number" id="budgetMax" name="budgetMax" step="0.01" value="${project.budgetMax}" required>
    </p>
    <p>
        <label for="deadline">Deadline:</label>
        <input type="date" id="deadline" name="deadline" value="${project.deadline}" required>
    </p>
    <p>
        <label for="status">Status:</label>
        <select id="status" name="status" required>
            <option value="pending" ${project.status == 'pending' ? 'selected' : ''}>Pending</option>
            <option value="in_progress" ${project.status == 'in_progress' ? 'selected' : ''}>In Progress</option>
            <option value="completed" ${project.status == 'completed' ? 'selected' : ''}>Completed</option>
        </select>
    </p>
    <button type="submit">Update Project</button>
</form>
<a href="projects">Back to list</a>
</body>
</html>

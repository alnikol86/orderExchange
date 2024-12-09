<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add Project</title>
</head>
<body>
<h1>Add New Project</h1>
<form action="projects?action=add" method="post">
    <p>
        <label for="personId">Person ID:</label>
        <input type="number" id="personId" name="personId" required>
    </p>
    <p>
        <label for="title">Title:</label>
        <input type="text" id="title" name="title" required>
    </p>
    <p>
        <label for="description">Description:</label>
        <textarea id="description" name="description" required></textarea>
    </p>
    <p>
        <label for="budgetMin">Budget Min:</label>
        <input type="number" id="budgetMin" name="budgetMin" step="0.01" required>
    </p>
    <p>
        <label for="budgetMax">Budget Max:</label>
        <input type="number" id="budgetMax" name="budgetMax" step="0.01" required>
    </p>
    <p>
        <label for="deadline">Deadline:</label>
        <input type="date" id="deadline" name="deadline" required>
    </p>
    <p>
        <label for="status">Status:</label>
        <select id="status" name="status" required>
            <option value="pending">Pending</option>
            <option value="in_progress">In Progress</option>
            <option value="completed">Completed</option>
        </select>
    </p>
    <button type="submit">Add Project</button>
</form>
<a href="projects">Back to list</a>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add New Message</title>
</head>
<body>
<h1>Add New Message</h1>
<form action="messages" method="post">
  <label for="senderId">Sender ID:</label>
  <input type="number" name="senderId" required><br>
  <label for="receiverId">Receiver ID:</label>
  <input type="number" name="receiverId" required><br>
  <label for="projectId">Project ID (optional):</label>
  <input type="number" name="projectId"><br>
  <label for="content">Content:</label>
  <textarea name="content" required></textarea><br>
  <button type="submit">Add Message</button>
</form>
<a href="messages">Back to All Messages</a>
</body>
</html>

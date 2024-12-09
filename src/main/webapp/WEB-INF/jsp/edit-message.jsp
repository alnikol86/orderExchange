<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit Message</title>
</head>
<body>
<h1>Edit Message</h1>
<form action="messages" method="post">
    <input type="hidden" name="id" value="${message.messageId}">
    <label for="senderId">Sender ID:</label>
    <input type="number" name="senderId" value="${message.senderId}" required><br>
    <label for="receiverId">Receiver ID:</label>
    <input type="number" name="receiverId" value="${message.receiverId}" required><br>
    <label for="projectId">Project ID (optional):</label>
    <input type="number" name="projectId" value="${message.projectId}"><br>
    <label for="content">Content:</label>
    <textarea name="content" required>${message.content}</textarea><br>
    <button type="submit">Update Message</button>
</form>
<a href="messages">Back to All Messages</a>
</body>
</html>

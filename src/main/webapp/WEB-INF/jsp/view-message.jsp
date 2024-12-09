<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Message</title>
</head>
<body>
<h1>Message Details</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <td>${message.messageId}</td>
    </tr>
    <tr>
        <th>Sender ID</th>
        <td>${message.senderId}</td>
    </tr>
    <tr>
        <th>Receiver ID</th>
        <td>${message.receiverId}</td>
    </tr>
    <tr>
        <th>Project ID</th>
        <td>${message.projectId}</td>
    </tr>
    <tr>
        <th>Content</th>
        <td>${message.content}</td>
    </tr>
    <tr>
        <th>Created At</th>
        <td>${message.createdAt}</td>
    </tr>
</table>
<a href="messages">Back to All Messages</a>
</body>
</html>

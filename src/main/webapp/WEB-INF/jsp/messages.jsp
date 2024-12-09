<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Messages</title>
</head>
<body>
<h1>Messages</h1>
<a href="messages?action=add">Add New Message</a>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Sender ID</th>
        <th>Receiver ID</th>
        <th>Project ID</th>
        <th>Content</th>
        <th>Created At</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="message" items="${messages}">
        <tr>
            <td>${message.messageId}</td>
            <td>${message.senderId}</td>
            <td>${message.receiverId}</td>
            <td>${message.projectId}</td>
            <td>${message.content}</td>
            <td>${message.createdAt}</td>
            <td>
                <a href="messages?action=view&id=${message.messageId}">View</a>
                <a href="messages?action=edit&id=${message.messageId}">Edit</a>
                <form action="messages" method="post" style="display:inline;">
                    <input type="hidden" name="_method" value="DELETE">
                    <input type="hidden" name="id" value="${message.messageId}">
                    <button type="submit" onclick="return confirm('Are you sure you want to delete this message?')">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

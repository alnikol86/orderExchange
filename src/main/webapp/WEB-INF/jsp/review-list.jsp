<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Reviews</title>
</head>
<body>
<h1>All Reviews</h1>
<table border="1">
    <thead>
    <tr>
        <th>Review ID</th>
        <th>Project ID</th>
        <th>Reviewer ID</th>
        <th>Reviewed Person ID</th>
        <th>Rating</th>
        <th>Comment</th>
        <th>Created At</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="review" items="${reviews}">
        <tr>
            <td>${review.reviewId}</td>
            <td>${review.projectId}</td>
            <td>${review.reviewerId}</td>
            <td>${review.reviewedPersonId}</td>
            <td>${review.rating}</td>
            <td>${review.comment}</td>
            <td>${review.createdAt}</td>
            <td>
                <a href="review?action=findById&id=${review.reviewId}">View</a>
                <a href="review?action=delete&id=${review.reviewId}">Delete</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="review?action=createForm">Add New Review</a>
</body>
</html>

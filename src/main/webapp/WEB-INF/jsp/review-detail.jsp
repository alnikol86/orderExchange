<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Review Details</title>
</head>
<body>
<h1>Review Details</h1>
<p><strong>Review ID:</strong> ${review.reviewId}</p>
<p><strong>Project ID:</strong> ${review.projectId}</p>
<p><strong>Reviewer ID:</strong> ${review.reviewerId}</p>
<p><strong>Reviewed Person ID:</strong> ${review.reviewedPersonId}</p>
<p><strong>Rating:</strong> ${review.rating}</p>
<p><strong>Comment:</strong> ${review.comment}</p>
<p><strong>Created At:</strong> ${review.createdAt}</p>
<a href="review?action=findAll">Back to List</a>
</body>
</html>

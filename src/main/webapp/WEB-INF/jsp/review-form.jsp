<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add/Edit Review</title>
</head>
<body>
<h1>${review == null ? "Add New Review" : "Edit Review"}</h1>
<form action="review?action=${review == null ? 'create' : 'update'}" method="post">
  <c:if test="${review != null}">
    <input type="hidden" name="reviewId" value="${review.reviewId}"/>
  </c:if>
  <p>
    <label>Project ID:</label>
    <input type="text" name="projectId" value="${review != null ? review.projectId : ''}" required/>
  </p>
  <p>
    <label>Reviewer ID:</label>
    <input type="text" name="reviewerId" value="${review != null ? review.reviewerId : ''}" required/>
  </p>
  <p>
    <label>Reviewed Person ID:</label>
    <input type="text" name="reviewedPersonId" value="${review != null ? review.reviewedPersonId : ''}" required/>
  </p>
  <p>
    <label>Rating:</label>
    <input type="number" name="rating" value="${review != null ? review.rating : ''}" min="1" max="5" required/>
  </p>
  <p>
    <label>Comment:</label>
    <textarea name="comment">${review != null ? review.comment : ''}</textarea>
  </p>
  <button type="submit">${review == null ? "Add Review" : "Update Review"}</button>
</form>
<a href="review?action=findAll">Cancel</a>
</body>
</html>

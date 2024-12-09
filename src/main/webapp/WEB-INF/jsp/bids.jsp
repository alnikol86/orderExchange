<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bids</title>
</head>
<body>
<h1>All Bids</h1>
<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Project ID</th>
        <th>Person ID</th>
        <th>Bid Amount</th>
        <th>Proposal</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="bid" items="${bids}">
        <tr>
            <td>${bid.bidId}</td>
            <td>${bid.projectId}</td>
            <td>${bid.personId}</td>
            <td>${bid.bidAmount}</td>
            <td>${bid.proposal}</td>
            <td>${bid.status}</td>
            <td>
                <a href="bids?id=${bid.bidId}">View</a>
                <form action="bids" method="post" style="display: inline;">
                    <input type="hidden" name="_method" value="delete">
                    <input type="hidden" name="id" value="${bid.bidId}">
                    <button type="submit">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>

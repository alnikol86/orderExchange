<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bid Details</title>
</head>
<body>
<h1>Bid Details</h1>
<p><strong>ID:</strong> ${bid.bidId}</p>
<p><strong>Project ID:</strong> ${bid.projectId}</p>
<p><strong>Person ID:</strong> ${bid.personId}</p>
<p><strong>Bid Amount:</strong> ${bid.bidAmount}</p>
<p><strong>Proposal:</strong> ${bid.proposal}</p>
<p><strong>Status:</strong> ${bid.status}</p>
<a href="bids">Back to All Bids</a>
</body>
</html>

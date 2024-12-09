package com.nikolaev.orderexchange.servlet;

import com.nikolaev.orderexchange.dao.impl.ReviewDAOImpl;
import com.nikolaev.orderexchange.entity.ReviewEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/review")
public class ReviewServlet extends HttpServlet {

    private final ReviewDAOImpl reviewDao;

    public ReviewServlet() throws SQLException {
        this.reviewDao = ReviewDAOImpl.getInstance(); // Инициализация DAO
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("findAll".equals(action)) {
            List<ReviewEntity> reviews = reviewDao.findAll();
            request.setAttribute("reviews", reviews);
            request.getRequestDispatcher("/WEB-INF/jsp/review-list.jsp").forward(request, response);

        } else if ("findById".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    Optional<ReviewEntity> review = reviewDao.findById(id);
                    if (review.isPresent()) {
                        request.setAttribute("review", review.get());
                        request.getRequestDispatcher("/WEB-INF/jsp/review-detail.jsp").forward(request, response);
                    } else {
                        response.getWriter().println("Review not found with ID: " + id);
                    }
                } catch (NumberFormatException e) {
                    response.getWriter().println("Invalid ID format");
                }
            } else {
                response.getWriter().println("ID parameter is missing");
            }

        } else {
            response.getWriter().println("Invalid action");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("save".equals(action)) {
            ReviewEntity review = extractReviewFromRequest(request);
            reviewDao.save(review);
            response.sendRedirect("/review?action=findAll");

        } else if ("update".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    ReviewEntity review = extractReviewFromRequest(request);
                    review.setReviewId(id);
                    reviewDao.update(review);
                    response.sendRedirect("/review?action=findAll");
                } catch (NumberFormatException e) {
                    response.getWriter().println("Invalid ID format");
                }
            } else {
                response.getWriter().println("ID parameter is missing");
            }

        } else if ("delete".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    ReviewEntity review = new ReviewEntity();
                    review.setReviewId(id);
                    if (reviewDao.delete(review)) {
                        response.sendRedirect("/review?action=findAll");
                    } else {
                        response.getWriter().println("Failed to delete review with ID: " + id);
                    }
                } catch (NumberFormatException e) {
                    response.getWriter().println("Invalid ID format");
                }
            } else {
                response.getWriter().println("ID parameter is missing");
            }

        } else {
            response.getWriter().println("Invalid action");
        }
    }

    private ReviewEntity extractReviewFromRequest(HttpServletRequest request) {
        ReviewEntity review = new ReviewEntity();
        review.setProjectId(Integer.parseInt(request.getParameter("projectId")));
        review.setReviewerId(Integer.parseInt(request.getParameter("reviewerId")));
        review.setReviewedPersonId(Integer.parseInt(request.getParameter("reviewedPersonId")));
        review.setRating(Integer.parseInt(request.getParameter("rating")));
        review.setComment(request.getParameter("comment"));
        return review;
    }
}
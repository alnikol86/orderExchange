package com.nikolaev.orderexchange.servlet;

import com.nikolaev.orderexchange.dao.BidDAO;
import com.nikolaev.orderexchange.dao.impl.BidDAOImpl;
import com.nikolaev.orderexchange.entity.BidEntity;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet("/bids")
public class BidServlet extends HttpServlet {
    private BidDAO bidDAO;

    @Override
    public void init() {
        try {
            bidDAO = BidDAOImpl.getInstance();
        } catch (Exception e) {
            try {
                throw new ServletException("Failed to initialize BidDAO", e);
            } catch (ServletException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String bidId = req.getParameter("id");

        if (bidId != null) {
            // Получение конкретной заявки
            Optional<BidEntity> bid = bidDAO.findById(Integer.parseInt(bidId));
            if (bid.isPresent()) {
                req.setAttribute("bid", bid.get());
                RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/bid.jsp");
                dispatcher.forward(req, resp);
            } else {
                req.setAttribute("error", "Bid not found");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            // Получение всех заявок
            List<BidEntity> bids = bidDAO.findAll();
            req.setAttribute("bids", bids);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/bids.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Создание новой заявки
        try {
            int projectId = Integer.parseInt(req.getParameter("project_id"));
            int personId = Integer.parseInt(req.getParameter("person_id"));
            BigDecimal bidAmount = new BigDecimal(req.getParameter("bid_amount"));
            String proposal = req.getParameter("proposal");
            String status = req.getParameter("status");

            BidEntity bid = new BidEntity();
            bid.setProjectId(projectId);
            bid.setPersonId(personId);
            bid.setBidAmount(bidAmount);
            bid.setProposal(proposal);
            bid.setStatus(status);

            bidDAO.save(bid);

            resp.sendRedirect(req.getContextPath() + "/bids");
        } catch (Exception e) {
            req.setAttribute("error", "Failed to create bid");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/error.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Обновление заявки
        try {
            int bidId = Integer.parseInt(req.getParameter("id"));
            Optional<BidEntity> optionalBid = bidDAO.findById(bidId);

            if (optionalBid.isPresent()) {
                BidEntity bid = optionalBid.get();
                if (req.getParameter("bid_amount") != null) {
                    bid.setBidAmount(new BigDecimal(req.getParameter("bid_amount")));
                }
                if (req.getParameter("proposal") != null) {
                    bid.setProposal(req.getParameter("proposal"));
                }
                if (req.getParameter("status") != null) {
                    bid.setStatus(req.getParameter("status"));
                }

                bidDAO.update(bid);
                resp.sendRedirect(req.getContextPath() + "/bids?id=" + bidId);
            } else {
                req.setAttribute("error", "Bid not found");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Failed to update bid");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/error.jsp");
            dispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        // Удаление заявки
        try {
            int bidId = Integer.parseInt(req.getParameter("id"));
            Optional<BidEntity> optionalBid = bidDAO.findById(bidId);

            if (optionalBid.isPresent()) {
                bidDAO.delete(optionalBid.get());
                resp.sendRedirect(req.getContextPath() + "/bids");
            } else {
                req.setAttribute("error", "Bid not found");
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (Exception e) {
            req.setAttribute("error", "Failed to delete bid");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/error.jsp");
            dispatcher.forward(req, resp);
        }
    }
}

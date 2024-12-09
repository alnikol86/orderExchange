package com.nikolaev.orderexchange.servlet;

import com.nikolaev.orderexchange.dao.MessageDAO;
import com.nikolaev.orderexchange.dao.impl.MessageDAOImpl;
import com.nikolaev.orderexchange.entity.MessageEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/messages")
public class MessageServlet extends HttpServlet {
    private final MessageDAO messageDAO = MessageDAOImpl.getInstance();

    public MessageServlet() throws SQLException {}

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String idParam = req.getParameter("id");

        if (action == null || action.isEmpty()) {
            // Отображение всех сообщений
            List<MessageEntity> messages = messageDAO.findAll();
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("messages.jsp").forward(req, resp);
        } else if ("view".equals(action) && idParam != null) {
            // Просмотр конкретного сообщения
            int id = Integer.parseInt(idParam);
            Optional<MessageEntity> message = messageDAO.findById(id);
            if (message.isPresent()) {
                req.setAttribute("message", message.get());
                req.getRequestDispatcher("/WEB-INF/jsp/view-message.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Message not found");
            }
        } else if ("add".equals(action)) {
            // Отображение формы добавления
            req.getRequestDispatcher("/WEB-INF/jsp/add-message.jsp").forward(req, resp);
        } else if ("edit".equals(action) && idParam != null) {
            // Отображение формы редактирования
            int id = Integer.parseInt(idParam);
            Optional<MessageEntity> message = messageDAO.findById(id);
            if (message.isPresent()) {
                req.setAttribute("message", message.get());
                req.getRequestDispatcher("/WEB-INF/jsp/edit-message.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Message not found");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        String senderIdParam = req.getParameter("senderId");
        String receiverIdParam = req.getParameter("receiverId");
        String projectIdParam = req.getParameter("projectId");
        String content = req.getParameter("content");

        if (idParam == null || idParam.isEmpty()) {
            // Добавление нового сообщения
            MessageEntity newMessage = new MessageEntity();
            newMessage.setSenderId(Integer.parseInt(senderIdParam));
            newMessage.setReceiverId(Integer.parseInt(receiverIdParam));
            newMessage.setContent(content);

            if (projectIdParam != null && !projectIdParam.isEmpty()) {
                newMessage.setProjectId(Integer.parseInt(projectIdParam));
            }

            messageDAO.save(newMessage);
        } else {
            // Обновление существующего сообщения
            int id = Integer.parseInt(idParam);
            MessageEntity updatedMessage = new MessageEntity();
            updatedMessage.setMessageId(id);
            updatedMessage.setSenderId(Integer.parseInt(senderIdParam));
            updatedMessage.setReceiverId(Integer.parseInt(receiverIdParam));
            updatedMessage.setContent(content);

            if (projectIdParam != null && !projectIdParam.isEmpty()) {
                updatedMessage.setProjectId(Integer.parseInt(projectIdParam));
            }

            messageDAO.update(updatedMessage);
        }
        resp.sendRedirect(req.getContextPath() + "/messages");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID is required");
            return;
        }

        int id = Integer.parseInt(idParam);
        Optional<MessageEntity> message = messageDAO.findById(id);
        if (message.isPresent()) {
            if (messageDAO.delete(message.get())) {
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete message");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Message not found");
        }
    }
}


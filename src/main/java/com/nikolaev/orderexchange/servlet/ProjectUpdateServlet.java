package com.nikolaev.orderexchange.servlet;

import com.nikolaev.orderexchange.dao.impl.ProjectUpdateDAOImpl;
import com.nikolaev.orderexchange.entity.ProjectUpdateEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/project-updates")
public class ProjectUpdateServlet extends HttpServlet {
    private ProjectUpdateDAOImpl projectUpdateDAO;

    @Override
    public void init() throws ServletException {
        try {
            projectUpdateDAO = ProjectUpdateDAOImpl.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Failed to initialize ProjectUpdateDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) action = "list";

        try {
            switch (action) {
                case "view":
                    viewProjectUpdate(req, resp);
                    break;
                case "add":
                    showAddForm(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                default:
                    listProjectUpdates(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException("Error handling GET request", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addProjectUpdate(req, resp);
                    break;
                case "edit":
                    updateProjectUpdate(req, resp);
                    break;
                case "delete":
                    deleteProjectUpdate(req, resp);
                    break;
                default:
                    resp.sendRedirect("project-updates");
            }
        } catch (Exception e) {
            throw new ServletException("Error handling POST request", e);
        }
    }

    private void listProjectUpdates(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectUpdateEntity> updates = projectUpdateDAO.findAll();
        req.setAttribute("updates", updates);
        req.getRequestDispatcher("/WEB-INF/jsp/project_updates.jsp").forward(req, resp);
    }

    private void viewProjectUpdate(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<ProjectUpdateEntity> update = projectUpdateDAO.findById(id);
        if (update.isPresent()) {
            req.setAttribute("update", update.get());
            req.getRequestDispatcher("/WEB-INF/jsp/project_update.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("project-updates");
        }
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/add_project_update.jsp").forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<ProjectUpdateEntity> update = projectUpdateDAO.findById(id);
        if (update.isPresent()) {
            req.setAttribute("update", update.get());
            req.getRequestDispatcher("/WEB-INF/jsp/edit_project_update.jsp").forward(req, resp);
        } else {
            resp.sendRedirect("project-updates");
        }
    }

    private void addProjectUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ProjectUpdateEntity update = new ProjectUpdateEntity();
        update.setProjectId(Integer.parseInt(req.getParameter("projectId")));
        update.setUpdateText(req.getParameter("updateText"));

        projectUpdateDAO.save(update);
        resp.sendRedirect("project-updates");
    }

    private void updateProjectUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<ProjectUpdateEntity> updateOpt = projectUpdateDAO.findById(id);

        if (updateOpt.isPresent()) {
            ProjectUpdateEntity update = updateOpt.get();
            update.setProjectId(Integer.parseInt(req.getParameter("projectId")));
            update.setUpdateText(req.getParameter("updateText"));

            projectUpdateDAO.update(update);
        }

        resp.sendRedirect("project-updates");
    }

    private void deleteProjectUpdate(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        Optional<ProjectUpdateEntity> updateOpt = projectUpdateDAO.findById(id);
        if (updateOpt.isPresent()) {
            projectUpdateDAO.delete(updateOpt.get());
        } else {
            req.getSession().setAttribute("errorMessage", "Project update not found!");
        }

        resp.sendRedirect("project-updates");
    }
}

package com.nikolaev.orderexchange.servlet;

import com.nikolaev.orderexchange.dao.impl.ProjectDAOImpl;
import com.nikolaev.orderexchange.entity.ProjectEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {

    private ProjectDAOImpl projectDAO;

    @Override
    public void init() throws ServletException {
        try {
            projectDAO = ProjectDAOImpl.getInstance();
        } catch (SQLException e) {
            throw new ServletException("Unable to initialize ProjectDAO", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "list";
        }

        try {
            switch (action) {
                case "view":
                    viewProject(req, resp);
                    break;
                case "add":
                    showAddForm(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                case "delete":
                    deleteProject(req, resp);
                    break;
                default:
                    listProjects(req, resp);
                    break;
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        try {
            if ("add".equals(action)) {
                addProject(req, resp);
            } else if ("edit".equals(action)) {
                updateProject(req, resp);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void listProjects(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<ProjectEntity> projects = projectDAO.findAll();
        req.setAttribute("projects", projects);
        req.getRequestDispatcher("/WEB-INF/jsp/projects.jsp").forward(req, resp);
    }

    private void viewProject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<ProjectEntity> project = projectDAO.findById(id);
        if (project.isPresent()) {
            req.setAttribute("project", project.get());
            req.getRequestDispatcher("/WEB-INF/jsp/project.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
        }
    }

    private void showAddForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/add_project.jsp").forward(req, resp);
    }

    private void addProject(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        ProjectEntity project = new ProjectEntity();
        setProjectFieldsFromRequest(req, project);
        projectDAO.save(project);
        resp.sendRedirect("projects");
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<ProjectEntity> project = projectDAO.findById(id);
        if (project.isPresent()) {
            req.setAttribute("project", project.get());
            req.getRequestDispatcher("/WEB-INF/jsp/edit_project.jsp").forward(req, resp);
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
        }
    }

    private void updateProject(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<ProjectEntity> projectOptional = projectDAO.findById(id);
        if (projectOptional.isPresent()) {
            ProjectEntity project = projectOptional.get();
            setProjectFieldsFromRequest(req, project);
            projectDAO.update(project);
            resp.sendRedirect("projects");
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Project not found");
        }
    }

    private void deleteProject(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Optional<ProjectEntity> project = projectDAO.findById(id);
        project.ifPresent(projectDAO::delete);
        resp.sendRedirect("projects");
    }

    private void setProjectFieldsFromRequest(HttpServletRequest req, ProjectEntity project) {
        project.setPersonId(Integer.parseInt(req.getParameter("personId")));
        project.setTitle(req.getParameter("title"));
        project.setDescription(req.getParameter("description"));
        project.setBudgetMin(new BigDecimal(req.getParameter("budgetMin")));
        project.setBudgetMax(new BigDecimal(req.getParameter("budgetMax")));
        project.setDeadline(LocalDate.parse(req.getParameter("deadline")));
        project.setStatus(req.getParameter("status"));
    }
}

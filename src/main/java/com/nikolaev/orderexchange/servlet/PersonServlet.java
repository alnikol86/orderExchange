package com.nikolaev.orderexchange.servlet;

import com.nikolaev.orderexchange.dao.impl.PersonDAOImpl;
import com.nikolaev.orderexchange.entity.PersonEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@WebServlet("/person")
public class PersonServlet extends HttpServlet {

    private final PersonDAOImpl personDao = PersonDAOImpl.getInstance(); // Инициализация DAO

        public PersonServlet() throws SQLException {
        }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        //person?action=findAll
        if ("findAll".equals(action)) {
            List<PersonEntity> persons = personDao.findAll();
            request.setAttribute("persons", persons);
            request.getRequestDispatcher("/person-list.jsp").forward(request, response);

            //person?action=findById&id=1
        } else if ("findById".equals(action)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                try {
                    int id = Integer.parseInt(idParam);
                    Optional<PersonEntity> person = personDao.findById(id);
                    if (person.isPresent()) {
                        request.setAttribute("person", person.get());
                        request.getRequestDispatcher("/person-detail.jsp").forward(request, response);
                    } else {
                        response.getWriter().println("Person not found with ID: " + id);
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
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Integer role = Integer.parseInt(request.getParameter("roleId"));

        PersonEntity person = new PersonEntity();
        person.setName(name);
        person.setEmail(email);
        person.setPassword(password);
        person.setRole(role);

        personDao.save(person);

        response.sendRedirect("/person?action=findAll");
    }
}

package com.nikolaev.orderexchange.dao.impl;

import com.nikolaev.orderexchange.dao.ProjectDAO;
import com.nikolaev.orderexchange.dataSource.DatabaseConnection;
import com.nikolaev.orderexchange.entity.ProjectEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectDAOImpl implements ProjectDAO {
    private static ProjectDAOImpl instance;
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    private static final String PERSON_ID = "person_id";
    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String BUDGET_MIN = "budget_min";
    private static final String BUDGET_MAX = "budget_max";
    private static final String DEADLINE = "deadline";
    private static final String STATUS = "status";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM project WHERE project_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM project";
    private static final String SAVE_QUERY = "INSERT INTO project (person_id, title, description, budget_min, budget_max, deadline, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE project SET person_id = ?, title = ?, description = ?, budget_min = ?, budget_max = ?, deadline = ?, status = ? WHERE project_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM project WHERE project_id = ?";

    public ProjectDAOImpl() throws SQLException {
    }

    public static synchronized ProjectDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProjectDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<ProjectEntity> findById(Integer id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToProject(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<ProjectEntity> findAll() {
        List<ProjectEntity> projectEntities = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                projectEntities.add(mapRowToProject(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectEntities;
    }

    @Override
    public ProjectEntity save(ProjectEntity projectEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            setProjectFieldsToStatement(projectEntity, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                projectEntity.setProjectId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectEntity;
    }

    @Override
    public void update(ProjectEntity projectEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setProjectFieldsToStatement(projectEntity, statement);
            statement.setInt(8, projectEntity.getProjectId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(ProjectEntity projectEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, projectEntity.getProjectId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setProjectFieldsToStatement(ProjectEntity projectEntity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, projectEntity.getPersonId());
        statement.setString(2, projectEntity.getTitle());
        statement.setString(3, projectEntity.getDescription());
        statement.setBigDecimal(4, projectEntity.getBudgetMin());
        statement.setBigDecimal(5, projectEntity.getBudgetMax());
        statement.setDate(6, Date.valueOf(projectEntity.getDeadline()));
        statement.setString(7, projectEntity.getStatus());
    }

    private ProjectEntity mapRowToProject(ResultSet resultSet) throws SQLException {
        ProjectEntity project = new ProjectEntity();

        project.setPersonId(resultSet.getInt(PERSON_ID));
        project.setTitle(resultSet.getString(TITLE));
        project.setDescription(resultSet.getString(DESCRIPTION));
        project.setBudgetMin(resultSet.getBigDecimal(BUDGET_MIN));
        project.setBudgetMax(resultSet.getBigDecimal(BUDGET_MAX));
        project.setDeadline(resultSet.getDate(DEADLINE).toLocalDate());
        project.setStatus(resultSet.getString(STATUS));

        return project;
    }
}

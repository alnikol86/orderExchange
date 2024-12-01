package com.nikolaev.orderexchange.dao.impl;

import com.nikolaev.orderexchange.dao.ProjectUpdateDAO;
import com.nikolaev.orderexchange.dataSource.DatabaseConnection;
import com.nikolaev.orderexchange.entity.ProjectUpdateEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectUpdateDAOImpl implements ProjectUpdateDAO {
    private static ProjectUpdateDAOImpl instance;
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    private static final String UPDATE_ID = "update_id";
    private static final String PROJECT_ID = "project_id";
    private static final String UPDATE_TEXT = "update_text";
    private static final String CREATED_AT = "created_at";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM project_update WHERE update_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM project_update";
    private static final String SAVE_QUERY = "INSERT INTO project_update (project_id, update_text) VALUES (?, ?)";
    private static final String UPDATE_QUERY = "UPDATE project_update SET project_id = ?, update_text = ? WHERE update_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM project_update WHERE update_id = ?";

    public ProjectUpdateDAOImpl() throws SQLException, SQLException {
    }

    public static synchronized ProjectUpdateDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new ProjectUpdateDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<ProjectUpdateEntity> findById(Integer id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToProjectUpdate(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<ProjectUpdateEntity> findAll() {
        List<ProjectUpdateEntity> projectUpdates = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                projectUpdates.add(mapRowToProjectUpdate(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectUpdates;
    }

    @Override
    public ProjectUpdateEntity save(ProjectUpdateEntity projectUpdate) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            setProjectUpdateFieldsToStatement(projectUpdate, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                projectUpdate.setUpdateId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projectUpdate;
    }

    @Override
    public void update(ProjectUpdateEntity projectUpdate) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setProjectUpdateFieldsToStatement(projectUpdate, statement);
            statement.setInt(3, projectUpdate.getUpdateId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(ProjectUpdateEntity projectUpdate) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, projectUpdate.getUpdateId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setProjectUpdateFieldsToStatement(ProjectUpdateEntity projectUpdate, PreparedStatement statement) throws SQLException {
        statement.setInt(1, projectUpdate.getProjectId());
        statement.setString(2, projectUpdate.getUpdateText());
    }

    private ProjectUpdateEntity mapRowToProjectUpdate(ResultSet resultSet) throws SQLException {
        ProjectUpdateEntity projectUpdate = new ProjectUpdateEntity();

        projectUpdate.setUpdateId(resultSet.getInt(UPDATE_ID));
        projectUpdate.setProjectId(resultSet.getInt(PROJECT_ID));
        projectUpdate.setUpdateText(resultSet.getString(UPDATE_TEXT));
        projectUpdate.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());

        return projectUpdate;
    }
}

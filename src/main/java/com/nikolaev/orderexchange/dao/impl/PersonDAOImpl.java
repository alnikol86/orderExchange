package com.nikolaev.orderexchange.dao.impl;

import com.nikolaev.orderexchange.dao.PersonDAO;
import com.nikolaev.orderexchange.dataSource.DatabaseConnection;
import com.nikolaev.orderexchange.entity.PersonEntity;
import com.nikolaev.orderexchange.entity.RoleEntity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PersonDAOImpl implements PersonDAO {
    private static PersonDAOImpl instance;
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    private static final String PERSON_ID = "person_id";
    private static final String PERSON_NAME = "person_name";
    private static final String EMAIL = "email";
    private static final String PASSWORD_HASH = "password_hash";
    private static final String ROLE_ID = "role_id";
    private static final String RATING = "rating";

    private PersonDAOImpl() throws SQLException {

    }

    public static synchronized PersonDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new PersonDAOImpl();
        }
        return instance;
    }

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM person WHERE person_id = ;";
    private static final String FIND_ALL_QUERY = "SELECT * FROM person;";
    private static final String SAVE = "INSERT INTO person (person_name, email, password_hash, role_id, rating) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE person SET person_name = ?, email = ?, password_hash = ?, role_id = ? WHERE person_id = ?";
    private static final String DELETE = "DELETE FROM person WHERE person_id = ?";

    @Override
    public Optional<PersonEntity> findById(Integer id) {
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_QUERY + id);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            if (resultSet.next()) {
                return Optional.of(mapRowToPerson(resultSet));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error to find person by ID", e);
        }
    }

    @Override
    public List<PersonEntity> findAll() {
        List<PersonEntity> personEntities = new ArrayList<>();
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_QUERY);
                ResultSet resultSet = preparedStatement.executeQuery();
        ) {
            while (resultSet.next()) {
                personEntities.add(mapRowToPerson(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error to get all persons", e);
        }
        return personEntities;
    }

    @Override
    public PersonEntity save(PersonEntity entity) {
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
        ) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setInt(4, entity.getRole().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error to save person", e);
        }
        return entity;
    }

    @Override
    public void update(PersonEntity entity) {
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
        ) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getEmail());
            preparedStatement.setString(3, entity.getPassword());
            preparedStatement.setInt(4, entity.getRole().getId());
            preparedStatement.setInt(5, entity.getPerson_id());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error to update person", e);
        }
    }

    @Override
    public boolean delete(PersonEntity entity) {
        try (
                Connection connection = databaseConnection.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(DELETE);
        ) {
            preparedStatement.setInt(1, entity.getPerson_id());
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error to delete person", e);
        }
    }

    private PersonEntity mapRowToPerson(ResultSet resultSet) throws SQLException {
        RoleEntity role = new RoleEntity();
        PersonEntity person = new PersonEntity();
        role.setRole(resultSet.getString(ROLE_ID));

        person.setPerson_id(resultSet.getInt(PERSON_ID));
        person.setName(resultSet.getString(PERSON_NAME));
        person.setEmail(resultSet.getString(EMAIL));
        person.setPassword(resultSet.getString(PASSWORD_HASH));
        person.setRole(role);
        person.setRating(resultSet.getDouble(RATING));

        return person;
    }
}

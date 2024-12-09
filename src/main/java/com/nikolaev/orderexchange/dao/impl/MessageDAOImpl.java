package com.nikolaev.orderexchange.dao.impl;

import com.nikolaev.orderexchange.dao.MessageDAO;
import com.nikolaev.orderexchange.dataSource.DatabaseConnection;
import com.nikolaev.orderexchange.entity.MessageEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageDAOImpl implements MessageDAO {
    private static MessageDAOImpl instance;
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    private static final String MESSAGE_ID = "message_id";
    private static final String SENDER_ID = "sender_id";
    private static final String RECEIVER_ID = "receiver_id";
    private static final String PROJECT_ID = "project_id";
    private static final String CONTENT = "content";
    private static final String CREATED_AT = "created_at";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM message WHERE message_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM message";
    private static final String SAVE_QUERY = "INSERT INTO message (sender_id, receiver_id, project_id, content) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE message SET sender_id = ?, receiver_id = ?, project_id = ?, content = ? WHERE message_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM message WHERE message_id = ?";

    public MessageDAOImpl() throws SQLException, SQLException {
    }

    public static synchronized MessageDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new MessageDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<MessageEntity> findById(Integer id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToMessage(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<MessageEntity> findAll() {
        List<MessageEntity> messages = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                messages.add(mapRowToMessage(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    @Override
    public MessageEntity save(MessageEntity messageEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            setMessageFieldsToStatement(messageEntity, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                messageEntity.setMessageId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messageEntity;
    }

    @Override
    public void update(MessageEntity messageEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setMessageFieldsToStatement(messageEntity, statement);
            statement.setInt(5, messageEntity.getMessageId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(MessageEntity messageEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, messageEntity.getMessageId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setMessageFieldsToStatement(MessageEntity messageEntity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, messageEntity.getSenderId());
        statement.setInt(2, messageEntity.getReceiverId());
        if (messageEntity.getProjectId() != null) {
            statement.setInt(3, messageEntity.getProjectId());
        } else {
            statement.setNull(3, Types.INTEGER);
        }
        statement.setString(4, messageEntity.getContent());
    }

    private MessageEntity mapRowToMessage(ResultSet resultSet) throws SQLException {
        MessageEntity message = new MessageEntity();

        message.setMessageId(resultSet.getInt(MESSAGE_ID));
        message.setSenderId(resultSet.getInt(SENDER_ID));
        message.setReceiverId(resultSet.getInt(RECEIVER_ID));
        int projectId = resultSet.getInt(PROJECT_ID);
        message.setProjectId(resultSet.wasNull() ? null : projectId);
        message.setContent(resultSet.getString(CONTENT));
        message.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());

        return message;
    }
}

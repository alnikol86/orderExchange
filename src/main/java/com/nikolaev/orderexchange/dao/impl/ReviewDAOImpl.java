package com.nikolaev.orderexchange.dao.impl;

import com.nikolaev.orderexchange.dao.ReviewDAO;
import com.nikolaev.orderexchange.dataSource.DatabaseConnection;
import com.nikolaev.orderexchange.entity.ReviewEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReviewDAOImpl implements ReviewDAO {
    private static ReviewDAOImpl instance;
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    private static final String REVIEW_ID = "review_id";
    private static final String PROJECT_ID = "project_id";
    private static final String REVIEWER_ID = "reviewer_id";
    private static final String REVIEWED_PERSON_ID = "reviewed_person_id";
    private static final String RATING = "rating";
    private static final String COMMENT = "comment";
    private static final String CREATED_AT = "created_at";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM review WHERE review_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM review";
    private static final String SAVE_QUERY = "INSERT INTO review (project_id, reviewer_id, reviewed_person_id, rating, comment) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE review SET project_id = ?, reviewer_id = ?, reviewed_person_id = ?, rating = ?, comment = ? WHERE review_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM review WHERE review_id = ?";

    public ReviewDAOImpl() throws SQLException, SQLException {
    }

    public static synchronized ReviewDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new ReviewDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<ReviewEntity> findById(Integer id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToReview(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<ReviewEntity> findAll() {
        List<ReviewEntity> reviews = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                reviews.add(mapRowToReview(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    @Override
    public ReviewEntity save(ReviewEntity reviewEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            setReviewFieldsToStatement(reviewEntity, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                reviewEntity.setReviewId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reviewEntity;
    }

    @Override
    public void update(ReviewEntity reviewEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setReviewFieldsToStatement(reviewEntity, statement);
            statement.setInt(6, reviewEntity.getReviewId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(ReviewEntity reviewEntity) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, reviewEntity.getReviewId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setReviewFieldsToStatement(ReviewEntity reviewEntity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, reviewEntity.getProjectId());
        statement.setInt(2, reviewEntity.getReviewerId());
        statement.setInt(3, reviewEntity.getReviewedPersonId());
        statement.setInt(4, reviewEntity.getRating());
        if (reviewEntity.getComment() != null) {
            statement.setString(5, reviewEntity.getComment());
        } else {
            statement.setNull(5, Types.VARCHAR);
        }
    }

    private ReviewEntity mapRowToReview(ResultSet resultSet) throws SQLException {
        ReviewEntity review = new ReviewEntity();

        review.setReviewId(resultSet.getInt(REVIEW_ID));
        review.setProjectId(resultSet.getInt(PROJECT_ID));
        review.setReviewerId(resultSet.getInt(REVIEWER_ID));
        review.setReviewedPersonId(resultSet.getInt(REVIEWED_PERSON_ID));
        review.setRating(resultSet.getInt(RATING));
        review.setComment(resultSet.getString(COMMENT));
        review.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());

        return review;
    }
}

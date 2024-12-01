package com.nikolaev.orderexchange.dao.impl;

import com.nikolaev.orderexchange.dao.BidDAO;
import com.nikolaev.orderexchange.dataSource.DatabaseConnection;
import com.nikolaev.orderexchange.entity.BidEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BidDAOImpl implements BidDAO {
    private static BidDAOImpl instance;
    private final DatabaseConnection databaseConnection = DatabaseConnection.getInstance();

    private static final String BID_ID = "bid_id";
    private static final String PROJECT_ID = "project_id";
    private static final String PERSON_ID = "person_id";
    private static final String BID_AMOUNT = "bid_amount";
    private static final String PROPOSAL = "proposal";
    private static final String STATUS = "status";
    private static final String CREATED_AT = "created_at";

    private static final String FIND_BY_ID_QUERY = "SELECT * FROM bid WHERE bid_id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM bid";
    private static final String SAVE_QUERY = "INSERT INTO bid (project_id, person_id, bid_amount, proposal, status) VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE bid SET project_id = ?, person_id = ?, bid_amount = ?, proposal = ?, status = ? WHERE bid_id = ?";
    private static final String DELETE_QUERY = "DELETE FROM bid WHERE bid_id = ?";

    public BidDAOImpl() throws SQLException, SQLException {
    }

    public static synchronized BidDAOImpl getInstance() throws SQLException {
        if (instance == null) {
            instance = new BidDAOImpl();
        }
        return instance;
    }

    @Override
    public Optional<BidEntity> findById(Integer id) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_QUERY)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapRowToBid(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List<BidEntity> findAll() {
        List<BidEntity> bids = new ArrayList<>();
        try (Connection connection = databaseConnection.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(FIND_ALL_QUERY);
            while (resultSet.next()) {
                bids.add(mapRowToBid(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bids;
    }

    @Override
    public BidEntity save(BidEntity bid) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            setBidFieldsToStatement(bid, statement);
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                bid.setBidId(generatedKeys.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bid;
    }

    @Override
    public void update(BidEntity bid) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            setBidFieldsToStatement(bid, statement);
            statement.setInt(6, bid.getBidId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean delete(BidEntity bid) {
        try (Connection connection = databaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, bid.getBidId());
            int rowsDeleted = statement.executeUpdate();
            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void setBidFieldsToStatement(BidEntity bid, PreparedStatement statement) throws SQLException {
        statement.setInt(1, bid.getProjectId());
        statement.setInt(2, bid.getPersonId());
        statement.setBigDecimal(3, bid.getBidAmount());
        statement.setString(4, bid.getProposal());
        statement.setString(5, bid.getStatus());
    }

    private BidEntity mapRowToBid(ResultSet resultSet) throws SQLException {
        BidEntity bid = new BidEntity();

        bid.setBidId(resultSet.getInt(BID_ID));
        bid.setProjectId(resultSet.getInt(PROJECT_ID));
        bid.setPersonId(resultSet.getInt(PERSON_ID));
        bid.setBidAmount(resultSet.getBigDecimal(BID_AMOUNT));
        bid.setProposal(resultSet.getString(PROPOSAL));
        bid.setStatus(resultSet.getString(STATUS));
        bid.setCreatedAt(resultSet.getTimestamp(CREATED_AT).toLocalDateTime());

        return bid;
    }
}

package soundbridge.database.managers.pojomanagers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import soundbridge.database.exception.NotFoundException;
import soundbridge.database.managers.ManagerAbstract;

import soundbridge.database.pojos.ClientPP;

import soundbridge.database.utils.DBUtils;

public class ClientPPManager extends ManagerAbstract<ClientPP> {

	@Override
	public List<ClientPP> selectAll() throws SQLException, NotFoundException, Exception {
		ArrayList<ClientPP> ret = (ArrayList<ClientPP>) doSelectAll();

		if (null == ret) {
			throw new NotFoundException("There are no ClientPPs");
		}

		return ret;
	}

	public List<ClientPP> doSelectAll() throws SQLException, Exception {
		ArrayList<ClientPP> ret = null;
		String sql = "SELECT * FROM ClientPP";

		Connection connection = null;

		Statement statement = null;
		ResultSet resultSet = null;

		try {
			Class.forName(DBUtils.DRIVER);

			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);

			statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {

				if (null == ret)
					ret = new ArrayList<ClientPP>();

				ClientPP clientpp = new ClientPP();

				
				String bankAccount = resultSet.getString("bankAccount");
				java.sql.Date sqlsuscriptionDate = resultSet.getDate("suscriptionDate");
				java.util.Date suscriptionDate = new java.util.Date(sqlsuscriptionDate.getTime());
	
				clientpp.setBankAccount(bankAccount);
				clientpp.setSuscriptionDate(suscriptionDate);
				

				ret.add(clientpp);
			}
		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (resultSet != null)
					resultSet.close();
			} catch (Exception e) {

			}
			;
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {

			}
			;
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {

			}
			;
		}

		return ret;
	}

	@Override
	public void insert(ClientPP clientpp) throws SQLException, Exception {
		Connection connection = null;
		Statement statement = null;

		try {
			Class.forName(DBUtils.DRIVER);
			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);
			statement = connection.createStatement();

			String sql = "INSERT INTO ClientPP (bankAccount, suscriptionDate) VALUES ( '"
					+ clientpp.getBankAccount() + "', '" + clientpp.getSuscriptionDate() + "')";

			statement.executeUpdate(sql);

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (statement != null)
					statement.close();
			} catch (Exception e) {
			}
			;
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
			;
		}

	}

	@Override
	public void update(ClientPP clientpp) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			Class.forName(DBUtils.DRIVER);

			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);

			String sql = "UPDATE ClientPP SET bankAccount = ?, subscriptionDate = ?";

			preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, clientpp.getBankAccount());
			preparedStatement.setDate(2, new java.sql.Date((clientpp.getSuscriptionDate()).getTime()));
			

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
			}
			;
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
			;
		}

	}

	@Override
	public void delete(ClientPP clientpp) throws SQLException, Exception {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		try {
			Class.forName(DBUtils.DRIVER);

			connection = DriverManager.getConnection(DBUtils.URL, DBUtils.USER, DBUtils.PASS);

			String sql = "DELETE FROM ClientPP WHERE id = ?";
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, clientpp.getId());

			preparedStatement.executeUpdate();

		} catch (SQLException sqle) {
			throw sqle;
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} catch (Exception e) {
			}
			;
			try {
				if (connection != null)
					connection.close();
			} catch (Exception e) {
			}
			;
		}

	}

}

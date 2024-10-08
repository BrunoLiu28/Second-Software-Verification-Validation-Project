package webapp.persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * An in-memory representation of a customer table record.
 * 
 * @author fmartins
 * @Version 1.2 (19/02/2015)
 *
 */
public class CustomerRowDataGateway {

	// Customer attributes 234623136

	/**
	 * Customer's internal identification (unique, primary key, sequential)
	 * Generated by the database engine.
	 */
	private int id;

	/**
	 * Customer's VAT number
	 */
	private int vat;

	/**
	 * Customer's name. In case of a company, the represents its commercial
	 * denomination
	 */
	private String designation;

	/**
	 * Customer's contact number
	 */
	private int phoneNumber;

	// 1. constructors

	// FOR TEST PURPOSE ONLY
	public CustomerRowDataGateway(int id, int vat, String designation, int phoneNumber) {
		this.id = id;
		this.vat = vat;
		this.designation = designation;
		this.phoneNumber = phoneNumber;
	}

	public CustomerRowDataGateway() {
	}

	public CustomerRowDataGateway(int vat, String designation, int phoneNumber) {
		this.vat = vat;
		this.designation = designation;
		this.phoneNumber = phoneNumber;
	}

	public CustomerRowDataGateway(ResultSet rs) throws RecordNotFoundException {
		try {
			fillAttributes(rs.getInt("vatNumber"), rs.getString("designation"), rs.getInt("phoneNumber"));
			this.id = rs.getInt("id");
		} catch (SQLException e) {
			throw new RecordNotFoundException("Customer does not exist", e);
		}
	}

	private void fillAttributes(int vat, String designation, int phoneNumber) {
		this.vat = vat;
		this.designation = designation;
		this.phoneNumber = phoneNumber;
	}

	// 2. getters and setters

	/**
	 * @return The id of the customer
	 */
	public int getCustomerId() {
		return id;
	}

	/**
	 * @return The customer's VAT number.
	 */
	public int getVAT() {
		return vat;
	}

	/**
	 * @return The customer's designation.
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @return The customer's phone number
	 */
	public int getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * Updates the customer designation.
	 * 
	 * @param designation The new designation to change to.
	 */
	public void setDesignation(String designation) {
		this.designation = designation;
	}

	/**
	 * Updates the phone number
	 * 
	 * @param phoneNumber The new phone number
	 */
	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * The insert customer SQL statement
	 */
	private static final String INSERT_CUSTOMER_SQL = "insert into customer (id, designation, phonenumber, vatnumber) "
			+ "values (DEFAULT, ?, ?, ?)";

	public void insert() throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(INSERT_CUSTOMER_SQL)) {
			// set statement arguments
			statement.setInt(3, vat);
			statement.setString(1, designation);
			statement.setInt(2, phoneNumber);
			// executes SQL
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Internal error!", e);
		}
	}

	/**
	 * The update customerPhone SQL statement
	 */
	private static final String UPDATE_PHONE_SQL = "update customer " + "set phonenumber = ? " + "where id = ?";

	public void updatePhoneNumber() throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(UPDATE_PHONE_SQL)) {
			// set statement arguments
			statement.setInt(1, phoneNumber);
			statement.setInt(2, id);
			// execute SQL
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Internal error updating customer " + id + ".", e);
		}
	}

	private static CustomerRowDataGateway loadCustomer(ResultSet rs) throws RecordNotFoundException {
		try {
			CustomerRowDataGateway newCustomer = new CustomerRowDataGateway(rs.getInt("vatnumber"),
					rs.getString("designation"), rs.getInt("phonenumber"));
			newCustomer.id = rs.getInt("id");
			return newCustomer;
		} catch (SQLException e) {
			throw new RecordNotFoundException("SaleProduct does not exist", e);
		}
	}

	/**
	 * The select customer by VAT SQL statement
	 */
	private static final String GET_ALL_CUSTOMERS_SQL = "select * from customer";

	/**
	 * Gets a customer by its VAT number
	 * 
	 * @param vat The VAT number of the customer to search for
	 * @return The result set of the query
	 * @throws PersistenceException When there is an error getting the customer from
	 *                              the database.
	 */

	public List<CustomerRowDataGateway> getAllCustomers() throws PersistenceException {
		List<CustomerRowDataGateway> customers = new ArrayList<CustomerRowDataGateway>();
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(GET_ALL_CUSTOMERS_SQL)) {
			try (ResultSet rs = statement.executeQuery()) {
				while (rs.next()) {
					customers.add(loadCustomer(rs));
				}
				rs.next();
				return customers;
			}
		} catch (SQLException e) {
			throw new PersistenceException("Internal error getting a customer by its VAT number", e);
		}
	}

	/**
	 * The update customerPhone SQL statement
	 */
	private static final String REMOVE_CUSTOMER_BY_VAT = "delete from customer " + "where vatnumber = ?";

	// ADICIONADO POR MIM PARA APAGAR OS SALES QUANDO APAGA O USER
	private static final String REMOVE_SALES_BY_CUSTOMER_VAT = "DELETE FROM sale " + "WHERE customer_vat = ?";

	// ADICIONADO POR MIM PARA APAGAR OS SALES DELIVERIES QUANDO APAGA O USER
	private static final String REMOVE_SALE_DELIVERIES_BY_CUSTOMER_VAT = "DELETE FROM saledelivery "
			+ "WHERE customer_vat = ?";

	public void removeCustomer() throws PersistenceException {
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(REMOVE_CUSTOMER_BY_VAT)) {
			// set statement arguments
			statement.setInt(1, vat);
			// execute SQL
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Internal error updating customer " + id + ".", e);
		}

		// ADICIONADO POR MIM PARA APAGAR OS SALES QUANDO APAGA O USER
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(REMOVE_SALES_BY_CUSTOMER_VAT)) {
			statement.setInt(1, vat);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Internal error updating customer " + id + ".", e);
		}

		// ADICIONADO POR MIM PARA APAGAR OS SALE DELIVERIES QUANDO APAGA O USER
		try (PreparedStatement statement = DataSource.INSTANCE.prepare(REMOVE_SALE_DELIVERIES_BY_CUSTOMER_VAT)) {
			statement.setInt(1, vat);
			statement.executeUpdate();
		} catch (SQLException e) {
			throw new PersistenceException("Internal error updating customer " + id + ".", e);
		}
	}

	private static final String CUSTOMER_EXISTS_QUERY = "SELECT 1 FROM Customers WHERE vat = ?";

//	public static boolean customerExists(int vat) throws PersistenceException {
//		try (PreparedStatement statement = DataSource.INSTANCE.prepare(CUSTOMER_EXISTS_QUERY)) {
//			statement.setInt(1, vat);
//			try (ResultSet resultSet = statement.executeQuery()) {
//				return resultSet.next();
//			}
//		} catch (SQLException e) {
//			throw new PersistenceException("Error checking if customer exists", e);
//		}
//	}

}

package DBSetup;

import static DBSetup.DBSetupUtils.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ninja_squad.dbsetup.DbSetup;
import com.ninja_squad.dbsetup.DbSetupTracker;
import com.ninja_squad.dbsetup.Operations;
import com.ninja_squad.dbsetup.destination.Destination;
import com.ninja_squad.dbsetup.destination.DriverManagerDestination;
import com.ninja_squad.dbsetup.operation.Operation;

import webapp.services.AddressesDTO;
import webapp.services.ApplicationException;
import webapp.services.CustomerDTO;
import webapp.services.CustomerService;
import webapp.services.CustomersDTO;
import webapp.services.SaleDTO;
import webapp.services.SaleDeliveryDTO;
import webapp.services.SaleService;
import webapp.services.SalesDTO;
import webapp.services.SalesDeliveryDTO;

public class DbSetupTests {

	private static Destination dataSource;

	// the tracker is static because JUnit uses a separate Test instance for every
	// test method.
	private static DbSetupTracker dbSetupTracker = new DbSetupTracker();

	@BeforeAll
	public static void setupClass() {
//    	System.out.println("setup Class()... ");

		startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
	}

	@BeforeEach
	public void setup() throws SQLException {
		
		//Reset à base de dados e preenchimento da mesma com os 
		Operation initDBOperations = Operations.sequenceOf(DELETE_ALL, INSERT_CUSTOMER_ADDRESS_DATA, INSERT_SALE_AND_SALE_DELIVERY_DATA);

		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);

		// Use the tracker to launch the DBSetup. This will speed-up tests
		// that do not change the DB. Otherwise, just use dbSetup.launch();
		dbSetupTracker.launchIfNecessary(dbSetup);
//		dbSetup.launch();
	}

	@Test
	public void testDuplicateClient() throws ApplicationException {
		assertThrows(ApplicationException.class, () -> CustomerService.INSTANCE.addCustomer(197672337, "JOSE FARIA", 914276732));
	}

	@Test
	public void uptadeCustomerContact() throws ApplicationException {
		int newPhone = 961234567;
		CustomerService.INSTANCE.updateCustomerPhone(197672337, newPhone);
		CustomerDTO updatedCustomer = CustomerService.INSTANCE.getCustomerByVat(197672337);
		assertEquals(newPhone, updatedCustomer.phoneNumber);
	}

	@Test
	public void testDeleteAllCustomers() throws ApplicationException {
		CustomersDTO allCustomers = CustomerService.INSTANCE.getAllCustomers();
		assertTrue(allCustomers.customers.size() > 0);
		for (CustomerDTO customer : allCustomers.customers) {
			CustomerService.INSTANCE.removeCustomer(customer.vat);
		}
		CustomersDTO allCustomersAfter = CustomerService.INSTANCE.getAllCustomers();
		allCustomersAfter.customers.size();
		assertTrue(allCustomersAfter.customers.size() == 0);
	}

	@Test
	public void testDeleteCertainCustomerAndAddAgain() throws ApplicationException {
		CustomerService.INSTANCE.removeCustomer(197672337);
		CustomerService.INSTANCE.addCustomer(197672337, "JOSE FARIA", 914276732);
		CustomerDTO customer = CustomerService.INSTANCE.getCustomerByVat(197672337);
		assertEquals(197672337, customer.vat);
		assertEquals(914276732, customer.phoneNumber);
		assertTrue(customer.designation.equals("JOSE FARIA"));
	}

	@Test
	public void testDeleteCertainCustomerAndItsSales() throws ApplicationException {
		CustomerService.INSTANCE.removeCustomer(197672337);
		SalesDTO sales2  = SaleService.INSTANCE.getSaleByCustomerVat(197672337);
		assertTrue(sales2.sales.size() == 0);
	}

	@Test
	public void testAddSaleTotalSaleIncrease() throws ApplicationException {
		int totalNumSales = SaleService.INSTANCE.getAllSales().sales.size(); 
		SaleService.INSTANCE.addSale(197672337);
		assertEquals(totalNumSales + 1, SaleService.INSTANCE.getAllSales().sales.size());
	}

	@Test
	public void testExpectedBehaviourForSales1() throws ApplicationException {
		//It is not possible to close a sale that doesnt exist!
		assertThrows(ApplicationException.class, () -> SaleService.INSTANCE.updateSale(5));
		
	}

	@Test
	public void testExpectedBehaviourForSales2() throws ApplicationException {
		//when a sale is closed it should change the status
		SaleService.INSTANCE.updateSale(1);
		SalesDTO sales  = SaleService.INSTANCE.getAllSales();
		assertTrue(sales.sales.get(0).statusId.equals("C"));
		
	}

	@Test
	public void testExpectedBehaviourForSaleDeliveries1() throws ApplicationException {
		//after adding a sale delivery the number of sale deliveries should increase
		int numSaleDeliveries = SaleService.INSTANCE.getSalesDeliveryByVat(197672337).sales_delivery.size();
		SaleService.INSTANCE.addSaleDelivery(1, 1);
		int finalNumSaleDeliveries = SaleService.INSTANCE.getSalesDeliveryByVat(197672337).sales_delivery.size();
		assertEquals(numSaleDeliveries+1, finalNumSaleDeliveries);
	}

	@Test
	public void testExpectedBehaviourForSaleDeliveries2() throws ApplicationException {
		//after deleting a customer its sale deliveries should be deleted too
		int numSaleDeliveries = SaleService.INSTANCE.getSalesDeliveryByVat(197672337).sales_delivery.size();
		assertTrue(numSaleDeliveries > 0);
		CustomerService.INSTANCE.removeCustomer(197672337);
		int finalNumSaleDeliveries = SaleService.INSTANCE.getSalesDeliveryByVat(197672337).sales_delivery.size();
		assertTrue(finalNumSaleDeliveries == 0);
	}

}

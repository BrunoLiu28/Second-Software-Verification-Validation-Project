package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static vvs_webapp.DBSetupUtils.*;

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

public class DbSetupTests {

	private static Destination dataSource;
	
    // the tracker is static because JUnit uses a separate Test instance for every test method.
    private static DbSetupTracker dbSetupTracker = new DbSetupTracker();
	
    @BeforeAll
    public static void setupClass() {
//    	System.out.println("setup Class()... ");
    	
    	startApplicationDatabaseForTesting();
		dataSource = DriverManagerDestination.with(DB_URL, DB_USERNAME, DB_PASSWORD);
    }
    
	@BeforeEach
	public void setup() throws SQLException {
//		System.out.print("setup()... ");
		
		Operation initDBOperations = Operations.sequenceOf(
			  DELETE_ALL
			, INSERT_CUSTOMER_ADDRESS_DATA
			);
		
		DbSetup dbSetup = new DbSetup(dataSource, initDBOperations);
		
        // Use the tracker to launch the DBSetup. This will speed-up tests 
		// that do not change the DB. Otherwise, just use dbSetup.launch();
        dbSetupTracker.launchIfNecessary(dbSetup);
//		dbSetup.launch();
	}
	
	@Test
	public void testDuplicateClient() throws ApplicationException {
		CustomerService.INSTANCE.addCustomer(197672337, "JOSE FARIA", 914276732);
		int size = CustomerService.INSTANCE.getAllCustomers().customers.size();
		
		assertEquals(NUM_INIT_CUSTOMERS, size);
	}
	
	@Test
	public void uptadeCustomerContact() throws ApplicationException {
		CustomerDTO actual = CustomerService.INSTANCE.getCustomerByVat(197672337);
		CustomerService.INSTANCE.
	}
	
	@Test
	public void testDeleteAllCustomers() throws ApplicationException {

	}

	@Test
	public void testDeleteCertainCustomerAndAddAgain() throws ApplicationException {
	}
	
	@Test
	public void testDeleteCertainCustomerAndItsSales() throws ApplicationException {
	}
	
	@Test
	public void testAddSaleTotalSaleIncrease() throws ApplicationException {
	}
	
	
	@Test
	public void testExpectedBehaviourForSales1() throws ApplicationException {
	}
	
	@Test
	public void testExpectedBehaviourForSales2() throws ApplicationException {
		
	}
	
	@Test
	public void testExpectedBehaviourForSaleDeliveries1() throws ApplicationException {

	}

	@Test
	public void testExpectedBehaviourForSaleDeliveries2() throws ApplicationException {
	}
	
	private boolean hasClient(int vat) throws ApplicationException {	
		CustomersDTO customersDTO = CustomerService.INSTANCE.getAllCustomers();
		
		for(CustomerDTO customer : customersDTO.customers)
			if (customer.vat == vat)
				return true;			
		return false;
	}
	

		
}

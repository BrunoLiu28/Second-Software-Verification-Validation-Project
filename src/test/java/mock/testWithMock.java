package mock;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.LinkedList;

import webapp.services.CustomerDTO;
import webapp.services.CustomerService;
import webapp.services.CustomersDTO;


public class testWithMock  {
	@Test
	public void getAllCustomersWithMockTest() throws Exception {
		
		CustomerService mock = mock(CustomerService.class);
	
		LinkedList<CustomerDTO> customerList = new LinkedList<>(Arrays.asList(
                new CustomerDTO(0, 123456789, "teste1", 961234567),
                new CustomerDTO(0, 197672337, "teste2", 961234565),
                new CustomerDTO(0, 168027852, "teste3", 961234563),
                new CustomerDTO(1, 269065822, "teste4", 987654321)
        ));

        when(mock.getAllCustomers()).thenReturn(new CustomersDTO(customerList));
        
        verify(mock).getAllCustomers();
	}
		
}
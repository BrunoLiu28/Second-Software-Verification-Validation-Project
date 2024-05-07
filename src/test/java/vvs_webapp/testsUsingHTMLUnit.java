package vvs_webapp;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

public class testsUsingHTMLUnit {

	
	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";
	private static final int APPLICATION_NUMBER_USE_CASES = 11;

	private static WebClient webClient;
	private static HtmlPage page;
	
	@BeforeClass
	public static void setUpClass() throws Exception {
		webClient = new WebClient(BrowserVersion.getDefault());
		
		// possible configurations needed to prevent JUnit tests to fail for complex HTML pages
//        webClient.setJavaScriptTimeout(15000);
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		
		page = webClient.getPage(APPLICATION_URL);
		assertEquals(200, page.getWebResponse().getStatusCode()); // OK status
	}
	
	@Test
	public void add2AddressToACustomer() throws Exception {
		DomElement addAddressToCustomerButton = page.getElementsById("botao2").get(1);
		System.out.println(addAddressToCustomerButton.asText());
		assertEquals("Insert new Address to Customer", addAddressToCustomerButton.asText());
		HtmlPage addAddressToCustomerPage = addAddressToCustomerButton.click();
		HtmlForm form = addAddressToCustomerPage.getForms().get(0);
		
		//First Address
		form.getInputByName("vat").setValueAttribute("197672337");
	    form.getInputByName("address").setValueAttribute("123 Main Stbbbbbbbbbbbbbbbbbb");
	    form.getInputByName("door").setValueAttribute("Apt 1");
	    form.getInputByName("postalCode").setValueAttribute("12345");
		form.getInputByName("locality").setValueAttribute("City");
		HtmlSubmitInput submitButton = form.getInputByValue("Insert");
	    HtmlPage nextPage = submitButton.click();
	    
	    //Second Address
	    form.getInputByName("vat").setValueAttribute("197672337");
	    form.getInputByName("address").setValueAttribute("123 Main Staaaaaaaaaaaaaaa");
	    form.getInputByName("door").setValueAttribute("Apt 1");
	    form.getInputByName("postalCode").setValueAttribute("12345");
		form.getInputByName("locality").setValueAttribute("City");
		submitButton = form.getInputByValue("Insert");
	    nextPage = submitButton.click();
	    //VERIFICAR TABLES ADDRESS DESSE CLIENTE 
	    //POR UM ASSERT
	    //DEPOIS RETIRAR OS ADDRESS INSERIDOS
        assertEquals("WebAppDemo Menu", page.getTitleText());
		
	}
	
	@Test
	public void add2NewCustomers() throws Exception {
		DomElement addAddressToCustomerButton = page.getElementsById("botao2").get(1);
		System.out.println(addAddressToCustomerButton.asText());
		assertEquals("Insert new Address to Customer", addAddressToCustomerButton.asText());
		HtmlPage addAddressToCustomerPage = addAddressToCustomerButton.click();
		HtmlForm form = addAddressToCustomerPage.getForms().get(0);
		
		//First Address
		form.getInputByName("vat").setValueAttribute("197672337");
	    form.getInputByName("address").setValueAttribute("123 Main Stbbbbbbbbbbbbbbbbbb");
	    form.getInputByName("door").setValueAttribute("Apt 1");
	    form.getInputByName("postalCode").setValueAttribute("12345");
		form.getInputByName("locality").setValueAttribute("City");
		HtmlSubmitInput submitButton = form.getInputByValue("Insert");
	    HtmlPage nextPage = submitButton.click();
	    
	    //Second Address
	    form.getInputByName("vat").setValueAttribute("197672337");
	    form.getInputByName("address").setValueAttribute("123 Main Staaaaaaaaaaaaaaa");
	    form.getInputByName("door").setValueAttribute("Apt 1");
	    form.getInputByName("postalCode").setValueAttribute("12345");
		form.getInputByName("locality").setValueAttribute("City");
		submitButton = form.getInputByValue("Insert");
	    nextPage = submitButton.click();
	    //VERIFICAR TABLES ADDRESS DESSE CLIENTE 
	    //POR UM ASSERT
	    //DEPOIS RETIRAR OS ADDRESS INSERIDOS
        assertEquals("WebAppDemo Menu", page.getTitleText());
		
	}
}

package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.xml.XmlPage;

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
		
		//SE CALHAR AQUI DAR SETUP E OBTER INVARIANTES PARA O SISTEMA.
	}
	
//	@Test
//	public void add2AddressToACustomerTest() throws Exception {
//		DomElement findCustomerByVatButton = page.getElementsById("botao2").get(3);
//		System.out.println(findCustomerByVatButton.asText());
//		assertEquals("Find customer by vat number", findCustomerByVatButton.asText());
//		HtmlPage findCustomerByVatPage = findCustomerByVatButton.click();
//		HtmlForm findCustomerByVatForm = findCustomerByVatPage.getForms().get(0);
//		findCustomerByVatForm.getInputByName("vat").setValueAttribute("197672337");
//		System.out.println();
//		HtmlSubmitInput submitButton2 = findCustomerByVatForm.getInputByValue("Get Customer");
//		HtmlPage clientInfoPage = submitButton2.click();
//		
//
//		List<DomElement> rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
//		int actualNumberOfAddresses = rows.size()-1;
////		DomElement addAddressToCustomerButton = page.getElementsById("botao2").get(1);
////		System.out.println(addAddressToCustomerButton.asText());
////		assertEquals("Insert new Address to Customer", addAddressToCustomerButton.asText());
////		HtmlPage addAddressToCustomerPage = addAddressToCustomerButton.click();
////		HtmlForm form = addAddressToCustomerPage.getForms().get(0);
////		
////		//First Address
////		form.getInputByName("vat").setValueAttribute("197672337");
////	    form.getInputByName("address").setValueAttribute("123 Main Stbbbbbbbbbbbbbbbbbb");
////	    form.getInputByName("door").setValueAttribute("Apt 1");
////	    form.getInputByName("postalCode").setValueAttribute("12345");
////		form.getInputByName("locality").setValueAttribute("City");
////		HtmlSubmitInput submitButton = form.getInputByValue("Insert");
////	    HtmlPage nextPage = submitButton.click();
////	    
////	    //Second Address
////	    form.getInputByName("vat").setValueAttribute("197672337");
////	    form.getInputByName("address").setValueAttribute("123 Main Staaaaaaaaaaaaaaa");
////	    form.getInputByName("door").setValueAttribute("Apt 1");
////	    form.getInputByName("postalCode").setValueAttribute("12345");
////		form.getInputByName("locality").setValueAttribute("City");
////		submitButton = form.getInputByValue("Insert");
////	    nextPage = submitButton.click();
//		
//		
//	    //VERIFICAR TABLES ADDRESS DESSE CLIENTE 
//	    //POR UM ASSERT
//	    //DEPOIS RETIRAR OS ADDRESS INSERIDOS
//
//
//		findCustomerByVatForm.getInputByName("vat").setValueAttribute("197672337");
//		submitButton2 = findCustomerByVatForm.getInputByValue("Get Customer");
//		clientInfoPage = submitButton2.click();
//		
//
//		rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
//		int numberOfAddresses = rows.size()-1;
//		
//	    for (DomElement row : rows) {
//	        List<HtmlElement> cells = row.getElementsByTagName("td");
//	        for (HtmlElement cell : cells) {
//	        	//ULTIMO CAMPO TEM MUITOS ESPACOS
//	            System.out.print(cell.getTextContent() + ";");
//	        }
//	        
//	        System.out.println();
//	    }
//	    assertEquals(numberOfAddresses, actualNumberOfAddresses + 2);
//	    
////	    final HtmlTable table = (HtmlTable) clientInfoPage.getByXPath("table class=\"w3-table w3-bordered\"").get(0);
////	    table.getRows().size();
////	    System.out.println(table.getRows().size());
//	    
////	    for (final HtmlTableRow row : table.getRows()) {
////	    	for (final HtmlTableCell cell : row.getCells()) {
////	    		System.out.print(cell.asText()+',');
////	    	}
//////	    	System.out.println();
////	    }
//
//	    //IMPLEMENTAR O DELETE ADDRESS E APAGAR OS 2 ADDRESS ADICIONADOS 
//	    //FAZER UM ASSERT PARA A INVARIANTE DO SISTEMA
//        assertEquals("WebAppDemo Menu", page.getTitleText());
//		
//	}
	
	@Test
	public void add2NewCustomersTest() throws Exception {
		DomElement addCustomerButton = page.getElementsById("botao2").get(0);
		assertEquals("Insert new Customer", addCustomerButton.asText());
		HtmlPage addCustomerPage = addCustomerButton.click();
		HtmlForm form = addCustomerPage.getForms().get(0);
		
		//First Customer
		String firstName = "Teste Teste";
		String firstPhone = "961234578";
		String firstVAT = "123456789";
		form.getInputByName("vat").setValueAttribute(firstVAT);
	    form.getInputByName("designation").setValueAttribute(firstName);
	    form.getInputByName("phone").setValueAttribute(firstPhone);
	    HtmlSubmitInput submitButton = form.getInputByValue("Get Customer");
//	    HtmlPage nextPage = submitButton.click();
	    submitButton.click();
	    
	    //Second Customer
	    String secondName = "Teste2";
		String secondPhone = "213456789";
		String secondVAT = "197672337";
	    form.getInputByName("vat").setValueAttribute(secondVAT);
	    form.getInputByName("designation").setValueAttribute(secondName);
	    form.getInputByName("phone").setValueAttribute(secondPhone);
	    submitButton = form.getInputByValue("Get Customer");
	    submitButton.click();
	    //VERIFICAR TABLES ADDRESS DESSE CLIENTE 
	    //POR UM ASSERT
	    //DEPOIS RETIRAR OS ADDRESS INSERIDOS
	    DomElement listAllCustomersButton = page.getElementsById("botao2").get(5);
	    HtmlPage listAllCustomersPage = listAllCustomersButton.click();
	    
	    List<DomElement> rows = (List<DomElement>) listAllCustomersPage.getElementsByTagName("tr");
	    
		DomElement firstAddedRow =rows.get(rows.size()-2);
		List<HtmlElement> firstCells = firstAddedRow.getElementsByTagName("td");
		System.out.println(firstCells.get(0).getTextContent());
		System.out.println(firstCells.get(1).getTextContent());
		System.out.println(firstCells.get(2).getTextContent());
		
		assertTrue(firstCells.get(0).getTextContent().equals(firstName));
		assertTrue(firstCells.get(1).getTextContent().equals(firstPhone));
		assertTrue(firstCells.get(2).getTextContent().equals(firstVAT));
		
		
		DomElement secondAddedRow =rows.get(rows.size()-1);
		List<HtmlElement> secondCells = secondAddedRow.getElementsByTagName("td");
		System.out.println(secondCells.get(0).getTextContent());
		System.out.println(secondCells.get(1).getTextContent());
		System.out.println(secondCells.get(2).getTextContent());
		assertTrue(secondCells.get(0).getTextContent().equals(secondName));
		assertTrue(secondCells.get(1).getTextContent().equals(secondPhone));
		assertTrue(secondCells.get(2).getTextContent().equals(secondVAT));
		
		DomElement removeCustomerButton = page.getElementsById("botao2").get(2);
	    HtmlPage removeCustomerPage = removeCustomerButton.click();
	    HtmlForm removeCustomerform = removeCustomerPage.getForms().get(0);
	    removeCustomerform.getInputByName("vat").setValueAttribute(firstVAT);
	    HtmlSubmitInput removeButton = form.getInputByValue("Remove");
	    removeButton.click();
	    removeCustomerform.getInputByName("vat").setValueAttribute(secondVAT);
	    removeButton.click();

	    //VERIFICAR A INVARIANTE DO SISTEMA
	    
        assertEquals("WebAppDemo Menu", page.getTitleText());
		
	}
	
	
	@Test
	public void startNewSaleTest() throws Exception {
		
		
	}
	
	@Test
	public void closeSaleTest() throws Exception {
		
		
	}
	
	@Test
	public void createCustomerSaleAndDeliveryTest() throws Exception {
		
		
	}
	
}

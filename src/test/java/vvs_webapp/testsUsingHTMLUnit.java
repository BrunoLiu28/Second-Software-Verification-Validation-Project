package vvs_webapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
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

import webapp.services.CustomerService;
import webapp.services.SaleService;

public class testsUsingHTMLUnit {

	private static final String APPLICATION_URL = "http://localhost:8080/VVS_webappdemo/";
	private static final int APPLICATION_NUMBER_USE_CASES = 11;

	private static WebClient webClient;
	private static HtmlPage page;

	@BeforeClass
	public static void setUpClass() throws Exception {
		webClient = new WebClient(BrowserVersion.getDefault());

		// possible configurations needed to prevent JUnit tests to fail for complex
		// HTML pages
//        webClient.setJavaScriptTimeout(15000);
//        webClient.getOptions().setJavaScriptEnabled(true);
//        webClient.getOptions().setThrowExceptionOnScriptError(false);
//        webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
//        webClient.getOptions().setCssEnabled(false);
//        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

		page = webClient.getPage(APPLICATION_URL);
		assertEquals(200, page.getWebResponse().getStatusCode()); // OK status

		// SE CALHAR AQUI DAR SETUP E OBTER INVARIANTES PARA O SISTEMA.
	}

	@Test
	public void add2AddressToACustomerTest() throws Exception {
		DomElement findCustomerByVatButton = page.getElementsById("botao2").get(3);
		System.out.println(findCustomerByVatButton.asText());
		assertEquals("Find customer by vat number", findCustomerByVatButton.asText());
		HtmlPage findCustomerByVatPage = findCustomerByVatButton.click();
		HtmlForm findCustomerByVatForm = findCustomerByVatPage.getForms().get(0);
		findCustomerByVatForm.getInputByName("vat").setValueAttribute("197672337");
		System.out.println();
		HtmlSubmitInput submitButton2 = findCustomerByVatForm.getInputByValue("Get Customer");
		HtmlPage clientInfoPage = submitButton2.click();
		

		List<DomElement> rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
		int actualNumberOfAddresses = rows.size()-1;
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


		findCustomerByVatForm.getInputByName("vat").setValueAttribute("197672337");
		submitButton2 = findCustomerByVatForm.getInputByValue("Get Customer");
		clientInfoPage = submitButton2.click();
		

		rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
		int numberOfAddresses = rows.size()-1;
		
	    for (DomElement row : rows) {
	        List<HtmlElement> cells = row.getElementsByTagName("td");
	        for (HtmlElement cell : cells) {
	        	//ULTIMO CAMPO TEM MUITOS ESPACOS
	            System.out.print(cell.getTextContent() + ";");
	        }
	        
	        System.out.println();
	    }
	    assertEquals(numberOfAddresses, actualNumberOfAddresses + 2);
	    
//	    final HtmlTable table = (HtmlTable) clientInfoPage.getByXPath("table class=\"w3-table w3-bordered\"").get(0);
//	    table.getRows().size();
//	    System.out.println(table.getRows().size());
	    
//	    for (final HtmlTableRow row : table.getRows()) {
//	    	for (final HtmlTableCell cell : row.getCells()) {
//	    		System.out.print(cell.asText()+',');
//	    	}
////	    	System.out.println();
//	    }

	    //IMPLEMENTAR O DELETE ADDRESS E APAGAR OS 2 ADDRESS ADICIONADOS 
	    //FAZER UM ASSERT PARA A INVARIANTE DO SISTEMA
        assertEquals("WebAppDemo Menu", page.getTitleText());
		
	}

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
		
		//Remover customers inseridos
		DomElement removeCustomerButton = page.getElementsById("botao2").get(2);
	    HtmlPage removeCustomerPage = removeCustomerButton.click();
	    HtmlForm removeCustomerform = removeCustomerPage.getForms().get(0);
	    removeCustomerform.getInputByName("vat").setValueAttribute(firstVAT);
	    HtmlSubmitInput removeButton = removeCustomerform.getInputByValue("Remove");
	    removeButton.click();
	    removeCustomerform.getInputByName("vat").setValueAttribute(secondVAT);
	    removeButton.click();

	    //VERIFICAR A INVARIANTE DO SISTEMA
	    
        assertEquals("WebAppDemo Menu", page.getTitleText());
		
	}

	@Test
	public void startNewSaleTest() throws Exception {
		String customerVAT = "197672337";
		DomElement newSaleButton = page.getElementsById("botao2").get(6);
		assertEquals("Insert new Sale", newSaleButton.asText());
		HtmlPage newSalePage = newSaleButton.click();
		HtmlForm newSaleform = newSalePage.getForms().get(0);
		newSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		HtmlSubmitInput addSaleButton = newSaleform.getInputByValue("Add Sale");
		HtmlPage saleInfoPage = addSaleButton.click();

		// verificar sale inserido
		Date todaysDate = new Date();
		List<DomElement> rows = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
		int numberOfSalesForThatCustomer = rows.size() - 1;
//		assertEquals(numberOfSalesForThatCustomer, 1); //MUDAR ESTE 1 para depois conseguir automaticamente  no inicio
		for (DomElement row : rows) {
			List<HtmlElement> cells = row.getElementsByTagName("td");
			for (int i = 0; i < cells.size(); i++) {
				HtmlElement cell = cells.get(i);
//	        for (HtmlElement cell : cells) {
				System.out.print(cell.getTextContent() + ";");
				if (i == 0) {
//	            	assertTrue(cell.getTextContent().equals(1)); //JUST A COUNTER
				} else if (i == 1) {
					assertTrue(cell.getTextContent().equals((new java.sql.Date(todaysDate.getTime())).toString())); // DATA
																													// DE
																													// HOJE
				} else if (i == 2) {
					assertTrue(cell.getTextContent().equals("0.0"));
				} else if (i == 3) {
					assertTrue(cell.getTextContent().equals("O"));
				} else if (i == 4) {
					assertTrue(cell.getTextContent().equals(customerVAT));
				}

			}
			// remove the sale inserted
			System.out.println();
		}
		// APAGAR SALE
		DomElement deleteLastSaleMenuButton = page.getElementsById("botao2").get(11);
		HtmlPage deleteLastSalePage = deleteLastSaleMenuButton.click();
		HtmlForm deleteLastSaleform = deleteLastSalePage.getForms().get(0);
		deleteLastSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		HtmlSubmitInput deleteLastSaleButton = deleteLastSaleform.getInputByValue("Add Sale");
		deleteLastSaleButton.click();
	}

	@Test
	public void closeSaleTest() throws Exception {
		String customerVAT = "197672337";
		DomElement newSaleButton = page.getElementsById("botao2").get(6);
		assertEquals("Insert new Sale", newSaleButton.asText());
		HtmlPage newSalePage = newSaleButton.click();
		HtmlForm newSaleform = newSalePage.getForms().get(0);
		newSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		HtmlSubmitInput addSaleButton = newSaleform.getInputByValue("Add Sale");
		HtmlPage saleInfoPage = addSaleButton.click();

		String saleID = "2";
		DomElement closeSaleButton = page.getElementsById("botao2").get(7);
		assertEquals("Close Existing Sale", closeSaleButton.asText());
		HtmlPage closeSalePage = closeSaleButton.click();
		HtmlForm closeSaleform = closeSalePage.getForms().get(0);
		closeSaleform.getInputByName("id").setValueAttribute(saleID);
		HtmlSubmitInput closeSaleButtonForm = closeSaleform.getInputByValue("Close Sale");
		closeSaleButtonForm.click();

		Date todaysDate = new Date();
		List<DomElement> rows = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
		int numberOfSalesForThatCustomer = rows.size() - 1;
		for (DomElement row : rows) {
			List<HtmlElement> cells = row.getElementsByTagName("td");
			for (int i = 0; i < cells.size(); i++) {
				HtmlElement cell = cells.get(i);
				System.out.print(cell.getTextContent() + ";");
				if (i == 0 && cell.getTextContent().equals(saleID)) {
					if (i == 1) {
						assertTrue(cell.getTextContent().equals((new java.sql.Date(todaysDate.getTime())).toString())); // DATA
																														// DE
																														// HOJE
					} else if (i == 2) {
						assertTrue(cell.getTextContent().equals("0.0"));
					} else if (i == 3) {
						assertTrue(cell.getTextContent().equals("C"));
					} else if (i == 4) {
						assertTrue(cell.getTextContent().equals(customerVAT));
					}
				}

			}
		}

		// APAGAR SALE
		DomElement deleteLastSaleMenuButton = page.getElementsById("botao2").get(11);
		HtmlPage deleteLastSalePage = deleteLastSaleMenuButton.click();
		HtmlForm deleteLastSaleform = deleteLastSalePage.getForms().get(0);
		deleteLastSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		HtmlSubmitInput deleteLastSaleButton = deleteLastSaleform.getInputByValue("Add Sale");
		deleteLastSaleButton.click();
	}

	@Test
	public void createCustomerSaleAndDeliveryTest() throws Exception {
		DomElement addCustomerButton = page.getElementsById("botao2").get(0);
		assertEquals("Insert new Customer", addCustomerButton.asText());
		HtmlPage addCustomerPage = addCustomerButton.click();
		HtmlForm form = addCustomerPage.getForms().get(0);

		// Insert Customer
		String name = "Teste Teste";
		String phone = "961234578";
		String VAT = "123456789";
		form.getInputByName("vat").setValueAttribute(VAT);
		form.getInputByName("designation").setValueAttribute(name);
		form.getInputByName("phone").setValueAttribute(phone);
		HtmlSubmitInput submitButton = form.getInputByValue("Get Customer");
		submitButton.click();

		// VERIFICACAO
		DomElement listAllCustomersButton = page.getElementsById("botao2").get(5);
		HtmlPage listAllCustomersPage = listAllCustomersButton.click();

		List<DomElement> rows = (List<DomElement>) listAllCustomersPage.getElementsByTagName("tr");

		DomElement firstAddedRow = rows.get(rows.size() - 1);
		List<HtmlElement> firstCells = firstAddedRow.getElementsByTagName("td");
// 		System.out.println(firstCells.get(0).getTextContent());
//	 	System.out.println(firstCells.get(1).getTextContent());
//	  	System.out.println(firstCells.get(2).getTextContent());

		assertTrue(firstCells.get(0).getTextContent().equals(name));
		assertTrue(firstCells.get(1).getTextContent().equals(phone));
		assertTrue(firstCells.get(2).getTextContent().equals(VAT));

		// Insert Sale
		DomElement newSaleButton = page.getElementsById("botao2").get(6);
		assertEquals("Insert new Sale", newSaleButton.asText());
		HtmlPage newSalePage = newSaleButton.click();
		HtmlForm newSaleform = newSalePage.getForms().get(0);
		newSaleform.getInputByName("customerVat").setValueAttribute(VAT);
		HtmlSubmitInput addSaleButton = newSaleform.getInputByValue("Add Sale");
		HtmlPage saleInfoPage = addSaleButton.click();

		// verificar sale inserido
		Date todaysDate = new Date();
		List<DomElement> rows2 = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
		int numberOfSalesForThatCustomer = rows2.size() - 1;
//		assertEquals(numberOfSalesForThatCustomer, 1); //MUDAR ESTE 1 para depois conseguir automaticamente  no inicio
//	    for (DomElement row : rows) {
		List<HtmlElement> cells = rows2.get(rows2.size() - 1).getElementsByTagName("td");
//		List<HtmlElement> cells = row.getElementsByTagName("td");
		for (int i = 0; i < cells.size(); i++) {
			HtmlElement cell = cells.get(i);
//	        for (HtmlElement cell : cells) {
			System.out.print(cell.getTextContent() + ";");
			if (i == 0) {
//	            	assertTrue(cell.getTextContent().equals(1)); //JUST A COUNTER
			} else if (i == 1) {
				assertTrue(cell.getTextContent().equals((new java.sql.Date(todaysDate.getTime())).toString())); // HOJE
			} else if (i == 2) {
				assertTrue(cell.getTextContent().equals("0.0"));
			} else if (i == 3) {
				assertTrue(cell.getTextContent().equals("O"));
			} else if (i == 4) {
				assertTrue(cell.getTextContent().equals(VAT));
			}

		}
//	    }

		// INSERT DELIVERY TO A SALE
		// Insert Sale
		DomElement saleDeliveryButton = page.getElementsById("botao2").get(9);
		assertEquals("Insert new Sale Delivery", saleDeliveryButton.asText());
		HtmlPage saleDeliveryPage = saleDeliveryButton.click();
		HtmlForm saleDeliveryform = saleDeliveryPage.getForms().get(0);
		saleDeliveryform.getInputByName("vat").setValueAttribute(VAT);
		HtmlSubmitInput getCustomerButton = saleDeliveryform.getInputByValue("Get Customer");
		HtmlPage addSaleDeliveryPage = getCustomerButton.click();

		HtmlForm addSaleDeliveryform = addSaleDeliveryPage.getForms().get(0);
		String addressID = Integer.toString(rows.size());
		String saleID = Integer.toString(rows2.size());
		addSaleDeliveryform.getInputByName("addr_id").setValueAttribute(addressID);
		addSaleDeliveryform.getInputByName("sale_id").setValueAttribute(saleID);
		HtmlSubmitInput insertButton = saleDeliveryform.getInputByValue("Insert");
		HtmlPage customerSaleInfoPage = insertButton.click();

		// VERIFICAR
		// verificar sale inserido
		List<DomElement> rows3 = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
		int numberOfDeliveryForThatCustomer = rows3.size() - 1;
//				assertEquals(numberOfSalesForThatCustomer, 1); //MUDAR ESTE 1 para depois conseguir automaticamente  no inicio
//			    for (DomElement row : rows) {
		List<HtmlElement> cells3 = rows3.get(rows3.size() - 1).getElementsByTagName("td");
//				List<HtmlElement> cells = row.getElementsByTagName("td");
		for (int i = 0; i < cells3.size(); i++) {
			HtmlElement cell = cells3.get(i);
//        for (HtmlElement cell : cells) {
			System.out.print(cell.getTextContent() + ";");
			if (i == 0) {
//            	assertTrue(cell.getTextContent().equals(1)); //JUST AN ID COUNTER
			} else if (i == 1) { //SALE ID
				assertTrue(cell.getTextContent().equals(saleID));
			} else if (i == 2) { //ADDRESS ID
				assertTrue(cell.getTextContent().equals(addressID));
			}
		}
		
		//DEPOIS DE VERIFICAR TUDO APAGAR DELIVERY SALE, SALE e CUSTOMER
	
	// APAGAR SALE
	DomElement deleteLastSaleMenuButton = page.getElementsById("botao2").get(11);
	HtmlPage deleteLastSalePage = deleteLastSaleMenuButton.click();
	HtmlForm deleteLastSaleform = deleteLastSalePage.getForms().get(0);
	deleteLastSaleform.getInputByName("customerVat").setValueAttribute(VAT);
	HtmlSubmitInput deleteLastSaleButton = deleteLastSaleform.getInputByValue("Add Sale");
	deleteLastSaleButton.click();
	}

}

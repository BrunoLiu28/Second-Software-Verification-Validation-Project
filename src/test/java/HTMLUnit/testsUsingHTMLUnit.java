package HTMLUnit;

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

//	@Test
//	public void add2AddressToACustomerTest() throws Exception {
//		String customerVAT = "197672337";
//		
//		DomElement findCustomerByVatButton = page.getElementsById("botao2").get(3);
//		assertEquals("Find customer by vat number", findCustomerByVatButton.asText());
//		HtmlPage findCustomerByVatPage = findCustomerByVatButton.click();
//		HtmlForm findCustomerByVatForm = findCustomerByVatPage.getForms().get(0);
//		findCustomerByVatForm.getInputByName("vat").setValueAttribute(customerVAT);
//		HtmlSubmitInput submitButton2 = findCustomerByVatForm.getInputByValue("Get Customer");
//		HtmlPage clientInfoPage = submitButton2.click();
//		List<DomElement> rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
//		int actualNumberOfAddresses = rows.size()-1;
//		
//		
//		DomElement addAddressToCustomerButton = page.getElementsById("botao2").get(1);
//		assertEquals("Insert new Address to Customer", addAddressToCustomerButton.asText());
//		HtmlPage addAddressToCustomerPage = addAddressToCustomerButton.click();
//		HtmlForm form = addAddressToCustomerPage.getForms().get(0);
//		
//		//First Address
//		String address1= "Rua 1";
//		String door1 = "Porta 1";
//		String postalCode1 = "1234-1";
//		String locality1 = "Lisboa";
//		form.getInputByName("vat").setValueAttribute(customerVAT);
//	    form.getInputByName("address").setValueAttribute(address1);
//	    form.getInputByName("door").setValueAttribute(door1);
//	    form.getInputByName("postalCode").setValueAttribute(postalCode1);
//		form.getInputByName("locality").setValueAttribute(locality1);
//		HtmlSubmitInput submitButton = form.getInputByValue("Insert");
//	    HtmlPage nextPage = submitButton.click();
//	    
//	    //Second Address
//	    String address2 = "Rua 2";
//		String door2 = "Porta 2";
//		String postalCode2 = "1234-2";
//		String locality2 = "Porto";
//	    form.getInputByName("vat").setValueAttribute(customerVAT);
//	    form.getInputByName("address").setValueAttribute(address2);
//	    form.getInputByName("door").setValueAttribute(door2);
//	    form.getInputByName("postalCode").setValueAttribute(postalCode2);
//		form.getInputByName("locality").setValueAttribute(locality2);
//		submitButton = form.getInputByValue("Insert");
//	    nextPage = submitButton.click();
//		
//		
//	    //VERIFICAR TABLES ADDRESS DESSE CLIENTE E VERIFICAR SE AUMENTOU O NUMERO DE ADDRESS EM 2
//		findCustomerByVatForm.getInputByName("vat").setValueAttribute(customerVAT);
//		submitButton2 = findCustomerByVatForm.getInputByValue("Get Customer");
//		clientInfoPage = submitButton2.click();
//		
//		rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
//		int numberOfAddresses = rows.size()-1;
//	    
//	    if (actualNumberOfAddresses == -1) { //BECAUSE IT DOESNT HAVE A TABLE AND WILL CREATE 1 ROW EXTRA FOR THE TABLE HEADER
//	    	assertEquals(numberOfAddresses, actualNumberOfAddresses + 3);
//	    } else {
//	    	assertEquals(numberOfAddresses, actualNumberOfAddresses + 2);
//	    }
//
//	    //DELETE DE TODOS OS ADDRESS DO CUSTOMER
//	    DomElement deleteAllAddressFromCustomerButton = page.getElementsById("botao2").get(12);
//		HtmlPage deleteAllAddressFromCustomerPage = deleteAllAddressFromCustomerButton.click();
//		HtmlForm deleteAllAddressFromCustomerForm = deleteAllAddressFromCustomerPage.getForms().get(0);
//		deleteAllAddressFromCustomerForm.getInputByName("customerVat").setValueAttribute(customerVAT);
//		HtmlSubmitInput deleteAllAddressButton = deleteAllAddressFromCustomerForm.getInputByValue("Delete All Address");
//		deleteAllAddressButton.click();
//		
//		//FAZER UM ASSERT PARA A INVARIANTE DO SISTEMA
//		clientInfoPage = (HtmlPage) clientInfoPage.refresh();
//		rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
//		int finalNumberOfAddresses = rows.size()-1;
//		
//		assertEquals(actualNumberOfAddresses, finalNumberOfAddresses);
//		
//	}
//
//	@Test
//	public void add2NewCustomersTest() throws Exception {
//		
//		//OBTER NUMERO DE CUSTOMER INICIAL
//		DomElement listAllCustomerButton = page.getElementsById("botao2").get(5);
//		HtmlPage listAllCustomerPage = listAllCustomerButton.click();
//		String numberOfClientsText = listAllCustomerPage.getElementById("body").asText();
//        String prefix = "Number of Clients: ";
//        int startIndex = numberOfClientsText.indexOf(prefix);
//        int endIndex = numberOfClientsText.indexOf("\n", startIndex);
//        String numberOfCustomersInicial = numberOfClientsText.substring(startIndex + prefix.length(), endIndex).trim();
//		
//		DomElement addCustomerButton = page.getElementsById("botao2").get(0);
//		assertEquals("Insert new Customer", addCustomerButton.asText());
//		HtmlPage addCustomerPage = addCustomerButton.click();
//		HtmlForm form = addCustomerPage.getForms().get(0);
//		
//		//First Customer
//		String firstName = "Customer Teste 1";
//		String firstPhone = "961234578";
//		String firstVAT = "123456789";
//		form.getInputByName("vat").setValueAttribute(firstVAT);
//	    form.getInputByName("designation").setValueAttribute(firstName);
//	    form.getInputByName("phone").setValueAttribute(firstPhone);
//	    HtmlSubmitInput submitButton = form.getInputByValue("Get Customer");
//	    submitButton.click();
//	    
//	    //Second Customer
//	    String secondName = "Customer Teste 2";
//		String secondPhone = "213456789";
//		String secondVAT = "269065822";
//	    form.getInputByName("vat").setValueAttribute(secondVAT);
//	    form.getInputByName("designation").setValueAttribute(secondName);
//	    form.getInputByName("phone").setValueAttribute(secondPhone);
//	    submitButton = form.getInputByValue("Get Customer");
//	    submitButton.click();
//	    
//	    
//	    //VERIFICAR TABLES ADDRESS DESSE CLIENTE
//	    DomElement listAllCustomersButton = page.getElementsById("botao2").get(5);
//	    HtmlPage listAllCustomersPage = listAllCustomersButton.click();
//	    
//	    List<DomElement> rows = (List<DomElement>) listAllCustomersPage.getElementsByTagName("tr");
//	    
//		DomElement firstAddedRow =rows.get(rows.size()-2);
//		List<HtmlElement> firstCells = firstAddedRow.getElementsByTagName("td");
//		assertTrue(firstCells.get(0).getTextContent().equals(firstName));
//		assertTrue(firstCells.get(1).getTextContent().equals(firstPhone));
//		assertTrue(firstCells.get(2).getTextContent().equals(firstVAT));
//		
//		
//		DomElement secondAddedRow =rows.get(rows.size()-1);
//		List<HtmlElement> secondCells = secondAddedRow.getElementsByTagName("td");
//		assertTrue(secondCells.get(0).getTextContent().equals(secondName));
//		assertTrue(secondCells.get(1).getTextContent().equals(secondPhone));
//		assertTrue(secondCells.get(2).getTextContent().equals(secondVAT));
//		
//		//Remover customers inseridos
//		DomElement removeCustomerButton = page.getElementsById("botao2").get(2);
//	    HtmlPage removeCustomerPage = removeCustomerButton.click();
//	    HtmlForm removeCustomerform = removeCustomerPage.getForms().get(0);
//	    removeCustomerform.getInputByName("vat").setValueAttribute(firstVAT);
//	    HtmlSubmitInput removeButton = removeCustomerform.getInputByValue("Remove");
//	    removeButton.click();
//	    removeCustomerform.getInputByName("vat").setValueAttribute(secondVAT);
//	    removeButton.click();
//
//	    //VERIFICAR A INVARIANTE DO SISTEMA
//		
//		//OBTER NUMERO DE CUSTOMER FINAL
//		DomElement listAllCustomerButton2 = page.getElementsById("botao2").get(5);
//		HtmlPage listAllCustomerPage2 = listAllCustomerButton2.click();
//		
//		String numberOfClientsText2 = listAllCustomerPage2.getElementById("body").asText();
//        int startIndex2 = numberOfClientsText2.indexOf(prefix);
//        int endIndex2 = numberOfClientsText2.indexOf("\n", startIndex2);
//        String numberOfCustomersFinal = numberOfClientsText2.substring(startIndex2 + prefix.length(), endIndex2).trim();
//		
//	    //VERIFICAR QUANTIDADE DE CUSTOMERS
//        assertEquals(numberOfCustomersInicial, numberOfCustomersFinal);
//		
//	}

	@Test
	public void startNewSaleTest() throws Exception {
		String customerVAT = "197672337";
		
		//OBTER INVARIANTE DO SISTEMA
		DomElement showCustomersSaleButton = page.getElementsById("botao2").get(8);
		assertEquals("Show Customer Sale's", showCustomersSaleButton.asText());
		HtmlPage showCustomersSalePage = showCustomersSaleButton.click();
		HtmlForm showCustomersSaleform = showCustomersSalePage.getForms().get(0);
		showCustomersSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		HtmlSubmitInput showCustomersSaleButtonForm = showCustomersSaleform.getInputByValue("Get Sales");
		HtmlPage saleInfoPage = showCustomersSaleButtonForm.click();
		List<DomElement> rows2 = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
		int numberOfSalesForThatCustomer = rows2.size() - 1;
		
		
		DomElement newSaleButton = page.getElementsById("botao2").get(6);
		assertEquals("Insert new Sale", newSaleButton.asText());
		HtmlPage newSalePage = newSaleButton.click();
		HtmlForm newSaleform = newSalePage.getForms().get(0);
		newSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		HtmlSubmitInput addSaleButton = newSaleform.getInputByValue("Add Sale");
		HtmlPage saleInfoPage2 = addSaleButton.click();

		// verificar sale inserido
		Date todaysDate = new Date();
		List<DomElement> rows = (List<DomElement>) saleInfoPage2.getElementsByTagName("tr");
		int numberOfSalesForThatCustomerAfterTest = rows.size() - 1;
		if (numberOfSalesForThatCustomer == -1) { //BECAUSE IT DOESNT HAVE A TABLE AND WILL CREATE 1 ROW EXTRA FOR THE TABLE HEADER
	    	assertEquals(numberOfSalesForThatCustomer+2, numberOfSalesForThatCustomerAfterTest);
	    } else {
	    	assertEquals(numberOfSalesForThatCustomer+1, numberOfSalesForThatCustomerAfterTest);
	    }
		
		for (DomElement row : rows) {
			List<HtmlElement> cells = row.getElementsByTagName("td");
			for (int i = 0; i < cells.size(); i++) {
				HtmlElement cell = cells.get(i);
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
		}
		
		// APAGAR SALE
		DomElement deleteLastSaleMenuButton = page.getElementsById("botao2").get(11);
		HtmlPage deleteLastSalePage = deleteLastSaleMenuButton.click();
		HtmlForm deleteLastSaleform = deleteLastSalePage.getForms().get(0);
		deleteLastSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		HtmlSubmitInput deleteLastSaleButton = deleteLastSaleform.getInputByValue("Delete Last Sale");
		deleteLastSaleButton.click();
		
		
		showCustomersSaleButton = page.getElementsById("botao2").get(8);
		assertEquals("Show Customer Sale's", showCustomersSaleButton.asText());
		showCustomersSalePage = showCustomersSaleButton.click();
		showCustomersSaleform = showCustomersSalePage.getForms().get(0);
		showCustomersSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
		showCustomersSaleButtonForm = showCustomersSaleform.getInputByValue("Get Sales");
		saleInfoPage = showCustomersSaleButtonForm.click();
		rows2 = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
		int numberOfSalesForThatCustomerFinal = rows2.size() - 1;
		
		assertEquals(numberOfSalesForThatCustomer, numberOfSalesForThatCustomerFinal);
	}

//	@Test
//	public void closeSaleTest() throws Exception {
//		// CRIAR A SALE
//		String customerVAT = "197672337";
//		DomElement newSaleButton = page.getElementsById("botao2").get(6);
//		assertEquals("Insert new Sale", newSaleButton.asText());
//		HtmlPage newSalePage = newSaleButton.click();
//		HtmlForm newSaleform = newSalePage.getForms().get(0);
//		newSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
//		HtmlSubmitInput addSaleButton = newSaleform.getInputByValue("Add Sale");
//		HtmlPage saleInfoPage = addSaleButton.click();
//
//		// OBETER O ID DO NOVO SALE INSERIDO
//		Date todaysDate = new Date();
//		List<DomElement> rows = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
////		int numberOfSalesForThatCustomer = rows.size() - 1;
////		assertEquals(numberOfSalesForThatCustomer, 1); //MUDAR ESTE 1 para depois conseguir automaticamente  no inicio
//		List<HtmlElement> cells = rows.get(rows.size() - 1).getElementsByTagName("td");
//		HtmlElement cell = cells.get(0);
//		String saleID = cell.getTextContent();
//
////		//FECHAR A SALE
//		DomElement closeSaleButton = page.getElementsById("botao2").get(7);
//		assertEquals("Close Existing Sale", closeSaleButton.asText());
//		HtmlPage closeSalePage = closeSaleButton.click();
//		HtmlForm closeSaleform = closeSalePage.getForms().get(0);
//		closeSaleform.getInputByName("id").setValueAttribute(saleID);
//		HtmlSubmitInput closeSaleButtonForm = closeSaleform.getInputByValue("Close Sale");
//		closeSaleButtonForm.click();
//
//		
//		// CONFIRMAR QUE A SALE FOI INSERIDA
////		saleInfoPage = (HtmlPage) saleInfoPage.refresh(); CANT USE THIS BECAUSE IT WILL ADD A NEW SALE
//		
//		DomElement showCustomersSaleButton = page.getElementsById("botao2").get(8);
//		assertEquals("Show Customer Sale's", showCustomersSaleButton.asText());
//		HtmlPage showCustomersSalePage = showCustomersSaleButton.click();
//		HtmlForm showCustomersSaleform = showCustomersSalePage.getForms().get(0);
//		showCustomersSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
//		HtmlSubmitInput showCustomersSaleButtonForm = showCustomersSaleform.getInputByValue("Get Sales");
//		saleInfoPage = showCustomersSaleButtonForm.click();
//		
//		List<DomElement> rows2 = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
//		int numberOfSalesForThatCustomer2 = rows2.size() - 1;
//		List<HtmlElement> cells2 = rows2.get(rows.size() - 1).getElementsByTagName("td");
//		for (int i = 0; i < cells2.size(); i++) {
//			HtmlElement cell2 = cells2.get(i);
//
//			if (i == 0) {
//				assertTrue(cell2.getTextContent().equals(saleID));
//			} else if (i == 1) {
//				assertTrue(cell2.getTextContent().equals((new java.sql.Date(todaysDate.getTime())).toString())); // DATA
//																													// DE
//																													// HOJE
//			} else if (i == 2) {
//				assertTrue(cell2.getTextContent().equals("0.0"));
//			} else if (i == 3) {
//				assertTrue(cell2.getTextContent().equals("C"));
//			} else if (i == 4) {
//				assertTrue(cell2.getTextContent().equals(customerVAT));
//			}
//		}
//
//		// APAGAR SALE
//		DomElement deleteLastSaleMenuButton = page.getElementsById("botao2").get(11);
//		HtmlPage deleteLastSalePage = deleteLastSaleMenuButton.click();
//		HtmlForm deleteLastSaleform = deleteLastSalePage.getForms().get(0);
//		deleteLastSaleform.getInputByName("customerVat").setValueAttribute(customerVAT);
//		HtmlSubmitInput deleteLastSaleButton = deleteLastSaleform.getInputByValue("Delete Last Sale");
//		deleteLastSaleButton.click();
//	}
//
//	@Test
//	public void createCustomerSaleAndDeliveryTest() throws Exception {
//		DomElement addCustomerButton = page.getElementsById("botao2").get(0);
//		assertEquals("Insert new Customer", addCustomerButton.asText());
//		HtmlPage addCustomerPage = addCustomerButton.click();
//		HtmlForm form = addCustomerPage.getForms().get(0);
//
//		// Insert Customer
//		String name = "Teste Teste";
//		String phone = "961234578";
//		String VAT = "123456789";
//		form.getInputByName("vat").setValueAttribute(VAT);
//		form.getInputByName("designation").setValueAttribute(name);
//		form.getInputByName("phone").setValueAttribute(phone);
//		HtmlSubmitInput submitButton = form.getInputByValue("Get Customer");
//		submitButton.click();
//
//		// VERIFICACAO DO CUSTOMER INSERIDO
//		DomElement listAllCustomersButton = page.getElementsById("botao2").get(5);
//		HtmlPage listAllCustomersPage = listAllCustomersButton.click();
//
//		List<DomElement> rows = (List<DomElement>) listAllCustomersPage.getElementsByTagName("tr");
//
//		DomElement firstAddedRow = rows.get(rows.size() - 1);
//		List<HtmlElement> firstCells = firstAddedRow.getElementsByTagName("td");
//
//		assertTrue(firstCells.get(0).getTextContent().equals(name));
//		assertTrue(firstCells.get(1).getTextContent().equals(phone));
//		assertTrue(firstCells.get(2).getTextContent().equals(VAT));
//
//		//ADICIONAR ADDRESS AO CUSTOMER
//		DomElement addAddressToCustomerButton = page.getElementsById("botao2").get(1);
//		assertEquals("Insert new Address to Customer", addAddressToCustomerButton.asText());
//		HtmlPage addAddressToCustomerPage = addAddressToCustomerButton.click();
//		form = addAddressToCustomerPage.getForms().get(0);
//		
//		//First Address
//		String address1= "Rua 1";
//		String door1 = "Porta 1";
//		String postalCode1 = "1234-1";
//		String locality1 = "Lisboa";
//		form.getInputByName("vat").setValueAttribute(VAT);
//	    form.getInputByName("address").setValueAttribute(address1);
//	    form.getInputByName("door").setValueAttribute(door1);
//	    form.getInputByName("postalCode").setValueAttribute(postalCode1);
//		form.getInputByName("locality").setValueAttribute(locality1);
//		submitButton = form.getInputByValue("Insert");
//	    HtmlPage nextPage = submitButton.click();
//		
//	    DomElement findCustomerByVatButton = page.getElementsById("botao2").get(3);
//		assertEquals("Find customer by vat number", findCustomerByVatButton.asText());
//		HtmlPage findCustomerByVatPage = findCustomerByVatButton.click();
//		HtmlForm findCustomerByVatForm = findCustomerByVatPage.getForms().get(0);
//		findCustomerByVatForm.getInputByName("vat").setValueAttribute(VAT);
//		HtmlSubmitInput submitButton2 = findCustomerByVatForm.getInputByValue("Get Customer");
//		HtmlPage clientInfoPage = submitButton2.click();
//	    
//		rows = (List<DomElement>) clientInfoPage.getElementsByTagName("tr");
//		String addressID = Integer.toString(rows.size()-1);
//		
//		
//		
//		// Insert Sale
//		DomElement newSaleButton = page.getElementsById("botao2").get(6);
//		assertEquals("Insert new Sale", newSaleButton.asText());
//		HtmlPage newSalePage = newSaleButton.click();
//		HtmlForm newSaleform = newSalePage.getForms().get(0);
//		newSaleform.getInputByName("customerVat").setValueAttribute(VAT);
//		HtmlSubmitInput addSaleButton = newSaleform.getInputByValue("Add Sale");
//		HtmlPage saleInfoPage = addSaleButton.click();
//
//		// OBETER O ID DO NOVO SALE INSERIDO
//		rows = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
//		List<HtmlElement> cells = rows.get(rows.size() - 1).getElementsByTagName("td");
//		HtmlElement cell = cells.get(0);
//		String saleID = cell.getTextContent();
//		
//
//		// verificar sale inserido
//		Date todaysDate = new Date();
//		List<DomElement> rows2 = (List<DomElement>) saleInfoPage.getElementsByTagName("tr");
//		int numberOfSalesForThatCustomer = rows2.size() - 1;
////		assertEquals(numberOfSalesForThatCustomer, 1); //MUDAR ESTE 1 para depois conseguir automaticamente  no inicio
//		cells = rows2.get(rows2.size() - 1).getElementsByTagName("td");
//		for (int i = 0; i < cells.size(); i++) {
//			cell = cells.get(i);
//			if (i == 0) {
////	            	assertTrue(cell.getTextContent().equals(1)); //JUST A COUNTER
//			} else if (i == 1) {
//				assertTrue(cell.getTextContent().equals((new java.sql.Date(todaysDate.getTime())).toString())); // HOJE
//			} else if (i == 2) {
//				assertTrue(cell.getTextContent().equals("0.0"));
//			} else if (i == 3) {
//				assertTrue(cell.getTextContent().equals("O"));
//			} else if (i == 4) {
//				assertTrue(cell.getTextContent().equals(VAT));
//			}
//
//		}
//
//		// INSERT DELIVERY TO A SALE
//		// Insert Sale
//		DomElement saleDeliveryButton = page.getElementsById("botao2").get(9);
//		assertEquals("Insert new Sale Delivery", saleDeliveryButton.asText());
//		HtmlPage saleDeliveryPage = saleDeliveryButton.click();
//		HtmlForm saleDeliveryform = saleDeliveryPage.getForms().get(0);
//		saleDeliveryform.getInputByName("vat").setValueAttribute(VAT);
//		HtmlSubmitInput getCustomerButton = saleDeliveryform.getInputByValue("Get Customer");
//		HtmlPage addSaleDeliveryPage = getCustomerButton.click();
//
//		HtmlForm addSaleDeliveryform = addSaleDeliveryPage.getForms().get(0);
////		String addressID = Integer.toString(rows.size());
//		addSaleDeliveryform.getInputByName("addr_id").setValueAttribute(addressID);
//		addSaleDeliveryform.getInputByName("sale_id").setValueAttribute(saleID);
//		HtmlSubmitInput insertButton = addSaleDeliveryform.getInputByValue("Insert");
//		HtmlPage customerSaleInfoPage = insertButton.click();
//
//		// verificar sale DELIVERY inserido
//		List<DomElement> rows3 = (List<DomElement>) customerSaleInfoPage.getElementsByTagName("tr");
//		int numberOfDeliveryForThatCustomer = rows3.size() - 1;
//		List<HtmlElement> cells3 = rows3.get(rows3.size() - 1).getElementsByTagName("td");
//		for (int i = 0; i < cells3.size(); i++) {
//			cell = cells3.get(i);
//			if (i == 0) {
////            	assertTrue(cell.getTextContent().equals(1)); //JUST AN ID COUNTER
//			} else if (i == 1) { // SALE ID
//				assertTrue(cell.getTextContent().equals(saleID));
//			} else if (i == 2) { // ADDRESS ID
//				assertTrue(cell.getTextContent().equals(addressID));
//			}
//		}
//
//		// DEPOIS DE VERIFICAR TUDO APAGAR DELIVERY SALE, SALE e CUSTOMER
//
//		// APAGAR SALE DELIVERY
//		DomElement deleteAllSaleDeliveryMenuButton = page.getElementsById("botao2").get(13);
//		HtmlPage deleteAllSaleDeliveryPage = deleteAllSaleDeliveryMenuButton.click();
//		HtmlForm deleteAllSaleDeliveryform = deleteAllSaleDeliveryPage.getForms().get(0);
//		deleteAllSaleDeliveryform.getInputByName("customerVat").setValueAttribute(VAT);
//		HtmlSubmitInput deleteAllSaleDeliveryButton = deleteAllSaleDeliveryform.getInputByValue("Delete All Sale Delivery");
//		deleteAllSaleDeliveryButton.click();
//		
//		// APAGAR SALE
//		DomElement deleteLastSaleMenuButton = page.getElementsById("botao2").get(11);
//		HtmlPage deleteLastSalePage = deleteLastSaleMenuButton.click();
//		HtmlForm deleteLastSaleform = deleteLastSalePage.getForms().get(0);
//		deleteLastSaleform.getInputByName("customerVat").setValueAttribute(VAT);
//		HtmlSubmitInput deleteLastSaleButton = deleteLastSaleform.getInputByValue("Delete Last Sale");
//		deleteLastSaleButton.click();
//		
//	    //DELETE DE TODOS OS ADDRESS DO CUSTOMER
//	    DomElement deleteAllAddressFromCustomerButton = page.getElementsById("botao2").get(12);
//		HtmlPage deleteAllAddressFromCustomerPage = deleteAllAddressFromCustomerButton.click();
//		HtmlForm deleteAllAddressFromCustomerForm = deleteAllAddressFromCustomerPage.getForms().get(0);
//		deleteAllAddressFromCustomerForm.getInputByName("customerVat").setValueAttribute(VAT);
//		HtmlSubmitInput deleteAllAddressButton = deleteAllAddressFromCustomerForm.getInputByValue("Delete All Address");
//		deleteAllAddressButton.click();
//		
//		//Remover customers inseridos
//		DomElement removeCustomerButton = page.getElementsById("botao2").get(2);
//	    HtmlPage removeCustomerPage = removeCustomerButton.click();
//	    HtmlForm removeCustomerform = removeCustomerPage.getForms().get(0);
//	    removeCustomerform.getInputByName("vat").setValueAttribute(VAT);
//	    HtmlSubmitInput removeButton = removeCustomerform.getInputByValue("Remove");
//	    removeButton.click();
//	}

}

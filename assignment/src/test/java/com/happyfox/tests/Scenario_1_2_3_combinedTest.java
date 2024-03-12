package com.happyfox.tests;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.testng.Assert;

import com.happyfox.base.HappyFoxTestBase;
import com.happyfox.pages.LoginPage;
import com.happyfox.pages.NewTicketPage;
import com.happyfox.pages.PriorityPage;
import com.happyfox.pages.StatusesPage;
import com.happyfox.pages.TicketDetailsPage;
import com.happyfox.pages.TicketsHomePage;
import com.happyfox.utils.Constants;
import com.happyfox.utils.WaitUtil;

import freemarker.log.Logger;

public class Scenario_1_2_3_combinedTest  extends HappyFoxTestBase {
	
	private static final Logger logger = Logger.getLogger(Scenario_1_2_3_combinedTest.class.getName());
  @Test
  public void allScenariosCombinedTest(){
	  
	  LoginPage loginPage = new LoginPage(driver);
	  logger.info("Statring test");
	  
	  //Login as Agent
	  loginPage.openAgentLogin();
	  
	  
	  String username = getProperty("username");
	  String password = getProperty("password");
	  loginPage.enterUsername(username);
	  loginPage.enterPassword(password);

	  loginPage.clickLogin();
	  
	  //Scenario 1
	  
	  //Login page validations
	  
	  TicketsHomePage ticketPage = new TicketsHomePage(driver);
	    
	  Assert.assertTrue(ticketPage.isTicketsHomepageDisplayed());
	  Assert.assertTrue(ticketPage.isTicketsListDisplayed());
	  Assert.assertEquals(ticketPage.getPagetitle(),Constants.ticket_page_title, "Incorrect Title");
	  
	  //navigate to statuses
	  ticketPage.navigateToStauses(driver);
	  StatusesPage statuspage = new StatusesPage(driver);
	  statuspage.clickOnAddStatus();
	  
	  //Add Statuses
	  
	  //Get Unique name
	  String baseName = getProperty("status_name");
	  String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());	  
	  
	  String statusesName = baseName + "_" + timestamp;
	  
	  //Status Page validations
	  Assert.assertEquals(statuspage.getPagetitle(),Constants.statuses_page_title, "Incorrect Title");
	  statuspage.createStatus(statusesName, getProperty("status_description"));
	  
	  //Check the created Status in list
	  Assert.assertTrue(statuspage.createdStatusCheck(statusesName));
	  
	  //Scroll to the Status created
	  statuspage.scrollToStatus(statusesName);
	  
	  
	  //Make Default
	  statuspage.makeDefault(statusesName);
	  
	  
	  //Create Priority
	  
	  //Navigate to  Priorities
	  
	  ticketPage.navigateToPriorities(driver);
	  PriorityPage priorityPage = new PriorityPage(driver);
	  priorityPage.clickOnAddPrioriity();
	  
	  //Priority page assertion
	  Assert.assertEquals(priorityPage.getPagetitle(),Constants.priorities_page_title, "Incorrect Title");
	  
	  //Add Priority
	  String priorityBaseName = getProperty("status_name");
	  String priorityName = priorityBaseName + timestamp;
	  priorityPage.createPriority(priorityName ,getProperty("priority_description") );
	  
	  //Check the created Status in list
	  AssertJUnit.assertTrue(priorityPage.createdPriorityCheck(priorityName));
	  
	  // scroll to created priority
	  
	  priorityPage.scrollToPriority(priorityName);
	  
	  //MakePriority Default
	  
	  priorityPage.makeDefault(priorityName);
	  
	  priorityPage.closeBrowser();
	  

	  // Initialize driver for customer login
	  
	  setupDriver(Constants.browser);
	  driver.manage().window().maximize();
	  driver.get(Constants.customer_url);
	  driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
	  WaitUtil.setDriver(driver);
	 
        
        NewTicketPage newTicketPage = new NewTicketPage(driver);
        
        
        //Subject , Message And Add Attachment
        newTicketPage.enterSubjectAndMessage(getProperty("ticket_subject"), getProperty("ticket_message"));
        newTicketPage.addAttachment();
        
        //Checking the attachment
        newTicketPage.verifyAttachment();
        
        newTicketPage.enterContactDetailsAndSubmit(getProperty("customer_name"), getProperty("customer_email"));
        // Validate Confirmation Page
        Assert.assertEquals(newTicketPage.getConfirmationMessage(), Constants.ticket_confirmation);
        
        newTicketPage.closeBrowser();
        
        
        setupDriver(Constants.browser);
        driver.manage().window().maximize();
  	  	driver.get(Constants.agent_url);
	  	driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		WaitUtil.setDriver(driver);
		
		LoginPage agentLoginPage = new LoginPage(driver);

		agentLoginPage.enterUsername(getProperty("username"));
		agentLoginPage.enterPassword(getProperty("password"));

		agentLoginPage.clickLogin();
		
		TicketDetailsPage ticketDetailspage= new TicketDetailsPage(driver);
		ticketDetailspage.openTicketDetails(getProperty("ticket_subject"));
		
		// validate Status and Priority
		Assert.assertTrue(statusesName.equalsIgnoreCase(ticketDetailspage.getTicketStatus()));
		System.out.println(priorityName);
		System.out.println(ticketDetailspage.getTicketPriority());
		
		Assert.assertTrue(priorityName.equalsIgnoreCase(ticketDetailspage.getTicketPriority()));

		
		//Reply via canned action
		
		ticketDetailspage.clickOnReply();
		ticketDetailspage.clickOnCannedAction();
		ticketDetailspage.enterCannedActionNameAndSelect(getProperty("canned_action_name"));
		
		//scenario - 3
		
		  ticketDetailspage.navigateToStauses(driver);
		  StatusesPage statusPage = new StatusesPage(driver);
		  TicketsHomePage ticketHomePage = new TicketsHomePage(driver);
		  statusPage.scrollToStatusToDelete(statusesName);
		  statusPage.createdStatusToDelete(statusesName);
		  statusPage.deleteStatusConfirmationModal();
		  Assert.assertEquals(statusPage.getDeleteModalHeader(), Constants.delete_status_header);
		  Assert.assertEquals(statusPage.getWarningMessage(), Constants.delete_status_warning);
		  
		  Assert.assertTrue(statusPage.statusDeleteSuccessToast());
		  
		  statusPage.waitForToastToDisappear();
		  
		  //Delete Priority
		  
		  ticketHomePage.navigateToPriorities(driver);
		  PriorityPage prioritiesPage = new PriorityPage(driver);
		  prioritiesPage.selectPriorityToDelete(priorityName);
		  Assert.assertEquals(prioritiesPage.getDeleteModalHeader(), Constants.delete_priority_header);
		  Assert.assertEquals(prioritiesPage.getWarningMessage(), Constants.delete_priority_warning);
		  prioritiesPage.deletePriorityConfirmationModal();
		  
		  Assert.assertTrue(prioritiesPage.priorityDeleteSuccessToast());
		  
		  //LogOut Agent
		  statusPage.waitForToastToDisappear();
		  ticketHomePage.logoutAgent();
		  Assert.assertEquals(agentLoginPage.getLogOutMessage(), Constants.logout_confirmation);
		  agentLoginPage.getLogOutMessage();
  }
  
  @Test
  public void oneAndThreeScenariosCombinedTest(){
	  
	  LoginPage loginPage = new LoginPage(driver);
	  logger.info("Statring test");
	  
	  //Login as Agent
	  loginPage.openAgentLogin();
	  
	  
	  String username = getProperty("username");
	  String password = getProperty("password");
	  loginPage.enterUsername(username);
	  loginPage.enterPassword(password);

	  loginPage.clickLogin();
	  
	  //Scenario 1
	  
	  //Login page validations
	  
	  TicketsHomePage ticketPage = new TicketsHomePage(driver);
	    
	  Assert.assertTrue(ticketPage.isTicketsHomepageDisplayed());
	  Assert.assertTrue(ticketPage.isTicketsListDisplayed());
	  Assert.assertEquals(ticketPage.getPagetitle(),Constants.ticket_page_title);
	  
	  //navigate to statuses
	  ticketPage.navigateToStauses(driver);
	  StatusesPage statuspage = new StatusesPage(driver);
	  statuspage.clickOnAddStatus();
	  
	  //Add Statuses
	  
	  //Get Unique name
	  String baseName = getProperty("status_name");
	  String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());	  
	  
	  String statusesName = baseName + "_" + timestamp;
	  
	  //Status Page validations
	  Assert.assertEquals(statuspage.getPagetitle(),Constants.statuses_page_title);
	  statuspage.createStatus(statusesName, getProperty("status_description"));
	  
	  //Check the created Status in list
	  Assert.assertTrue(statuspage.createdStatusCheck(statusesName));
	  
	  //Scroll to the Status created
	  statuspage.scrollToStatus(statusesName);
	  
	  
	  //Make Default
	  statuspage.makeDefault(statusesName);
	  
	  
	  //Create Priority
	  
	  //Navigate to  Priorities
	  
	  ticketPage.navigateToPriorities(driver);
	  PriorityPage priorityPage = new PriorityPage(driver);
	  priorityPage.clickOnAddPrioriity();
	  
	  //Priority page assertion
	  Assert.assertEquals(priorityPage.getPagetitle(),Constants.priorities_page_title);
	  
	  //Add Priority
	  String priorityBaseName = getProperty("status_name");
	  String priorityName = priorityBaseName + timestamp;
	  priorityPage.createPriority(priorityName ,getProperty("priority_description") );
	  
	  //Check the created Status in list
	  Assert.assertTrue(priorityPage.createdPriorityCheck(priorityName));
	  
	  // scroll to created priority
	  
	  priorityPage.scrollToPriority(priorityName);
	  
	  //MakePriority Default
	  
	  priorityPage.makeDefault(priorityName);
	  
	  //Delete Status
	  priorityPage.navigatetToStatuses(driver);
	  statuspage.scrollToStatus(statusesName);
	  statuspage.createdStatusToDelete(statusesName);
	  statuspage.deleteStatusConfirmationModal();
	  Assert.assertEquals(statuspage.getDeleteModalHeader(), Constants.delete_status_header);
	  Assert.assertEquals(statuspage.getWarningMessage(), Constants.delete_status_warning);
	  
	  Assert.assertTrue(statuspage.statusDeleteSuccessToast());
	  
	  statuspage.waitForToastToDisappear();
	  
	  //Delete Priority
	  ticketPage.navigateToPriorities(driver);
	  priorityPage.selectPriorityToDelete(priorityName);
	  Assert.assertEquals(priorityPage.getDeleteModalHeader(), Constants.delete_priority_header);
	  Assert.assertEquals(priorityPage.getWarningMessage(), Constants.delete_priority_warning);
	  priorityPage.deletePriorityConfirmationModal();
	  
	  Assert.assertTrue(priorityPage.priorityDeleteSuccessToast());
	  
	  //LogOut Agent
	  statuspage.waitForToastToDisappear();
	  ticketPage.logoutAgent();
	  Assert.assertEquals(loginPage.getLogOutMessage(), Constants.logout_confirmation);
	  loginPage.getLogOutMessage();
	  
  }
}

package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.

		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign up was successful. 
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depening on the rest of your code.
		*/
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	private void clickElementById(WebDriverWait webDriverWait, String elementId) {
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(elementId)));
		WebElement element = driver.findElement(By.id(elementId));
		element.click();
	}

	private void fillTextInElement(WebDriverWait webDriverWait, String textFieldId, String text) {
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(textFieldId)));
		WebElement fileSelectButton = driver.findElement(By.id(textFieldId));
		fileSelectButton.sendKeys(text);
	}

	private void cleanUpTextElement(WebDriverWait webDriverWait, String textFieldId) {
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id(textFieldId)));
		WebElement fileSelectButton = driver.findElement(By.id(textFieldId));
		fileSelectButton.clear();
	}

	private void createNewNote(WebDriverWait webDriverWait, String newNoteTitle, String newNoteDes) {
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-notes-tab");

		clickElementById(webDriverWait,"add-new-note");

		fillTextInElement(webDriverWait, "note-title", newNoteTitle);

		fillTextInElement(webDriverWait, "note-description", newNoteDes);

		clickElementById(webDriverWait,"note-save-changes");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-notes-tab");
	}

	private void createNewCredential(WebDriverWait webDriverWait, String credentialURL, String credentialUsername, String credentialPassword) {
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-credentials-tab");

		clickElementById(webDriverWait,"add-new-credential");

		fillTextInElement(webDriverWait, "credential-url", credentialURL);

		fillTextInElement(webDriverWait, "credential-username", credentialUsername);

		fillTextInElement(webDriverWait, "credential-password", credentialPassword);

		clickElementById(webDriverWait,"credential-save-changes");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-credentials-tab");
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a succesful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("Redirection","Test","RT","123");
		
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test","UT","123");
		doLogIn("UT", "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test","LFT","123");
		doLogIn("LFT", "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "upload5m.zip";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testHomePageIsNotAccessibleAfterLogOut(){
		// Create a test account
		doMockSignUp("First name 1","Last name 1","UserName1","123");
		doLogIn("UserName1", "123");
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertTrue(driver.getPageSource().contains("Logged in as:"));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertFalse(driver.getPageSource().contains("Logged in as:"));
	}

	@Test
	public void testAddNewNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		String newNoteTitle = "New Note Title";
		String newNoteDes = "New note description";

		doMockSignUp("testAddNewNote","testAddNewNote","testAddNewNote","testAddNewNote");
		doLogIn("testAddNewNote", "testAddNewNote");

		createNewNote(webDriverWait, newNoteTitle, newNoteDes);

		Assertions.assertTrue(driver.getPageSource().contains(newNoteTitle));
		Assertions.assertTrue(driver.getPageSource().contains(newNoteDes));

	}

	@Test
	public void testEditNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		String newNoteTitle = "New Note Title 2";
		String newNoteDes = "New note description 2";
		String updatedNoteTitle = "New Note Title 3";
		String updatedNoteDes = "New note description 3";

		doMockSignUp("testEditNote","testEditNote","testEditNote","testEditNote");
		doLogIn("testEditNote", "testEditNote");

		createNewNote(webDriverWait, newNoteTitle, newNoteDes);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("edit-userNote-button")));
		WebElement element = driver.findElements(By.className("edit-userNote-button")).get(0);
		element.click();
		cleanUpTextElement(webDriverWait, "note-title");
		fillTextInElement(webDriverWait, "note-title", updatedNoteTitle);
		cleanUpTextElement(webDriverWait, "note-description");
		fillTextInElement(webDriverWait,"note-description",updatedNoteDes );

		clickElementById(webDriverWait,"note-save-changes");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-notes-tab");

		Assertions.assertTrue(driver.getPageSource().contains(updatedNoteTitle));
		Assertions.assertTrue(driver.getPageSource().contains(updatedNoteDes));

	}

	@Test
	public void testDeleteNote(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		String noteTitle = "Random Notes 0.2";
		String noteDes = "Random Notes 0.2";
		String noteTitle2 = "New Device!";
		String noteDes2 = "New Device!";

		doMockSignUp("testDeleteNote","testDeleteNote","testDeleteNote","testDeleteNote");
		doLogIn("testDeleteNote", "testDeleteNote");

		createNewNote(webDriverWait, noteTitle, noteDes);
		createNewNote(webDriverWait, noteTitle2, noteDes2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("delete-note")));
		WebElement element = driver.findElements(By.className("delete-note")).get(0);
		element.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-notes-tab");

		Assertions.assertFalse(driver.getPageSource().contains(noteTitle));
		Assertions.assertFalse(driver.getPageSource().contains(noteDes));
		Assertions.assertTrue(driver.getPageSource().contains(noteTitle2));
		Assertions.assertTrue(driver.getPageSource().contains(noteDes2));

	}

	@Test
	public void testAddNewCredential(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		String credentialURL = "gmail.com";
		String credentialUsername = "samer@gmail.com";
		String credentialPassword = "654321asd";

		doMockSignUp("testAddNewCredential","testAddNewCredential","testAddNewCredential","testAddNewCredential");
		doLogIn("testAddNewCredential", "testAddNewCredential");

		createNewCredential(webDriverWait, credentialURL, credentialUsername, credentialPassword);

		Assertions.assertTrue(driver.getPageSource().contains(credentialURL));
		Assertions.assertTrue(driver.getPageSource().contains(credentialUsername));

	}

	@Test
	public void testEditCredential(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		String credentialURL = "gmail.com";
		String credentialUsername = "samer@gmail.com";
		String credentialPassword = "654321asd";
		String updatedCredentialURL = "gmx.com";
		String updatedCredentialUsername = "husam@gmx.com";
		String updatedCredentialPassword = "dsa654";

		doMockSignUp("testEditCredential","testEditCredential","testEditCredential","testEditCredential");
		doLogIn("testEditCredential", "testEditCredential");

		createNewCredential(webDriverWait, credentialURL, credentialUsername, credentialPassword);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("edit-credential-button")));
		WebElement element = driver.findElements(By.className("edit-credential-button")).get(0);
		element.click();
		cleanUpTextElement(webDriverWait, "credential-url");
		fillTextInElement(webDriverWait, "credential-url", updatedCredentialURL);
		cleanUpTextElement(webDriverWait, "credential-username");
		fillTextInElement(webDriverWait,"credential-username",updatedCredentialUsername );
		cleanUpTextElement(webDriverWait, "credential-password");
		fillTextInElement(webDriverWait,"credential-password",updatedCredentialPassword );

		clickElementById(webDriverWait,"credential-save-changes");
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-credentials-tab");

		Assertions.assertTrue(driver.getPageSource().contains(updatedCredentialURL));
		Assertions.assertTrue(driver.getPageSource().contains(updatedCredentialUsername));
		Assertions.assertFalse(driver.getPageSource().contains(credentialURL));
		Assertions.assertFalse(driver.getPageSource().contains(credentialUsername));

	}

	@Test
	public void testDeleteCredential(){
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		String firstCredentialURL = "gmail.com";
		String firstCredentialUsername = "samer@gmail.com";
		String firstCredentialPassword = "654321asd";
		String secondCredentialURL = "gmx.com";
		String secondCredentialUsername = "husam@gmx.com";
		String secondCredentialPassword = "dsa654";

		doMockSignUp("testDeleteCredential","testDeleteCredential","testDeleteCredential","testDeleteCredential");
		doLogIn("testDeleteCredential", "testDeleteCredential");

		createNewCredential(webDriverWait, firstCredentialURL, firstCredentialUsername, firstCredentialPassword);
		createNewCredential(webDriverWait, secondCredentialURL, secondCredentialUsername, secondCredentialPassword);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.className("delete-credential")));
		WebElement element = driver.findElements(By.className("delete-credential")).get(0);
		element.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success")));
		driver.get("http://localhost:" + this.port + "/home");

		clickElementById(webDriverWait,"nav-credentials-tab");
		clickElementById(webDriverWait,"nav-credentials-tab");

		Assertions.assertFalse(driver.getPageSource().contains(firstCredentialURL));
		Assertions.assertFalse(driver.getPageSource().contains(firstCredentialUsername));
		Assertions.assertTrue(driver.getPageSource().contains(secondCredentialURL));
		Assertions.assertTrue(driver.getPageSource().contains(secondCredentialUsername));

	}

}

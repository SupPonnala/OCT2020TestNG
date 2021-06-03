package variousCocepts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest_TestNG {

	WebDriver driver;
	String browser = null;
	String url = null;

	@BeforeClass
	public void readConfig() {

		Properties prop = new Properties();
		// InputStream // BufferedReader // FileReader // Scanner

		try {
			InputStream input = new FileInputStream("src\\main\\java\\config\\config.properties");
			prop.load(input);
			browser = prop.getProperty("browser");
			System.out.println("Used Browser: " + browser);
			url = prop.getProperty("url");
			System.out.println("Used Environment: " + url);
			

		} catch (IOException e) {
			e.printStackTrace();

		}

	}

	@BeforeMethod
	public void init() {
		// to call multiple browser
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", "drivers\\msedgedriver.exe");
			driver = new EdgeDriver();
		}

		driver.get("https://techfios.com/billing/?ng=admin/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test(priority = 1)
	public void loginTest() {

		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong Page!");
		// Element library
		// Storing using webElement class
		WebElement USER_NAME_FIELD_ELEMENT = driver.findElement(By.xpath("//input[@id='username']"));
		WebElement PASSWORD_FIELD_ELEMENT = driver.findElement(By.xpath("//input[@id='password']"));
		WebElement SIGNIN_BUTTON_ELEMENT = driver.findElement(By.xpath("//button[@name='login']"));

		USER_NAME_FIELD_ELEMENT.clear(); // this will help to clear any existing data in that fields
		USER_NAME_FIELD_ELEMENT.sendKeys("demo@techfios.com");
		PASSWORD_FIELD_ELEMENT.sendKeys("abc123");
		SIGNIN_BUTTON_ELEMENT.click();

		Assert.assertEquals(driver.getTitle(), "Dashboard- iBilling", "Wrong Page!");
	}

	@Test(priority = 2)
	public void addCustomerTest() {

		// Element Library
		By USER_NAME_FIELD = By.id("username");
		By PASSWORD_FIELD = By.id("password");
		By SIGNIN_BUTTON = By.name("login");
		By MENU_BUTTON = By.xpath("//i[@class='fa fa-dedent']");
		By DASHBOARD_BUTTON = By.xpath("//span[contains(text(), 'Dashboard')]");
		By CUSTOMERS_BUTTON = By.xpath("//span[contains(text(), 'Customers')]");
		By ADD_CUSTOMER_BUTTON = By.xpath("//a[contains(text(), 'Add Customer')]");
		By ADD_CONTACT_LOCATOR = By.xpath("//h5[contains(text(), 'Add Contact')]");
		By FULL_NAME_FIELD = By.xpath("//input[@id='account']");
		By COMPANY_NAME_FIELD = By.xpath("//select[@id='cid']");
		By EMAIL_FIELD = By.xpath("//input[@id='email']");
		By PHONE_FIELD = By.xpath("//input[@id='phone']");
		By ADDRESS_FIELD = By.xpath("//input[@id='address']");
		By CITY_FIELD = By.xpath("//input[@id='city']");
		By STATE_REGION_FIELD = By.xpath("//input[@id='state']");
		By ZIP_FIELD = By.xpath("//input[@id='zip']");
		By SUBMIT_BUTTON = By.xpath("//button[@class='btn btn-primary']");
		By CONTACT_LIST_BUTTON = By.xpath("//a[contains(text(),'list contacts')]");

		// Login Data
		String loginId = "demo@techfios.com";
		String password = "abc123";

		// Test or Mock Data
		String fullName = "Test Oct";
		String companyName = "Techfios";
		String emailAddress = "Test@gmail.com";
		String phoneNumber = "1234445555";

		Assert.assertEquals(driver.getTitle(), "Login - iBilling", "Wrong Page!");
		driver.findElement(USER_NAME_FIELD).sendKeys(loginId);
		driver.findElement(PASSWORD_FIELD).sendKeys(password);
		driver.findElement(SIGNIN_BUTTON).click();
		Assert.assertEquals(driver.getTitle(), "Dashboard- iBilling", "Wrong Page!");

		// Add Customer
		driver.findElement(CUSTOMERS_BUTTON).click();

		// explicit wait
		waitForElement(driver, 5, ADD_CUSTOMER_BUTTON);
		driver.findElement(ADD_CUSTOMER_BUTTON).click();

		waitForElement(driver, 5, FULL_NAME_FIELD);

		// generate random number
		Random rnd = new Random();
		int randomNum = rnd.nextInt(999);

		driver.findElement(FULL_NAME_FIELD).sendKeys(fullName + randomNum);

		// select dropdown
		waitForElement(driver, 3, COMPANY_NAME_FIELD);
		Select sel = new Select(driver.findElement(COMPANY_NAME_FIELD));
		sel.selectByVisibleText("Techfios");

		waitForElement(driver, 3, EMAIL_FIELD);
		driver.findElement(EMAIL_FIELD).sendKeys(emailAddress);
	}

	public void waitForElement(WebDriver driver, int timeInseconds, By locator) {

		WebDriverWait wait = new WebDriverWait(driver, timeInseconds);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

	}

	@AfterMethod
	public void tearDown() {
		driver.close();
		driver.quit();
	}
	
	

}

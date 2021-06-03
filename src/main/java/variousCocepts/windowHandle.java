package variousCocepts;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class windowHandle {
	WebDriver driver;

	@Test
	public void init() throws InterruptedException {

		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver_win32\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://www.yahoo.com/");
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		System.out.println(driver.getTitle());
		String handle1 = driver.getWindowHandle();
		System.out.println(handle1);
		
		driver.findElement(By.xpath("//*[@id=\"atomic\"]")).sendKeys("xpath");
		driver.findElement(By.xpath("//*[@id=\"ybar-search\"]")).click();
		
		System.out.println(driver.getTitle());
		String handle2 = driver.getWindowHandle();
		System.out.println(handle2);
		
		driver.findElement(By.linkText("XPath Tutorial - W3Schools")).click();
		
		System.out.println(driver.getTitle());
		Set<String> handle3 = driver.getWindowHandles();
		
		for(String i : handle3) {
			System.out.println(i);
			driver.switchTo().window(i);
		}
		System.out.println(driver.getTitle());
		
		driver.close();
		driver.quit();  

}
}


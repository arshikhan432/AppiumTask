package appium.test;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;

public class AppiumDemo {
	
	AndroidDriver driver;
	
	@BeforeClass
	public void setUp() {
		
		ObjectMapper objectMapper = new ObjectMapper();
        try {
            // Read the JSON file and map/convert it to the User class
            AppiumConf appiumConf = objectMapper.readValue(new File(System.getProperty("user.dir")+"\\src\\test\\java\\config.json"), AppiumConf.class);
            
            UiAutomator2Options options = new UiAutomator2Options();
            options.setPlatformName(appiumConf.getPlatformName());
            options.setPlatformVersion(appiumConf.getPlatformVersion()); 
            options.setDeviceName(appiumConf.getDeviceName()); 
            options.setAutomationName(appiumConf.getAutomationName());
            options.setApp(System.getProperty("user.dir")+"\\resources\\WikipediaSample.apk"); 
         // Initialize the Appium driver
            try {
    			this.driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
    
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }

        
	}
	
	@Test(enabled = false)	
    public void task1() {
    	

        try {
           
        	boolean canScrollMore;
            do {
                canScrollMore = (Boolean) ((JavascriptExecutor) driver).executeScript("mobile: scrollGesture", ImmutableMap.of(
                    "left", 100, 
                    "top", 100, 
                    "width", 200, 
                    "height", 200,
                    "direction", "down",
                    "percent", 1.0
                ));
            } while (canScrollMore);

            System.out.println("Session started successfully!");
         
            String[] navigation_page = {"My lists", "History", "Nearby"};
           
            for (String page : navigation_page) {
                
                WebElement page_link = this.driver.findElement(By.xpath("//android.widget.FrameLayout[@content-desc='" + page + "']"));
                page_link.click();
                Thread.sleep(3000);
            }
            
            WebElement explore_link = this.driver.findElement(By.xpath("//android.widget.FrameLayout[@content-desc='Explore']"));
            explore_link.click();
            	
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
	
	@Test
    public void task2() {

        try {
            
            WebElement element = this.driver.findElement(By.id("org.wikipedia.alpha:id/search_container"));
            element.click();
            
            WebElement element_search_text = this.driver.findElement(By.id("org.wikipedia.alpha:id/search_src_text"));
            element_search_text.sendKeys("New York");
            
            Thread.sleep(3000);
            
            WebElement element_search_list = this.driver.findElement(By.id("org.wikipedia.alpha:id/search_results_list']"));
            if (!element_search_list.isDisplayed()) {
            	Assert.assertTrue(false);
            }
            Thread.sleep(3000);
            
            WebElement close_search = this.driver.findElement(By.id("org.wikipedia.alpha:id/search_close_btn"));
            
            Actions actions = new Actions(driver);
            actions.contextClick(close_search).perform();

            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
	
	@Test
	public void task3() {
    	
        try {

            WebElement element = this.driver.findElement(By.id("org.wikipedia.alpha:id/drawer_icon_menu"));
            element.click();
            
            Thread.sleep(3000);
            
            WebElement element_setting = this.driver.findElement(By.id("org.wikipedia.alpha:id/main_drawer_settings_container"));
            element_setting.click();
            Thread.sleep(3000);
            
            List<WebElement> lst_options = driver.findElements(By.id("org.wikipedia.alpha:id/switchWidget"));
            
            for(WebElement ele_opt : lst_options) {
            	String checked;
            	
            	checked = ele_opt.getAttribute("checked");
            	
            	if(checked.equalsIgnoreCase("true")) {
            		ele_opt.click();
            	}
            	
            }
            
            WebElement back_button = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Navigate up\"]"));
            back_button.click();
            
            
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
	
	@AfterClass
	public void tearDown() {
		// Quit the this.driver session
        this.driver.quit();
	}
	
}

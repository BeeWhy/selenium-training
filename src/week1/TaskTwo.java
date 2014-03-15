package week1;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 3/13/14.
 */
public class TaskTwo {
    public static void main(String[] args){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.cdw.com/");
        WebElement closer = driver.findElement(By.className("btnClose"));
        closer.click();
        WebElement searchBox = driver.findElement(By.id("searchbox2"));
        searchBox.sendKeys("3133487"); //replaced the 2961650 number since product was unavailable today
        WebElement goButton = driver.findElement(By.className("searchButton"));
        goButton.click();


        WebElement searchresults = (new WebDriverWait(driver, 1000))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("primaryProductName"))); //classname "hproduct"

        System.out.print(searchresults.getText());

        //Verify if the following sections are shown: PRODUCT OVERVIEW, TECHNICAL SPECIFICATIONS, ACCESSORIES, WARRANTIES AND SERVICES, PRODUCT REVIEWS
        WebElement productOverview, techSpecs, accessories, warrAndServs, reviews;

        List<WebElement> elementsToShow = new ArrayList<WebElement>();
        productOverview = driver.findElement(By.partialLinkText("Product Overview"));
        elementsToShow.add(productOverview);

//        techSpecs = driver.findElement(By.partialLinkText("Technical Specifications"));
//        elementsToShow.add(techSpecs);

        techSpecs = driver.findElement(By.id("TS_pnlDynamicHeader"));
        elementsToShow.add(techSpecs);
//        accessories =  driver.findElement(By.cssSelector("value=\"Accessories\""));
//        elementsToShow.add(accessories);

        accessories = driver.findElement(By.id("AA_pnlPostbackHeader"));
        elementsToShow.add(accessories);

//        warrAndServs = driver.findElement(By.cssSelector("value=\"Warranties and Services\""));
//        elementsToShow.add(warrAndServs);

        warrAndServs = driver.findElement(By.id("WAR_pnlPostbackHeader"));
        elementsToShow.add(warrAndServs);

        reviews = driver.findElement(By.partialLinkText("Product Reviews"));
        elementsToShow.add(reviews);

        for (WebElement section : elementsToShow){
            if(elementIsShown(section)){
                System.out.println("\nelement " + section.getText() +" is shown");

            }else {
                System.out.println("\nelement " + section.getText() +" is NOT shown");

            }
        }

        WebElement expandAccriesBtn = driver.findElement(By.name("AA$ibtnPostbackHeader"));
        expandAccriesBtn.click();
        File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
// Now you can do whatever you need to do with it, for example copy somewhere
        try {
            FileUtils.copyFile(scrFile, new File("./screen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        WebElement scndAccriesExpandBtn = driver.findElement(By.name("AA$CA-1$ibtnPostbackHeader"));
        scndAccriesExpandBtn.click();

        WebElement temp = (new WebDriverWait(driver, 1000))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("AA_CA-1_rac_dlAccessories_ctl02_mpdAccessories_hlName")));


        try {
            FileUtils.copyFile(scrFile, new File("./screen2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        WebElement scndAccessory = driver.findElement(By.id("AA_CA-1_rac_dlAccessories_ctl02_mpdAccessories_hlName"));
        scndAccessory.click();

        WebElement scndElem = (new WebDriverWait(driver, 1000))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("primaryProductName")));

        final String cdwNumberOrig = driver.findElement(By.id("primaryProductPartNumbers")).getText().substring(29, 36);
        System.out.println("original cdw # is "+cdwNumberOrig);
        WebElement qtyInput = driver.findElement(By.id("txtQty"));
        qtyInput.sendKeys("\b2");
//        WebElement addToCartBtn = driver.findElement(By.cssSelector(".add-to-cart-button.yellowButton.smallButton"));
        WebElement addToCartBtn = driver.findElement(By.name("primaryProductZone$primaryProductAvailabilityAndPricingZone$ctl06"));

        addToCartBtn.click();

        WebElement cartPopUp = (new WebDriverWait(driver, 1000))
                .until(ExpectedConditions.presenceOfElementLocated(By.className("itemrecord")));

        String achievedQty = driver.findElement(By.className("mediumdetail")).getText();
        int finalQty = Integer.parseInt(achievedQty);
        if(finalQty == 2){
            System.out.println("quantity matches input qty");
        } else {
            System.out.println("quantity doesn't match input qty");
        }

        driver.close();
    }



    private static boolean elementIsShown(WebElement element){
        return element.isDisplayed();
    }
}

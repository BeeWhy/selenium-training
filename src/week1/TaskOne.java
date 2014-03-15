package week1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 3/5/14.
 */
public class TaskOne {
    public static void main(String[] args){
        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.cdw.com/");
        WebElement closer = driver.findElement(By.className("btnClose"));
        closer.click();
       WebElement searchBox = driver.findElement(By.id("searchbox2"));
        searchBox.sendKeys("cables");
        WebElement goButton = driver.findElement(By.className("searchButton"));
        goButton.click();


        WebElement searchresults = (new WebDriverWait(driver, 1000))
                .until(ExpectedConditions.presenceOfElementLocated(By.id("lblShowingResultsTop")));

        System.out.print(searchresults.getText());
        String showingResults = searchresults.getText();

        String numberOfItems = showingResults.replace("Showing: 1 - 25 of ", "");
        int itemsFound = Integer.parseInt(numberOfItems);

        int toCompare = 18320;
        if (itemsFound==toCompare){
            System.out.println(itemsFound + " equals provided number of results: " + toCompare);
        } else {
            System.out.println("/n" +itemsFound + " NOT equals provided number of results: " + toCompare);

        }
        WebElement searchTable = driver.findElement(By.id("searchtable"));
        List<WebElement> items = searchTable.findElements(By.className("searchrow"));
        System.out.println(items.size());
      //  WebElement chosenItem = driver.findElement(By.id("ctrlResults_dlResults_ctl02_resultItem_lblCdwProductCode"));

       String price;


       Map<String, WebElement> mapOfItems =  convertToMap(items, driver);
        if (mapOfItems.containsKey("1365849")){
           WebElement searchedItem = mapOfItems.get("1365849");

            price = searchedItem.findElement(By.className("selected-price price")).getText().substring(1);
            System.out.println(price);
            double priceDbl = Double.parseDouble(price);
            System.out.println(priceDbl);

        }else {
            System.out.println("doesn't contain product with id 1365849");

            //price = items.get(0).findElement(By.className("selected-price price")).getText().substring(1);
            price = items.get(0).findElement(By.id("ctrlResults_dlResults_ctl01_resultItem_addToCartNugget_ctl09_singleCurrentItemLabel")).getText().substring(1);
            System.out.println(price);
            double priceDbl = Double.parseDouble(price);
            System.out.println(priceDbl);

            WebElement qtyInput = driver.findElement(By.id("ctrlResults_dlResults_ctl01_resultItem_addToCartNugget_ctl08_txtQty"));
            qtyInput.sendKeys("\b12");
            WebElement addToCartBtn = driver.findElement(By.name("ctrlResults$dlResults$ctl01$resultItem$addToCartNugget$ctl10"));
            addToCartBtn.click();
            WebElement checkoutWindow = (new WebDriverWait(driver, 1000))
                    .until(ExpectedConditions.presenceOfElementLocated(By.className("content")));
            WebElement totalPrice = driver.findElement(By.id("ucCartConfirmation_cartitems_repCartItems_ctl00_cartitem_itemtotal"));
            String totalPriceValue = totalPrice.getText().substring(1);
            double totalPriceDbl = Double.parseDouble(totalPriceValue);
            double expectedTotal = priceDbl*12.0;
            System.out.println(expectedTotal);
            if (expectedTotal==totalPriceDbl){
                System.out.println("total price was calculated correctly: " + totalPriceDbl);
            } else {
                System.out.println("total price was calculated incorrectly: " + totalPriceDbl + ", should be " + expectedTotal);

            }


        }


       driver.close();


    }

    private static Map<String, WebElement> convertToMap(List<WebElement> items, WebDriver driver) {
        Map<String, WebElement> itemsMap = new HashMap<String, WebElement>();
        for(   WebElement item : items){
            int count = 01;
            String s = String.format("%02d", count);

            WebElement subElement = item.findElement(By.className("searchrow-description"));
                   // findElement(By.id("ctrlResults_dlResults_ct"+count+"_resultItem_lblCdwProductCode")));
            String key = driver.findElement(By.id("ctrlResults_dlResults_ctl"+s+"_resultItem_lblCdwProductCode")).getText();
            itemsMap.put(key, item);
            count++;
        }
        return itemsMap;
    }


}

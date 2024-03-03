package utils;

import base.BaseSetup;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;

public class SafeActions implements Waits {
    BaseSetup baseSetup = new BaseSetup();
    public WebDriver driver;

    public SafeActions() {
        driver = baseSetup.getDriver();
    }

    public void navigateToURL(String URL) {
        driver.get(URL);
        System.out.println("User navigated to " + URL);
    }

    public void refresh(){
        driver.navigate().refresh();
    }

    public boolean isElementPresent(By locator, int waitTime) {
        boolean bFlag = false;
        try {
            WebDriverWait wait = new WebDriverWait(driver, waitTime);
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            if (driver.findElement(locator).isDisplayed() || driver.findElement(locator).isEnabled()) {
                bFlag = true;
                System.out.println("Element " + locator + " is displayed");
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element " + locator + " was not found in dom");
        } catch (Exception e) {
            System.out.println("Element " + locator + " is not displayed");
        }
        return bFlag;
    }

    public static boolean WaitUntilClickable(WebDriver driver, By bylocator, int iWaitTime) throws Exception {
        boolean bFlag = false;
        WebDriverWait wait = new WebDriverWait(driver, iWaitTime);
        for (int i = 0; i < 5; i++) {
            try {
                wait.until(ExpectedConditions.elementToBeClickable(bylocator));
                if (driver.findElement((bylocator)).isDisplayed()) {
                    bFlag = true;
                }
                break;
            } catch (java.util.NoSuchElementException e) {
                e.printStackTrace();
                bFlag = false;
                Assert.fail("Element " + bylocator + " is not displayed" + "\n"
                        + e.getMessage());
            } catch (StaleElementReferenceException e) {
                continue;
            } catch (TimeoutException e) {
                Assert.fail("Timed out....." + e.getMessage());
                break;
            } catch (Exception e) {
                e.printStackTrace();
                bFlag = false;
                continue;
            }
        }
        return bFlag;
    }

    public void scrollIntoElementView(By Locator) throws Exception {
        try {
            WebElement element = driver.findElement(Locator);
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", element);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            Assert.fail("Element not found" + Locator + "\n" + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Unable to scroll the page to find " + Locator + "\n" + e.getMessage());
        }
    }

    public void click(By locator, int optionWaitTime, String friendlyElement) {
        try {
            int waitTime = optionWaitTime;
            if (isElementPresent(locator, waitTime)) {
                scrollIntoElementView(locator);
                WebElement element = driver.findElement(locator);
                element.click();
                System.out.println("Clicked on " + friendlyElement);
            } else {
                Assert.fail("Unable to click the element " + locator);
            }
        } catch (StaleElementReferenceException e) {
            Assert.fail("Element with " + locator + " is not attached to the page document");
        } catch (NoSuchElementException e) {
            Assert.fail("Element " + locator + " was not found in DOM");
        } catch (Exception e) {
            Assert.fail("Element " + locator + " was not clickable");
        }
    }

    protected int getWaitTime(int[] optionalWaitArray) {
        if (optionalWaitArray.length <= 0) {
            return MEDIUMWAIT;
        } else {
            return optionalWaitArray[0];
        }
    }

    public void clearAndType(By locator, String text, String friendlyWebElementName, int... optionWaitTime) {
        int waitTime = 0;
        try {
            waitTime = getWaitTime(optionWaitTime);
            if (isElementPresent(locator, waitTime)) {
                scrollIntoElementView(locator);
                WebElement element = driver.findElement(locator);
                element.clear();
                element.sendKeys(text);
                System.out.println("Cleared the " + friendlyWebElementName + " and entered the text " + text);
            } else {
                Assert.fail("Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " was not found in DOM in time - " + waitTime);
            }
        } catch (StaleElementReferenceException e) {
            Assert.fail("Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
        } catch (NoSuchElementException e) {
            Assert.fail("Text " + text + " to be entered after " + " clear the   " + friendlyWebElementName + " was not found in DOM in time - " + waitTime + " - NoSuchElementException");
        } catch (Exception e) {
            Assert.fail("Text " + text + " to be entered after " + " clear the  " + friendlyWebElementName + " was not found in DOM");
        }
    }

    public void selectByVisibleText(By locator, String visibleText, String friendlyWebElementName, int... optionWaitTime) {
        int waitTime = 0;
        try {
            waitTime = getWaitTime(optionWaitTime);
            if (isElementPresent(locator, waitTime)) {
                scrollIntoElementView(locator);
                WebElement selectElement = driver.findElement(locator);
                Select select = new Select(selectElement);
                select.selectByVisibleText(visibleText);
                System.out.println("Selected dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName);
            } else {
                System.out.println("Unable to select dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName);
                Assert.fail("Unable to select dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName);
            }
        } catch (StaleElementReferenceException e) {
            System.out.println("Dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
            Assert.fail("Dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName + " is not attached to the page document - StaleElementReferenceException");
        } catch (NoSuchElementException e) {
            System.out.println("Dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName + " was not found in DOM - NoSuchElementException");
            Assert.fail("Dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName + " was not found in DOM - NoSuchElementException");
        } catch (Exception e) {
            System.out.println("Unable to select dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName + " - " + e);
            Assert.fail("Unable to select dropdown item by visible text option:" + visibleText + " from " + friendlyWebElementName);
        }
    }

    /**
     * Purpose - To switch the frame
     *
     * @param frame
     */
    public void switchFrame(String frame) {
        try {
            waitForSeconds(2);
            driver.switchTo().frame(frame);
            System.out.println("Navigated to frame with id " + frame);
        } catch (Exception e) {
            System.out.println("Unable to navigate to frame with id " + frame + " - " + e);
            Assert.fail("Unable to navigate to frame with id " + frame);
        }
    }

    /**
     * Purpose - To switch frame with index
     * @param frameIndex
     */
    public void switchFrameWithIndex(int frameIndex){
        try {
            waitForSeconds(2);
            driver.switchTo().frame(frameIndex);
            System.out.println("Navigated to frame with frame index " + frameIndex);
        } catch (Exception e) {
            System.out.println("Unable to navigate to frame with frame index " + frameIndex + " - " + e);
            Assert.fail("Unable to navigate to frame with frame index " + frameIndex);
        }
    }

    /**
     * Purpose - Switch to parent frame
     */
    public void switchToParentFrame() {
        driver.switchTo().parentFrame();
        System.out.println("Navigated to Parent frame from current frame");
    }

    /**
     * Purpose - To switch to default content
     */
    public void switchDefaultContent(){
        waitForSeconds(2);
        driver.switchTo().defaultContent();
        System.out.println("Navigated to default content from current frame");
    }

    /**
     * Purpose - Wait
     *
     * @param seconds
     */
    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method - Method to hover on an element based on locator using Actions,it waits until the element is loaded and then hovers on the element
     *
     * @param locator  locator
     * @param waitTime wait time
     */
    public void mouseHover(By locator, String friendlyWebElementName, int waitTime) {
        try {
            if (isElementPresent(locator, waitTime)) {
                Actions builder = new Actions(driver);
                WebElement HoverElement = driver.findElement(locator);
                builder.moveToElement(HoverElement).build().perform();
//                WebElement HoverElement = driver.findElement(locator);
//                String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
//                ((JavascriptExecutor) driver).executeScript(mouseOverScript, HoverElement);
                System.out.println("Hovered on " + friendlyWebElementName);
            } else {
                System.out.println("Element was not visible to hover ");
                Assert.fail(friendlyWebElementName + " was not visible to hover ");
            }
        } catch (NoSuchElementException e) {
            System.out.println(friendlyWebElementName + " was not found in DOM in time - " + waitTime);
            Assert.fail(friendlyWebElementName + " was not found in DOM in time - " + waitTime);
        } catch (Exception e) {
            System.out.println("Unable to hover the cursor on " + friendlyWebElementName + " - " + e);
            Assert.fail("Unable to hover the cursor on " + friendlyWebElementName);
        }
    }

    /**
     * Purpose - To switch to window
     *
     * @param windowTitle
     */
    public void switchToWindow(String windowTitle) {
        try {
            boolean bFlag = false;
            for (String handle : driver.getWindowHandles()) {
                driver.switchTo().window(handle);
                if (driver.getTitle().contains(windowTitle)) {
                    System.out.println("Navigated successfully to new window with title " + windowTitle);
                    bFlag = true;
                    break;
                }
            }
            if (!bFlag) {
                System.out.println("Window with specified title " + windowTitle + " doesn't exists. Please check the window title or wait until the new window appears");
                Assert.fail("Window with specified number " + windowTitle + " doesn't exists. Please check the window title or wait until the new window appears");
            }
        } catch (Exception e) {
            System.out.println("Unable to navigate to window with title " + windowTitle + " - " + e);
            Assert.fail("Unable to navigate to window with title " + windowTitle);
        }
    }

    /**
     * TODO JavaScript method for clicking on an element
     *
     * @param locator  - locator value by which element is recognized
     * @param waitTime - Time to wait for an element
     */
    public void javaScriptClick(By locator, int waitTime, String friendlyWebElementName) {
        try {
            if (isElementPresent(locator, waitTime)) {
                scrollIntoElementView(locator);
                WebElement element = driver.findElement(locator);
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
                System.out.println("Clicked on " + friendlyWebElementName);
            } else {
                System.out.println("Unable to click on " + friendlyWebElementName);
                Assert.fail("Unable to click on " + friendlyWebElementName);
            }
        } catch (NoSuchElementException e) {
            System.out.println(friendlyWebElementName + " was not found in DOM in time - " + waitTime + " - NoSuchElementException");
            Assert.fail(friendlyWebElementName + " was not found in DOM in time - " + waitTime + " - NoSuchElementException");
        } catch (Exception e) {
            System.out.println(friendlyWebElementName + " was not found in in the webpage - " + e);
            Assert.fail(friendlyWebElementName + " was not found in in the webpage");
        }
    }

    /**
     * Purpose - Wait until page gets loaded
     */
    public void waitForPageToLoad() {
        System.out.println("Waiting for page to be loaded...");
        try {
            int waitTime = 0;
            boolean isPageLoadComplete = false;
            do {
                isPageLoadComplete = ((String)((JavascriptExecutor)driver).executeScript("return document.readyState")).equals("complete");
                System.out.print(".");
                Thread.sleep(500);
                waitTime++;
                if(waitTime > 500)
                {
                    break;
                }
            } while(!isPageLoadComplete);
            if(!isPageLoadComplete) {
                System.out.println("Unable to load webpage with in default timeout 250 seconds");
                Assert.fail("unable to load webpage with in default timeout 250 seconds");
            }
        } catch(Exception e) {
            System.out.println("Unable to load web page - " + e);
            Assert.fail("unable to load webpage");
        }
    }


    /**
     * This method is used to return number of locators found
     * @param Locator
     * @return , returns number of locators
     */
    public int getLocatorCount(By Locator, String friendlyWebElementName) {
        int iCount = 0;
        try {
            iCount = driver.findElements(Locator).size();
        } catch (Exception e) {
            Assert.fail("Some exception occurred while retrieving Locator count from " + friendlyWebElementName);
        }
        return iCount;
    }

    /**
     * Purpose - To switch to first window
     */
    public void switchToFirstWindow() {
        try {
            ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(handles.get(1));
        } catch (Exception e) {
            System.out.println("Unable to navigate to first window - " + e);
            Assert.fail("Unable to navigate to first window");
        }
    }

    /**
     * Purpose - To switch to first window
     */
    public void closeCurrentWindowAndSwitchToMain() {
        try {
            driver.close();
            ArrayList<String> handles = new ArrayList<String> (driver.getWindowHandles());
            driver.switchTo().window(handles.get(0));
        } catch (Exception e) {
            System.out.println("Unable to navigate to first window - " + e);
            Assert.fail("Unable to navigate to first window");
        }
    }

    public String getPageSource(){
        return driver.getPageSource();
    }

    public String getPageTitle(){
        return driver.getCurrentUrl();
    }

    public void deleteAllCookies(){
        driver.manage().deleteAllCookies();
    }

    public void verifyFileDownloaded(String fileName){
        String downloadPath = System.getProperty("user.dir");
        boolean fileStatus = false;
        File dir = new File(downloadPath);
        File[] dir_contents = dir.listFiles();
        for (int i = 0; i < dir_contents.length; i++) {
            String dirName = dir_contents[i].getName();
            if (dirName.contains(fileName)){
                fileStatus = true;
                dir_contents[i].delete();
            }
        }
        Assert.assertTrue("File not downloaded", fileStatus);
    }

    public static int getRandomNumber() {
        Random rand = new Random();
        return rand.nextInt();
    }
}
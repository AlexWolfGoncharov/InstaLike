package com.github.alexwolfgoncharov.instalike.main;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


/**
 * Created by alexwolf on 06.02.16.
 */
public class Like implements Runnable {
    private static final String sFileName = "instalike.properties";
    private static String sDirSeparator = System.getProperty("file.separator");
    private static Properties props = new Properties();
    private static  String login;
    private static  WebDriver driver;
    private static String status = "start";








    public  void run() {


        status = "run";

        int count = 0;

        try {
            File currentDir = new File(".");
            String sFilePath = currentDir.getCanonicalPath() + sDirSeparator + sFileName;
            System.out.println(sFilePath);
            FileInputStream ins = new FileInputStream(sFilePath);

            props.load(ins);

            String LOGIN = props.getProperty("login");
            String PASS = props.getProperty("password");
            boolean neewdLog = Boolean.valueOf(props.getProperty("first"));
            String path = props.getProperty("path");
            String enternButton = props.getProperty("enter");
            System.out.println(path);

            login = LOGIN;
            System.out.println(LOGIN + " | " + PASS);


            System.out.println("Start InstaLike...");
//        logger.info("Start InstaLike...");



            DesiredCapabilities caps = new DesiredCapabilities();
            caps.setJavascriptEnabled(true);                //< not really needed: JS enabled by default
            caps.setCapability("takesScreenshot", true);    //< yeah, GhostDriver haz screenshotz!
            caps.setCapability(
                    PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                    path
            );


            ArrayList<String> cliArgsCap = new ArrayList<String>();

            cliArgsCap.add("--web-security=false");
            cliArgsCap.add("--ssl-protocol=any");
            cliArgsCap.add("--ignore-ssl-errors=true");



            caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            caps.setCapability(CapabilityType.SUPPORTS_BROWSER_CONNECTION, true);

            caps.setCapability(
                    PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
            caps.setCapability(
                    PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
                    new String[] { "--logLevel=2" });

            // Launch driver (will take care and ownership of the phantomjs process)
            driver = new PhantomJSDriver(caps);

//
            String url = "https://instagram.com";
//
            System.out.println("Create connection to: " + url);



            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
            driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
            driver.manage().window().setSize(new Dimension(1280, 720));
            driver.get(url);
//            driver.getPageSource();
            File screen = new File("screen.txt");
            File screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);

            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));

//            System.out.println(driver.getPageSource());
            if (neewdLog) {
                WebElement enter = driver.findElement(By.className(enternButton));


                List<WebElement> webElements =     driver.findElements(By.tagName("button"));

                for (WebElement elems : webElements){
                    System.out.println(elems.getText());


                }
                if (enter != null) {

                    System.out.println("Click enter ...");
                    enter.click();

                }
            }

            System.out.println("===========================================\n\r\n\r");
            List<WebElement>  form =  driver.findElements(By.className("_kp5f7"));
            System.out.println(form.size());

            driver.findElement(By.name("username")).sendKeys(LOGIN,  Keys.ARROW_DOWN);
            System.out.println("User: " + LOGIN);

            WebElement pass =

                    driver.findElement(By.name("password"));
            pass.sendKeys(PASS,  Keys.ARROW_DOWN);
            pass.submit();

            screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));


            System.out.println("===========================================\n\r\n\r");
        } catch (IOException e) {
            e.printStackTrace();
            status = "error";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";

        }
//
        File screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
        try {
            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
            status = "error";
        }


        while(true) {

            like(driver);
//            screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
//            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));

            driver.navigate().refresh();

        }



    }

    private  void like(WebDriver driver) {
            status = "like";

        try {

            driver.findElement(By.className("_oidfu")).click();

            Thread.sleep(200L);
            Point current = driver.manage().window().getPosition();
            driver.manage().window().setPosition(current.moveBy(2000,0));
            Thread.sleep(200L);


        } catch (org.openqa.selenium.NoSuchElementException e){
            System.out.println(e.getLocalizedMessage());
            status = "error";
        } catch (InterruptedException e) {
            status = "error";

        }


        try{

//            System.out.println(driver.getPageSource());

            List<WebElement> needLike = driver.findElements(By.className("coreSpriteHeartOpen"));
            Date cur = new Date();
            System.out.println("");
            System.out.println( cur.toLocaleString() + "| " + driver.getCurrentUrl()+ login +"| Find not liked elemends: " + needLike.size());
//        logger.info("Find not liked elemends: " + needLike.size());
            for (WebElement elem : needLike) {
                elem.click();
                System.out.print(".");
//                Thread.sleep(20L);
            }

        } catch (org.openqa.selenium.NoSuchElementException e){
            System.out.println(e.getLocalizedMessage());
            status = "error";
//        } finally {
//            driver.close();

        }



    }


    public void stopDriver(){
        System.out.println("Stop WebDriver");

        status = "start";
        driver.close();
        driver.quit();
        needStart();

    }


    String getStatus(){

        return status;
    }

    public void needStart(){
        status = "start";

    }



}

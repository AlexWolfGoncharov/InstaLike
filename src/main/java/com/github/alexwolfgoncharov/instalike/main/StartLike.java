package com.github.alexwolfgoncharov.instalike.main;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import org.apache.log4j.Logger;

/**
 * Created by alexwolf on 25.12.15.
 */
public class StartLike {

//    final static org.apache.log4j.Logger logger = Logger.getLogger(StartLike.class);
    private static final String sFileName = "instalike.properties";
    private static String sDirSeparator = System.getProperty("file.separator");
    private static Properties props = new Properties();




    public static void main(String[] args)  {
        WebDriver driver = null;

        try {
        File currentDir = new File(".");
        String sFilePath = currentDir.getCanonicalPath() + sDirSeparator + sFileName;
        System.out.println(sFilePath);
        FileInputStream ins = new FileInputStream(sFilePath);

            props.load(ins);

        String LOGIN = props.getProperty("login");
        String PASS = props.getProperty("password");
        boolean neewdLog = Boolean.valueOf(props.getProperty("first"));

        System.out.println(LOGIN + " | " + PASS);


        System.out.println("Start InstaLike...");
//        logger.info("Start InstaLike...");
//            System.setProperty("phantomjs.binary.path", PhantomJsDowloader.getPhantomJsPath());

//

//            DesiredCapabilities capabilities = new DesiredCapabilities();
        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
            capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
            capabilities.setCapability(CapabilityType.SUPPORTS_BROWSER_CONNECTION, true);


        ArrayList<String> cliArgsCap = new ArrayList<String>();
        capabilities = DesiredCapabilities.phantomjs();
        cliArgsCap.add("--web-security=false");
        cliArgsCap.add("--ssl-protocol=any");
        cliArgsCap.add("--ignore-ssl-errors=true");
        capabilities.setCapability("takesScreenshot", true);
        capabilities.setCapability(
                PhantomJSDriverService.PHANTOMJS_CLI_ARGS, cliArgsCap);
        capabilities.setCapability(
                PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS,
                new String[] { "--logLevel=2" });


//        WebDriver driver = new FirefoxDriver();



        URL hubUrl = new URL(props.getProperty("hubUrl"));
//        DesiredCapabilities capabilities = new DesiredCapabilities();
//        capabilities.setBrowserName("phantomjs");
//
            DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
            desiredCapabilities.setJavascriptEnabled(true);
            desiredCapabilities.setCapability("takesScreenshot", true);
        driver = new RemoteWebDriver(hubUrl, capabilities);
//         driver = new PhantomJSDriver(capabilities);

//        WebDriver driver = new RemoteWebDriver(new URL("http://52.32.125.105:4444/wd/hub"), capabilities);


        driver  = new Augmenter().augment(driver);
        String url = "https://instagram.com";
//        String url = "https://petition.president.gov.ua/";
//String url = "https://google.com";
        System.out.println("Create connection to: " + url);
//        logger.info("Create connection to: " + url);



        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1280, 720));
//        for(int i = 1; i < 5; i ++){
//            try {
//                getData(driver, url);
//            } catch (IOException e) {
////                e.printStackTrace()
//                logger.error(e.getMessage());
//            }
//
//        }

        driver.get(url);
        driver.getPageSource();
        File screen = new File("screen.txt");
        File screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
        int count = 0;
        FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));

//        BufferedWriter bw = new BufferedWriter(new FileWriter(screen));
//        bw.write(b64Screen);
        System.out.println(driver.getPageSource());
//       try {
//
        if (neewdLog) {
            WebElement enter = driver.findElement(By.className("_k6cv7"));
            if (enter != null) {

                System.out.println("Нажимаем вход...");
                enter.click();

            }
        }
//       } finally {
//
//       }
        //        WebElement enter = driver.findElement(By.linkText("Вход"));

//        screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
//        FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));

        System.out.println("===========================================\n\r\n\r");
        List<WebElement>  form =  driver.findElements(By.className("_qy55y"));
        System.out.println(form.size());

        driver.findElement(By.name("username")).sendKeys(LOGIN,  Keys.ARROW_DOWN);
        System.out.println("User: " + LOGIN);

//        WebElement pass =

        driver.findElement(By.name("password")).sendKeys(PASS,  Keys.ARROW_DOWN);

//        screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
//            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));


//
//        for (WebElement elem : form) {
//            System.out.println(elem.getText());
//             if ("username".equals(elem.getAttribute("name"))) {
//                 elem.sendKeys("DNEPR_JEWELRY",  Keys.ARROW_DOWN);
//
//                 System.out.println("username: DNEPR_JEWELRY");
//
//             }
//
//            if ("username".equals(elem.getAttribute("password"))) {
//                elem.sendKeys("alex121189", Keys.ARROW_DOWN);
//                System.out.println("password: alex121189");
//
//            }
//            screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
//            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));
//
//        }


        System.out.println("===========================================\n\r\n\r");
//        System.out.println(driver.getPageSource());
//        WebElement buttonLogin =

                driver.findElement(By.className("_nv5lf")).click();

        Thread.sleep(3000L);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
//        } finally {
//            driver.close();

        }
//
//        screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
//        FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));


        while(true) {

            like(driver);
//            screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
//            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));
            driver.navigate().refresh();

        }

//        screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
//        FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));
//
//        System.out.println(driver.getPageSource());
//        driver.quit();





    }


    private static void like(WebDriver driver) {


        try {

            driver.findElement(By.className("_oidfu")).click();

            Thread.sleep(200L);
            Point current = driver.manage().window().getPosition();
            driver.manage().window().setPosition(current.moveBy(1000,1000));
            Thread.sleep(200L);


        } catch (org.openqa.selenium.NoSuchElementException e){
            System.out.println(e.getLocalizedMessage());
        } catch (InterruptedException e) {

        }


        try{


            List<WebElement> needLike = driver.findElements(By.className("coreSpriteHeartOpen"));
            Date cur = new Date();
            System.out.println("");
            System.out.println( cur.toLocaleString() + "| " + driver.getCurrentUrl()+"| Find not liked elemends: " + needLike.size());
//        logger.info("Find not liked elemends: " + needLike.size());
            for (WebElement elem : needLike) {
                elem.click();
                System.out.print(".");
//                Thread.sleep(20L);
            }

        } catch (org.openqa.selenium.NoSuchElementException e){
            System.out.println(e.getLocalizedMessage());
//        } finally {
//            driver.close();

        }



    }



}

package com.github.alexwolfgoncharov.instalike.main;

import com.github.igorsuhorukov.phantomjs.PhantomJsDowloader;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


import org.apache.log4j.Logger;
/**
 * Created by alexwolf on 25.12.15.
 */
public class StartLike {

    final static org.apache.log4j.Logger logger = Logger.getLogger(StartLike.class);


    public static void main(String[] args) throws Exception {


        System.out.println("Start InstaLike...");

            System.setProperty("phantomjs.binary.path", PhantomJsDowloader.getPhantomJsPath());

//

        DesiredCapabilities capabilities = DesiredCapabilities.htmlUnit();
//        capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
//        capability.setCapability(CapabilityType.SUPPORTS_BROWSER_CONNECTION, true);


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
        WebDriver driver = new PhantomJSDriver(capabilities);
        driver  = new Augmenter().augment(driver);
        String url = "https://instagram.com";
//        String url = "https://petition.president.gov.ua/";
//String url = "https://google.com";
        System.out.println("Create connection to: " + url);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(15, TimeUnit.SECONDS);
        driver.manage().window().setSize(new Dimension(1920, 1080));
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
//        File screen = new File("screen.txt");
        File screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
        int count = 0;
        FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));

//        BufferedWriter bw = new BufferedWriter(new FileWriter(screen));
//        bw.write(b64Screen);
        System.out.println(driver.getPageSource());
//       try {
//
//           WebElement enter = driver.findElement(By.className( "_k6cv7"));
//           if (enter != null) {
//
//               System.out.println("Нажимаем вход...");
//               enter.click();
//
//           }
//       } finally {
//
//       }
        //        WebElement enter = driver.findElement(By.linkText("Вход"));

        screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
        FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));

        System.out.println("===========================================\n\r\n\r");
        List<WebElement>  form =  driver.findElements(By.className("_qy55y"));
        System.out.println(form.size());

        driver.findElement(By.name("username")).sendKeys("DNEPR_JEWELRY",  Keys.ARROW_DOWN);

//        WebElement pass =

        driver.findElement(By.name("password")).sendKeys("alex121189",  Keys.ARROW_DOWN);

        screensh= ((TakesScreenshot) driver).getScreenshotAs( OutputType.FILE);
            FileUtils.copyFile(screensh, new File("screenshot_" + count++ + ".png"));


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
        System.out.println(driver.getPageSource());
//        WebElement buttonLogin =

                driver.findElement(By.className("_nv5lf")).click();

        Thread.sleep(3000L);




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


    private static void like(WebDriver driver) throws InterruptedException {



        driver.findElement(By.className("_oidfu")).click();

        Thread.sleep(200L);
        Point current = driver.manage().window().getPosition();
        driver.manage().window().setPosition(current.moveBy(1000,1000));
        Thread.sleep(200L);
         current = driver.manage().window().getPosition();
        driver.manage().window().setPosition(current.moveBy(1000,1000));
        List<WebElement> needLike = driver.findElements(By.className("coreSpriteHeartOpen"));
        System.out.println("Find not liked elemends: " + needLike.size());
        for (WebElement elem : needLike) {
            elem.click();
            Thread.sleep(200L);
        }

    }



    private static void getData(WebDriver driver, String url) throws IOException {
        driver.get(url);
//      List<WebElement> fioList = driver.findElements(By.className("reducer"));
        String title = driver.getTitle();
        System.out.println(url);
        List<WebElement> fioList =  driver.findElements(By.tagName("li"));

        FileWriter fileWrite = new FileWriter("rezult_10.12.2015.txt",true);
        BufferedWriter bufferWritter = new BufferedWriter(fileWrite);


        for (WebElement elem : fioList){
            String str = elem.getText();
            int fist = 0;
            if (str.indexOf(" ") == 0)
                fist = 1;
            int fistP = str.lastIndexOf(" (дата підпису:");
            if (fistP < 0) fistP =str.length();

            int fistT = (str.lastIndexOf(" (дата підпису:")+16);
            if (fistT < 16) fistT = 0;
            int lastT = str.lastIndexOf(")");
            if(lastT < 0) lastT = 0;

            String a = str.substring(fist,fistP);
            String b = str.substring(fistT, lastT);
            String c = a + ";"+ b;
            bufferWritter.write(c);


            bufferWritter.write(";\n");

        }
        bufferWritter.close();

    }

}

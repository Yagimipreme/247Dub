package org.example;
import org.openqa.selenium.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v128.filesystem.model.File;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Radios {
    String url = "";
    boolean isPlaying = false;
    WebDriver driver;

    public Radios() {
        // Set path for chrome-driver
        // System.setProperty("webdriver.chrome.driver", "chromedriver_linux64");
        // Configure ChromeOptions for headless mode
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        options.addArguments("--disable-gpu"); // better performance
        // Pfad zur CRX-Datei (z.B. C:/path/to/extension.crx)
        String crxFilePath = "src/EPCNNFBJFCGPHGDMGGKAMKMGOJDAGDNN_25_2_0_0.crx";
        options.addExtensions(new java.io.File(crxFilePath));
        this.driver = new ChromeDriver(options);
        System.out.println("Browser created");
    }

    void skipYt() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20)); // Wartezeit bis zu 20 Sekunden
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Reject all']/ancestor::div[@class='yt-spec-button-shape-next__button-text-content']")));
            element.click();
        //driver.findElement(By.xpath("//button[contains(@class, 'yt-spec-button-shape-next') and contains(@class, 'yt-spec-button-shape-next--filled')]")).click();
        //System.out.println("skipping");
        //wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("iframe")));
        //
        //reject_button.click();
        } catch (Exception e) {
            System.out.print("kein  reject btn");
        }
        try {
            WebElement skipAdButton = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'yt-spec-button-shape-next__button-text-content')]")));
            skipAdButton.click();
            System.out.println("Werbung übersprungen.");
        } catch (Exception e) {
            System.out.print("2kein Reject btn2");
        }
    }

    void play() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "chromedriver_linux64");
        System.out.println("Playing" + this.url + "|  isPlaying: " + this.isPlaying);
        if (this.driver == null) {
            throw new IllegalStateException("WebDriver nicht initalisiert");
        }
        switch (url) {
            case "MABU":
                // playMabu(){
                if (this.isPlaying) {
                    try {
                        driver.get("https://mabu-beatz-radio.com/streams/dub-techno/");
                        // Thread.sleep(5);
                        WebElement play = driver.findElement(By.id("play"));
                        System.out.println(play.getText());
                        play.click();
                    } catch (Exception error) {
                        error.printStackTrace();
                        driver.quit();
                    }
                }

                break;

            case "http://popupplayer.radio.net/popupplayer/index.html?station=schizoiddubtechno&tenant=www.radio.de&authId=7929a0dd-5120-4bc7-9785-11f9929e35d6":
                WebElement play = driver.findElement(By.tagName("g"));
                // System.out.print(play.getText());
                play.click();
            default:
                System.out.println("No Radio selected");
                break;

            case "c-RED":
                
                driver.get("https://www.youtube.com/watch?v=QGRbjj5fS2Q");
                //driver.manage().addCookie(ytCookie);
                skipYt();
        }

    }

    public static void main(String[] args) {

    }

    void stop() {
        driver.quit();
    }

    void changeIsPlaying() {
        isPlaying = !isPlaying;
    }

    void setUrl(String url) {
        this.url = url;
    }

    /*Cookie ytCookie = new Cookie.Builder("YSC", "abcd1234efgh5678ijkl")
        .domain(".youtube.com")    // Optional, falls du die Domain angeben möchtest
        .path("/")                 // Der Pfad, auf dem das Cookie gültig ist
        .isSecure(true)            // Wird nur über HTTPS gesendet
        .isHttpOnly(false)         // Nicht nur für HTTP-Anfragen
        .sameSite("Lax")           // SameSite-Attribut (Lax, Strict, None)
        .build();
    */
}

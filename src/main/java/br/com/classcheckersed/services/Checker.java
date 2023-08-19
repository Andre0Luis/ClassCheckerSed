package br.com.classcheckersed.services;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.time.Duration;

public class Checker {

    private EdgeDriver driver;
    private Wait<WebDriver> wait;
    private WebElement reveled;

    public void classChecker() throws IOException {

        try {
            configBrowser();
            login();
        } finally {
            System.out.printf("VAI FINALIZAR!!!");
            //exiteLogin();
        }
    }

    private void configBrowser() throws IOException {

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    }


    private void login(){

        driver.get("https://sed.educacao.sp.gov.br/");
        driver.findElement(By.id("name")).clear();
        driver.findElement(By.id("name")).sendKeys("xxx");
        driver.findElement(By.id("senha")).clear();
        driver.findElement(By.id("senha")).sendKeys("xxx");
        driver.findElement(By.id("botaoEntrar")).click();

        reveled = driver.findElement(By.linkText("PC / Coordenador de Gestão Pedagógica"));
        wait.until(d -> reveled.isDisplayed());
        driver.findElement(By.linkText("PC / Coordenador de Gestão Pedagógica")).click();


        reveled = driver.findElement(By.id("btnFechar"));
        wait.until(d -> reveled.isDisplayed());
        driver.findElement(By.id("btnFechar")).click();
    }

    private void exiteLogin(){
        driver.quit();
    }

}

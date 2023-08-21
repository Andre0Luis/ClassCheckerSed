package br.com.classcheckersed.services;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import java.util.Map;

public class Checker {

    private EdgeDriver driver;
    private Wait<WebDriver> wait;
    private WebElement reveled;

    private Select select;

    private WebElement selectElement;

    private int cont = 0;


    public void classChecker() throws IOException {

        try {
            configBrowser();
            login();
            selectClasses();
        } finally {
            System.out.printf("VAI FINALIZAR!!!");
            //exiteLogin();
        }
    }

    private void configBrowser() throws IOException {

        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    }

    private void login(){

        try {
            driver.get("https://sed.educacao.sp.gov.br/");
            driver.findElement(By.id("name")).clear();
            driver.findElement(By.id("name")).sendKeys("xxx");
            driver.findElement(By.id("senha")).clear();
            driver.findElement(By.id("senha")).sendKeys("xxxx@");
            driver.findElement(By.id("botaoEntrar")).click();

            reveled = driver.findElement(By.linkText("PC / Coordenador de Gestão Pedagógica"));
            wait.until(d -> reveled.isDisplayed());
            driver.findElement(By.linkText("PC / Coordenador de Gestão Pedagógica")).click();

            reveled = driver.findElement(By.id("btnFechar"));
            wait.until(d -> reveled.isDisplayed());
            driver.findElement(By.id("btnFechar")).click();

            driver.get("https://sed.educacao.sp.gov.br/RegistroAula/RelatorioTurma");

            Thread.sleep(3000);

        } catch (Exception e) {
            System.out.println("Erro no processo do login");
            e.printStackTrace();
        }

    }

    private void selectClasses(){
        try {

            System.out.printf("VAI SELECIONAR AS TURMAS!!!");

            reveled = driver.findElement(By.id("filt-escola"));
            wait.until(d -> reveled.isDisplayed());
            selectElement = driver.findElement(By.id("filt-escola"));
            select = new Select(selectElement);
                    //Id da escola
            select.selectByValue("10285");

            Thread.sleep(2000);

            typeTuition();

        } catch (Exception e){
            System.out.println("Erro no processo de selecionar as turmas");
            e.printStackTrace();
        }


    }

    private void typeTuition() throws InterruptedException {

        Map<String, String> tuition = Map.of(
                "ENSINO FUNDAMENTAL DE 9 ANOS", "14",
                "NOVO ENSINO MÉDIO", "101"
        );

        try {
            tuition.forEach((key, value) -> {
                try {
                reveled = driver.findElement(By.id("filt-tipoEnsino"));
                wait.until(d -> reveled.isDisplayed());
                selectElement = driver.findElement(By.id("filt-tipoEnsino"));
                select = new Select(selectElement);
                //Id do tipo de ensino
                select.selectByValue(value);
                Thread.sleep(2000);
                typeClass();
                } catch (InterruptedException e) {
                    System.out.println("Erro no processo de seleção do tipo de ensino");
                    throw new RuntimeException(e);
                }

            });

        } catch (Exception e){
            System.out.println("Erro no processo de selecionar o tipo de ensino");
            e.printStackTrace();
        }
    }

    private void typeClass(){
        Map<String, String> classesEF = Map.of(
                "6° ANO A TARDE ANUAL", "38555193",
                "6° ANO B TARDE ANUAL", "38555257",
                "7° ANO A TARDE ANUAL", "38635943",
                "7° ANO B TARDE ANUAL", "38636007",
                "7° ANO C TARDE ANUAL", "38636071",
                "8° ANO A TARDE ANUAL", "38550907",
                "8° ANO B TARDE ANUAL", "38550970",
                "9° ANO A TARDE ANUAL", "38568523",
                "9° ANO B TARDE ANUAL", "38568587",
                "9° ANO C TARDE ANUAL", "38568651"
        );

        Map<String, String> classesEM1 = Map.of(
                        "1ª SERIE A MANHA ANUAL", "38518937",
                        "1ª SERIE B MANHA ANUAL", "38519000",
                        "1ª SERIE C MANHA ANUAL", "38518873",
                        "1ª SERIE D MANHA ANUAL", "38804599",
                        "1ª SERIE E TARDE ANUAL", "38804638",
                        "1ª SERIE F NOITE ANUAL", "38890687",
                        "2ª SERIE A MANHA ANUAL", "38522999",
                        "2ª SERIE B MANHA ANUAL", "38523063",
                        "2ª SERIE C MANHA ANUAL", "38523128",
                        "2ª SERIE D MANHA ANUAL", "38523192"
        );
        Map<String, String> classesEM2 = Map.of(
                "2ª SERIE E NOITE ANUAL", "38523256",
                "2ª SERIE F NOITE ANUAL", "39149850",
                "3ª SERIE B MANHA ANUAL", "38632054",
                "3ª SERIE C MANHA ANUAL", "38632310",
                "3ª SERIE D MANHA ANUAL", "38632246",
                "3ª SERIE E NOITE ANUAL", "38632374",
                "3ª SERIE F NOITE ANUAL", "38632182",
                "3ª SERIE G NOITE ANUAL", "39149877"
        );

        classesEF.forEach((key, value) -> {
            try {
            reveled = driver.findElement(By.id("filt-turma"));
            wait.until(d -> reveled.isDisplayed());
            selectElement = driver.findElement(By.id("filt-turma"));
            select = new Select(selectElement);
            //Id da turma
            select.selectByValue(value);
            Thread.sleep(2000);
            driver.findElement(By.id("btnPesquisar")).click();
            Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Erro no processo de seleção da turma");
                throw new RuntimeException(e);
            } finally {
                printScreenWindow();
            }
        });

    }

    private void printScreenWindow() {
        try {
            Thread.sleep(1000);
            driver.manage().window().maximize();
            Thread.sleep(1000);
            driver.manage().window().fullscreen();
            Thread.sleep(1000);

          WebElement modalPrint = driver.findElement(By.id("sedUiModalWrapper_1body"));
          File srcFile = modalPrint.getScreenshotAs(OutputType.FILE);
          FileUtils.copyFile(srcFile, new File("C:\\Users\\andre\\Documents\\Teste_Prints\\teste"+ cont +".png"));
          cont++;

        } catch (InterruptedException e) {
            System.out.println("Erro no processo de printar a tela");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro no processo de printar a tela");
            throw new RuntimeException(e);
        }
    }

    private void exiteLogin(){
        driver.quit();
    }

}

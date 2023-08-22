package br.com.classcheckersed.services;

import br.com.classcheckersed.domains.dto.DataSelectorDTO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Checker {

    private EdgeDriver driver;
    private Wait<WebDriver> wait;
    private WebElement reveled;

    private Select select;

    private WebElement selectElement;

    @Value("${api.login.email}")
    private String email;
    @Value("${api.login.password}")
    private String password;
    @Value("${api.url}")
    private String url;
    @Value("${api.url_relatorio}")
    private String url_relatorio;

    private int cont = 0;

    public Checker() {

    }

    public void classChecker() throws IOException {

        try {
            configBrowser();
            login();
            selectClasses();
        } finally {
            System.out.printf("VAI FINALIZAR!!!");
            exiteLogin();
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
            driver.findElement(By.id("name")).sendKeys("xxxx");
            driver.findElement(By.id("senha")).clear();
            driver.findElement(By.id("senha")).sendKeys("xxx");
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
        finally {
            driver.manage().window().maximize();
        }

    }

    private void selectClasses(){
        try {

            System.out.printf("VAI SELECIONAR AS TURMAS!!!");
            driver.findElement(By.id("filt-grupoescola"));
            System.out.println("ACHOU A ESCOLA!!!");

            selectElement = driver.findElement(By.id("filt-escola"));
            System.out.println("PREPARANDO PARA SELECIONAR A ESCOLA!!!");
            select = new Select(selectElement);
                    //Id da escola
            select.selectByValue("10285");
            System.out.println("SELECIONOU A ESCOLA!!!");

            Thread.sleep(2000);

            typeTuition();

        } catch (Exception e){
            System.out.println("Erro no processo de selecionar as turmas");
            e.printStackTrace();
        }


    }

    private void typeTuition() throws InterruptedException {

        List<DataSelectorDTO> typeTuition = new ArrayList<>();
        typeTuition.add(new DataSelectorDTO("ENSINO FUNDAMENTAL DE 9 ANOS", "14"));
        typeTuition.add(new DataSelectorDTO("NOVO ENSINO MÉDIO", "101"));


//        Map<String, String> tuition = Map.of(
//                "ENSINO FUNDAMENTAL DE 9 ANOS", "14",
//                "NOVO ENSINO MÉDIO", "101"
//        );

        try {
            typeTuition.forEach(item -> {
                System.out.println("VAI SELECIONAR O TIPO DE ENSINO!!!");

                try {

                driver.findElement(By.id("filt-grupotipoEnsino"));
                    System.out.println("ACHOU O TIPO DE ENSINO!!!");
//                reveled = driver.findElement(By.id("filt-tipoEnsino"));
//                wait.until(d -> reveled.isDisplayed());
                selectElement = driver.findElement(By.id("filt-tipoEnsino"));
                select = new Select(selectElement);
                //Id do tipo de ensino
                select.selectByValue(item.getId());
                Thread.sleep(2000);

                } catch (InterruptedException e) {
                    System.out.println("Erro no processo de seleção do tipo de ensino");
                    throw new RuntimeException(e);
                } finally {
                    typeClass();
                }

            });

        } catch (Exception e){
            System.out.println("Erro no processo de selecionar o tipo de ensino");
            e.printStackTrace();
        }
    }

    private void typeClass(){

        List<DataSelectorDTO> typeClassEF = new ArrayList<>();
        typeClassEF.add(new DataSelectorDTO("6° ANO A TARDE ANUAL", "38555193"));
        typeClassEF.add(new DataSelectorDTO("6° ANO B TARDE ANUAL", "38555257"));
        typeClassEF.add(new DataSelectorDTO("7° ANO A TARDE ANUAL", "38635943"));
        typeClassEF.add(new DataSelectorDTO("7° ANO B TARDE ANUAL", "38636007"));
        typeClassEF.add(new DataSelectorDTO("7° ANO C TARDE ANUAL", "38636071"));
        typeClassEF.add(new DataSelectorDTO("8° ANO A TARDE ANUAL", "38550907"));
        typeClassEF.add(new DataSelectorDTO("8° ANO B TARDE ANUAL", "38550970"));
        typeClassEF.add(new DataSelectorDTO("9° ANO A TARDE ANUAL", "38568523"));
        typeClassEF.add(new DataSelectorDTO("9° ANO B TARDE ANUAL", "38568587"));
        typeClassEF.add(new DataSelectorDTO("9° ANO C TARDE ANUAL", "38568651"));


//        Map<String, String> classesEF = Map.of(
//                "6° ANO A TARDE ANUAL", "38555193",
//                "6° ANO B TARDE ANUAL", "38555257",
//                "7° ANO A TARDE ANUAL", "38635943",
//                "7° ANO B TARDE ANUAL", "38636007",
//                "7° ANO C TARDE ANUAL", "38636071",
//                "8° ANO A TARDE ANUAL", "38550907",
//                "8° ANO B TARDE ANUAL", "38550970",
//                "9° ANO A TARDE ANUAL", "38568523",
//                "9° ANO B TARDE ANUAL", "38568587",
//                "9° ANO C TARDE ANUAL", "38568651"
//        );

        List<DataSelectorDTO> classesEM = new ArrayList<>();
        classesEM.add(new DataSelectorDTO("1ª SERIE A MANHA ANUAL", "38518937"));
        classesEM.add(new DataSelectorDTO("1ª SERIE B MANHA ANUAL", "38519000"));
        classesEM.add(new DataSelectorDTO("1ª SERIE C MANHA ANUAL", "38518873"));
        classesEM.add(new DataSelectorDTO("1ª SERIE D MANHA ANUAL", "38804599"));
        classesEM.add(new DataSelectorDTO("1ª SERIE E TARDE ANUAL", "38804638"));
        classesEM.add(new DataSelectorDTO("1ª SERIE F NOITE ANUAL", "38890687"));
        classesEM.add(new DataSelectorDTO("2ª SERIE A MANHA ANUAL", "38522999"));
        classesEM.add(new DataSelectorDTO("2ª SERIE B MANHA ANUAL", "38523063"));
        classesEM.add(new DataSelectorDTO("2ª SERIE C MANHA ANUAL", "38523128"));
        classesEM.add(new DataSelectorDTO("2ª SERIE D MANHA ANUAL", "38523192"));
        classesEM.add(new DataSelectorDTO("2ª SERIE E NOITE ANUAL", "38523256"));
        classesEM.add(new DataSelectorDTO("2ª SERIE F NOITE ANUAL", "39149850"));
        classesEM.add(new DataSelectorDTO("3ª SERIE B MANHA ANUAL", "38632054"));
        classesEM.add(new DataSelectorDTO("3ª SERIE C MANHA ANUAL", "38632310"));
        classesEM.add(new DataSelectorDTO("3ª SERIE D MANHA ANUAL", "38632246"));
        classesEM.add(new DataSelectorDTO("3ª SERIE E NOITE ANUAL", "38632374"));
        classesEM.add(new DataSelectorDTO("3ª SERIE F NOITE ANUAL", "38632182"));
        classesEM.add(new DataSelectorDTO("3ª SERIE G NOITE ANUAL", "39149877"));


//        Map<String, String> classesEM1 = Map.of(
//                        "1ª SERIE A MANHA ANUAL", "38518937",
//                        "1ª SERIE B MANHA ANUAL", "38519000",
//                        "1ª SERIE C MANHA ANUAL", "38518873",
//                        "1ª SERIE D MANHA ANUAL", "38804599",
//                        "1ª SERIE E TARDE ANUAL", "38804638",
//                        "1ª SERIE F NOITE ANUAL", "38890687",
//                        "2ª SERIE A MANHA ANUAL", "38522999",
//                        "2ª SERIE B MANHA ANUAL", "38523063",
//                        "2ª SERIE C MANHA ANUAL", "38523128",
//                        "2ª SERIE D MANHA ANUAL", "38523192"
//        );
//        Map<String, String> classesEM2 = Map.of(
//                "2ª SERIE E NOITE ANUAL", "38523256",
//                "2ª SERIE F NOITE ANUAL", "39149850",
//                "3ª SERIE B MANHA ANUAL", "38632054",
//                "3ª SERIE C MANHA ANUAL", "38632310",
//                "3ª SERIE D MANHA ANUAL", "38632246",
//                "3ª SERIE E NOITE ANUAL", "38632374",
//                "3ª SERIE F NOITE ANUAL", "38632182",
//                "3ª SERIE G NOITE ANUAL", "39149877"
//        );

            typeClassEF.forEach(item -> {
            try {
            System.out.println("VAI SELECIONAR A TURMA!!!" + item.getNome());
            Thread.sleep(2000);
            driver.findElement(By.id("filt-grupoturma"));
                System.out.println("ACHOU A TURMA!!!");
//            reveled = driver.findElement(By.id("filt-turma"));
//            wait.until(d -> reveled.isDisplayed());
            selectElement = driver.findElement(By.id("filt-turma"));
            select = new Select(selectElement);
            //Id da turma
                System.out.println("O value da turma é: " + item.getId());
            select.selectByValue(item.getId());
            Thread.sleep(5000);
            driver.findElement(By.id("btnPesquisar")).click();
            Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Erro no processo de seleção da turma");
                throw new RuntimeException(e);
            } finally {
                System.out.println("VAI PRINTAR A TELA!!!");
                printScreenWindow();
                System.out.println("PRINTOU A TELA!!!");
            }
        });

    }

    private void printScreenWindow() {
        try {

            Thread.sleep(1000);

          WebElement modalPrint = driver.findElement(By.id("sedUiModalWrapper_1body"));
            System.out.println("ACHOU O MODAL!!!");
          File srcFile = modalPrint.getScreenshotAs(OutputType.FILE);
            System.out.println("Fazendo o get do screenshot!!!");
          FileUtils.copyFile(srcFile, new File("C:\\Users\\andre\\Documents\\Teste_Prints\\teste"+ cont +".png"));
            System.out.println("COPIOU O ARQUIVO E SALVOU!!!");
          cont = cont + 1;

        } catch (InterruptedException e) {
            System.out.println("Erro no processo de printar a tela");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Erro no processo de printar a tela");
            throw new RuntimeException(e);
        } finally {
            driver.findElement(By.id("sedUiModalWrapper_1close")).click();
        }
    }

    private void exiteLogin(){
        driver.quit();
    }

}

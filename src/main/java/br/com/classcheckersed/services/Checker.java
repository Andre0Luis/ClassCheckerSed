package br.com.classcheckersed.services;

import br.com.classcheckersed.domains.dto.DataSelectorDTO;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import java.util.ArrayList;
import java.util.List;

public class Checker {

    private static Logger logger = LoggerFactory.getLogger(Checker.class);
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

    private int cont = 1;

    public Checker() {
    }

    public void classChecker() throws IOException {
        try {
                configBrowser();
                login();
                selectClasses();
        } finally {
            logger.atInfo().log("Finalizando o processo");
                exiteLogin();
        }
    }

    private void configBrowser() throws IOException {
            logger.atInfo().log("Configurando o browser");
        EdgeOptions edgeOptions = new EdgeOptions();
        edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        driver = new EdgeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            logger.atInfo().log("Browser configurado");
    }

    private void login(){
                logger.atInfo().log("Iniciando o processo de login");
        try {
            driver.get("https://sed.educacao.sp.gov.br/");
            driver.findElement(By.id("name")).clear();
                logger.atInfo().log("Preenchendo o usuário");
            driver.findElement(By.id("name")).sendKeys("xxx");
            driver.findElement(By.id("senha")).clear();
                logger.atInfo().log("Preenchendo a senha");
            driver.findElement(By.id("senha")).sendKeys("xxx");
                logger.atInfo().log("Clicando no botão de login");
            driver.findElement(By.id("botaoEntrar")).click();

            reveled = driver.findElement(By.linkText("PC / Coordenador de Gestão Pedagógica"));
            wait.until(d -> reveled.isDisplayed());

            if (reveled.isDisplayed() == false) {
                throw new RuntimeException("Erro no login");
            }

            driver.findElement(By.linkText("PC / Coordenador de Gestão Pedagógica")).click();

            reveled = driver.findElement(By.id("btnFechar"));
            wait.until(d -> reveled.isDisplayed());
            driver.findElement(By.id("btnFechar")).click();

            logger.atInfo().log("Redirecionando para a página de relatório de turmas");
            driver.get("https://sed.educacao.sp.gov.br/RegistroAula/RelatorioTurma");

            Thread.sleep(3000);

        } catch (Exception e) {
            logger.atError().log("Erro no processo de login");
            e.printStackTrace();
            exiteLogin();
        }
        finally {
            logger.atInfo().log("Maximizando a tela");
            driver.manage().window().maximize();
            logger.atInfo().log("Finalizando o processo de login");
        }

    }

    private void selectClasses(){
        try {
                logger.atInfo().log("Iniciando o processo de seleção das turmas");
            driver.findElement(By.id("filt-grupoescola"));

            selectElement = driver.findElement(By.id("filt-escola"));
                logger.atInfo().log("Selecionando a escola");
            select = new Select(selectElement);
            select.selectByValue("10285");
                logger.atDebug().log("Escola selecionada" + select.getFirstSelectedOption().getText());
            Thread.sleep(2000);

            typeTuition();

        } catch (Exception e){
            logger.atError().log("Erro no processo de selecionar as turmas");
            e.printStackTrace();
        }


    }

    private void typeTuition() throws InterruptedException {

        List<DataSelectorDTO> typeTuition = new ArrayList<>();
        typeTuition.add(new DataSelectorDTO("ENSINO FUNDAMENTAL DE 9 ANOS", "14"));
        typeTuition.add(new DataSelectorDTO("NOVO ENSINO MÉDIO", "101"));

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

        try {
            typeTuition.forEach(item -> {
                    logger.atInfo().log("Iniciando o processo de seleção do tipo de ensino");

                try {
                driver.findElement(By.id("filt-grupotipoEnsino"));
                selectElement = driver.findElement(By.id("filt-tipoEnsino"));
                select = new Select(selectElement);
                    logger.atDebug().log("Selecionando o tipo de ensino: " + item.getNome());
                select.selectByValue(item.getId());
                Thread.sleep(2000);

                } catch (InterruptedException e) {
                    logger.atError().log("Erro no processo de seleção do tipo de ensino");
                    throw new RuntimeException(e);
                } finally {
                    if (item.getId().equals("14")) {
                        logger.atInfo().log("Iniciando o processo de seleção das turmas do ensino fundamental");
                        typeClass(typeClassEF);
                    } else {
                        logger.atInfo().log("Iniciando o processo de seleção das turmas do ensino médio");
                        typeClass(classesEM);
                    }
                }

            });

        } catch (Exception e){
            logger.atError().log("Erro no processo de selecionar o tipo de ensino");
            e.printStackTrace();
        }
    }

    private void typeClass(List<DataSelectorDTO> typeClass){
            typeClass.forEach(item -> {
            try {
                logger.atInfo().log("Iniciando o processo de seleção da turma");
            Thread.sleep(2000);
            driver.findElement(By.id("filt-grupoturma"));
            selectElement = driver.findElement(By.id("filt-turma"));
            select = new Select(selectElement);
                logger.atDebug().log("Selecionando a turma: " + item.getNome());
            select.selectByValue(item.getId());
            Thread.sleep(5000);
            driver.findElement(By.id("btnPesquisar")).click();
            Thread.sleep(5000);
            } catch (InterruptedException e) {
                logger.atError().log("Erro no processo de seleção da turma");
                throw new RuntimeException(e);
            } finally {
                logger.atInfo().log("Iniciando o processo de printar a tela");
                    printScreenWindow(item.getNome());
                logger.atInfo().log("Finalizando o processo de printar a tela");
            }
        });

    }

    private void printScreenWindow(String nameClass) {
        try {
            Thread.sleep(1000);
            logger.atInfo().log("Procurando o modal para printar");
          WebElement modalPrint = driver.findElement(By.className("modal-content"));
          File srcFile = modalPrint.getScreenshotAs(OutputType.FILE);
            logger.atInfo().log("Salvando o print");
          FileUtils.copyFile(srcFile, new File("C:\\Users\\andre\\Documents\\Teste_Prints\\"+ nameClass +".png"));
            logger.atInfo().log("Print salvo");
          cont++;
            logger.atInfo().log("Essa é a " + cont + "/28 turmas printadas");

        } catch (InterruptedException e) {
            logger.atError().log("Erro no processo de printar a tela");
            e.printStackTrace();
        } catch (IOException e) {
            logger.atError().log("Erro no processo de printar a tela");
            throw new RuntimeException(e);
        } finally {
            logger.atInfo().log("Fechando o modal");
            driver.findElement(By.className("close")).click();
        }
    }

    private void exiteLogin(){
        driver.quit();
        logger.atInfo().log("Processo finalizado");
        System.exit(0);
    }

}

# Aplicação de Automação com Spring Boot e Selenium

Este é o README para a aplicação de automação desenvolvida em Java utilizando o framework Spring Boot, juntamente com o Selenium, para automatizar a captura de screenshots das telas da página do SED SP. O objetivo principal desta aplicação é facilitar a verificação das aulas ministradas em um determinado dia.

## Requisitos do Sistema

Antes de executar a aplicação, certifique-se de que o seguinte software esteja instalado:

1. Java Development Kit (JDK) - Versão 17 ou superior
2. Spring Boot CLI (opcional, a aplicação pode ser executada também com Maven)
3. Selenium WebDriver - Utilizado para automatizar a interação com o navegador
4. Navegador Chrome - O Selenium WebDriver será configurado para interagir com o Chrome

## Configuração

1. Clone o repositório para o seu ambiente local:

```shell
git clone https://github.com/Andre0Luis/ClassCheckerSed.git
```

2. Configure o WebDriver do Selenium:
   
   Certifique-se de ter o Chrome instalado no sistema e baixe o ChromeDriver compatível com a versão do Chrome instalada. Coloque o arquivo `chromedriver` na pasta de recursos do projeto.

3. Configure as propriedades da aplicação:

   Edite o arquivo `application.properties` localizado em `src/main/resources` e ajuste as configurações conforme necessário, como URL do SED SP e outras configurações específicas.

## Executando a Aplicação

Você pode executar a aplicação de duas maneiras:

### 1. Utilizando Spring Boot CLI

Abra o terminal na pasta raiz do projeto e execute o seguinte comando:

```shell
spring run src/main/java/com/seu/pacote/AplicacaoAutomacaoApplication.java
```

### 2. Utilizando Maven

Abra o terminal na pasta raiz do projeto e execute o seguinte comando:

```shell
mvn spring-boot:run
```

A aplicação será iniciada e começará a automatizar a captura de screenshots das telas do SED SP de acordo com as configurações fornecidas.

## Resultados

Os screenshots capturados serão salvos em uma pasta especificada na configuração da aplicação. Certifique-se de verificar essa pasta para ver os resultados da automação.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para enviar pull requests para melhorar esta aplicação de automação.

## Contato

Se você tiver alguma dúvida ou precisar de assistência, entre em contato com [aluis283@gmail.com](mailto:aluis283@gmail.com).

package br.com.classcheckersed;

import br.com.classcheckersed.services.Checker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class ClassCheckerSedApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ClassCheckerSedApplication.class, args);
		new Checker().classChecker();
	}

}

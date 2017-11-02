package fr.biocoop.poissonnerie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;

@SpringBootApplication
@EnableWebMvc
public class Application {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }

}

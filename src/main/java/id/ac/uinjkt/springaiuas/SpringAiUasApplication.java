package id.ac.uinjkt.springaiuas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class SpringAiUasApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiUasApplication.class, args);
    }
}

package zerobase.devidend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DividendPrjApplication {

    public static void main(String[] args) {
        SpringApplication.run(DividendPrjApplication.class, args);

    }

}

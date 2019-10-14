package fr.jeywhat.coverback;

import fr.jeywhat.coverback.service.CoverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoverbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoverbackApplication.class, args);
	}

}

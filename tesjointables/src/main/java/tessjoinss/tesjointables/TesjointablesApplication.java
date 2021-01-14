package tessjoinss.tesjointables;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TesjointablesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TesjointablesApplication.class, args);
	}

}

package is.hi.hbv501.videoleiga.videoleiga;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class VideoleigaApplication {

	public static void main(String[] args) {
		SpringApplication.run(VideoleigaApplication.class, args);
	}

}

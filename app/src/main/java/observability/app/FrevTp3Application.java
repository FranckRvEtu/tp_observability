package observability.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class FrevTp3Application implements CommandLineRunner {

    @Autowired
    private Environment env;

    public FrevTp3Application(Environment env) {}

	public static void main(String[] args) {
		SpringApplication.run(FrevTp3Application.class, args);
	}

    @Override
    public void run(String... args) throws Exception {

    }

}

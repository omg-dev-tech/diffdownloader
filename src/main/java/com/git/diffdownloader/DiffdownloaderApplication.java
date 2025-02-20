package com.git.diffdownloader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.StandardEnvironment;

@SpringBootApplication
public class DiffdownloaderApplication implements ApplicationRunner{

	private final String defaultPath = "file:./application.properties";

	private final ConfigurableEnvironment environment;

    public DiffdownloaderApplication(ConfigurableEnvironment environment) {
        this.environment = environment;
    }


	public static void main(String[] args) {
		// args 체크하기

		SpringApplication.run(DiffdownloaderApplication.class, args);
	}

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
		String mode = "";
		// e 로 main 인지 release 인지 확인
		// main 인지 release 인지에 따라 다른 프로퍼티 읽음
		if (args.containsOption("e")) {
			mode = args.getOptionValues("e").get(0);
		} else if (args.containsOption("env")) {
			mode = args.getOptionValues("env").get(0);
		} else {
			throw new Exception("No env argument");
		}

		String configPath = args.getOptionValues("p") != null && !args.getOptionValues("p").isEmpty()
                ? "file:" + args.getOptionValues("p").get(0)
                : defaultPath;

		System.setProperty("spring.config.location",  configPath);
		((StandardEnvironment) environment).getSystemProperties()
		.put("spring.config.location", configPath);

		
		// 최신 태그 가져오기


		// 
    }

	

}

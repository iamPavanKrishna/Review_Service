package com.group4.reviewservice;


import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;

@SpringBootApplication
@EnableWebMvc
public class ReviewServiceApplication {

	public static void main(String[] args) {
		Map<String, String> env = Dotenv.load()
                .entries()
                .stream()
                .collect(
                        Collectors.toMap(DotenvEntry::getKey, DotenvEntry::getValue));

		Map<String, Object> envProperties = env.entrySet()
        .stream()
        .collect(Collectors.toMap(Map.Entry::getKey, e -> (Object)e.getValue()));
				
        new SpringApplicationBuilder(ReviewServiceApplication.class)
                .environment(new StandardEnvironment() {
                    @Override
                    protected void customizePropertySources(MutablePropertySources propertySources) {
                        super.customizePropertySources(propertySources);
                        propertySources.addLast(new MapPropertySource("dotenvProperties", envProperties));
                    }
                }).run(args);
		// SpringApplication.run(ReviewServiceApplication.class, args);
	}
}

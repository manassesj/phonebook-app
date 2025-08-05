package com.phonebook;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.phonebook.config.EnvConfig;

@SpringBootApplication
public class PhonebookApiApplication {
      public static void main(String[] args) {
          SpringApplication app = new SpringApplication(PhonebookApiApplication.class);

          Map<String, Object> props = new HashMap<>();
          props.put("spring.datasource.url", EnvConfig.get("DB_URL"));
          props.put("spring.datasource.username", EnvConfig.get("DB_USER"));
          props.put("spring.datasource.password", EnvConfig.get("DB_PASSWORD"));
          props.put("spring.profiles.active", EnvConfig.get("PROFILE"));

          app.setDefaultProperties(props);
          app.run(args);
      }
}

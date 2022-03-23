package io.mosip.kernel.bio.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication (exclude= {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class TestBootApplication  {
	public static void main(String[] args) {
		SpringApplication.run(TestBootApplication.class, args);
	}
}
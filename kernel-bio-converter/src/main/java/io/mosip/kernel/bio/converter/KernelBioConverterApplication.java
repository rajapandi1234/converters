package io.mosip.kernel.bio.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (scanBasePackages = {"${mosip.auth.adapter.impl.basepackage}, io.mosip.kernel.bio.*"})
public class KernelBioConverterApplication {
	public static void main(String[] args) {
		SpringApplication.run(KernelBioConverterApplication.class, args);
	}
}
package com.example.pi_projsoftware;

import org.springframework.boot.SpringApplication;

public class TestPiProjsoftwareApplication {

	public static void main(String[] args) {
		SpringApplication.from(PiProjsoftwareApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

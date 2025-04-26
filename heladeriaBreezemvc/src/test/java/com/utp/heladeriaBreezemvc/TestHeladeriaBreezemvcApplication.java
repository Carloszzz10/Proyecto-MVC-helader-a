package com.utp.heladeriaBreezemvc;

import org.springframework.boot.SpringApplication;

public class TestHeladeriaBreezemvcApplication {

	public static void main(String[] args) {
		SpringApplication.from(HeladeriaBreezemvcApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}

package com.example.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/*
 * アプリケーション起動クラス
 */
@SpringBootApplication
public class EcApplication {

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index() {

		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(EcApplication.class, args);
	}

}

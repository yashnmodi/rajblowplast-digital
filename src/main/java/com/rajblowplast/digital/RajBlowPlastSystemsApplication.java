package com.rajblowplast.digital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication (exclude = {DataSourceAutoConfiguration.class})
public class RajBlowPlastSystemsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RajBlowPlastSystemsApplication.class, args);
	}

}

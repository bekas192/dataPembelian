package com.kurasi.dataPembelian;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DataPembelianApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataPembelianApplication.class, args);
	}

}

package com.example.exercises.demo;

import com.example.exercises.demo.CompositionEx.SmartKitchen;
import com.example.exercises.demo.EncapsulationEx.Printer;
import com.example.exercises.demo.Files.FilesEx;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ExercisesApplication implements CommandLineRunner {

	@Autowired
	//private FilesEx filesEx;
	//private SmartKitchen smartKitchen;
	private Printer printer;

	public static void main(String[] args) {

		SpringApplication.run(ExercisesApplication.class, args);

	}

	@Override
	public void run(String... args) throws Exception {
		//FilesEx.exercises();
		//SmartKitchen.exercises();
		Printer.exercises();
	}

}

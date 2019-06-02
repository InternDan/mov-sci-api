package com.movsci.processingapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessingApiApplication {

	static{ org.apache.tomcat.jni.Library.loadLibrary("opencv_java346"); }

	public static void main(String[] args) {
		SpringApplication.run(ProcessingApiApplication.class, args);
	}

}

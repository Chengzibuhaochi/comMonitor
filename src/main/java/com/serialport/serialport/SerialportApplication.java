package com.serialport.serialport;

import com.serialport.serialport.util.ComMonitor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SerialportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SerialportApplication.class, args);

        ComMonitor.init();
    }

}

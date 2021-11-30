package com.zensar;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunner implements org.springframework.boot.CommandLineRunner,Ordered{

	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		for (String string : args) {
			System.out.println("<<--shrishail inside run of commandline runner ----->>"+string);
		}
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 2;
	}

}

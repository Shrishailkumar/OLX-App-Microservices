package com.zensar;

import org.springframework.boot.ApplicationArguments;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRunner implements org.springframework.boot.ApplicationRunner, Ordered {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("--shrishail inside run of ApplicationRunner  -----");
		for (String string : args.getSourceArgs()) {
			System.out.println("<<--shrishail inside run of ApplicationRunner ----->>"+string);
		}
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return 1;
	}

}

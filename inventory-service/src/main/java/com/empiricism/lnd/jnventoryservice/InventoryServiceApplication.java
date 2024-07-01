package com.empiricism.lnd.jnventoryservice;

import com.empiricism.lnd.jnventoryservice.inventory.Inventory;
import com.empiricism.lnd.jnventoryservice.inventory.InventoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
@EnableDiscoveryClient
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

//	@Bean
//	public CommandLineRunner loadData(InventoryRepository inventoryRepository) {
//		return  args -> {
//			Inventory inventory = new Inventory();
//			inventory.setSkuCode("123abc");
//			inventory.setQuantity(100);
//
//			Inventory inventory1 = new Inventory();
//			inventory1.setSkuCode("1234abc");
//			inventory1.setQuantity(0);
//
//			inventoryRepository.save(inventory1);
//			inventoryRepository.save(inventory);
//
//		};
//	}
}

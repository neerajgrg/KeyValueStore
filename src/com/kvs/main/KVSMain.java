package com.kvs.main;

import java.util.Scanner;

import com.kvs.service.ExpiredKeysCollectionService;
import com.kvs.service.ExpiredKeysFileServiceImpl;
import com.kvs.store.KVStore;

public class KVSMain {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Tell the ttl of store service in hour(1), minute(2) or second(3)");
		ExpiredKeysCollectionService expiredKeysCollectionService = new ExpiredKeysFileServiceImpl("filepath");
		
		KVStore kvStoreService = new KVStore(expiredKeysCollectionService, 2, 1);
		
		try {
			System.out.println("Press 1 : Put Key-Value pair");
			System.out.println("Press 2 : Read Key-Value pair");
			System.out.println("Press 3 : Get Avg of keys");
			int option = scanner.nextInt();
			scanner.nextLine();//To take care of '\n'
			switch (option) {
			case 1:                     //Create
				System.out.print("Give the key : ");
				Integer key = Integer.parseInt(scanner.nextLine());

				System.out.println("Give the value : ");
				Integer value = Integer.parseInt(scanner.nextLine());
				try {
					kvStoreService.createKeyValue(key, value);
				} catch (Exception e) {
					System.err.println("Failure: Key-Value pair has not been added successfully to DataStore");
					e.printStackTrace();
				}
				System.out.println("SUCCESS: Key-Value pair has been added successfully to DataStore");
				break;
			case 2://Read
				System.out.print("Give the key : ");
				key = Integer.parseInt(scanner.nextLine());

				Integer keyValue = kvStoreService.getValue(key);

				if(keyValue == null) {
					System.err.println("FAILED, either the key does not exist or has already been expired");
				} else {
					System.out.println("value for key " + key + " is " + keyValue );
				}
				break;
			case 3://Average
				System.out.print(" Getting the average of all the keys ");
				double avg = kvStoreService.getKeysAverage();
				System.out.println("Average of all the keys is  "  + avg );
				break;
			default:
				System.err.println("FAILED: Please give the correct option");
				break;
			}
		} catch(Exception e){
			System.err.println("Got some error in key value data store " + e);
		} finally {
			scanner.close();
		}
		
	}
}

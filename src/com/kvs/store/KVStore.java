package com.kvs.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.kvs.model.KeyValueModel;
import com.kvs.service.ExpiredKeysCollectionService;

public class KVStore {

	private long ttlInMillis;

	private Map<Integer, KeyValueModel> map;
	
	private ExpiredKeysCollectionService expiredKeysCollectionService;

	public KVStore(ExpiredKeysCollectionService expiredKeysCollectionService, int ttltime, int unitOfttl) {

		if(unitOfttl == 1) {
			this.ttlInMillis  = ttltime * 3600 * 1000;
		} else if (unitOfttl == 2) { 
			this.ttlInMillis  = ttltime * 60 * 1000;
		} else {
			this.ttlInMillis  = ttltime *  1000;
		}
		map = new ConcurrentHashMap<Integer, KeyValueModel>();
		this.expiredKeysCollectionService = expiredKeysCollectionService;
		new CleanerThread().start();
	}

	public void createKeyValue(Integer key, Integer value) throws Exception {
		long currentTimeInMillis = System.currentTimeMillis();
		KeyValueModel keyValueModel = new KeyValueModel(key, value, currentTimeInMillis);
		map.put(key, keyValueModel);
	}

	public Integer getValue(Integer key) {
		KeyValueModel keyValueModel = map.get(key);
		if(keyValueModel == null || isKeyExpired(key)) return null;
		return keyValueModel.getValue();
	}

	public double getKeysAverage() {
		long sum = 0l;
		int noOfKeys = 0;
		for(Integer key : map.keySet()) {
			if(!isKeyExpired(key)) {
				sum += key;
				noOfKeys++;
			}
		}
		return sum / noOfKeys;
	}

	private boolean isKeyExpired(Integer key) {
		KeyValueModel keyValueModel = map.get(key);
		long currentTimeInMillis = System.currentTimeMillis();
		return ( keyValueModel.getInsertionTimeInMillis() + ttlInMillis) <= currentTimeInMillis ;
	}


	class CleanerThread extends Thread {
		@Override
		public void run() {
			System.out.println("Initiating Cleaner Thread..");
			while (true) {
				cleanMap();
				try {
					Thread.sleep(ttlInMillis / 2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		private void cleanMap() {
			for (Integer key : map.keySet()) {
				if (isKeyExpired(key)) {
					map.remove(key);
					expiredKeysCollectionService.collectKey(key);
					System.out.println("Removing key : " + key );
				}
			}
		}
	}
}



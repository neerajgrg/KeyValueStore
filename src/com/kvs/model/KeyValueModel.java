package com.kvs.model;

public class KeyValueModel {
	
	private Integer key;
	private Integer value;
	private long insertionTimeInMillis;
	
	public KeyValueModel(Integer key, Integer value, long insertionTimeInMillis) {
		super();
		this.key = key;
		this.value = value;
		this.insertionTimeInMillis = insertionTimeInMillis;
	}
	
	public Integer getKey() {
		return key;
	}
	public void setKey(Integer key) {
		this.key = key;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
	}
	public long getInsertionTimeInMillis() {
		return insertionTimeInMillis;
	}
	public void setInsertionTimeInMillis(long insertionTimeInMillis) {
		this.insertionTimeInMillis = insertionTimeInMillis;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyValueModel other = (KeyValueModel) obj;
		if (key == null) {
			if (other.key != null)
				return false;
		} else if (!key.equals(other.key))
			return false;
		return true;
	}
	
}

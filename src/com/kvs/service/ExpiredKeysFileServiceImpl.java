package com.kvs.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExpiredKeysFileServiceImpl implements ExpiredKeysCollectionService{
	
	private File file;
	
	public ExpiredKeysFileServiceImpl(String filePath) {
		createFile(filePath);
	}
	
	private void createFile(String path){
	    Path filePath = Paths.get(path);
	    try {
	      Files.createDirectories(filePath.getParent());
	      Files.createFile(filePath);
	    } catch (FileAlreadyExistsException fae) {
	      //Don't want to throw exception if File already exists
	    } catch(IOException e){
	        e.printStackTrace();
	    }
	    this.file  = new File(path);
	 }

	@Override
	public void collectKey(Integer key) {
		writeToFile(key);
	}
	
	public boolean writeToFile(Integer key) {
	    try (PrintWriter pr = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
	      pr.println(key);
	    } catch (IOException e) {
	      e.printStackTrace();
	      return false;
	    }
	    return true;
	  }
	
}

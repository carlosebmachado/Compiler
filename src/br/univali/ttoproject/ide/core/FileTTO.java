package br.univali.ttoproject.ide.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.filechooser.FileSystemView;

public class FileTTO {
	
	private String name;
	private String path;
	
	public FileTTO() {
		name = "untitled.tto";
		path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath() + "\\";
	}

	public FileTTO(String name, String path) {
		this.name = name;
		this.path = path;
	}

	public FileTTO(String fullPath) {
		var slice = fullPath.split("\\\\");
		name = slice[slice.length-1];
		path = "";
		for (int i = 0; i < slice.length-1; i++) {
			path += slice[i] + "\\";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
	public void save(String content) {
		try (PrintWriter out = new PrintWriter(getFullPath())) {
		    out.println(content);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public String load() {
		String content = "";
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(getFullPath()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();

		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();
		    }
		    content = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		    try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return content;
	}

	public String getFullPath() {
		return path + name;
	}
	
}

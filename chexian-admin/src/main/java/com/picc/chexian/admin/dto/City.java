package com.picc.chexian.admin.dto;

import lombok.Data;

@Data
public class City {
	private String id;
	private String name;
	
	public City(String id, String name){
		this.id = id;
		this.name = name;
	}

}

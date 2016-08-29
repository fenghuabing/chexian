package com.picc.chexian.admin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Province {
	private String id;
	private String name;
	private List<City> cities = new ArrayList<City>();
	
	public Province(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	public void addCity(City city){
		cities.add(city);
	}
}

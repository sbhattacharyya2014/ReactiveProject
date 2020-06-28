package com.subhadip.practice.reactive.document;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	
	private String Id;
	private String description;
	private double price;

}

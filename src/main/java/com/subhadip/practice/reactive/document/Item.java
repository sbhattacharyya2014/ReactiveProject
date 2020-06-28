package com.subhadip.practice.reactive.document;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
	
	@Id
	private String Id;
	private String description;
	private double price;

}

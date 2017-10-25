package com.sr.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="PERSISTENCEMODEL")
@Entity
public class PersistenceModel {
	
	@Id
	private long id;
	private String sequence;
	
	/*---------------- getters and setters of fields ----------------- */
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getSequence() {
		return sequence;
	}
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}
	
	/*------------------ constructors based on field ------------------- */
	public PersistenceModel(long id, String sequence) {
		super();
		this.id = id;
		this.sequence = sequence;
	}
	
	/*------------------- default constructor -------------------------- */ 
	public PersistenceModel()
	{
		
	}
	
	
	
}

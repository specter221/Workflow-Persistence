package com.sr.service;
import java.util.List;

import com.sr.domain.PersistenceModel;

public interface PersistenceService {
	/*-------------Retrieve by id ----------------- */
	public PersistenceModel getWorkflow(long id);
	
	/*-------------Retrieve all ---------------------*/ 
	public List<PersistenceModel> getWorkflow(); 
	
	/*------------Add a new workflow------------------*/
	public PersistenceModel addWorkflow(PersistenceModel persistencemodel); 
	
	/*------------Update a workflow-------------------*/  
	public PersistenceModel updateWorkflow(PersistenceModel persistencemodel); 
	
	/*------------Delete a workflow-------------------*/
    public boolean deleteWorkflow(int id); 

}

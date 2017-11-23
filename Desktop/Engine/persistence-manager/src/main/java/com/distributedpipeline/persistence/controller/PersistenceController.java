package com.distributedpipeline.persistence.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.distributedpipeline.persistence.domain.JobIdDetails;
import com.distributedpipeline.persistence.domain.TaskLibrary;
import com.distributedpipeline.persistence.domain.Tasks;
import com.distributedpipeline.persistence.domain.Workflow;
import com.distributedpipeline.persistence.exceptions.NotNullException;
import com.distributedpipeline.persistence.exceptions.TaskLibraryNotFoundException;
import com.distributedpipeline.persistence.exceptions.WorkflowAlreadyExistsException;
import com.distributedpipeline.persistence.exceptions.WorkflowNotFoundException;
import com.distributedpipeline.persistence.message.PersistenceProducer;
import com.distributedpipeline.persistence.repo.PersistenceJobRepos;
import com.distributedpipeline.persistence.service.PersistenceServiceImpl;
import com.distributedpipeline.persistence.utility.LogExecutionTime;

@Controller
@RequestMapping("/v1.0/persistence")
@CrossOrigin(origins="*")
public class PersistenceController {
     final static Logger logger = Logger.getLogger(PersistenceController.class);
	
	@Autowired
	PersistenceProducer persistenceProducer;
	
	@Autowired
	PersistenceJobRepos persistenceJobRepos;
	
	@Autowired
	private PersistenceServiceImpl persistenceservice;
		
	/*----------------------------------Get workflow ------------------------------------ */
	@LogExecutionTime
	@RequestMapping(value="/workflow" , method=RequestMethod.GET)
	public ResponseEntity<Iterable<Workflow>> getWorkflow() throws WorkflowNotFoundException {
		Iterable<Workflow> workflow = persistenceservice.getWorkflow();
		if(workflow == null)
		{
		   return new ResponseEntity<Iterable<Workflow>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<Workflow>>(persistenceservice.getWorkflow(),HttpStatus.OK);
	}

   /* ------------------------------ get workflow by name ------------------------------- */
	@RequestMapping(value = "/workflow/{workFlowName}", method = RequestMethod.GET)
	public ResponseEntity<Workflow> getWorkFlow(@PathVariable("workFlowName") String name) throws WorkflowNotFoundException, NotNullException {
		return new ResponseEntity<Workflow>(persistenceservice.getWorkflowByName(name), HttpStatus.OK);
	}
		
	/*---------------------------------add workflow -------------------------------------- */
	@RequestMapping(value="/workflow", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> addWorkflow( @RequestBody  Workflow workflow) {
		String result = "";
		try {
			if(workflow.getWorkFlowName()!=null) {
				result = persistenceservice.addWorkflow(workflow);
			}
			else {
				result = "Workflow name cannot be null";
			}
			return new ResponseEntity<String>(result,HttpStatus.OK);
		}
		catch(Exception e) { 
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	/*--------------------------------update workflow -------------------------------------*/
	@RequestMapping(value="/workflow", method=RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<String> updateWorkflow(@RequestBody Workflow workflow) throws WorkflowAlreadyExistsException {
		Workflow workflow1= persistenceservice.updateWorkflow(workflow);
        if(workflow1==workflow) {
             return new ResponseEntity<String>("workflow updated", HttpStatus.OK); 
        }
        throw new WorkflowAlreadyExistsException("workflow already exists");
	}

	/*-------------------------------- delete workflow ----------------------------------- */
	@RequestMapping(method=RequestMethod.DELETE, value="/workflow/{name}")
    public ResponseEntity<String> deleteWorkflow(@PathVariable(value="name") String name){
		persistenceservice.deleteWorkflow(name);
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
    }
	
	/*------------------------                    ----------------------------------------
                               Method For TaskLibrary    
    --------------------------                    --------------------------------------*/
	
	 /*--------------------------------Get tasklibrary -------------------------------- */
	@RequestMapping(value="/task" , method=RequestMethod.GET)
	public ResponseEntity getTaskLibrary() throws WorkflowNotFoundException, TaskLibraryNotFoundException {
		Iterable<TaskLibrary> tasklibrary = persistenceservice.getTaskLibrary();	
		if(tasklibrary == null)
		{
			return new ResponseEntity<Iterable<TaskLibrary>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Iterable<TaskLibrary>>(tasklibrary, HttpStatus.OK);
	}
	
	
	/*--------------------------- get tasklibrary by name ---------------------------- */
	@RequestMapping(value = "/task/{taskName}", method = RequestMethod.GET)
	public ResponseEntity<TaskLibrary> getTaskLibrary(@PathVariable("taskName") String name) throws TaskLibraryNotFoundException {
		return new ResponseEntity<TaskLibrary>(persistenceservice.gettaskLibraryByName(name), HttpStatus.OK);
	}
		
	/*----------------------------------add tasklibrary ------------------------------ */
	@RequestMapping(value="/task", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity addTaskLibrary( @RequestBody  TaskLibrary taskLibrary) {
		try {
			return new ResponseEntity<TaskLibrary>(persistenceservice.addTaskLibrary(taskLibrary), HttpStatus.OK);
		}
		catch(Exception e) { 
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}   

	/*----------------------------------update tasklibrary ----------------------------*/
	@RequestMapping(value="/task", method=RequestMethod.PUT, consumes="application/json")
    public ResponseEntity<String> updateTaskLibrary(@RequestBody TaskLibrary taskLibrary) throws WorkflowAlreadyExistsException
    {
		TaskLibrary tasklibrary1= persistenceservice.updateTaskLibrary(taskLibrary);
        if(tasklibrary1==taskLibrary) {
        	return new ResponseEntity<String>("TaskLibrary updated", HttpStatus.OK);
        }
        throw new WorkflowAlreadyExistsException("TaskLibrary already exists");
    }
        
	/*--------------------------------- delete tasklibrary --------------------------- */
	@RequestMapping(method=RequestMethod.DELETE, value="/task/{name}", consumes="application/json")
    public ResponseEntity<String> deleteTaskLibrary(@PathVariable(value="name") String taskName){
		persistenceservice.deleteTaskLibrary(taskName);
		return new ResponseEntity<String>("Deleted succesfully", HttpStatus.OK);
	}
	
	/*--------------------------------- get task command from tasklibrary --------------------------- */
	@RequestMapping(method=RequestMethod.GET, value="/task/command/{taskName}")
    public ResponseEntity<String> getCommandFromTaskLibrary(@PathVariable(value="taskName") String taskName){
		return new ResponseEntity<String>(persistenceservice.getTaskCommand(taskName), HttpStatus.OK);
	}
	
	/*--------------------------------- user authentication --------------------------- */
	@RequestMapping(value="/workflow/{userName}/{workFlowName}" , method=RequestMethod.GET)
	public ResponseEntity<String> getWorkflowforuser(@PathVariable(value="userName") String userName, @PathVariable(value="workFlowName") String workFlowName) throws WorkflowNotFoundException, TaskLibraryNotFoundException {
		return new ResponseEntity<String>(persistenceservice.userPermissions(workFlowName, userName),HttpStatus.OK);
	}
	
	/*--------------------------------- get tasks name for a workflow --------------------------- */
	@RequestMapping(value="/tasks/{workFlowName}" , method=RequestMethod.GET)
	public ResponseEntity<List<String>> getWorkflowforuser(@PathVariable(value="workFlowName") String workFlowName) throws WorkflowNotFoundException, TaskLibraryNotFoundException {
		return new ResponseEntity<List<String>>(persistenceservice.getTasksOfWorkflow(workFlowName),HttpStatus.OK);
	}
	
	/*--------------------------------- get tasks Details by task name of a workflow --------------------------- */
	@RequestMapping(value="/tasks/{workFlowName}/{task_name}" , method=RequestMethod.GET)
	public ResponseEntity<Tasks> getWorkflowDetails(@PathVariable(value="workFlowName") String workFlowName, @PathVariable(value="task_name") String task_name) {
		return new ResponseEntity<>(persistenceservice.getDetailsOfTask(workFlowName, task_name),HttpStatus.OK);
	}
	
	
	/*------------------------                    ----------------------------------------
                                 Method For Job Manager   
     --------------------------                    --------------------------------------*/
	
	
	
	/* ------------------------------ get job details by user name ------------------------------- */
	@RequestMapping(value = "/jobdetails/userName/{userName}", method = RequestMethod.GET)
	public ResponseEntity<Iterable<JobIdDetails>> getJobDetailsByUserName(@PathVariable("userName") String userName) {
		return new ResponseEntity<>(persistenceservice.getJobDetailsByUserName(userName), HttpStatus.OK);
	}
	
	/* ------------------------------ get job details by job id ------------------------------- */
	@RequestMapping(value = "/jobdetails/jobId/{jobId}", method = RequestMethod.GET)
	public ResponseEntity<JobIdDetails> getJobDetailsByJobId(@PathVariable("jobId") String jobId) {
		return new ResponseEntity<JobIdDetails>(persistenceJobRepos.getJobIdDetailsByJobId(jobId), HttpStatus.OK);
	}
	
	/*----------------------------------add Job Details ------------------------------ */
	@RequestMapping(value="/jobdetails", method=RequestMethod.POST, consumes="application/json")
	public ResponseEntity<String> saveJobDetails( @RequestBody  JobIdDetails jobIdDetails) {
		try {
			return new ResponseEntity<>(persistenceservice.addJobDetails(jobIdDetails), HttpStatus.OK);
		}
		catch(Exception e) { 
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}  
	
	/*----------------------------------get all Job Details ------------------------------ */
	@RequestMapping(value = "/jobdetails", method = RequestMethod.GET)
	public ResponseEntity<?> getJobDetails() {
		return new ResponseEntity<Iterable<JobIdDetails>>(persistenceservice.getAllJobDetails(), HttpStatus.OK);
	}
	
}

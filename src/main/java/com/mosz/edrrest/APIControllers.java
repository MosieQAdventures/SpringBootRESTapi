package com.mosz.edrrest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIControllers {

	@Autowired
	private TaskRepo taskRepo;
	
	//------------- REQUIRED ENDPOINTS -----------------------------------
	
	@GetMapping(value = "/result/{id}")
	public String getTaskResult(@PathVariable long id) {
		Task task = taskRepo.findById(id).get();
		String message = "Result of id " + id + " can be found here:\n" + task.getPath();
		return message;
	}
	
	@GetMapping(value = "/running")
	public String getTaskResultCount() {
		List<Task> listTask = taskRepo.findAll();
		int count = listTask.size();
		return "Result Count: " + count;
	}
	
	@PostMapping(value = "/newjob/{command}")
	public String newTaskv2(@PathVariable String command) {
		MainTaskHelper mainTaskHelper = new MainTaskHelper();
		List<String> list = mainTaskHelper.getCommandList(command);
		int i = 0; 
		for (String commandItem : list) {
			mainTaskHelper.executeTask(commandItem, i);
			Task task = new Task();
			task = mainTaskHelper.setTaskParameters(task, commandItem, i);
			taskRepo.save(task);
			i++;
		}
		return command;
	}
	
	//----------------- BASIC CRUD ENDPOINTS --------------------
	
	@GetMapping(value = "/")
	public String getPage() {
		return "Welcome";
	}
	
	@GetMapping(value = "/tasks")
	public List<Task> getTasks() {
		return taskRepo.findAll();
	}
	
	@PostMapping(value = "/save")
	public String saveTask(@RequestBody Task task) {
		taskRepo.save(task);
		return "saved...";
	}
	
	@PutMapping(value = "/update/{id}")
	public String updateTask(@PathVariable long id, @RequestBody Task task) {
		Task updatedTask = taskRepo.findById(id).get();
		updatedTask.setMinLength(task.getMinLength());
		updatedTask.setMaxLength(task.getMaxLength());
		updatedTask.setStringsToCreateCount(task.getStringsToCreateCount());
		updatedTask.setCharList(task.getCharList());
		updatedTask.setPath(task.getPath());
		taskRepo.save(updatedTask);
		return "Updated task with the id: " + id;
	}
	
	@DeleteMapping(value = "/delete/{id}")
	public String deleteTask(@PathVariable long id) {
		Task deleteTask = taskRepo.findById(id).get();
		taskRepo.delete(deleteTask);
		return "Deleted task with the id: " + id;
	}
	
	@PostMapping(value = "/newjob")   //using json body from Postman
	public String newTask(@RequestBody Task task) {
		taskRepo.save(task); 
		System.out.print(task.getMaxLength());
		return "saved...";
	}
}

package com.target.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.target.entities.request.ApiLogRequest;
import com.target.service.logger.ApiLogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/logs")
@RequiredArgsConstructor
public class ApiLogController {

	 private final ApiLogService apiLogService;
	
	 	
	 	@GetMapping
	    public List<ApiLogRequest> getLogs() {
	        return apiLogService.getLogs();
	    }

	    /**
	     * Limpa todos os logs
	     */
	    @DeleteMapping("/clear")
	    public void clearLogs() {
	        apiLogService.clearLogs();
	    }
	    
	   	 
}
package com.target.service.logger;

import java.util.List;

import com.target.entities.request.ApiLogRequest;

public interface ApiLogService {
	
	 void addLog(ApiLogRequest log);

	    List<ApiLogRequest> getLogs();

	    void clearLogs();

}

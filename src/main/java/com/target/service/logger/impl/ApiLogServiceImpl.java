package com.target.service.logger.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.target.entities.request.ApiLogRequest;
import com.target.service.logger.ApiLogService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiLogServiceImpl implements ApiLogService {

    private final List<ApiLogRequest> logs = new ArrayList<>();

    @Override
    public void addLog(ApiLogRequest logEntry) {

        logs.add(logEntry);

        analisarStatus(logEntry);
    }

    private void analisarStatus(ApiLogRequest logEntry) {

        int status = logEntry.getStatus();

        // 🎯 Monitorar tudo que não for sucesso (2xx)
        if (status < 200 || status >= 300) {

            if (status >= 500) {
                log.error("🚨 ERRO CRÍTICO | {} {} | Status {} | IP {}",
                        logEntry.getMethod(),
                        logEntry.getPath(),
                        status,
                        logEntry.getIp());

            } else {
                log.warn("⚠️ ERRO HTTP | {} {} | Status {} | IP {}",
                        logEntry.getMethod(),
                        logEntry.getPath(),
                        status,
                        logEntry.getIp());
            }
        }
    }

    @Override
    public List<ApiLogRequest> getLogs() {
        return new ArrayList<>(logs);
    }

    @Override
    public void clearLogs() {
        logs.clear();
    }
}
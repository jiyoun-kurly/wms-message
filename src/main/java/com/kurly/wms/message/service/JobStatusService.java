package com.kurly.wms.message.service;

import com.kurly.wms.message.domain.WmsJobStatus;

import java.util.List;
import java.util.function.Consumer;

public interface JobStatusService {

	List<WmsJobStatus> getCurrentRunningJob() throws Exception;

	int updateJobStatus(String status) throws Exception;

	boolean hasRunningJob(Consumer<String> messageConsumer) throws Exception;
}

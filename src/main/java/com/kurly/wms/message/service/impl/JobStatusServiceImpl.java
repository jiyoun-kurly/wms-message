package com.kurly.wms.message.service.impl;

import com.kurly.wms.message.domain.WmsJobStatus;
import com.kurly.wms.message.repository.WmsJobStatusRepository;
import com.kurly.wms.message.service.JobStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobStatusServiceImpl implements JobStatusService {

	private final WmsJobStatusRepository wmsJobStatusRepository;

	@Override
	public List<WmsJobStatus> getCurrentRunningJob() throws Exception {
		return wmsJobStatusRepository.getCurrentRunningJob();
	}

	@Override
	@Transactional
	public int updateJobStatus(String status) throws Exception {
		return wmsJobStatusRepository.updateJobStatus(status);
	}

	@Override
	public boolean hasRunningJob(Consumer<String> messageConsumer) throws Exception {
		//job_status 확인
		try {
			List<WmsJobStatus> ifJobStatusList = this.getCurrentRunningJob();
			if (ifJobStatusList != null && ifJobStatusList.size() > 0) {
				String result = "진행중인 할당 작업이 존재합니다. 작업이 끝난 후 주문 전송 해 주세요."
						+ ifJobStatusList.stream().map(ifJobStatus -> "\n" + ifJobStatus.getDescription() + " - " + ifJobStatus.getJob_time())
						.collect(Collectors.toList());
				messageConsumer.accept(result);
				return true;
			}
		}catch(Exception e) {
			throw new Exception("cannot check job status");
		}
		return false;
	}
}

package com.kurly.wms.message.repository;

import com.kurly.wms.message.domain.WmsJobStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WmsJobStatusRepository {

    List<WmsJobStatus> getCurrentRunningJob() throws Exception;
    int updateJobStatus(String status) throws Exception;
}

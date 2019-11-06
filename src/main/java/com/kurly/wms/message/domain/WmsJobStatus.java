package com.kurly.wms.message.domain;

import lombok.Data;

@Data
public class WmsJobStatus {
    private String seq;
    private String job;
    private String job_group_code;
    private String status;
    private String description;
    private String job_time;
}

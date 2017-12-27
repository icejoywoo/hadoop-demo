package com.github.icejoywoo.yarn.event;

import org.apache.hadoop.yarn.event.AbstractEvent;

public class JobEvent extends AbstractEvent<JobEventType> {

    private String jobID;

    public JobEvent(String jobID, JobEventType jobEventType) {
        super(jobEventType);

        this.jobID = jobID;
    }

    public String getJobID() {
        return jobID;
    }
}

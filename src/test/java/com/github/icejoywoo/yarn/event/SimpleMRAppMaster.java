package com.github.icejoywoo.yarn.event;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.service.CompositeService;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.event.AsyncDispatcher;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.apache.hadoop.yarn.event.Event;
import org.apache.hadoop.yarn.event.EventHandler;

import javafx.concurrent.Task;

public class SimpleMRAppMaster extends CompositeService {

    private Dispatcher dispatcher;
    private String jobID;
    private int taskNumber;
    private String[] taskIDs;

    public SimpleMRAppMaster(String name, String jobID, int taskNumber) {
        super(name);

        this.jobID = jobID;
        this.taskNumber = taskNumber;

        taskIDs = new String[taskNumber];
        for (int i = 0; i < taskNumber; i++) {
            taskIDs[i] = jobID + "_task_" + i;
        }
    }

    @Override
    protected void serviceInit(Configuration conf) throws Exception {
        dispatcher = new AsyncDispatcher();

        dispatcher.register(JobEventType.class, new JobEventDispatcher());
        dispatcher.register(TaskEventType.class, new TaskEventDispatcher());
        addService((Service) dispatcher);

        super.serviceInit(conf);
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    private class JobEventDispatcher implements EventHandler {
        @Override
        public void handle(Event event) {
            if (event.getType() == JobEventType.JOB_KILL) {
                System.out.println("Receive JOB_KILL event, killing all the tasks.");

                for (int i = 0; i < taskNumber; i++) {
                    dispatcher.getEventHandler().handle(new TaskEvent(taskIDs[i], TaskEventType.T_KILL));
                }
            } else if (event.getType() == JobEventType.JOB_INIT) {
                System.out.println("Receive JOB_INIT event, scheduling tasks.");

                for (int i = 0; i < taskNumber; i++) {
                    dispatcher.getEventHandler().handle(new TaskEvent(taskIDs[i], TaskEventType.T_SCHEDULE));
                }
            }
        }
    }

    private class TaskEventDispatcher implements EventHandler {
        @Override
        public void handle(Event event) {
            if (event.getType() == TaskEventType.T_KILL) {
                System.out.println("Receive T_KILL event of task");
            } else if (event.getType() == TaskEventType.T_SCHEDULE) {
                System.out.println("Receive T_SCHEDULE event of task");
            }
        }
    }

    /**
     * simple test main
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        String jobID = "job_20171226_1";
        SimpleMRAppMaster appMaster = new SimpleMRAppMaster("Simple App Master", jobID, 5);

        YarnConfiguration conf = new YarnConfiguration(new Configuration());
        appMaster.serviceInit(conf);
        appMaster.serviceStart();

        appMaster.getDispatcher().getEventHandler().handle(new JobEvent(jobID, JobEventType.JOB_INIT));
        appMaster.getDispatcher().getEventHandler().handle(new JobEvent(jobID, JobEventType.JOB_KILL));
    }
}

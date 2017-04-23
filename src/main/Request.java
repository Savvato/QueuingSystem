package main;

import java.util.Date;

public class Request
{
    private Date startTime;
    private Date finishTime;

    public void start() {
        this.startTime = new Date();
    }

    public void finish() {
        this.finishTime = new Date();
    }

    public long getTimeInSystem() {
        return this.finishTime.getTime() - this.startTime.getTime();
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }
}

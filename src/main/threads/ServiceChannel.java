package main.threads;

import main.Request;

import java.util.Random;

public class ServiceChannel extends Thread
{
    public boolean isBusy = false;

    public long endServicingTimeOfCurrentRequest;

    private long averageServiceTime;

    private long averageVariation;

    private Random random;

    public ServiceChannel(long averageServiceTime) {
        this.averageServiceTime = averageServiceTime;
        this.init();
    }

    private void init() {
        this.averageVariation = (long) Math.sqrt(this.averageServiceTime);
        this.random = new Random();
    }

    public void handleMoment(Request request, long moment) {
        if (!this.isBusy) {
            this.setBusy(moment);
            request.setEndQueueTime(moment);
            request.setStartServiceTime(moment);
            request.setFinishServiceTime(this.endServicingTimeOfCurrentRequest);
        }
    }

    private void setBusy(long moment) {
        this.isBusy = true;
        this.endServicingTimeOfCurrentRequest = moment + this.getServiceTime();
    }

    private long getServiceTime() {
        long number = (long) this.random.nextGaussian() + this.averageServiceTime;
        long variation = (long) this.random.nextGaussian() * this.averageVariation;
        number += variation;
        return number > 0 ? number : -number;
    }

    /**
     * Проверка занятости канала
     *
     * @param moment
     */
    public void checkBusy(long moment) {
        if (moment == this.endServicingTimeOfCurrentRequest) {
            this.isBusy = false;
        }
    }
}

package main.parts;

import main.QueuingSystem;
import main.Request;

import java.util.Random;

public class RequestGenerator
{
    private static final int REQUESTS_CONT = 1000;

    private final Random random = new Random();

    private final long averageTimeInterval;

    private final long averageVariation;

    private long internalVirtualTime = 0;

    private QueuingSystem queuingSystem;

    /**
     * RequestGenerator constructor
     *
     * @param queuingSystem CMO
     */
    public RequestGenerator(QueuingSystem queuingSystem) {
        this.queuingSystem = queuingSystem;
        this.averageTimeInterval = this.queuingSystem.averageRequestIncomingTimeInterval;
        this.averageVariation = (long) Math.sqrt(this.averageTimeInterval);

    }

    public void run() {
        for (int index = 1; index <= REQUESTS_CONT; index++) {
            long timeInterval = this.getTimeInterval();
            Request request = new Request();
            request.setStartQueueTime(this.internalVirtualTime + timeInterval);
            queuingSystem.queue.offer(request);
            this.internalVirtualTime += timeInterval;
        }
    }

    /**
     * Расчет случайного временного интервала
     *
     * @return случайное число
     */
    private long getTimeInterval() {
        long number = (long)this.random.nextGaussian() + this.averageTimeInterval;
        long variation = (long)this.random.nextGaussian() * this.averageVariation;
        number += variation;
        return number > 0 ? number : -number;
    }
}

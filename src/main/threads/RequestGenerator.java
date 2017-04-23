package main.threads;

import main.QueuingSystem;
import main.Request;

import java.util.Random;

public class RequestGenerator extends Thread
{
    private final Random random = new Random();

    private final int averageTimeInterval = 50;
    private final int averageVariation = 10;

    private QueuingSystem queuingSystem;

    /**
     * RequestGenerator constructor
     *
     * @param queuingSystem
     */
    public RequestGenerator(QueuingSystem queuingSystem) {
        super("RequestGeneratorThread");
        this.queuingSystem = queuingSystem;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                this.sleep(this.getTimeInterval());
                Request request = new Request();
                request.start();
                this.queuingSystem.queue.offer(request);
            }
            catch (InterruptedException e) {
                e = null;
            }
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

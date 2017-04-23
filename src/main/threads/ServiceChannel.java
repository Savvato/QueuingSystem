package main.threads;

import main.QueuingSystem;
import main.Request;

public class ServiceChannel extends Thread
{
    private QueuingSystem queuingSystem;

    private long serviceTime;

    /**
     * ServiceChanel constructor
     *
     * @param queuingSystem
     */
    public ServiceChannel(QueuingSystem queuingSystem, long serviceTime) {
        this.queuingSystem = queuingSystem;
        this.serviceTime = serviceTime;
    }

    public void run() {
        while (!this.isInterrupted()) {
            Request request;
            synchronized (this.queuingSystem.queue) {
                request = this.queuingSystem.queue.poll();
            }
            if (request != null) {
                try {
                    this.sleep(serviceTime);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
                request.finish();
                synchronized (this.queuingSystem.sink) {
                    this.queuingSystem.sink.add(request);
                }
            }
        }
    }
}

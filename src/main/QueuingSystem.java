package main;

import main.threads.RequestGenerator;
import main.threads.ServiceChannel;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class QueuingSystem extends Thread
{
    /**
     * Очередь заявок
     */
    public PriorityQueue<Request> queue;

    public ArrayList<Request> sink;

    /**
     * Генератор заявок
     */
    private RequestGenerator requestGenerator;

    /**
     * Каналы обслуживания
     */
    private ServiceChannel[] serviceChannels;

    /**
     * Количество каналов обслуживания
     */
    private int serviceChannelsCount;

    /**
     * Среднее время обслуживания
     */
    private long serviceTime;

    /**
     * Конструктор системы
     *
     * @param serviceChannelsCount Количество каналов обслуживания
     */
    public QueuingSystem(int serviceChannelsCount, long serviceTime) {
        this.serviceChannelsCount = serviceChannelsCount;
        this.serviceTime = serviceTime;
        this.init();
    }

    /**
     * Инициализация системы массового обслуживания
     */
    private void init() {
        this.queue = new PriorityQueue<>(100);
        this.sink = new ArrayList<Request>();
        this.requestGenerator = new RequestGenerator(this);
        this.serviceChannels = new ServiceChannel[this.serviceChannelsCount];
        for (int index = 0; index < this.serviceChannels.length; index++) {
            this.serviceChannels[index] = new ServiceChannel(this, this.serviceTime);
        }
    }


    public void run() {
        this.requestGenerator.start();
        for (ServiceChannel serviceChannel: this.serviceChannels) {
            serviceChannel.start();
        }

        try {
            this.sleep(10000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.requestGenerator.interrupt();
        for (ServiceChannel serviceChannel: this.serviceChannels) {
            serviceChannel.interrupt();
        }

    }

    /**
     * Расчет среднего времени заявки в системе
     *
     * @return
     */
    public long calculateAverageTimeInSystem() {
        if (this.sink.size() == 0){
            return 0;
        }
        long sumTimeForRequests = 0;
        for (Request request : this.sink) {
            sumTimeForRequests += request.getTimeInSystem();
        }
        return sumTimeForRequests / this.sink.size();
    }
}

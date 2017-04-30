package main;

import main.threads.RequestGenerator;
import main.threads.ServiceChannel;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class QueuingSystem
{
    /**
     * Очередь заявок
     */
    public PriorityQueue<Request> queue;

    /**
     * Сток, массив обработанныъ заявок
     */
    public ArrayList<Request> sink;

    /**
     * Средний интервал времени между прибытиями заявок
     */
    public long averageRequestIncomingTimeInterval;

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
    private long averageServiceTime;

    /**
     * Конструктор системы
     *
     * @param serviceChannelsCount Количество каналов обслуживания
     */
    public QueuingSystem(long averageRequestIncomingTimeInterval, int serviceChannelsCount, long averageServiceTime) {
        this.averageRequestIncomingTimeInterval = averageRequestIncomingTimeInterval;
        this.serviceChannelsCount = serviceChannelsCount;
        this.averageServiceTime = averageServiceTime;
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
            this.serviceChannels[index] = new ServiceChannel(this.averageServiceTime);
        }
    }

    public void run() {
        this.requestGenerator.run();
        long moment = 0;

        while (this.queue.peek() != null) {
            Request request = this.queue.peek();
            if (request.getStartQueueTime() <= moment) {
                ServiceChannel serviceChannel = this.getNotBusyServiceChannel();
                if (serviceChannel != null) {
                    request = this.queue.poll();
                    serviceChannel.handleMoment(request, moment);
                    this.sink.add(request);
                }
            }
            for (ServiceChannel serviceChannel : this.serviceChannels) {
                serviceChannel.checkBusy(moment);
            }
            moment++;
        }
    }

    /**
     * Поиск незанятого канала обслуживания
     *
     * @return Канал обслуживания
     */
    private ServiceChannel getNotBusyServiceChannel() {
        for (ServiceChannel serviceChannel: this.serviceChannels) {
            if (!serviceChannel.isBusy) {
                return serviceChannel;
            }
        }
        return null;
    }

    /**
     * Расчет среднего времени заявки в системе
     *
     * @return
     */
    public long calculateAverageTimeInSystem() {
        if (this.sink.size() == 0) {
            return 0;
        }
        long sumTimeForRequests = 0;
        for (Request request : this.sink) {
            sumTimeForRequests += request.getTimeInSystem();
        }
        return sumTimeForRequests / this.sink.size();
    }

}

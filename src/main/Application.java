package main;

import java.util.Scanner;

public class Application
{
    /**
     * Количество каналов обслуживания
     */
    private int serviceChannelsCount;

    /**
     * Средний интервал времени между прибытиями заявок
     */
    private long averageRequestIncomingTimeInterval;

    /**
     * Среднее время обслуживания
     */
    private long averageServiceTime;

    private QueuingSystem queuingSystem;

    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        application.run();
    }

    public void run() throws InterruptedException {
        this.readInputData();
        this.queuingSystem = new QueuingSystem(this.averageRequestIncomingTimeInterval, this.serviceChannelsCount, this.averageServiceTime);
        this.queuingSystem.run();
        this.printOutData();
    }

    private void readInputData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter average request incoming time interval:");
        this.averageRequestIncomingTimeInterval = scanner.nextLong();

        System.out.println("Enter count of service channels:");
        this.serviceChannelsCount = scanner.nextInt();

        System.out.println("Enter average service time:");
        this.averageServiceTime = scanner.nextLong();
    }

    private void printOutData() {
        long averageTime = this.queuingSystem.calculateAverageTimeInSystem();
        System.out.println("Average time of requests in system: " + averageTime + " ms");
        System.out.println("Requests serviced: " + this.queuingSystem.sink.size());
        System.out.println("============================");
        System.out.println("Requests in queue: " + this.queuingSystem.queue.size());
    }
}

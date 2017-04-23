package main;

import java.util.Scanner;

public class Application
{
    /**
     * Количество каналов обслуживания
     */
    private int serviceChannelsCount;

    /**
     * Среднее время обслуживания
     */
    private long serviceTime;

    private QueuingSystem queuingSystem;

    public static void main(String[] args) throws InterruptedException {
        Application application = new Application();
        application.run();
    }

    public void run() throws InterruptedException {
        this.readInputData();
        this.queuingSystem = new QueuingSystem(this.serviceChannelsCount, this.serviceTime);
        this.queuingSystem.start();
        this.queuingSystem.join();
        this.printOutData();
    }

    private void readInputData() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter count of service channels:");
        this.serviceChannelsCount = scanner.nextInt();

        System.out.println("Enter service time:");
        this.serviceTime = scanner.nextLong();
    }

    private void printOutData() {
        long averageTime = this.queuingSystem.calculateAverageTimeInSystem();
        System.out.println("Average time of requests in system: " + averageTime + " ms");
        System.out.println("Requests serviced: " + this.queuingSystem.sink.size());
        for (Request request: this.queuingSystem.sink) {
            System.out.println("============================");
            System.out.println("Time in system: " + request.getTimeInSystem());
            System.out.println("Start time: " + request.getStartTime().getTime());
            System.out.println("Finish time: " + request.getFinishTime().getTime());
        }
    }
}

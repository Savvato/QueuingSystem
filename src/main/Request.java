package main;

public class Request implements Comparable<Request>
{
    private long startQueueTime;

    private long endQueueTime;

    private long startServiceTime;

    private long finishServiceTime;

    public long getStartQueueTime() {
        return startQueueTime;
    }

    public void setStartQueueTime(long startQueueTime) {
        this.startQueueTime = startQueueTime;
    }

    public long getEndQueueTime() {
        return endQueueTime;
    }

    public void setEndQueueTime(long endQueueTime) {
        this.endQueueTime = endQueueTime;
    }

    public long getStartServiceTime() {
        return startServiceTime;
    }

    public void setStartServiceTime(long startServiceTime) {
        this.startServiceTime = startServiceTime;
    }

    public long getFinishServiceTime() {
        return finishServiceTime;
    }

    public void setFinishServiceTime(long finishServiceTime) {
        this.finishServiceTime = finishServiceTime;
    }

    @Override
    public int compareTo(Request r) {
        if (this.getFinishServiceTime() < r.getFinishServiceTime()) {
            return -1;
        }
        if (this.getFinishServiceTime() > r.getFinishServiceTime()) {
            return 1;
        }
        return 0;
    }

    public long getTimeInSystem() {
        return this.finishServiceTime - this.startQueueTime;
    }

    public void log() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("SQ: " + this.startQueueTime)
                .append(" SS: " + this.startServiceTime)
                .append(" FS: " + this.finishServiceTime);
        System.out.println(stringBuilder.toString());
    }
}

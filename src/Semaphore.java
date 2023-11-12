class Semaphore {
    private int permits;

    public Semaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        if (permits == 0) {
            wait();
        }
        permits--;
    }

    public synchronized void release() {
        permits++;
        if (permits > 0) {
            notify();
        }
    }

    public synchronized boolean hasPermits() {
        return permits > 0;
    }
}
class Semaphore {
    private int permits;

    public Semaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        while (permits == 0) {
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

    public boolean hasPermits() {
        return permits > 0;
    }
}
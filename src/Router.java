import java.util.ArrayList;
// import java.util.concurrent.Semaphore;

class Router {
    private int size;
    private Semaphore devices;
    private ArrayList<Device> connections;
    private int currentConnection = 1;

    public Router(int maxConnections) {
        size = maxConnections;
        devices = new Semaphore(size);
        connections = new ArrayList<>();
    }

    public void connect(Device device) throws InterruptedException {
        devices.acquire();
        connections.add(device);
        currentConnection = (currentConnection % size) + 1;
    }

    public void disconnect(Device device) {
        connections.remove(device);
        devices.release();
    }

    public synchronized int devicesNum() {
        return connections.size();
    }

    public int getConnectionNum() {
        return currentConnection;
    }

    public int getSize() {
        return size;
    }
}

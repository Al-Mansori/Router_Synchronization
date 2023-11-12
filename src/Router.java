import java.util.ArrayList;


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
        currentConnection = (currentConnection % size) + 1;
        connections.add(device);
        
        
    }

    public void disconnect(Device device) {
        connections.remove(device);
        devices.release();
    }

    public synchronized int devicesNum() {
        return connections.size();
    }

    public synchronized int getConnectionNum() {
        return currentConnection;
    }

    public int getSize() {
        return size;
    }
}

import java.util.ArrayList;


class Router {
    private int size;
    private Semaphore devices;
    private ArrayList<Device> connections;
    private int currentConnection = 1;

    public Router(int maxConnections) {
        size = maxConnections;
        devices = new Semaphore(size);
        connections = new ArrayList<>(size);
    }

    public void connect(Device device) throws InterruptedException {
        devices.acquire();   
        connections.add(device);
        device.setConnection(currentConnection);
        currentConnection = (currentConnection  % size) + 1;
        
    }

    public void disconnect(Device device) {
        connections.remove(device);
        devices.release();
        currentConnection = device.getConnection();
    }

    public synchronized boolean hasPermits() {
        return devices.hasPermits();
    }

    public int getCurrentConnection() {
        return currentConnection;
        
    }
}

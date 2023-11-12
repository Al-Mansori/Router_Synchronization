import java.util.ArrayList;
// import java.util.concurrent.Semaphore;

class Router {
    private int size;
    private Semaphore devices;
    private ArrayList<Device> connections;
    private int inptr = 1;
    private int outptr = 1;

    public Router(int maxConnections) {
        size = maxConnections;
        devices = new Semaphore(size);
        connections = new ArrayList<>();
    }

    public void connect(Device device) throws InterruptedException {
        if(connections.size() >= size) {
            System.err.println(device.getName() + " (" + device.getType() + ") " + "arrived and waiting");
        } else {
            System.err.println(device.getName() + " (" + device.getType() + ") " + "arrived");
        }
        connections.add(device);
        inptr = (inptr + 1) % size;
        devices.acquire();
        System.out.println("Connection " + inptr + ": " + device.getName() + ": " + device.getType() + " Occupied");
    }

    public void disconnect(Device device) {
  
        devices.release();
        connections.remove(device);
        
        outptr = (outptr + 1) % size;
        System.out.println("Connection " + outptr + ": " + device.getName() + ": " + device.getType() + " Logged out");
    }

    public int devicesNum() {
        return connections.size();
    }
}

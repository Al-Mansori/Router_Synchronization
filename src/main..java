import java.util.ArrayList;
import java.util.Scanner;

class Semaphore {
    private int permits;

    public Semaphore(int permits) {
        this.permits = permits;
    }

    public synchronized void acquire() throws InterruptedException {
        permits--;
        if (permits < 0) {
            wait();
        }  
    }

    public synchronized void release() {
        permits++;
        if (permits <= 0) {
            notify();
        }
    }

    public boolean hasPermits() {
        return permits > 0;
    }
}

class Device extends Thread {
    private String type;
    private Router router;
    private int connection;

    public Device() {

    }
    
    public Device(String name, String type, Router router) {
        setName(name);
        this.type = type;
        this.router = router;
    }

    public String  getType() {
        return type;
    }

    public void setConnection(int _connection) {
        this.connection = _connection;
        
    }

    public int getConnection() {
        return connection;
    }
    
    public void run() {
        try {
            synchronized (router) {
                if (router.hasPermits()) {
                    System.err.println(getName() + " (" + getType() + ") " + "arrived");
                } else {
                    System.err.println(getName() + " (" + getType() + ") " + "arrived and waiting");
                }       
            }

            setConnection(router.getCurrentConnection());
            router.connect(this);
            System.out.println("Connection " + connection + ": " + getName() + " Occupied");
            System.out.println("Connection " + connection + ": " + getName() + " login");
            System.out.println("Connection " + connection + ": " + getName() + " Performs Online activity");
            Thread.sleep((long) (Math.random() * 5000 * Math.random()));
            System.out.println("Connection " + connection + ": " + getName() + " Logged out");
            router.disconnect(this);
    

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }
}


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


class Network {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is the number of WI-FI Connections?");
        int maxConnections = scanner.nextInt();

        System.out.println("What is the number of devices Clients want to connect?");
        int totalDevices = scanner.nextInt();

        Router router = new Router(maxConnections);

        ArrayList<Device> devices = new ArrayList<>();

        for (int i = 0; i < totalDevices; i++) {
            System.out.println("Enter device name and type (e.g., C1 mobile): ");
            String name = scanner.next();
            String type = scanner.next();

            devices.add(new Device(name, type, router));

        }

        for (Device device : devices) {
            device.start();
        }
    }
}
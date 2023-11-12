import java.util.ArrayList;
import java.util.Scanner;

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
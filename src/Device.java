class Device extends Thread {
    private String type;
    private Router router;

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

    public void run() {
        try {
            int connectionNum;
            synchronized (router) {
                if (router.devicesNum() >= router.getSize()) {
                    System.err.println(getName() + " (" + getType() + ") " + "arrived and waiting");
                } else {
                    System.err.println(getName() + " (" + getType() + ") " + "arrived");
                }
                connectionNum = router.getConnectionNum();
            }
            router.connect(this);
            System.out.println("Connection " + connectionNum + ": " + getName() + " Occupied");
            System.out.println("Connection " + connectionNum + ": " + getName() + " login");
            System.out.println("Connection " + connectionNum + ": " + getName() + " Performs Online activity");
            sleep((long) (Math.random() * 5000));
            System.out.println("Connection " + connectionNum + ": " + getName() + " Logged out");
            router.disconnect(this);

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }
}

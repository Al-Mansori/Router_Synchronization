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
            router.connect(this);
            System.out.println("Connection " + getName() + ": " + getType() + " login");
            System.out.println("Connection " + getName() + ": " + getType() + " Performs Online activity");
            Thread.sleep((long) (Math.random() * 5000));
            System.err.println("Num : " + router.devicesNum());
            router.disconnect(this);

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }
}

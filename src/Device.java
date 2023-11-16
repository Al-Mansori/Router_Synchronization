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
            Thread.sleep((long) (Math.random() * 5000));
            System.out.println("Connection " + connection + ": " + getName() + " Logged out");
            router.disconnect(this);
    

        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        
        
    }
}

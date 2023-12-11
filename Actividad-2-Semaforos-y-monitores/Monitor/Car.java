package Monitor;

class Car extends Thread {
    private Bridge bridge;
    private String direction;

    /**
     * 
     * @param bridge
     * @param direction
     */
    public Car(Bridge bridge, String direction) {
        this.bridge = bridge;
        this.direction = direction;
    }

    /**
     * 
     */
    public void run() {
        // Obtenemos el nombre del hilo para imprimir la información
        String carName = Thread.currentThread().getName();

        // Hacemos que el coche cruce el puente
        bridge.crossBridge(carName, direction);

        // Esperamos 5 segundos
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Y salimos del puente
        bridge.exitBridge(carName, direction);
    }
}
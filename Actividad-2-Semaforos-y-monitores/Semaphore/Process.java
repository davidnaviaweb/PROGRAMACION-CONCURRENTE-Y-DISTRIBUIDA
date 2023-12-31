package Semaphore;

import java.util.Random;

class Process extends Thread {
    private Resource resource;

    /**
     * 
     * @param resource
     */
    public Process(Resource resource) {
        this.resource = resource;
    }

    /**
     * 
     */
    public void run() {
        // Inicializamos un generador de números aleatorios
        Random random = new Random();

        // Inicializamos la cantidad que se va a reservar
        int unitsToReserve = 0;

        // Usaremos un flag para forzar que este proceso adquiera una cantidad random de
        // unidades del recurso en algún momento
        boolean acquired = false;
        do {
            // Generamos una cantidad random de unidades, entre 1 y el máximo posible
            unitsToReserve = random.nextInt(1, resource.getSize());

            // Adquirimos el recurso
            acquired = resource.reserve(unitsToReserve);

            // Pausamos el hilo durante un tiempo random
            pause(random);

        } while (acquired == false);

        // Por último, liberamos las unidades de recurso adquiridas previamente
        resource.release(unitsToReserve);
    }

    /**
     * 
     * @param random
     */
    public void pause(Random random) {
        try {
            Thread.sleep(random.nextLong(1000, 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
package Monitor;

class Bridge {
    private boolean crossingFromNorth = false;
    private boolean crossingFromSouth = false;
    private int crossingCars = 0;

    /**
     * 
     * @param carName
     * @param direction
     */
    public synchronized void crossBridge(String carName, String direction) {
        // Si el coche viene del norte
        if (direction.equals("norte")) {
            // Esperamos indefinidamente si hay coches que vienen del sur cruzando el puente
            while (crossingFromSouth) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // Cuando ya no estamos esperando, entonces es que hay coches cruzando desde el
            // norte
            crossingFromNorth = true;

            // Si el coche viene del sur
        } else {
            // Esperamos indefinidamente si hay coches que vienen del norte cruzando el
            // puente
            while (crossingFromNorth) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // Cuando ya no estamos esperando, entonces es que hay coches cruzando desde el
            // sur
            crossingFromSouth = true;
        }

        // Además llevamos un contador de la cantidad de coches que hay en el puente, ya
        // que el puente puede ser cruzado por varios coches a la vez siempre que vengan
        // de la misma dirección
        crossingCars++;

        // Imprimimos la información sobre el coche que está cruzando
        System.out.println(carName + " cruzando el puente.");
    }

    /**
     * 
     * @param carName
     * @param direction
     */
    public synchronized void exitBridge(String carName, String direction) {
        // Restamos uno a la cantidad de coches que hay cruzando en este momento
        crossingCars--;

        // Imprimimos la información sobre el coche que está saliendo del puente
        System.out.println(carName + " ha salido del puente.");

        // Si ya no queda ningún coche en el puente, reseteamos los monitores
        if (crossingCars == 0) {
            crossingFromNorth = false;
            crossingFromSouth = false;
        }

        // Notificamos a todos los hilos de coches que están esperando
        notifyAll();
    }
}

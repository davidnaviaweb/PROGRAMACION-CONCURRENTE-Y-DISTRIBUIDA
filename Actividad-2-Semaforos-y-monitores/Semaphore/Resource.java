package Semaphore;

import java.util.concurrent.Semaphore;

class Resource {
    private Semaphore semaphore;
    private int size;
    private int unitsAvailable;

    public Resource(int size) {
        // Inicializamos el semáforo binario
        this.semaphore = new Semaphore(1);

        // Populamos el tamaño total y las unidades disponibles
        this.size = size;
        this.unitsAvailable = size;
    }

    /**
     * 
     * @return
     */
    public int getSize() {
        return size;
    }

    /**
     * 
     * @param r
     * @return
     */
    public boolean reserve(int r) {
        // Inicializamos el valor de retorno, que indicará al proceso si ha podido
        // obtener las unidades solicitadas
        boolean acquired = false;

        try {
            // Bloqueamos el semáforo
            semaphore.acquire();

            // Si tenemos unidades disponibles suficientes para adjudicar
            if (r <= unitsAvailable) {
                // Restamos las unidades adjudicadas a la cantidad de unidades disponibles
                unitsAvailable -= r;
                System.out.println(Thread.currentThread().getName() + " ha reservado " + r + " unidades del recurso. Quedan " + unitsAvailable + " unidades disponibles");

                // Y seteamos el valor de retorno a True
                acquired = true;

            } else {
                // En caso contrario, no hay unidades disponibles suficientes, así que sólo se
                // imprime un mensaje con la información
                String message = unitsAvailable == 0 ? "No quedan unidades disponibles"
                        : "Sólo quedan " + unitsAvailable + " unidades disponibles";

                System.out.println(
                        Thread.currentThread().getName() + " no puede reservar " + r + " unidades. " + message);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Por último, liberamos el semáforo
            semaphore.release();
        }

        // Y devolvemos si se han podido adjudicar las unidades solicitadas
        return acquired;
    }

    /**
     * 
     * @param l
     */
    public void release(int l) {
        try {
            // Bloqueamos el semáforo
            semaphore.acquire();

            // Devolvemos las unidades adjudicadas a la cantidad de unidades disponibles
            unitsAvailable += l;

            // Imprimimos un mensaje con la información
            System.out.println(Thread.currentThread().getName() + " ha liberado " + l
                    + " unidades del recurso. Ahora hay " + unitsAvailable + " unidades disponibles");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // Por último, liberamos el semáforo
            semaphore.release();
        }
    }
}
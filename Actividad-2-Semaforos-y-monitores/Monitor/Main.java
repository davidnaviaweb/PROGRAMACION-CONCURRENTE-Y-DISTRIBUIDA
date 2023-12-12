package Monitor;

import java.util.Random;

public class Main {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Generamos el puente, que contiene el monitor
        Bridge bridge = new Bridge();
        Random random = new Random();

        // Generamos 10 hilos en los que cada cochecruzará el puente en una dirección aleatoria
		for (int i=1 ; i<=10 ; i++){
            // Obtenemos una dirección aleatoria
            String direction = generateRandomDirection(random);

            // Creamos un hilo para el nuevo coche
            (new Thread(new Car(bridge, direction), "Coche del " + direction + " número " + i)).start();

            // Esperamos entre 1 y 5 segundos antes de generar el siguiente hilo de coche
            try {
                Thread.sleep(random.nextLong(1000, 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 
     * @return
     */
    public static String generateRandomDirection(Random random) {
        String[] directions = {"norte", "sur"};
        int index = random.nextInt(directions.length);
        return directions[index];
    }
}

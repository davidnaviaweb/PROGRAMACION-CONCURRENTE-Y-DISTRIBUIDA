package Semaphore;

import java.util.Random;

public class Main {
    /**
     * 
     * @param args
     */
    public static void main(String[] args) {
        // Inicializamos el contenedor de recursos con 5 unidades disponibles
        System.out.println("Iniciando contenedor de recursos con 5 unidades");
        Resource recurso = new Resource(5);  

        // Generamos un random, que usaremos luego para pausar los hilos
        Random random = new Random();

        // Generamos 10 procesos distintos que competirán por los recursos
		for (int i=1 ; i<=10 ; i++){

            // Generamos un hilo con el proceso y arrancamos
            (new Thread(new Process(recurso), "Proceso " + i)).start();
            
            // Esperamos entre 1 y 5 segundos para lanzar el siguiente hilo
            try {
                Thread.sleep(random.nextLong(1000, 5000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

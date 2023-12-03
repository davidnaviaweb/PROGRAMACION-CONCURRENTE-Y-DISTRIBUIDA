package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        // Creamos un nuevo socket en el puerto 2505
        try (Socket socket = new Socket("localhost", 2505)) {

            // Construir los componentes de lectura y escritura
            BufferedReader serverInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter clientOutput = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader userInputReader = new BufferedReader(new InputStreamReader(System.in));
            String serverMessage;

            // Bucle principal para manejar la conexión con el servidor
            outerWhile: while (true) {
                // Bucle de espera para recibir y manejar el mensaje desde el servidor
                while ((serverMessage = serverInput.readLine()) != null && serverMessage != "EXIT") {
                    // Si obtenemos la respuesta de cierre, salimos del bucle principal
                    if (serverMessage.equals("EXIT")) {
                        break outerWhile;
                    }
                    // Si obtenemos la respuesta de fin de respuesta, salimos del bucle de espera
                    if (serverMessage.equals("ENDMESSAGE")) {
                        break;
                    }
                    // Si no, seguimos imprimiendo líneas de la respuesta
                    System.out.println(serverMessage);
                }

                // Pedimos al usuario que introduzca una opción
                System.out.print("Elige una opción y pulsa ENTER: ");

                // Leemos la opción elegida
                String userChoice = userInputReader.readLine();

                // Enviar la opción al servidor
                clientOutput.println(userChoice);
            }
        } catch (IOException e) {
            System.out.println("Se produjo un error: " + e.getMessage());
        }
    }
}

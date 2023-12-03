package Server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Server {

	public static void main(String[] args) {
		// Creamos un nuevo socket para el servidor en el puerto 2505
		try (ServerSocket serverSocket = new ServerSocket(2505)) {
			System.out.println("Servidor corriendo en el puerto 2505.");

			// Bucle principal del servidor
			while (true) {
				// Aceptar una nueva conexión de cliente
				Socket clientSocket = serverSocket.accept();
				System.out.println("Nuevo cliente conectado: " + clientSocket);

				// Crear un nuevo hilo para manejar el cliente conectado
				Thread clientThread = new Thread(() -> handleClient(clientSocket));
				clientThread.start();
			}

		} catch (IOException e) {
			// Manejar excepciones
			System.out.println("Error en el servidor: " + e.getMessage());
		}
	}

	/**
	 * Método para manejar cada cliente conectado
	 * 
	 * @param clientSocket
	 */
	private static void handleClient(Socket clientSocket) {
		try {
			// Construir los componentes de lectura y escritura
			BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			PrintWriter serverOutput = new PrintWriter(clientSocket.getOutputStream(), true);

			// Mensaje de bienvenida
			serverOutput.println("¡Bienvenido a LibroChat!\n");
			serverOutput.println("Elige un libro y podrás leer algunas páginas...\n");

			// Bucle principal para procesar la respuesta del cliente
			while (true) {
				
				// Enviar menú de opciones
				serverOutput.println("1. El Quijote\n2. Cien años de soledad\n3. La Regenta\n4. Lazarillo de Tormes\n5. La Celestina\n6. Cerrar");
				
				// Enviar mensaje de terminación de la salida
				serverOutput.println("ENDMESSAGE");

				// Recibimos el input desde el cliente
				String clientChoice = clientInput.readLine();
				if (clientChoice == null) {
					break;
				}

				// Procesamos la respuesta y enviamos
				String response = processClientChoice(clientChoice);
				serverOutput.println(response);
			}

		} catch (IOException e) {
			// Manejar excepciones
			System.out.println("Error: " + e.getMessage());
		} finally {
			// Cerrar la conexión con el cliente
			try {
				clientSocket.close();
				System.out.println("Conexión cerrada con el cliente: " + clientSocket);
			} catch (IOException e) {
				System.out.println("Error cerrando la conexión con el cliente: " + e.getMessage());
			}
		}
	}

	/**
	 * Método para procesar las elecciones del cliente
	 * 
	 * @param choice
	 * @return
	 */
	private static String processClientChoice(String choice) {
		String response;

		switch (choice) {
			case "1":
				response = "\nEl Quijote, la obra maestra de Miguel de Cervantes.\n\nAquí tienes:\n\n";
				return response + getFileContent("quijote.txt");
			case "2":
				response = "\nCien años de soledad, máximo exponente del realismo mágico.\n\nAquí tienes:\n\n";
				return response + getFileContent("cien.txt");
			case "3":
				response = "\nLa Regenta, la gran novela del XIX español.\n\nAquí tienes:\n\n";
				return response + getFileContent("regenta.txt");
			case "4":
				response = "\nEl lazarillo de Tormes, la obra cumbre de la picaresca española.\n\nAquí tienes:\n\n";
				return response + getFileContent("lazarillo.txt");
			case "5":
				response = "\nLa Celestina, uno de los mejores ejemplos de comedia humanística.\n\nAquí tienes:\n\n";
				return response + getFileContent("celestina.txt");
			case "6":
				return "Gracias por usar LibroChat.\n\nAdiós\nEXIT";
			default:
				return "Opción incorrecta, elige una opción válida.\n\n";
		}
	}

	/**
	 * Método para leer el contenido de un fichero de texto
	 * 
	 * @param fileName
	 * @return 
	 */
	private static String getFileContent(String fileName) {
		// Ruta relativa al fichero
		String filePath = "assets" + File.separator + fileName;

		// Generamos la ruta absoluta al fichero para su lectura
		File file = new File(filePath);
		String absolutePath = file.getAbsolutePath();

		StringBuilder content = new StringBuilder();

		// Leemos el contenido del fichero
		try (BufferedReader fileReader = new BufferedReader(
				new InputStreamReader(new FileInputStream(absolutePath), StandardCharsets.UTF_8))) {
			String line;
			while ((line = fileReader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (IOException e) {
			return "Error leyendo archivo: " + e.getMessage();
		}

		// Devolvemos el contenido en formato de cadena
		return content.toString();
	}
}

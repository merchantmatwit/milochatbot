import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatbotClient {
	String host;
	int port;
	String name;

	public ChatbotClient(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		ChatbotClient client = new ChatbotClient("localhost", 1234);

		client.run();
	}

	public void run() {
		try {
			Socket s = new Socket(host, port);
			System.out.println("Milo is online!");

			new Read(s, this).start();
			new Write(s, this).start();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setName(String name) {
		this.name = name;
	}

	String getName() {
		return this.name;
	}

	class Read extends Thread {
		private Socket socket;
		private ChatbotClient client;
		private BufferedReader reader;

		public Read(Socket socket, ChatbotClient client) {
			this.socket = socket;
			this.client = client;

			try {
				InputStream in = socket.getInputStream();
				reader = new BufferedReader(new InputStreamReader(in));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {
			while (true) {
				try {
					String response = reader.readLine();
					System.out.println(response);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	class Write extends Thread {
		private Socket socket;
		private ChatbotClient client;
		private PrintWriter writer;

		public Write(Socket socket, ChatbotClient client) {
			this.socket = socket;
			this.client = client;

			try {
				OutputStream out = socket.getOutputStream();
				writer = new PrintWriter(out, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void run() {

			Scanner s = new Scanner(System.in);
			System.out.print("Enter your name: ");
			String name = s.nextLine();
			client.setName(name);
			writer.println(name);

			String message = "";
			while (true) {
				message = s.nextLine();
				writer.println(message);
				if (message.toLowerCase().contains("quit") || (message.toLowerCase().contains(("bye")))) {
					break;
				} else {
					message = s.nextLine();
					writer.println(message);
				}
			}
			try {
				socket.close();
				writer.close();
				System.exit(0);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}

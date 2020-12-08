import java.io.*;
import java.lang.reflect.Array;
import java.net.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.lang.Math;

class ChatbotServer {

	private int port;
	private ArrayList<User> users = new ArrayList<>();
	private ArrayList<String> messages = new ArrayList<>();

	ChatbotServer(int port) {
		this.port = port;
	}

	public static void main(String argv[]) throws Exception {
		// Creates socket on port 1234
		ChatbotServer server = new ChatbotServer(1234);
		server.run();

	}

	public void run() {
		try {
			ServerSocket s = new ServerSocket(port);
			System.out.println("Waiting for connection...");

			while (true) {
				Socket connection = s.accept();
				System.out.println("Client connected.");
				User user = new User(connection, this);
				users.add(user);
				user.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void addMessage(String message) {
		messages.add(message);
		if (messages.size() > 6) {
			messages.remove(0);
		}
	}

	void broadcastOnly(String message, User inputUser) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i) == inputUser) {
				users.get(i).sendMessage(message);
			}
		}
	}

	void broadcast(String message, User inputUser) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i) != inputUser) {
				users.get(i).sendMessage(message);
			}
		}
	}

	void disconnectUser(String name, User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i) == user) {
				broadcast(new String(name + " has left the chat."), users.get(i));
				addMessage(new String(name + " has left the chat."));
				users.remove(i);
				System.out.println(name + " has left the chat.");

			}
		}
	}

	static class User extends Thread {
		private Socket s;
		private ChatbotServer server;
		private PrintWriter write;

		public User(Socket s, ChatbotServer server) {
			this.s = s;
			this.server = server;
		}

		public void run() {
			try {
				InputStream inFromClient = s.getInputStream();
				OutputStream outToClient = s.getOutputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(inFromClient));
				write = new PrintWriter(outToClient, true);

				String name = reader.readLine();
				String outputMessage = name + " has joined the chatbot!" + "\r\n";
				server.addMessage(outputMessage);

				System.out.println(outputMessage);
				server.broadcast(outputMessage, this);

				server.broadcastOnly(new String("Milo: Hi " + name
						+ "! My name is Milo, I'll be assisting you in any questions you might have about The Journey of The Ranger and Wizard."),
						this);

				String clientMessage = "You: ";

				while ((!clientMessage.toLowerCase().contains("quit")
						|| (!clientMessage.toLowerCase().contains(("bye"))))) {

					clientMessage = reader.readLine();

					outputMessage = "You: " + clientMessage;

					server.broadcastOnly(outputMessage, this);

					if ((clientMessage.toLowerCase().contains("quit")
							|| (clientMessage.toLowerCase().contains(("bye"))))) {
						server.addMessage(outputMessage);
						server.disconnectUser(name, this);

					} else if (clientMessage.toLowerCase().startsWith("hi")
							|| clientMessage.toLowerCase().contains("hey")
							|| clientMessage.toLowerCase().contains(("hello"))
							|| clientMessage.toLowerCase().contains(("hola"))) {

						outputMessage = ("Milo: Hi " + name + "!" + " What's your question?");
						String outResponse2 = outputMessage + "\r\n";
						server.broadcastOnly(outResponse2, this);
					}

					else if (clientMessage.toLowerCase().contains("main")
							&& clientMessage.toLowerCase().contains(("character"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 3) + 1;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("who")
							&& clientMessage.toLowerCase().contains(("ayla"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 3) + 6;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("who")
							&& clientMessage.toLowerCase().contains(("mo"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 3) + 11;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("power")
							&& clientMessage.toLowerCase().contains(("mo"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 16;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("power")
							&& clientMessage.toLowerCase().contains(("Ayla"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 20;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("ending")) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 24;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);
					}

					else if (clientMessage.toLowerCase().contains("mechanic")
							|| clientMessage.toLowerCase().contains(("control"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 28;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("how")
							&& clientMessage.toLowerCase().contains(("play"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 32;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);
					}

					else if (clientMessage.toLowerCase().contains("option")
							|| clientMessage.toLowerCase().contains(("choice"))
							|| clientMessage.toLowerCase().contains(("branches"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 35;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("many")
							&& clientMessage.toLowerCase().contains(("character"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 39;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if ((clientMessage.toLowerCase().contains("what"))
							&& ((clientMessage.toLowerCase().contains("story")
									|| clientMessage.toLowerCase().contains("game")))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 43;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);
						
					} else if (clientMessage.toLowerCase().contains("long")) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 46;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);
						
					} else if (clientMessage.toLowerCase().contains("many")
							&& clientMessage.toLowerCase().contains(("people"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 50;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);
					}

					else if (clientMessage.toLowerCase().contains("who")
							&& clientMessage.toLowerCase().contains(("play"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 53;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("where")
							&& clientMessage.toLowerCase().contains(("place"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 57;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("when")
							&& clientMessage.toLowerCase().contains(("place"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 60;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("game")
							&& clientMessage.toLowerCase().contains(("name"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 2) + 63;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("title")) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 67;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("different")
							&& clientMessage.toLowerCase().contains(("character"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 70;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("made")
							&& clientMessage.toLowerCase().contains(("game"))) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 1) + 73;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else if (clientMessage.toLowerCase().contains("joke")) {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 3) + 76;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					} else {
						String[] responses = readArray("responses.txt");
						int randomNumber = (int) (Math.random() * 4) + 81;
						String outResponse2 = responses[randomNumber] + "\r\n";
						server.broadcastOnly(outResponse2, this);

					}

				}

			}

			catch (Exception e) {

			}

		}

		void sendMessage(String message) {
			write.println(message);
		}

		public static String[] readArray(String file) {
			int lineCounter = 0;
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				try {
					while (br.readLine() != null) {
						lineCounter++;
					}
				} catch (IOException e) {

					System.out.println("Read in 1");
				}
				String[] responses = new String[lineCounter];

				BufferedReader br2 = new BufferedReader(new FileReader(file));

				for (int i = 0; i < responses.length; i++) {

					try {
						responses[i] = br2.readLine();
					} catch (IOException e) {

						e.printStackTrace();
					}
				}
				return responses;
			} catch (FileNotFoundException e) {

			}
			return null;

		}

	}

}

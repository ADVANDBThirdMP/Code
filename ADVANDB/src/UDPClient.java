import java.io.*;
import java.net.*;
import java.util.ArrayList;

import com.mysql.jdbc.SocksProxySocketFactory;
import com.mysql.jdbc.StandardLoadBalanceExceptionChecker;

class UDPClient {
	private DBConnect db = new DBConnect();

	private static DatagramPacket receivePacket;

	static DatagramSocket clientSocket;

	private DataInputStream in;

	private DatagramPacket sendQueryInDataGramPacket;
	
	public UDPClient() {

	}

	public void handShakeAndGetQuery(String query) throws Exception {
		System.out.println("App started");
		// Gets input from user
		

		// Makes socket used to send
		clientSocket = new DatagramSocket();

		// Too whom it is sent
		InetAddress IPAddress = InetAddress.getByName("localhost");
		byte[] sendQueryToServer = new byte[1024];
		byte[] receiveData = new byte[1024];

		
		
		
		
		// loop para paulit ulit
		// do {

		// converts string to bytes
		sendQueryToServer = query.getBytes();

		// Send it to server
		sendQueryInDataGramPacket = new DatagramPacket(sendQueryToServer, sendQueryToServer.length, IPAddress, 9876);
		clientSocket.send(sendQueryInDataGramPacket);

		// Waits for reply/acknowledgement from server
		receivePacket = new DatagramPacket(receiveData, receiveData.length);

		try {

			//passes receivePacket
			commitQuery();

		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			System.out.println("Server did not accept handshake, aborting transaction");

		}

		// } while (!sentence.equals("stop"));

		clientSocket.close();
	}

	public DataInputStream commitQuery() throws IOException {

		clientSocket.setSoTimeout((int) 5000);

		//for handshake
		try {
			clientSocket.receive(receivePacket);
			String handshake = new String(receivePacket.getData());

			if (receivePacket.getData() != null) {
				System.out.println("Server accepted handshake:" + handshake);
			}
			System.out.println("Server is up");
		} catch (Exception e) {
			System.out.println("Server is down");
		}

		//for size of queried table row count
		try {
			clientSocket.receive(receivePacket);
			String queriedTableRowCount = new String(receivePacket.getData());

			if (receivePacket.getData() != null) {
				System.out.println("Size" + queriedTableRowCount);
			}
			System.out.println("Server is up");
		} catch (Exception e) {
			System.out.println("Server is down");
		}

		//for the queried table
		try {
			clientSocket.receive(receivePacket);
			if (receivePacket.getData() != null) {

				ByteArrayInputStream queriedTableInBytes = new ByteArrayInputStream(receivePacket.getData());
				in = new DataInputStream(queriedTableInBytes);
//
//				while (in.available() > 0) {
//					String element = in.readUTF();
//					System.out.println(element);
//				}
			}
			System.out.println("Server is up");

		} catch (Exception e) {
			System.out.println("Server is down");
		}
		return in;

	}
}
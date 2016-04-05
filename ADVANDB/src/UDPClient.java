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
	private DataInputStream in1;


	private DatagramPacket sendQueryInDataGramPacket;
	private String queriedTableColumnCount;
	private String handShake;
	
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
	//	private String nothing;


	public void commitQuery() throws IOException {

//		clientSocket.setSoTimeout((int) 5000);

		//for handshake
		try {
			clientSocket.receive(receivePacket);
			handShake = new String(receivePacket.getData());
			System.out.println("shit");


			if (receivePacket.getData() != null) {
				System.out.println("Server accepted handshake:" + handShake);
			}
			System.out.println("Server is up");
		} catch (Exception e) {
			System.out.println("Server is down");
		}

		//for size of queried table column count
		try {
			clientSocket.receive(receivePacket);
			queriedTableColumnCount = new String(receivePacket.getData()/*, 0, receivePacket.getLength()*/);

			
			
			if (receivePacket.getData() != null) {
				System.out.println("Size" + queriedTableColumnCount);
			}
			
		} catch (Exception e) {
			System.out.println("Server is down");
		}
		//for the queried table column names
		try {
			clientSocket.receive(receivePacket);
			if (receivePacket.getData() != null) {

				ByteArrayInputStream queriedTableColumnNamesInBytes = new ByteArrayInputStream(receivePacket.getData());
				in1 = new DataInputStream(queriedTableColumnNamesInBytes);
//
//				while (in.available() > 0) {
//					String element = in.readUTF();
//					System.out.println(element);
//				}
			}

		} catch (Exception e) {
			System.out.println("Server is down");
		}
		//for the queried table
		try {
			clientSocket.receive(receivePacket);
			if (receivePacket.getData() != null) {

				ByteArrayInputStream queriedTableInBytes = new ByteArrayInputStream(receivePacket.getData());
				in1 = new DataInputStream(queriedTableInBytes);
//
//				while (in.available() > 0) {
//					String element = in.readUTF();
//					System.out.println(element);
//				}
			}

		} catch (Exception e) {
			System.out.println("Server is down");
		}
		
		
	}
	
	public DataInputStream getTable(){
		return in;
	}


	public int getColumnCount(){
		String str = queriedTableColumnCount.replaceAll("\\D+","");
		return Integer.parseInt(str);
	}
	
	
	public String getHandShake(){
		return handShake;
	}
	
	public DataInputStream getTableColumnNames(){
		return in1;
	}
}
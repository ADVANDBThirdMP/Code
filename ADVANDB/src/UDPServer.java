import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

class UDPServer{

	private DBConnect db = new DBConnect();
	private DatagramSocket serverSocket;
	private byte[] receiveData = new byte[1024];
	private byte[] sendResultSet = new byte[1024];
	private byte[] sendHandShake = new byte[1024];
	private byte[] sendColumnCount = new byte[1024];
	
	private DatagramPacket receivePacket;

	public UDPServer() throws Exception{
		// Creates a socket for receiving packets
		serverSocket = new DatagramSocket(9876);
		receivePacket = new DatagramPacket(receiveData, receiveData.length);
		OpenServer();
	}
	
	public void OpenServer() throws Exception{
		while(true)
		{
			
			System.out.println("shit1");
			// Created socket receives packet and execute query
			serverSocket.receive(receivePacket);
			
			System.out.println("shit2");
			String queryFromClient = new String(receivePacket.getData());
			ResultSet resultSet = db.executeQuery(queryFromClient);

			// Using received packet I get address and port number
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			// Send back sentence as acknowledgement/reply
			String sendBackHandshake = "I accepted handshake";
			sendHandShake = sendBackHandshake.getBytes();

			ResultSetMetaData metadata = resultSet.getMetaData();
			int numberOfColumns = metadata.getColumnCount();

			ArrayList<String> arrayList = new ArrayList<String>();
			while (resultSet.next()) {
				int i = 1;
				while (i <= numberOfColumns) {
					arrayList.add(resultSet.getString(i++));
				}
			}

			// gives back the resultset to client
			ResultSetMetaData rsmd = resultSet.getMetaData();

			String columnCount = Integer.toString(rsmd.getColumnCount());
			sendColumnCount = columnCount.getBytes();

		
			// write to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			for (String element : arrayList) {
				out.writeUTF(element);
			}
			byte[] bytes = baos.toByteArray();

			DatagramPacket returnHandShake = new DatagramPacket(sendHandShake, sendHandShake.length, IPAddress, port);
			serverSocket.send(returnHandShake);

			DatagramPacket returnSizeResultSetArrayinBytes = new DatagramPacket(sendColumnCount, sendColumnCount.length,
					IPAddress, port);
			serverSocket.send(returnSizeResultSetArrayinBytes);

			DatagramPacket returnResultSetArrayinBytes = new DatagramPacket(bytes, bytes.length, IPAddress, port);
			serverSocket.send(returnResultSetArrayinBytes);


		}
	}

	}

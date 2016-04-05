import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

class UDPServer {

	private DBConnect db = new DBConnect();
	private DatagramSocket serverSocket;
	private byte[] receiveData = new byte[1024];
	private byte[] sendResultSet = new byte[1024];
	private byte[] sendHandShake = new byte[1024];
	private byte[] sendColumnCount = new byte[1024];
	private byte[] sendModel = new byte[1024];


	private String nothing;
	private DatagramPacket receivePacket;

	public UDPServer() throws Exception {
		// Creates a socket for receiving packets
		serverSocket = new DatagramSocket(9876);
		receivePacket = new DatagramPacket(receiveData, receiveData.length);
		OpenServer();
	}

	public void OpenServer() throws Exception {
		while (true) {

			// Created socket receives packet and execute query
			serverSocket.receive(receivePacket);

			String queryFromClient = new String(receivePacket.getData(), 0, receivePacket.getLength());

			// queryFromClient = "Select max(id) from hpq_alp";
			ResultSet resultSet = db.executeQuery(queryFromClient);

			System.out.println("result in udp server" + resultSet);

			// Using received packet I get address and port number
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			// Send back sentence as acknowledgement/reply
			String sendBackHandshake = "I accepted handshake";
			sendHandShake = sendBackHandshake.getBytes();

			

			// gives back the resultset to client
			ResultSetMetaData rsmd = resultSet.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			

			// resultset = cachedrowsetimpl
			CachedRowSetImpl cas = new CachedRowSetImpl();
			cas.populate(resultSet);
			
			Model model = new Model();
			ArrayList<String> columnNames = new ArrayList<String>();

			for(int i = 1; i<=numberOfColumns; i++){
				columnNames.add(rsmd.getColumnName(i));
			}
			
			model.setCas(cas);
			model.setColumnNames(columnNames);
			
			Object object = (Object) model;
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(object);
			
			out.toByteArray();
			
			
			sendColumnCount = null;
			String columnCount = Integer.toString(rsmd.getColumnCount());

			
			
			
			sendColumnCount = (columnCount.getBytes());
			//dito nagawa yung dump na di elegant


			sendModel = out.toByteArray();


			
			DatagramPacket returnHandShake = new DatagramPacket(sendHandShake, sendHandShake.length, IPAddress, port);
			serverSocket.send(returnHandShake);

			DatagramPacket returnSizeResultSetArrayinBytes = new DatagramPacket(sendColumnCount, sendColumnCount.length,
					IPAddress, port);
			serverSocket.send(returnSizeResultSetArrayinBytes);

			DatagramPacket returnModel = new DatagramPacket(sendModel, sendModel.length, IPAddress, port);
			serverSocket.send(returnModel);
			
		
			
			
				
			
			


			
			
			
		}
	}

}

//ResultSetMetaData metadata = resultSet.getMetaData();
//int numberOfColumns = metadata.getColumnCount();
//
//ArrayList<String> arrayList = new ArrayList<String>();
//while (resultSet.next()) {
//	int i = 1;
//	while (i <= numberOfColumns) {
//		arrayList.add(resultSet.getString(i++));
//	}
//}
//
//// write to byte array
//ByteArrayOutputStream baos = new ByteArrayOutputStream();
//DataOutputStream out = new DataOutputStream(baos);
//for (String element : arrayList) {
//	out.writeUTF(element);
//}
//
//
//
//byte[] bytes = baos.toByteArray();
//
//
//
//ArrayList<String> columnNames = new ArrayList<String>();
//for(int i = 1; i <= numberOfColumns; i++){
//	columnNames.add(metadata.getColumnName(i));
//}
//
//ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
//DataOutputStream out1 = new DataOutputStream(baos1);
//for (String element : columnNames) {
//	out1.writeUTF(element);
//}
//
//byte[] bytes1 = baos1.toByteArray();
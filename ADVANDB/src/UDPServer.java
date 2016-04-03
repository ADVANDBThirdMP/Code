import java.io.*;
import java.net.*;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

class UDPServer {
	public static void main(String args[]) throws Exception {
		DBConnect db = new DBConnect();
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendResultSet = new byte[1024];
		byte[] sendHandShake = new byte[1024];
		byte[] sendColumnCount = new byte[1024];

		while (true) {
			// Creates a socket for receiving packets
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			// Created socket receives packet and execute query
			serverSocket.receive(receivePacket);
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
			        while(i <= numberOfColumns) {
			            arrayList.add(resultSet.getString(i++));
			        }
			}

			
			// gives back the resultset to client
			ResultSetMetaData rsmd = resultSet.getMetaData();

			String columnCount = Integer.toString(rsmd.getColumnCount());
			sendColumnCount = columnCount.getBytes();

//			// The column count starts from 1
//			for (int i = 1; i <= columnCount; i++){
//				if(resultSet.next()){
//					sendResultSet = resultSet.getBytes(rsmd.getColumnName(i));
//					DatagramPacket returnResult = new DatagramPacket(sendResultSet, sendResultSet.length, IPAddress, port);
//					serverSocket.send(returnResult);
//					}
//			
//			}
			
//			CachedRowSet queryResult = new CachedRowSetImpl();
//			queryResult.populate(resultSet); 
//			
//			
//			queryResult.getByte(numberOfColumns);
//			
			// write to byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(baos);
			for (String element : arrayList) {
			    out.writeUTF(element);
			}
			byte[] bytes = baos.toByteArray();
			
			DatagramPacket returnHandShake = new DatagramPacket(sendHandShake, sendHandShake.length, IPAddress, port);
			serverSocket.send(returnHandShake);
			
			DatagramPacket returnSizeResultSetArrayinBytes = new DatagramPacket(sendColumnCount,sendColumnCount.length, IPAddress, port);
			serverSocket.send(returnSizeResultSetArrayinBytes);
			

			DatagramPacket returnResultSetArrayinBytes = new DatagramPacket(bytes, bytes.length, IPAddress, port);
			serverSocket.send(returnResultSetArrayinBytes);
			
	
			
		
//			DatagramPacket returnResultSetArray = new DatagramPacket(, IPAddress, port);
//			serverSocket.send(returnResultSetArray);
			
			
		}
	}
}
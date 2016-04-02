import java.io.*; 
import java.net.*; 
class UDPServer
{    
	public static void main(String args[]) throws Exception       
	{          
		DBConnect db = new DBConnect();
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)                
		{
			//Creates a socket for receiving packets
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);    
			
			//Created socket receives packet
			
			serverSocket.receive(receivePacket);                   
			String queryFromClient = new String( receivePacket.getData());                   
			int count = db.executeQuery(queryFromClient);
			
			
			//Using received packet I get address and port number
			InetAddress IPAddress = receivePacket.getAddress();                   
			int port = receivePacket.getPort();                   
			
			//Send back sentence as acknowledgement/reply
			String sendBackHandshake = "I accepted handshake";                  
			
			sendData = sendBackHandshake.getBytes();     
			
			//Send acknowledgement to whom pack is from
			DatagramPacket sendHandshakeMessage = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendHandshakeMessage);   
			
			
			//gives back the result to client
			String result = Integer.toString(count);
			
			sendData = result.getBytes();
			
			
			DatagramPacket returnResult = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(returnResult); 
			
		}       
	} 
}
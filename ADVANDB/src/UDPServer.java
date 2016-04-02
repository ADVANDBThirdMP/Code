import java.io.*; 
import java.net.*; 
class UDPServer
{    
	public static void main(String args[]) throws Exception       
	{          
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(true)                
		{
			//Creates a socket for receiving packets
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);    
			//Created socket receives packet
			serverSocket.receive(receivePacket);                   
			String sentence = new String( receivePacket.getData());                   
			System.out.println("RECEIVED: " + sentence);
			//Using received packet I get address and port number
			InetAddress IPAddress = receivePacket.getAddress();                   
			int port = receivePacket.getPort();                   
			//Send back sentence as acknowledgement/reply
			String capitalizedSentence = sentence.toUpperCase();                  
			capitalizedSentence.concat(" Pogi si glenn");
			sendData = capitalizedSentence.getBytes();     
			//Send acknowledgement to whom pack is from
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);                
		}       
	} 
}
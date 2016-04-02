import java.io.*; 
import java.net.*; 
class UDPClient 
{    
	public static void main(String args[]) throws Exception    
	//o 3o
	{       
		//Gets input from user
				BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));     
				
				//Makes socket used to send 
				DatagramSocket clientSocket = new DatagramSocket();       
				
				//Too whom it is sent
				InetAddress IPAddress = InetAddress.getByName("10.100.202.219");       
				byte[] sendData = new byte[1024];       
				byte[] receiveData = new byte[1024];   
				
				String sentence; 
				
				
				//loop para paulit ulit
				do{
				sentence = inFromUser.readLine();
				
				//converts string to bytes
				sendData = sentence.getBytes();       
				
				//Send it to server
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);       
				clientSocket.send(sendPacket);       
				
				//Waits for reply/acknowledgement from server
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);       
				clientSocket.receive(receivePacket);     
				
				String handshake = new String(receivePacket.getData());  
				
				//not null yes handshake PROCEED
				if(receivePacket.getData() != null)
				{
					System.out.println("Server accepted handshake:" + handshake);      

				}
				//null no handshake ABORT
				else if(receivePacket.getData() == null){
					System.out.println("Server did not accept handshake:" + handshake);      
				}
				
				}while(!sentence.equals("stop"));
				

				
				clientSocket.close();   
			
				
				
				//Comment    
	}  // Pogi si brandon
	
	

}
import java.net.*;
import java.io.*;
import java.sql.Timestamp;
import java.util.Random;

public class Client {

    int clockCounter=0;
    String sendParameter = "send";
    String receiveParameter ="receive";
    int receivedOffset =0;

    private int randomCall() throws IOException, ClassNotFoundException {
        int call = new Random().nextInt(100000);
       return call % 3;
    }

    private void clockIncrement(String errorIndicator) {
        int random = new Random().nextInt(5);
        if(random == 4 && errorIndicator.equalsIgnoreCase("Y")){
            clockCounter = clockCounter + 100;
        }else {
            clockCounter++;
        }
    }

    private void send(Socket socket, String poId, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        //System.out.println("Send Event Generated");
        clockCounter++;
        int encryptedClockValue = EncryptionHelper.encryptMessage(clockCounter);
        String sendEvent = poId + "@" + sendParameter + "@" + clockCounter;
        //System.out.println(sendEvent);
        out.writeObject(sendEvent);
        out.flush();
        receivedOffset = (Integer) in.readObject();
        //System.out.println("receieved offset:" + receivedOffset);
        clockCounter = clockCounter + receivedOffset + 1;
        
    }

    private void receive(Socket socket, String poId, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
        //System.out.println("receive Event Generated");
        String receiveEvent = poId + "@" + receiveParameter + "@" + "";
        //System.out.println(receiveEvent);
        out.writeObject(receiveEvent);
        out.flush();
        receivedOffset = (Integer) in.readObject();
        //System.out.println("receieved offset:" + receivedOffset);
        clockCounter = clockCounter + receivedOffset + 1;

    }


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        int port = 2010;
        String poId = args[0]; // process object is given at runtime as argument
        String isFaulty = args[1];

        Client client = new Client();
		long beginTime = System.currentTimeMillis();

        while (true) {
            int random = client.randomCall();
            //System.out.println("Random : " + random);
            if(random == 0){
                client.clockIncrement(isFaulty);
                continue;
            }
            Socket socket = new Socket("10.234.136.56", port);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            if(random == 1 ){
                client.send(socket, poId, in, out);
            }
            else if(random == 2){
                client.receive(socket, poId, in, out);
            }
            //System.out.println("Client Event Finished");
            out.close();
            in.close();
            System.out.println( "Client-" + poId + "; " + (System.currentTimeMillis() - beginTime)/1000 + "; " + client.clockCounter);
            socket.close();
        }
    }


}
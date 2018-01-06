import java.io.*;
import java.net.*;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MasterThread extends Thread{

    static final int TOTAL_NO_PO = 4;
   
    private final AtomicInteger masterClockCounter;
    private static int poClock1 = 0;
    private static int poClock2 = 0;
    private static int poClock3 = 0;
    private static int poClock4 = 0;
    private static int poOffSet1 = 0;
    private static int poOffSet2 = 0;
    private static int poOffSet3 = 0;
    private static int poOffSet4 = 0;

    Socket socket;


    public MasterThread(Socket clientSocket,
                        AtomicInteger masterClockCounter){
        this.masterClockCounter = masterClockCounter;
        this.socket = clientSocket;
    }



    private void randomCall() throws IOException, ClassNotFoundException {
        int call = new Random().nextInt(3);
        switch (call) {
            case 0:
                clockIncrement();
            default:
                break;
        }
    }

    synchronized void clockIncrement() {
        masterClockCounter.getAndIncrement();
    }

    synchronized void send(Socket socket, int poOffset, ObjectOutputStream out) throws IOException {
        //System.out.println("offset sent :" + poOffset);
        out.writeObject(poOffset);
        out.flush();
        
    }

    synchronized void receive(Socket socket, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {

        String receivedMessage = ((String) in.readObject());
        String[] receivedParameters = receivedMessage.split("@");
        String poId = receivedParameters[0];
        String eventType = receivedParameters[1];


        if(eventType.equalsIgnoreCase("receive")){
        	
        	if(poId.equalsIgnoreCase("1")) {
        		send(socket, poOffSet1, out);
        		//System.out.println("RECEIVE**** " + poId + " :: " + poOffSet1);
        		poOffSet1 = 0;
        	} else if(poId.equalsIgnoreCase("2")) {
        		send(socket, poOffSet2, out);
        		//System.out.println("RECEIVE**** " + poId + " :: " + poOffSet2);
        		poOffSet2 = 0;
        	} else if(poId.equalsIgnoreCase("3")) {
        		send(socket, poOffSet3, out);
        		//System.out.println("RECEIVE**** " + poId + " :: " + poOffSet3);
        		poOffSet3 = 0;
        	} else if(poId.equalsIgnoreCase("4")) {
        		send(socket, poOffSet4, out);
        		//System.out.println("RECEIVE**** " + poId + " :: " + poOffSet4);
        		poOffSet4 = 0;
        	}
        	
        	/*System.out.println("RECEIVE AFTER pClocks----");
        	System.out.println( "1" + " :: " + poOffSet1);
        	System.out.println( "2" + " :: " + poOffSet2);
        	System.out.println( "3" + " :: " + poOffSet3);
        	System.out.println( "4" + " :: " + poOffSet4);*/
        	
        }

        if(eventType.equalsIgnoreCase("send")){
            int poClock = Integer.parseInt(receivedParameters[2]);
            int decryptedClockValue = EncryptionHelper.decryptMessage(poClock);
            int sum =0;
            
            if(poId.equalsIgnoreCase("1")) {
        		poClock1 = poClock;
        		//System.out.println("SEND**** " + poId + " :: " + poClock1);
        	} else if(poId.equalsIgnoreCase("2")) {
        		poClock2 = poClock;
        		//System.out.println("SEND**** " + poId + " :: " + poClock2);
        	} else if(poId.equalsIgnoreCase("3")) {
        		poClock3 = poClock;
        		//System.out.println("SEND**** " + poId + " :: " + poClock3);
        	} else if(poId.equalsIgnoreCase("4")) {
        		poClock4 = poClock;
        		//System.out.println("SEND**** " + poId + " :: " + poClock4);
        	}
            
            
           sum = (poClock1 + poClock2 + poClock3 + poClock4 + masterClockCounter.get());

            int average = (sum + masterClockCounter.get()) / (TOTAL_NO_PO + 1);

            // adjust the master clock to correct value
            masterClockCounter.set(average);
            //System.out.println("Master Clock SET :: " + average);

            poOffSet1 = average - poClock1;
            poOffSet2 = average - poClock2;
            poOffSet3 = average - poClock3;
            poOffSet4 = average - poClock4;
            
            if(poId.equalsIgnoreCase("1")) {
        		//System.out.println("SEND**** " + poId + " :: " + poOffSet1);
        		send(socket, poOffSet1, out);
        		poClock1 = poClock1 + poOffSet1 + 1;
        		poOffSet1 = 0;
        	} else if(poId.equalsIgnoreCase("2")) {
        		//System.out.println("SEND**** " + poId + " :: " + poOffSet2);
        		send(socket, poOffSet2, out);
        		poClock2 = poClock2 + poOffSet2 + 1;
        		poOffSet2 = 0;
        	} else if(poId.equalsIgnoreCase("3")) {
        		//System.out.println("SEND**** " + poId + " :: " + poOffSet3);
        		send(socket, poOffSet3, out);
        		poClock3 = poClock3 + poOffSet3 + 1;
        		poOffSet3 = 0;
        	} else if(poId.equalsIgnoreCase("4")) {
        		//System.out.println("SEND**** " + poId + " :: " + poOffSet4);
        		send(socket, poOffSet4, out);
        		poClock4 = poClock4 + poOffSet4 + 1;
        		poOffSet4 = 0;
        	}
            
            /*System.out.println("SEND AFTER pClocks----");
        	System.out.println( "1" + " :: " + poOffSet1);
        	System.out.println( "2" + " :: " + poOffSet2);
        	System.out.println( "3" + " :: " + poOffSet3);
        	System.out.println( "4" + " :: " + poOffSet4);*/
          
            
            
        }

        //System.out.println("End :" + receivedMessage);
    }


    public void run(){
            try {
                randomCall();
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                receive(socket, in , out);
                System.out.println( "Master; " + (System.currentTimeMillis() - MasterServer.beginTime)/1000 + " ; " + this.masterClockCounter);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

}
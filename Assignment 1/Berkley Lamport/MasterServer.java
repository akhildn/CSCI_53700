import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by anaya on 10/22/2017.
 */
public class MasterServer {
	public static long beginTime;
    public static void main(String args[]) throws IOException, InterruptedException {
        AtomicInteger masterClockCounter = new AtomicInteger(0);
		beginTime = System.currentTimeMillis();
        int port = 2010;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("connecting to Client:" + port);
        ExecutorService service = Executors.newFixedThreadPool(1);
        while(true){
            Thread.sleep(1000);
            Socket socket = serverSocket.accept();
            service.execute( new MasterThread(socket,
                    masterClockCounter
            ));
        }
    }
}

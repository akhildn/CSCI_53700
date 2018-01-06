import java.rmi.*;
import java.rmi.server.*;

public class Server{
	private static final long serialVersionUID = 1L;

	public static void main(String args[]){
		try{
		// Create RMI server with rmiregistry on port 2010	
		System.out.println("Creating a RMI Server!");
        ServerController controller = new ServerControllerImpl();
		Naming.rebind("//10.234.136.55:2010/Server", controller);
		System.out.println("Server Ready!");
		}catch (Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
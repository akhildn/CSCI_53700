import java.rmi.*;
import java.rmi.server.*;
import java.io.File;

// Interface Implementation

public class ServerControllerImpl extends UnicastRemoteObject implements ServerController {
	public ServerControllerImpl()throws RemoteException{}
	
	@Override
	public int connect() throws RemoteException {
		return 1;
	}
	@Override
	public String getPath() throws RemoteException {
		String path = System.getProperty("user.dir");
		return path;
	}
	@Override
	public String echo(String message) throws RemoteException {
	    System.out.println("Message from Client : " + message);
		return message;
	}
	@Override
	public String isFileExists(String fname) throws RemoteException {
        File f = new File(fname);
        if(f.exists()) {
            return "Found";
        }
        return "Not Found";
	}
	@Override
	public int[] sortList(int[] a) throws RemoteException {

        int temp;
        int size = a.length;
        for(int i=0; i < size; i++) {
            for (int j = 1; j < (size - i); j++) {
                if (a[j - 1] > a[j]) {
                    temp = a[j - 1];
                    a[j - 1] = a[j];
                    a[j] = temp;
                }
            }
        }
         return a;
	}
	@Override
	public int[][] mMultiplication(int[][] a, int[][] b, int row, int col) throws RemoteException {
		int[][] rM = new int[row][col];
		for(int i=0; i<row; i++){
		    for(int j=0; j<col; j++){
		        for (int k=0; k<col; k++){
                    rM[i][j] += a[i][k] * b[k][j];
                }
            }
		}
	    return rM;
	}

}
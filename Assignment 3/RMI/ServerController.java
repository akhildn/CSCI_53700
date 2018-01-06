import java.rmi.*;
import java.rmi.server.*;

// RMI interface for server
public interface ServerController extends java.rmi.Remote{
	// abstract classes
	public int connect() throws RemoteException;
	public String getPath() throws RemoteException;
	public String echo(String message) throws RemoteException;
	public String isFileExists(String fname) throws RemoteException;
	public int[] sortList(int[] a) throws RemoteException;
	public int[][] mMultiplication(int[][] a, int[][] b, int row, int col) throws RemoteException;
}
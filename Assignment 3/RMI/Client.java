import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

public class Client{
	public static void main(String args[]){

		int ch;
		int[] a;
		int[][] m1,m2;

		try {

		Scanner in = new Scanner(System.in);
		
		// connects to server 
		ServerController controller= (ServerController) Naming.lookup("//10.234.136.55:2010/Server");
		int connectionStatus = controller.connect();
		
		// checks if connection was succesfull 
        if(connectionStatus == 1){
          	System.out.println("Connected to server....");
        }

        do{
			// MENU
			System.out.println("1. Get Path");
			System.out.println("2. Echo");
			System.out.println("3. File Check");
			System.out.println("4. Sort List");
			System.out.println("5. Matrix Multiplication");
			System.out.println("6. Performace");
			System.out.println("7. Exit");
			System.out.println("Enter you choice");
			ch = in.nextInt();
			switch (ch){
				// Get path 
				case 1:
					System.out.println("Path is :" + controller.getPath());
					break;
				// ECHO	
				case 2:
					System.out.println("Enter a message");
					String message = in.next();
					System.out.println("Echo from server :" + controller.echo(message));
					break;
				// File Status	
				case 3:
					System.out.println("Enter a file name");
					String fname = in.next();
					System.out.println("File Status :" + controller.isFileExists(fname));
					break;
				// Sort array	
				case 4:
					System.out.println("enter list size");
					int size = in.nextInt();
					a= new int[size];
					System.out.println("enter list elements");
					for(int i=0; i<size; i++) {
						a[i] = in.nextInt();
					}
					System.out.println("Sorted list:");
					int[] r = new int[size];
					r=controller.sortList(a);
					for(int i=0; i<size; i++) {
						System.out.print(" " + r[i]);
					}
					System.out.println();
					break;
				// Matrix multiplication 	
				case 5:
					System.out.println("enter number of rows:");
					int row = in.nextInt();
					System.out.println("enter number of columns:");
					int col = in.nextInt();

					m1 = new int[row][col];
					m2 = new int[row][col];

					for(int k=1; k<3; k++) {
						System.out.println();
						System.out.println("Enter values for Matrix" + k);
						for (int i = 0; i < row; i++) {
							for (int j = 0; j < col; j++) {
								System.out.println("Enter value for [" + i + "] [" + j + "]:");
								if(k==1){
									m1[i][j] = in.nextInt();
								}else{
									m2[i][j] = in.nextInt();
								}
							}
						}
					}

					int[][] rM = controller.mMultiplication(m1, m2, row, col);
					System.out.println("Resultant Matrix:");

					for (int i = 0; i < row; i++) {
						for (int j = 0; j < col; j++) {
							System.out.print(rM[i][j] + ", ");
						}
						System.out.println();
					}
					break;
					
				case 6:
					System.exit(0);

			}
		}while(true);


		
		} catch(Exception e){
			System.out.println("Exception: " + e.getMessage());
			e.printStackTrace();
		}
		
		
}
}
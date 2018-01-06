CSCI-537 : Assignment-1

Server ip : 10.234.136.56

How to compile: 
-use makefile to compile, just type in make in the directroy where .java are present

How to execute: 
java MasterServer, for master object
java Client 1 n, for process object "1" is argument passed for client id and "n" arugment
		 passed to state if that process object is fauly or not
java Client 2 y
java Client 3 n
java Client 4 n

Notes:
The current code has equal probability for all 3 events on process object. But were tested 
with unequal probablities and results are stated in report.

Client.java -> process object
MasterThread.java -> master object
MasterServer.java -> used to establish concurrent connections between master and process objects
EncryptionHelper.java -> helps in encrypting process objects logical clock before it sends
 
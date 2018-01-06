CSCI 53700 Assignment - 2 

How to Run: 
- login into tesla or one of 6 machines
- create an directory, name it assignment2
- copy add.x into assignment2 directory
- to in the assignment 2 directory by typing : cd assginment2
- to generate rpc stub files type : rpcgen -a -C add.x
- Now files will be generated to check if were generated type : ls 
- To complie the generated files type : make -f Makefile.add
- Now to run the server type : ./add_server 
- Now to run the client type : ./add_client <host_ip>, same on other machines 
- client will display menu with functionalities select accordingly. 



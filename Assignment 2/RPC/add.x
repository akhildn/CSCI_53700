struct sortList{
	int listSize;
	int list[100];
	int sortedList[100];
};
struct serverEcho{
	char echo[1000];
};
struct checkFile{
	char fname[1000];
};
struct matrixMultiplication{
	int row;
	int col;
	int matrix1[100];
	int matrix2[100];
	int resultMatrix[100];
};

program RPC_Assignment{
	version RPC_VERS{
			string getPath(void)=1;
			sortList sort(sortList)=2;
			string printEcho(serverEcho)=3;
			string isFileExist(checkFile)=4;
			matrixMultiplication mMultiply(matrixMultiplication)=5;
	}=1;
}=0x31111111;
import java.util.*;

class State {
	char[] board;

	public State(char[] arr) {
		this.board = Arrays.copyOf(arr, arr.length);
	}

	public int getScore() {
		int player1Disk=0;
		int player2Disk=0;
		for(int i =0; i< board.length; i++){
			if(board[i]=='1'){
				++player1Disk;			
			}else if(board[i]=='2'){
				++player2Disk;			
			}		
		}
		
		if(player1Disk>player2Disk){
			return 1;		
		}else if(player1Disk<player2Disk){
			return -1;		
		}
		return 0;
	}

	public boolean isTerminal() {

		// TO DO: determine if the board is a terminal node or not and return boolean
		// use get successors in the place of iterating through the entire board and then looking for empty space
		/*
		 * check if there are any 0s
		 * if not return true
		 * if yes return false
		 * and check if the two players have any valid moves
		 * if not return true*/
		if(this.getSuccessors('1').length == 0 && this.getSuccessors('2').length == 0)
			return true;

		//Else the baord is not a terminal node
		return false;

	}
	public boolean isOpponent(char currPlayer, char check) {
		//System.out.println("player and board " + currPlayer +" "+ check );
		if(currPlayer=='1' && check=='2') {
			return true;
		} else if(currPlayer=='2' && check=='1') {
			return true;
		}
		return false;
	}
	public boolean isinit(char check) {
		if(check=='0') {
			return true;
		}
		return false;
	}
	public boolean isBoundVertical(int give) {
		if(give<0 || give>=16) {
			return false;
		}
		return true;
	}
	public boolean isBoundHor(int give, int row) {
		if(give<0 || give>=16) {
			return false;
		}
		if(give<row*4) {
			return false;
		}
		if(give>=(row+1)*4) {
			return false;
		}
		return true;
	}
//**************************************************************************
//                  BOUND CODE
//**************************************************************************
public boolean isBoundHorR (int i){
    	
    	if(i+1 != 4 && i+1 !=8 && i+1 !=12 && i+1 !=16){
    		return true;
    	}
    	else
    		return false;
    	
    }
    
    
public boolean isBoundHorL (int i){
    	
    	if(i-1 != -1 && i-1 !=3 && i-1 !=7 && i-1 !=11){
    		return true;
    	}
    	else
    		return false;
    	
    }
    
    public boolean isBoundVerticalD(int i){
    	
    	if(i+4 <= 15)
    		return true;
    	else 
    		return false;
    	
    }
    
  public boolean isBoundVerticalU(int i){
    	
    	if(i-4 >= 0)
    		return true;
    	else 
    		return false;
    	
    }
  
  
  
  public boolean downRightBound(int i){
	  
	  if(isBoundHorR(i) && isBoundVerticalD(i)){
		  return true;
	  }
	  
	  return false;
	  }
  
  
  public boolean upperLeftBound(int i){
	  
	  if(isBoundHorL(i) && isBoundVerticalU(i)){
		  return true;
	  }
	  
	  return false;
	  }
	  
  
public boolean downLeftBound(int i){
	  
	  if(isBoundHorL(i) && isBoundVerticalD(i)){
		  return true;
	  }
	  
	  return false;
	  }
  
public boolean upperRightBound(int i){
	  
	  if(isBoundHorR(i) && isBoundVerticalU(i)){
		  return true;
	  }
	  
	  return false;
	  }
//**************************************************************************
//                  BOUND CODE
//**************************************************************************
	/*public State legalMove(char player, char[] board, int legalPos, int give) {
    	State result = new State(board);
    	for(int i =0; i< board.length; i++) {
    		if(i==legalPos) {
    			board[i] = player;
    			boolean reach = true;
    			int j=0;
    			while(reach) {
    				if(give==1) {
    					if(isBoundHor(i+j, i/4) && board[i+j]!=player && board[i+j]!=0) {
    						board[i+j]=player;
    					}else if(isBoundHor(i+j, i/4) && board[i+j]==0) {
    						reach = false;
    					}
    				}else if(give ==2) {

    					if(isBoundVertical(i+j) && board[i+j]!=player && board[i+j]!=0) {
    						board[i+j]=player;
    					}else if(isBoundHor(i+j, i/4) && board[i+j]==0) {
    						reach = false;
    					}

    				}
    			}
    		}
    	}
    	return result;
    }*/
	public State legalMove(char player, char [] board, int legalPos, int give) {
		State result = new State(board);
		int j=0;

		return result;

	}
	public State[] getSuccessors(char player) {

		// TO DO: get all successors and return them in proper order
		//first detect all the legal moves
		//for the specified symbol in the arg

		//basically three steps are to be followed for this function
		//#1 find the legal moves
		//#2 place the given symbol in all the legal positions
		//#3 after placing the symbols in their respective legal positions we have
		// to flip the 2s(is if the given symbol is 2) contigious col or row or diagonal
		//and then return the state

		//define legal moves here:
		//we can only place the 1 row wise or coloumn wise or diagnal wise adjacent 2 which is beside a 1 only along which the 1 
		// we place is valid with 2


		//horizontally next element is i+1
		//vertically next element is i+4
		//diagonally next element is (i+4) +1; that is go vertical and one horizontal
		//three if statements in the for loop
		// one if there is an immediate horizontal opposite elem. 
		// one of there is an immediate vertical opposite elem.
		// vice versa for diagonal elem as well

		// now inside one of these if statements 
		// the if statements themselves give information to check for the next horzontal or vertical or diagonal elem.
		// if diagonal for example then check if the next diagonal elem to this diag elem is same as the symbol we would 
		//like to place.
		
		ArrayList<State> successorsList = new ArrayList<State>();

		//System.out.println(this.getBoard());
		
		for(int i =0; i< this.board.length; i++) {
			boolean anySuccessors = false;
			char [] temp = new char[16];
			//System.out.println("**************************************************************");
			//System.out.println("for loop starts itr# " + i + "the value of player here --> " + player);
	//		System.out.println("**************************************************************");
			System.arraycopy(this.board,0, temp, 0, this.board.length);

			//right horizontal pos
		

			if( isinit(this.board[i]) && isBoundHorR(i) && isBoundHorR(i+1) && isOpponent(player,this.board[i+1])) {
				//System.out.println("sample check1!");
				//check next horizontal
				//System.out.println("Entered right Horizontal condition ");
				int j=i+2;
				int count=0;
				int counter=0;
				//System.out.println(isBoundHor(j,i/4));
				while(isBoundHorR(j-1) && count==0 && counter==0) {
					//System.out.println(board[j]);
					if(this.board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					++j;
				}
				j=i+1;
				if(count>0) {
				//	System.out.println("A part of a legal state exists Horizontal RIGHT");
					anySuccessors = true;
					//temp[i]=player;
					while(isBoundHorR(j) && board[j]!='0') {
						temp[j]=player;
						++j;
					}
				//	System.out.println("resulting temp array ");
				//	for(int h =0; h< temp.length; h++) {
				//		System.out.print(temp[h]);
				//	}System.out.println();
				//System.out.println("###############################################################");
				}
			}
			//left horizontal pos
			

			if(isinit(this.board[i]) && isBoundHorL(i) && isBoundHorL(i-1) && isOpponent(player,this.board[i-1])) {
				//System.out.println("Entered left Horizontal condition ");
				//check next horizontal
				int j=i-2;
				int count=0;
				int counter =0;
				while(isBoundHorL(j+1) && count==0 && counter==0) {
					if(i==13){
				//		System.out.print("board -->" + this.board[j] + " at j--> " + j);				
					}
					//System.out.println();
					if(this.board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					--j;
					//if(i==13)
					//System.out.println("count: " +count);
				}
				j=i-1;
				if(count>0) {
				//	System.out.println("A part of a legal state exists Horizontal LEFT");
					//temp[i]=player;
					anySuccessors = true;
					while(isBoundHorL(j) && this.board[j]!='0') {
						temp[j]=player;
						--j;
					}
				//	System.out.println("resulting temp array ");
				//	for(int h =0; h< temp.length; h++) {
				//		System.out.print(temp[h]);
				//	}System.out.println();
				//	System.out.println("###############################################################");
				}
			}
			//vertical below opponent check
			if(isinit(this.board[i]) && isBoundVerticalD(i) && isOpponent(player,board[i+4])) {
				//next vertical below
				//System.out.println("Entered below vertical condition ");
				int j=i+8;
				int count=0;
				int counter=0;
				while(isBoundVerticalD(j-4) && count==0 && counter==0) {
					if(this.board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					j = j+4;
				}
				j=i+4;
				if(count>0) {
				//	System.out.println("A part of a legal state exists vertical BELOW");
					//temp[i]=player;
					anySuccessors = true;
					while(isBoundVerticalD(j) && this.board[j]!='0') {
						temp[j]=player;
						j = j+4;
					}
				//	System.out.println("resulting temp array ");
				//	for(int h =0; h< temp.length; h++) {
				//		System.out.print(temp[h]);
				//	}
				//	System.out.println();
				//	System.out.println("###############################################################");

				}
			}
			//vertical above
			if(i==12){
				//System.out.println("player at 12 is " + player);			
			}
			/*if(i==13){
				System.out.println(isOpponent(player, this.board[i-4]));
				System.out.println("value --> " + this.board[i-4]);
				System.out.println("player value here " + player);			
			}*/
			if(isinit(this.board[i]) && isBoundVerticalU(i) && isOpponent(player,this.board[i-4])) {
				//next vertical above
				//System.out.println("Entered above vertical condition ");
				int j=i-8;
				int count=0;
				int counter =0;
				while(isBoundVerticalU(j+4) && count==0 && counter==0) {
					if(this.board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					j=j-4;
				}
				j=i-4;
				if(count>0) {
					//System.out.println("A part of a legal state exists vertical ABOVE");

					//temp[i]=player;
					anySuccessors = true;
					while(isBoundVerticalU(j) && this.board[j]!='0') {
						temp[j]=player;
						j=j-4;
					}
				//	System.out.println("resulting temp array ");
				//	for(int h =0; h< temp.length; h++) {
				//		System.out.print(temp[h]);
				//	}System.out.println();
				//	System.out.println("###############################################################");

				}
			}
			//diagonal lower right opponent check
			

			if(isinit(this.board[i]) && downRightBound(i) && isOpponent(player,board[(i+4)+1])) {

				//System.out.println("Entered lower diagonal right condition ");
				int j=i+10;
				int count=0;
				int counter=0;
				//doubt j-5 ^^^^^^^^^^^^
				while(downRightBound(j-5) && count==0 && counter==0) {
					if(this.board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					j=j+5;
				}
				j=i+5;
				if(count>0) {
					//temp[i]=player;
				//	System.out.println("A part of a legal state exists lower diagonal right");
					anySuccessors = true;
					while(downRightBound(j) && this.board[j]!='0') {
						temp[j]=player;
						j=j+5;
					}
				//	System.out.println("resulting temp array ");
				//	for(int h =0; h< temp.length; h++) {
				//		System.out.print(temp[h]);
				//	}System.out.println();
				//	System.out.println("###############################################################");

				}
			}
			//diag lower left
		

			if(isinit(this.board[i]) && downLeftBound(i) && isOpponent(player,this.board[(i+4)-1])) {

				//System.out.println("Entered lower diagonal left condition ");
				int j=i+6;
				int count=0;
				int counter=0;
				while(downLeftBound(j-3) && count==0 && counter==0) {

					if(this.board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					j=j+3;
				}
				j=i+3;
				if(count>0) {

					//temp[i]=player;
				//	System.out.println("A part of a legal state exists lower diagonal left");
					anySuccessors = true;
					while(downLeftBound(j) && this.board[j]!='0') {
						temp[j]=player;
						j=j+3;
					}
				//	System.out.println("resulting temp array ");
				//	for(int h =0; h< temp.length; h++) {
				//		System.out.print(temp[h]);
				//	}System.out.println();
				//	System.out.println("###############################################################");

				}
			}
			//upper left
		

			if(isinit(this.board[i]) && upperLeftBound(i) &&
					isOpponent(player,this.board[(i-4)-1])) {
				//next upper left
				//System.out.println("Entered upper left diagonal condition ");
				int j=i-10;
				int count=0;
				int counter =0;
				while(upperLeftBound(j+5) && count==0 && counter==0) {
					if(this.board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					j=j-5;
				}
				j=i-5;
				if(count>0) {
					//System.out.println("A part of a legal state exists upper left diagonal");
					//temp[i]=player;
					anySuccessors = true;
					while(upperLeftBound(j) && board[j]!='0') {
						temp[j]=player;
						j=j-5;
					}
					/*System.out.println("resulting temp array ");
					for(int h =0; h< temp.length; h++) {
						System.out.print(temp[h]);
					}System.out.println();*/
				//System.out.println("###############################################################");

				}
			}
			//upper right
			

			if(isinit(this.board[i]) && upperRightBound(i) && isOpponent(player,board[i-3])) {
				//System.out.println("Entered upper right diagonal condition ");
				int j=i-6;
				int count=0;
				int counter=0;
				while(upperRightBound(j+3) && count==0 && counter==0) {
					if(board[j]==player) {
						++count;
					}
					if(this.board[j]=='0'){
						++counter;
					}
					j=j-3;
				}
				j=i-3;
				if(count>0) {

					//temp[i]=player;
				//	System.out.println("A part of a legal state exists upper right diagonal");
					anySuccessors = true;
					while(upperRightBound(j) && this.board[j]!='0') {
						temp[j]=player;
						j=j-3;
					}
					//System.out.println("resulting temp array ");
					/*for(int h =0; h< temp.length; h++) {
						System.out.print(temp[h]);
					}*/
					//System.out.println("###############################################################");


				}
			}
			
			if(anySuccessors){
				//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
				temp[i]= player;
				State newSuc = new State(temp);
				successorsList.add(newSuc);
				//System.out.println(newSuc.getBoard());
				//System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
			}
			
		}
		State[] successors = new State[successorsList.size()];
		
			
		for(int i =0; i< successorsList.size(); i++) {
			successors[i] = successorsList.get(i);
			//System.out.println(successorsList.get(i).getBoard());
		}

			
		return successors;
	}

	public void printState(int option, char player) {
		if(option == 1){
			State[] currSucc = this.getSuccessors(player);
			if(currSucc.length == 0 && !this.isTerminal()){
				System.out.println(this.getBoard());
			}
			if(currSucc.length == 0 && this.isTerminal())
				return;

			if(currSucc.length != 0){
				for(int i=0; i<currSucc.length; i++){
					System.out.println(currSucc[i].getBoard());
				}
			}
		}
		if(option == 2){
			if(this.isTerminal()){
				System.out.println(this.getScore());
			}
			else{	
				System.out.println("non-terminal");
			}

		}
		if(option == 3){
			int utilValue =0;
			utilValue = Minimax.run(this, player);
			System.out.println(utilValue);
			System.out.println(Minimax.miniMaxCnt);
		}
		if(option == 4 || option == 6){
			if(this.isTerminal())
				return;
			if(this.getSuccessors(player).length == 0 && !this.isTerminal())
				System.out.println(this.getBoard());
			if(this.getSuccessors(player).length != 0){
				State[] currSucc = this.getSuccessors(player);				
				System.out.println(currSucc[0].getBoard());
			}
		}
		if(option == 5){
			int utilValue = Minimax.run_with_pruning(this, player);
			System.out.println(utilValue);
			System.out.println(Minimax.alphaBetaCnt);
		}
	}

	public String getBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 16; i++) {
			builder.append(this.board[i]);
		}
		return builder.toString().trim();
	}

	public boolean equals(State src) {
		for (int i = 0; i < 16; i++) {
			if (this.board[i] != src.board[i])
				return false;
		}
		return true;
	}
}

class Minimax {
	static int miniMaxCnt = 0;
	static int alphaBetaCnt = 0;
	static State permState = null;
	private static int max_value(State curr_state) {
		++miniMaxCnt;
		State temp = null; 
		int max = Integer.MIN_VALUE, sameVal = Integer.MIN_VALUE;
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}
		else{	
			State successors [] = curr_state.getSuccessors('1');
			
			if(successors.length > 0){
				for(int i=0 ; i<successors.length ; i++){
					max = Math.max(max, min_value(successors[i]));
					if( max > sameVal){
						sameVal = max;
						 temp = successors[i];
					}
				}
				permState = temp;
				return max;
			}
			else{
				int min =  min_value(curr_state);
				if(min > sameVal){
					temp = curr_state;
				}
				 permState = temp;
				 return min;
			}
		}
	}

	private static int min_value(State curr_state) {
		++miniMaxCnt;
		int b = Integer.MAX_VALUE;
		int copy = b;
		State obj = null;
     
		
		if(curr_state.isTerminal())
		{
			return curr_state.getScore();
		}
		else
		{
			
			State successors [] = curr_state.getSuccessors('2');
			
			if(successors.length > 0)
			{
			
				for(int i=0 ; i<successors.length ; i++)
				{
					State type = successors[i];
					b = Math.min(b, max_value(type));
					
					if(b < copy)
					{
						copy = b;
						obj = type;
					}
				}
				
				temp = obj;
				return b;
			}
			else
			{
				int value =  max_value(curr_state);
				if(value < copy)
				{
					obj = curr_state;
				}
				
				temp = obj;
				return value;
				
			}
			
		}
		
	}

	private static int max_value_with_pruning(State curr_state, int alpha, int beta) {
		++alphaBetaCnt;
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}

		State[] currSucc = curr_state.getSuccessors('1');

		
		if(currSucc.length == 0 && curr_state.isTerminal() == false){
			alpha = Math.max(alpha, min_value_with_pruning(curr_state, alpha, beta));
		}

		for(int i=0; i< currSucc.length; i++){
			alpha = Math.max(alpha, min_value_with_pruning(currSucc[i], alpha, beta));
			if(alpha >= beta)
				return beta;
		}

		return alpha;
	}

	private static int min_value_with_pruning(State curr_state, int alpha, int beta) {
		++alphaBetaCnt;	
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}

		State[] currSucc = curr_state.getSuccessors('2');

		if(currSucc.length == 0 && curr_state.isTerminal() == false){
			beta = Math.min(beta, max_value_with_pruning(curr_state, alpha, beta));
		}

		for(int i=0; i< currSucc.length; i++){
			beta = Math.min(beta, max_value_with_pruning(currSucc[i], alpha, beta));
			if(alpha >= beta)
				return alpha;
		}

		return beta;
	}

	public static int run(State curr_state, char player) {

		int value = 0;
	
		if(player == '1'){
			value = max_value(curr_state);
		}
		else if(player == '2'){
			value = min_value(curr_state);	
		}
		return value;
	}

	public static int run_with_pruning(State curr_state, char player) {
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int value = 0;
		if(player == '1'){
			value = max_value_with_pruning(curr_state, alpha, beta);
		}
		else if(player == '2'){
			value= min_value_with_pruning(curr_state, alpha, beta);
		}
		return value;
	}
}

public class Reversi {
	public static void main(String args[]) {
		if (args.length != 3) {
			System.out.println("Invalid Number of Input Arguments");
			return;
		}
		int flag = Integer.valueOf(args[0]);
		char[] board = new char[16];
		for (int i = 0; i < 16; i++) {
			board[i] = args[2].charAt(i);
		}
		int option = flag / 100;
		char player = args[1].charAt(0);
		if ((player != '1' && player != '2') || args[1].length() != 1) {
			System.out.println("Invalid Player Input");
			return;
		}
		State init = new State(board);
		init.printState(option, player);
	}
}

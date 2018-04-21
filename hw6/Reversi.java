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
		if(this.getSuccessors('1').length == 0 && this.getSuccessors('2').length == 0)
			return true;
		return false;
	}
	public boolean initStateCheck(char check) {
		if(check=='0') {
			return true;
		}
		return false;
	}
	public boolean verticalBoundCheck(int i) {
		if(i<0 || i>=16) {
			return false;
		}
		return true;
	}
	public boolean horizBoundCheck(int i, int row) {
		if(i<0 || i>=16) {
			return false;
		}
		if(i<row*4) {
			return false;
		}
		if(i>=(row+1)*4) {
			return false;
		}
		return true;
	}
	public boolean opponentCheck(char player, char check) {
		if(player=='1' && check=='2') {
			return true;
		} else if(player=='2' && check=='1') {
			return true;
		}
		return false;
	}	
	
	public boolean leftCheck (int i){
    	if(i-1 != -1 && i-1 !=3 && i-1 !=7 && i-1 !=11)
    		return true;
      	return false;
	}
 
    public boolean upCheck(int i){
      	if(i-4 >= 0)
      		return true;
      	return false;
    }
  

    public boolean downCheck(int i){  	
    	if(i+4 <= 15)
    		return true; 
    	return false;
    }
    
	public boolean rightCheck (int i){
	    	if(i+1 != 4 && i+1 !=8 && i+1 !=12 && i+1 !=16)
	    		return true;
	    	return false;
		}
  
  	public boolean lowerRightCheck(int i){
	  if(rightCheck(i) && downCheck(i))
		  return true;
	  return false;
	}
  
  
  	public boolean upperLeftCheck(int i){
	  if(leftCheck(i) && upCheck(i))
		  return true;
	  return false;
	}
	  
  
	public boolean lowerLeftCheck(int i){
	  if(leftCheck(i) && downCheck(i))
		  return true;
	  return false;
	}
  
	public boolean upperRightCheck(int i){
	  if(rightCheck(i) && upCheck(i))
		  return true;
	  return false;
	}
	
	public State[] getSuccessors(char player) {				
		ArrayList<State> successorsList = new ArrayList<State>();

		for(int i=0; i<this.board.length; i++) {
			boolean successors_present = false;
			char[] new_array = new char[16];
			
			System.arraycopy(this.board,0, new_array, 0, this.board.length);

			if( initStateCheck(this.board[i]) && rightCheck(i) && rightCheck(i+1)
			 && opponentCheck(player,this.board[i+1])) {
				int j=i+2;
				int myCount=0;
				int freeCount=0;
				while(rightCheck(j-1) && myCount==0 && freeCount==0) {
					if(this.board[j]==player) {
						++myCount;
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					++j;
				}
				j=i+1;
				if(myCount>0) {
					successors_present = true;
					while(rightCheck(j) && board[j]!='0') {
						new_array[j]=player;
						++j;
					}
				}
			}
			if(initStateCheck(this.board[i]) && leftCheck(i) && leftCheck(i-1) 
				&& opponentCheck(player,this.board[i-1])) {
				int j=i-2;
				int myCount=0;
				int freeCount =0;
				while(leftCheck(j+1) && myCount==0 && freeCount==0) {
					if(i==13){
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					if(this.board[j]==player) {
						++myCount;
					}
					j=j-1;
				}
				j=i-1;
				if(myCount>0) {
					successors_present = true;
					while(leftCheck(j) && this.board[j]!='0') {
						new_array[j]=player;
						j=j-1;
					}
				}
			}
			if(initStateCheck(this.board[i]) && downCheck(i)
			 && opponentCheck(player,board[i+4])) {
				int j=i+8;
				int myCount=0;
				int freeCount=0;
				while(downCheck(j-4) && myCount==0 && freeCount==0) {
					if(this.board[j]==player) {
						++myCount;
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					j = j+4;
				}
				j=i+4;
				if(myCount>0) {
					successors_present = true;
					while(downCheck(j) && this.board[j]!='0') {
						new_array[j]=player;
						j = j+4;
					}

				}
			}
	

			if(initStateCheck(this.board[i]) && 
				upCheck(i) && opponentCheck(player,this.board[i-4])) {
				int j=i-8;
				int myCount=0;
				int freeCount =0;
				while(upCheck(j+4) && myCount==0 && freeCount==0) {
					if(this.board[j]==player) {
						++myCount;
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					j=j-4;
				}
				j=i-4;
				if(myCount>0) {
					successors_present = true;
					while(upCheck(j) && this.board[j]!='0') {
						new_array[j]=player;
						j=j-4;
					}

				}
			}
			

			if(initStateCheck(this.board[i]) && lowerRightCheck(i) 
				&& opponentCheck(player,board[(i+4)+1])) {

				int j=i+10;
				int myCount=0;
				int freeCount=0;
				while(lowerRightCheck(j-5) && myCount==0 && freeCount==0) {
					if(this.board[j]==player) {
						++myCount;
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					j=j+5;
				}
				j=i+5;
				if(myCount>0) {
					successors_present = true;
					while(lowerRightCheck(j) && this.board[j]!='0') {
						new_array[j]=player;
						j=j+5;
					}

				}
			}
			if(initStateCheck(this.board[i]) && lowerLeftCheck(i)
			 && opponentCheck(player,this.board[(i+4)-1])) {

				int j=i+6;
				int myCount=0;
				int freeCount=0;
				while(lowerLeftCheck(j-3) && myCount==0 && freeCount==0) {

					if(this.board[j]==player) {
						++myCount;
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					j=j+3;
				}
				j=i+3;
				if(myCount>0) {

					successors_present = true;
					while(lowerLeftCheck(j) && this.board[j]!='0') {
						new_array[j]=player;
						j=j+3;
					}

				}
			}
			if(initStateCheck(this.board[i]) && upperLeftCheck(i) &&
					opponentCheck(player,this.board[(i-4)-1])) {
				int j=i-10;
				int myCount=0;
				int freeCount =0;
				while(upperLeftCheck(j+5) && myCount==0 && freeCount==0) {
					if(this.board[j]==player) {
						++myCount;
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					j=j-5;
				}
				j=i-5;
				if(myCount>0) {
					successors_present = true;
					while(upperLeftCheck(j) && board[j]!='0') {
						new_array[j]=player;
						j=j-5;
					}
				}
			}
			if(initStateCheck(this.board[i]) && 
				upperRightCheck(i) && opponentCheck(player,board[i-3])) {
				int j=i-6;
				int myCount=0;
				int freeCount=0;
				while(upperRightCheck(j+3) && myCount==0 && freeCount==0) {
					if(board[j]==player) {
						++myCount;
					}
					if(this.board[j]=='0'){
						++freeCount;
					}
					j=j-3;
				}
				j=i-3;
				if(myCount>0) {		
					successors_present = true;
					while(upperRightCheck(j) && this.board[j]!='0') {
						new_array[j]=player;
						j=j-3;
					}
				}
			}
			if(successors_present){
				new_array[i]=player;
				State newSuc = new State(new_array);
				successorsList.add(newSuc);
			}
		}
		State[] successors = new State[successorsList.size()];			
		for(int i =0; i< successorsList.size(); i++) {
			successors[i] = successorsList.get(i);
		}
		return successors;
	}

	public void printState(int option, char player) {
		if(option == 1){
			State[] nxt = this.getSuccessors(player);
			if(nxt.length == 0){ 
				if(this.getSuccessors(player).length == 0 && !this.isTerminal()) {
					System.out.println(this.getBoard());
				}
				if(this.isTerminal()) {
					return;
				}
			}
			if(nxt.length >= 0){
				for(State s : nxt) 
					System.out.println(s.getBoard());
			}
		}
		if(option == 2){
			System.out.println(isTerminal() ? this.getScore() : "non-terminal");
		}
		if(option == 3){
			System.out.println(Minimax.run(this, player));
			System.out.println(Minimax.callCount);
		}

		if(option == 6 || option == 4){
			if(this.isTerminal()) return ;
			if(this.getSuccessors(player).length == 0) {
				if(!this.isTerminal())
					System.out.println(this.getBoard());
			}
			if(this.getSuccessors(player).length != 0){
				State[] allnextstates = this.getSuccessors(player);
				State best_state = allnextstates[0]; //selecting the first successor
				System.out.println(best_state.getBoard());
			}
		}
		if(option == 5){
			System.out.println(Minimax.run_with_pruning(this, player));
			System.out.println(Minimax.callCount);
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
	static int callCount = 0;

	private static int max_value(State curr_state) {
		callCount++;
		int max = Integer.MIN_VALUE;
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		} else if(curr_state.getSuccessors('1').length > 0) {	
			State[] next_states = curr_state.getSuccessors('1');
			for(State next_state : next_states) 
				max = Math.max(max, min_value(next_state));
			return max;
		} else {
			max = min_value(curr_state);
			return max;
		}
	}

	private static int min_value(State curr_state) {
 		callCount++;
		int min = Integer.MAX_VALUE;
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		} else if(curr_state.getSuccessors('2').length > 0) {
			State[] next_states = curr_state.getSuccessors('2');
			for(State next_state : next_states)
				min = Math.min(min, max_value(next_state));
			return min;
		} else {
			min = max_value(curr_state);
			return min;
		}
		
	}

	private static int max_value_with_pruning(State curr_state, int alpha, int beta) {
		callCount++;
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}
		if(curr_state.getSuccessors('1').length == 0 && !curr_state.isTerminal()){
			alpha = Math.max(alpha, min_value_with_pruning(curr_state, alpha, beta));
		}	
		State[] nxt = curr_state.getSuccessors('1');	
		for(State s : nxt){
			alpha = Math.max(alpha, min_value_with_pruning(s, alpha, beta));
			if(alpha >= beta)
				return beta;
		}
		return alpha;
	}

	private static int min_value_with_pruning(State curr_state, int alpha, int beta) {
		callCount++;	
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}
		if(curr_state.getSuccessors('2').length == 0 && !curr_state.isTerminal()){
			beta = Math.min(beta, max_value_with_pruning(curr_state, alpha, beta));
		}
		State[] nxt = curr_state.getSuccessors('2');
		for(State s : nxt){
			beta = Math.min(beta, max_value_with_pruning(s, alpha, beta));
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

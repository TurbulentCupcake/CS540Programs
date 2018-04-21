import java.util.*;


class State {
	char[] board;
	//game theoretic calue global counter = number of min_value calls
	// + max_value calls

	public State(char[] arr) {
		this.board = Arrays.copyOf(arr, arr.length);
	}

	public int getScore() {

		// TO DO: return game theoretic value of the board
		int darkDisks = 0;
		int lightDisks = 0;

		for(Character c : board){
			if(c == '1'){
				darkDisks++;
			}
			else if(c == '2'){
				lightDisks++;
			}

		}

		//If dark wins
		if(darkDisks > lightDisks){
			return 1;
		}

		//If light wins
		else if(darkDisks < lightDisks){
			return -1;
		}

		//For a tie
		else if(darkDisks == lightDisks){
			return 0;
		}
		//Return 0 on a tie
		return 0;
	}

	public boolean isTerminal() {

		// TO DO: determine if the board is a terminal node or not and return boolean
		//Game is terminal node if the board is full, i.e., no '0' in the board,
		//Use num of disks to make it efficient;

		//The game board is also a terminal node if both the players cannot
		//make a legal move
		if(this.getSuccessors('1').length == 0 && this.getSuccessors('2').length == 0)
			return true;

		//Else the baord is not a terminal node
		return false;
	}

	public State move(char player, int row, int col) {
		State new_state;

		char[][] board = convert2D(this.board.clone());

		board[row][col] = player;
		int R = row, C = col;  

		char opponent;
		if(player =='1') {
			opponent = '2';
		}
		else {
			opponent = '1';
		}

		if(row-1 >= 0 && col-1 >= 0 && board[row-1][col-1] == opponent){

			row = row-1;
			col = col-1;

			while(row > 0 && col > 0 && board[row][col] == opponent)
			{
				row--;
				col--;
			}
			if(row >= 0 && col >= 0 && board[row][col] == player) 
			{
				while(row != R-1 && col!= C-1)

					board[++row][++col] = player;
			}
		}

		row=R;
		//Assignign column
		col=C; 

		//Next condition
		if(row-1 >= 0 && board[row-1][col] == opponent){
			row = row-1;

			while(row>0 && board[row][col] == opponent) 
				row--;

			if(row>=0 && board[row][col] == player) {

				while(row!=R-1)

					board[++row][col]=player;

			}
		} 

		row=R; 

		if(row-1 >= 0 && col+1 <= 3 && board[row-1][col+1] == opponent){

			row = row-1; 
			col = col+1;

			while(row > 0 && col < 3 && board[row][col] == opponent)
			{
				row--;
				col++;

			}

			if(row >= 0 && col <= 3 && board[row][col] == player){
				while(row!=R-1 && col!=C+1)

					board[++row][--col] = player;
			}
		}   

		row = R;
		col = C;

		if(col-1 >= 0 && board[row][col-1] == opponent){

			col = col-1;

			while(col>0 && board[row][col] == opponent)
				col--;

			if(col >= 0 && board[row][col] == player){

				while(col!=C-1)
					board[row][++col] = player;
			}
		}

		col=C; 

		if(col+1 <= 3 && board[row][col+1] == opponent){
			col=col+1;

			while(col < 3 && board[row][col] == opponent)
				col++;

			if(col <= 3 && board[row][col] == player) {
				while(col!=C+1)
					board[row][--col] = player;
			}
		}

		col=C; 

		if(row+1<=3 && col-1>=0 && board[row+1][col-1] == opponent){ 

			row=row+1;
			col=col-1;

			while(row<3 && col>0 && board[row][col] == opponent)
			{
				row++;
				col--;

			}

			if(row <= 3 && col >= 0 && board[row][col] == player) {

				while(row!=R+1 && col!=C-1)
					board[--row][++col] = player;

			}
		}

		row=R;
		col=C; 

		if(row+1 <= 3 && board[row+1][col] == opponent){ 

			row=row+1;

			while(row < 3 && board[row][col] == opponent) 
				row++;

			if(row <= 3 && board[row][col] == player){
				while(row!=R+1)
					board[--row][col] = player;

			}
		}

		row=R;

		if(row+1 <= 3 && col+1 <=3 && board[row+1][col+1] == opponent){

			row=row+1;
			col=col+1;

			while(row<3 && col<3 && board[row][col] == opponent)
			{
				row++;

				col++;
			}

			if(row<=3 && col<=3 && board[row][col] == player)while(row!=R+1 && col!=C+1)board[--row][--col] = player;}

		char[] spots = convert1D(board);
		new_state = new State(spots);

		if(new_state.getBoard().equals(this.getBoard())) {

			new_state = null;
		}

		return new_state;
	}

	//Convert board to 2D representation
	public char[][] convert2D(char[] board){
		char[][] result = new char[4][4];
		int curr = 0;
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4;j++) {
				result[i][j] = this.board[curr];
				curr++;
			}
		} 
		return result;
	}

	//Convert back to 1D form
	public char[] convert1D(char[][] board) {
		char[] result = new char[16];
		int index = 0; 
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				result[index] = board[i][j];
				index++;
			}
		}
		return result;
	}

	public boolean exists(ArrayList<Integer> row, ArrayList<Integer> col, int rowVal, int colVal) {
		boolean result = false;
		for(int i = 0 ; i < row.size(); i++) {
			if((row.get(i) == rowVal) && (col.get(i) == colVal)) {
				result = true;
			}
		}
		return result;
	}

	public State[] getSuccessors(char player) {

		// TO DO: get all successors and return them in proper order
		// TO DO: get all successors and return them in proper order
		char[][] board = convert2D(this.board);

		char opponent;
		if(player == '2')
			opponent = '1';
		else 
			opponent = '2';

		ArrayList<State> successors = new ArrayList<State>();

		ArrayList<Integer> row = new ArrayList<Integer>();

		ArrayList<Integer> col = new ArrayList<Integer>();
		//ArrayList<Integer[]> indices = new ArrayList<Integer[]>();

		for(int i = 0;i < 4; ++i){

			for(int j = 0;j < 4; ++j){

				if(board[i][j] == opponent){

					int I = i, J = j; 
					//upper left diag
					if(i-1 >= 0 && j-1 >= 0 && board[i-1][j-1] == '0'){  

						i = i+1; 
						j = j+1;

						while(i < 3 && j < 3 && board[i][j] == opponent)
						{
							i++;j++;
						}

						if(i <= 3 && j <= 3 && board[i][j] == player) {

							if(!exists(row,col,I-1,J-1)) {

								row.add(I-1);
								col.add(J-1);


							}
						}
					} 
					i=I;j=J;
					//left
					if(i-1>=0 && board[i-1][j] == '0'){
						i = i+1;

						while(i < 3 && board[i][j] == opponent) 
							i++;
						if(i<=3 && board[i][j] == player) {

							if(!exists(row,col,I-1,J)) {

								row.add(I-1);
								col.add(J);
							}
						}
					} 
					i=I;

					//lower left diag
					if(i-1 >= 0 && j+1 <= 3 && board[i-1][j+1] == '0'){

						i = i+1; j = j-1;

						while(i<3 && j>0 && board[i][j] == opponent)
						{
							i++;j--;
						}
						if(i<=3 && j>=0 && board[i][j] == player) {
							
							if(!exists(row,col,I-1,J+1)) {
								row.add(I-1);
								col.add(J+1);
							}

						}
					}  

					i=I;
					j=J;

					//up
					if(j-1>=0 && board[i][j-1] == '0'){
						j = j+1;

						while(j < 3 && board[i][j] == opponent)
							j++;

						if(j <= 3 && board[i][j] == player) {

							if(!exists(row,col,I,J-1)) {

								row.add(I);
								col.add(J-1);
							}
						}
					}

					j=J;
					//down
					if(j+1 <= 3 && board[i][j+1] == '0'){

						j=j-1;

						while(j > 0 && board[i][j] == opponent)
							j--;

						if(j >= 0 && board[i][j] == player) {

							if(!exists(row,col,I,J+1)) {

								row.add(I);
								col.add(J+1);
							}
						}
					}
					j=J;
					//upper right
					if(i+1<=3 && j-1>=0 && board[i+1][j-1] == '0'){

						i=i-1;
						j=j+1;

						while(i > 0 && j < 3 && board[i][j] == opponent){

							i--;
							j++;
						}

						if(i >= 0 && j <= 3 && board[i][j] == player) {

							if(!exists(row,col,I+1,J-1)) {

								row.add(I+1);
								col.add(J-1);

							}
						}
					}

					i=I;
					j=J;
					//right
					if(i+1 <= 3 && board[i+1][j] == '0'){
						i=i-1;

						while(i > 0 && board[i][j] == opponent) 
							i--;

						if(i >= 0 && board[i][j] == player) {

							if(!exists(row,col,I+1,J)) {

								row.add(I+1);
								col.add(J);
							}
						}
					}
					i=I;
					//lower right diag
					if(i+1 <= 3 && j+1 <=3 && board[i+1][j+1] == '0'){

						i=i-1;
						j=j-1;

						while(i > 0 && j > 0 && board[i][j] == opponent)
						{
							i--;
							j--;
						}

						if(i >= 0 && j >= 0 && board[i][j] == player) {

							if(!exists(row, col, I+1, J+1)) {	

								row.add(I+1);
								col.add(J+1);
							}
						}
					}

					i=I;
					j=J;
				}
			} 
		} 
		//Adding to successors
		for(int i = 0; i < row.size(); i++) {

			successors.add(move(player, row.get(i), col.get(i)));
		}

		//Create an array,
		State[] succsArray = new State[successors.size()];
		//get data in array
		succsArray = successors.toArray(succsArray);

		return succsArray;	
	}


	public void printState(int option, char player) {

		// TO DO: print a State based on option (flag)
		//Print the successors of a state when option == 1
		if(option == 1){

			//Get successors for the players
			State[] successors = this.getSuccessors(player);

			//If the state has no successors but is not a terminal
			//node, print the board itself.
			if(successors.length == 0 && !this.isTerminal()){
				System.out.println(this.getBoard());
			}

			//If there are no valid moves available and the node is 
			//a terminal node
			if(successors.length == 0 && this.isTerminal())
				return;

			//Print the successors in natural reading order
			if(successors.length != 0){

				//Print the states
				for(State state : successors){
					System.out.println(state.getBoard());
				}
			}
		}

		//If option is 2, check whether the board is a terminal node or not
		if(option == 2){

			//Print the game theoretic value of the state, 1 if dark wins, -1 if light wins, 0
			//if it is a tie.
			if(this.isTerminal()){
				System.out.println(this.getScore());
			}
			else{
				//Print non terminal if the state is not a terminal node
				System.out.println("non-terminal");
			}

		}


		//If option is 3, run the minimax algorithm on our board
		if(option == 3){

			//First print the game theoretic value for the given
			//player-board pair
			int gameTheoreticValue = Minimax.run(this, player);
			System.out.println(gameTheoreticValue);
			//Print the number of states explored
			System.out.println(Minimax.counterMinimax);
			
		}

		//If option is four
		if(option == 4 || option == 6){

			//If board is terminal, produce no output
			if(this.isTerminal())
				return;

			//If no move possible, but not terminal, print the board
			if(this.getSuccessors(player).length == 0 && !this.isTerminal())
				System.out.println(this.getBoard());

			//Else print the first successor
			if(this.getSuccessors(player).length != 0){
				State[] successors = this.getSuccessors(player);
				//Print the first move of the player
				System.out.println(successors[0].getBoard());
			}

		}


		//If flag is equal to 5, run the alpha - beta pruning
		//algorithm
		if(option == 5){

			//First print the game theoretic value for the given
			//player-board pair
			int gameTheoreticValue = Minimax.run_with_pruning(this, player);
			System.out.println(gameTheoreticValue);
			//Print the number of states explored
			System.out.println(Minimax.counterPruning);

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

	static int counterMinimax = 0;
	static int counterPruning = 0;

	private static int max_value(State curr_state) {
		// TO DO: implement Max-Value of the Minimax algorithm
		//COunter that increments minimax value by 1, as number of 
		//minimax calls is equal to this counter plus the other counter.
		counterMinimax++; 


		//Check if the current state is a terminal state, if it is, return
		//its terminal value
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}

		int max_value = Integer.MIN_VALUE;
		State[] successors = curr_state.getSuccessors('1');

		if(successors.length == 0 && curr_state.isTerminal() == false){
			max_value = Math.max(max_value,  min_value(curr_state));
		}
		//		else if(successors.length != 0 && curr_state.isTerminal() == false){

		//Get the successors of the current state, as dark always runs first
		//it will call maxValue;


		//Select the successor that returns the biggest value
		for(State state : successors){
			max_value = Math.max(max_value, min_value(state));
		}	
		//		}
		return max_value;
	}

	private static int min_value(State curr_state) {

		// TO DO: implement Min-Value of the Minimax algorithm
		counterMinimax++;

		//Check if the current state is a terminal state, if it is, return
		//its terminal value
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}

		int min_value = Integer.MAX_VALUE;
		//Get the successors of the current state, as light always runs second
		//it will call minValue;
		State[] successors = curr_state.getSuccessors('2');

		if(successors.length == 0 && curr_state.isTerminal() == false){
			min_value = Math.min(min_value, max_value(curr_state));
		}


		//Select the successor that returns the smallest(optimal) value
		for(State state : successors){
			min_value = Math.min(min_value, max_value(state));
		}


		return min_value;
	}

	private static int max_value_with_pruning(State curr_state, int alpha, int beta) {

		// TO DO: implement Max-Value of the alpha-beta pruning algorithm
		counterPruning++;

		//If s is a terminal state, return its game value
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}

		State[] successors = curr_state.getSuccessors('1');

		//If there is no valid move available but it is not a terminal node
		if(successors.length == 0 && curr_state.isTerminal() == false){
			alpha = Math.max(alpha, min_value_with_pruning(curr_state, alpha, beta));
		}

		for(State state : successors){
			alpha = Math.max(alpha, min_value_with_pruning(state, alpha, beta));
			if(alpha >= beta)
				return beta;
		}

		return alpha;
	}

	private static int min_value_with_pruning(State curr_state, int alpha, int beta) {

		// TO DO: implement Min-Value of the alpha-beta pruning algorithm
		counterPruning++;

		//If s is a terminal state, return its game value
		if(curr_state.isTerminal()){
			return curr_state.getScore();
		}

		State[] successors = curr_state.getSuccessors('2');

		if(successors.length == 0 && curr_state.isTerminal() == false){
			beta = Math.min(beta, max_value_with_pruning(curr_state, alpha, beta));
		}

		for(State state : successors){
			beta = Math.min(beta, max_value_with_pruning(state, alpha, beta));
			if(alpha >= beta)
				return alpha;
		}

		return beta;
	}

	public static int run(State curr_state, char player) {

		// TO DO: run the Minimax algorithm and return the game theoretic value
		int result = 0;
		//Calls the max_value part because dark plays first, wants max value
		if(player == '1'){
			//Returns number of times the max function runs
			result = max_value(curr_state);
		}
		//Start at min, as light's turn, and it wants min value and
		//therefore subsequently will call min_value nd return
		else if(player == '2'){
			result = min_value(curr_state);	
		}
		//Return the number of states , i.e., minCounter + max Counter
		//Return minCounter + maxCounter;
		return result;
	}

	public static int run_with_pruning(State curr_state, char player) {

		// TO DO: run the alpha-beta pruning algorithm and return the game theoretic value

		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		int result = 0;
		//If player is '1', run max pruning, else run min pruning
		if(player == '1'){
			result = max_value_with_pruning(curr_state, alpha, beta);
		}
		else if(player == '2'){
			result = min_value_with_pruning(curr_state, alpha, beta);
		}

		return result;
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



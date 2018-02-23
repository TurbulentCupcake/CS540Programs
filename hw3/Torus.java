import java.util.*;
import java.util.HashMap;


class State {
	int[] board;
	State parentPt;
	int depth;

	public State(int[] arr) {
		this.board = Arrays.copyOf(arr, arr.length);
		this.parentPt = null;
		this.depth = 0;
	}

	private int[] swapInts(int[] arr, int row_a, int col_a, int row_b, int col_b) {
		int[] temp_array = new int[arr.length];
		System.arraycopy(arr,0,temp_array,0, arr.length);
		int temp = temp_array[row_a*3 + col_a];
		temp_array[row_a*3 + col_a] = temp_array[row_b*3 + col_b];
		temp_array[row_b*3 + col_b] = temp;
		return temp_array;
	}

	public State[] getSuccessors() {
		
		// TO DO: get all four successors and return them in sorted order

		// create an array of type State that holds the 4 successors
		State[] successors = new State[4];
		State[] new_successors = new State[4];
		List<Integer> temp_successor = new ArrayList<Integer>();

		// find the location of 0
		for(int i = 0 ; i < this.board.length ; i++) { 

			// move the pieces and obtain the required states
			if(this.board[i] == 0) { 

				

				// obtain the row and column of the space
				int row = (int)i/3;
				int col = (int)i%3;
				int new_row, new_col;

				/*for each new array, create a new array through
					positional modification using the newly derived
					indices of new_row and new_col
				*/

				// shift left 
				new_row = row;
				new_col = col - 1 + 3;
				new_col = new_col%3;
/*				System.out.println("new_row = " + new_row + " | new_col = " + new_col);
				System.out.println("-------------------");*/

				successors[0] = new State(swapInts(this.board,row,col,new_row,new_col));

				// shift right
				new_row = row;
				new_col = col + 1 + 3;
				new_col = new_col%3;
				/*System.out.println("new_row = " + new_row + " | new_col = " + new_col);
				System.out.println("-------------------");*/
				successors[1] = new State(swapInts(this.board,row,col,new_row,new_col));
				


				// shift upp
				new_col = col;
				new_row = row - 1 + 3;
				new_row = new_row%3;
/*				System.out.println("new_row = " + new_row + " | new_col = " + new_col);
				System.out.println("-------------------");*/

				successors[2] = new State(swapInts(this.board,row,col,new_row,new_col));


				//shift down 
				new_col = col;
				new_row = row + 1 + 3;
				new_row = new_row%3;
/*				System.out.println("new_row = " + new_row + " | new_col = " + new_col);
				System.out.println("-------------------");
*/
				successors[3] = new State(swapInts(this.board,row,col,new_row,new_col));

				// create a index map for ensuring sorted state placement
				Map<Integer, State> index_map = new HashMap<Integer, State>();

				for(int j = 0 ; j < successors.length ; j++) { 
					index_map.put(Integer.parseInt(successors[j].getBoard().replaceAll("\\s+","")),successors[j]);
				}

				// store the integer keys into another array for sorting
				for( Integer key : index_map.keySet() ) {
					temp_successor.add(key);
				}
				
				// sort the new Array
				Collections.sort(temp_successor);

				// run through the new sorted array and swap state positions based on sorted array
				// set their parent and depth attributes too
				for(int j = 0 ; j < temp_successor.size() ; j++)  {
					new_successors[j] = index_map.get(temp_successor.get(j));
					new_successors[j].depth = this.depth+1; 
					new_successors[j].parentPt = this;
				}

				// once the new successors have been created, break out of the loop
				break;
 
			}
		}




		// return the new succesors
		return new_successors;
	}

	public void printState(int option) {
		
		// TO DO: print a torus State based on option (flag)
		if(option == 1) {
			System.out.println(this.getBoard());
		} else if(option == 2) {
			System.out.println(this.getBoard());
		} else if(option == 3) {
			if(this.parentPt == null) {
				System.out.println(this.getBoard() + " parent 0 0 0 0 0 0 0 0 0 ");
			} else {
				System.out.println(this.getBoard() + " parent " + this.parentPt.getBoard());
			}
		} else if(option == 4) {
			System.out.println(this.getBoard());
		}

		
	}

	public String getBoard() {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			builder.append(this.board[i]).append(" ");
		}
		return builder.toString().trim();
	}

	public boolean isGoalState() {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != (i + 1) % 9)
				return false;
		}
		return true;
	}

	public boolean equals(State src) {
		for (int i = 0; i < 9; i++) {
			if (this.board[i] != src.board[i])
				return false;
		}
		return true;
	}



}





public class Torus {

	// seperate function to deal with when you hit the goal
	public static void hitGoal(State goalState, int goalChecked, List<State> prefix, Stack<State> stack) {

		for(State pre : prefix) {
			pre.printState(1);
		}
		goalState.printState(1);
		System.out.println("Goal-check " + goalChecked);
		int stack_size = 0;
		while(!stack.empty()) { 
			stack_size++;
		}
		System.out.println("Max-stack-size " + stack_size);
		System.exit(0);
	
	}

	public static void main(String args[]) {
		if (args.length < 10) {
			System.out.println("Invalid Input");
			return;
		}
		int flag = Integer.valueOf(args[0]);
		int[] board = new int[9];
		for (int i = 0; i < 9; i++) {
			board[i] = Integer.valueOf(args[i + 1]);
		}
		int option = flag / 100;
		int cutoff = flag % 100;
		if (option == 1) {
			State init = new State(board);
			State[] successors = init.getSuccessors();
			for (State successor : successors) {
				successor.printState(option);
			}
		} else {
			State init = new State(board);
			State goalState = new State(new int[]{1,2,3,4,5,6,7,8,0});
			Stack<State> stack = new Stack<>();
			List<State> prefix = new ArrayList<State>();
			int goalChecked = 0;
			int maxStackSize = Integer.MIN_VALUE;

			if(option == 2 || option == 3 || option == 4 || option == 5) {

				// this option implements depth-limited dfs
				int depth;
				if(option == 4) {
					depth = cutoff;
				} else {
					depth = 0;
				}

				while(true) {

					stack.push(init);

					// refresh prefix for each new iteration of iterative deepening
					if(option == 5) {
						prefix = new ArrayList<State>();
						stack = new Stack<State>();
						stack.push(init);
						System.out.println("depth = " + depth);
					}

					while(!stack.isEmpty()) {
							
							// pop out the current state
							State current = stack.pop();

							// check for goal
							if(current.isGoalState()) {
								// go to hit goal
								hitGoal(current,goalChecked,prefix,stack);

							} else { 
								goalChecked++;
							}


							if(option == 2 ||  option == 3) { 
								current.printState(option);
							}


							// check if its parent is present in the
							// prefix. If it is the first element, then
							// add it to the prefix list.
							if(current.parentPt == null || prefix.size() == 0) {
								prefix.add(current);
							} else {
								// iterate through the prefix list and find the parent
								// of the current state
								for(int i = 0 ; i < prefix.size() ; i++) {

									if(prefix.get(i).equals(current.parentPt)) {

										// once found, remove everyone from i+1 to end of the list
										prefix.subList(i + 1, prefix.size()).clear();

										// add the popped element into the prefix list
										prefix.add(current);
									}
								}

							}

							// this is kinda iffy, but im guessing that since the topmost element 
							// in the stack will be goal checked first, it will be the first one
							// with a unique depth number. So that would be the newest guy in the
							// prefix list. If we increment the depth value everytime we notice 
							// that particular depth once, we wouldn't see the other states
							// with the same depth value.
							if(current.depth == depth && option == 4) {
								// iterate through the list and print the elements
								for(State pre : prefix) {
									pre.printState(option);
								}
								depth++;
							}

							// generate all successive states for the current depth
							State[] successors = current.getSuccessors();
							
							// iterate through all the successors and check if they are present
							// in the prefix list, if not, add them to the stack, else skip
							// THIS WAY IS SUPER CHILDISH PROBZ CHANGE IT UP
							for(State successor : successors) {

								boolean pflag = false;
								// check if the state exists in the prefix
								for( State pre : prefix ) {

									if(pre.equals(successor)) {
										pflag = true;
										break;
									} 

								}

								// if it does not exist, then push into stack
								if(!pflag && successor.depth <= cutoff) {

									stack.push(successor);
								}

							}

						
					}

					// any option other than iterative deepening will have to
					// run until the stack is empty
					if(option != 5) {
						break;
					} else if(option == 5) {
						// if you're using iterative deepening, increase depth. 
						depth++;
					}


				}


			} 
		}
	}
}

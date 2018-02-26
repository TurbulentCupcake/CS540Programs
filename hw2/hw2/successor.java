import java.io.*;
import java.util.*;


public class successor {


	public static int checkConstraints(int JugA, int JugB, int JugC, int a, int b, int c ) {

		if(a > JugA || b > JugB || c > JugC) {
			System.err.println("Current quantities may not exceed jug sizes");
			System.exit(1);
		}

		return 0;

	}

	public static int emptyJugs(int[] capacities, int[] current) {

		int[] capacities_copy = new int[3];
		int[] current_copy = new int[3];

		for(int i = 0; i < current.length; i++) {

			if(current[i] == 0) { 
				continue;
			}
			System.arraycopy(current, 0, current_copy, 0, current.length);
			current_copy[i] = 0;
			if(!Arrays.equals(current,current_copy)) {
				System.out.println(current_copy[0] + " " + current_copy[1] + " " + current_copy[2]);
			}
		}

		return 0;
		
	}

	public static int fillJugs(int[]  capacities, int[] current) {

		int[] capacities_copy = new int[3];
		int[] current_copy = new int[3];

		for(int i = 0; i < current.length; i++) {

			if(current[i] == capacities[i]) { 
				continue;
			}
			System.arraycopy(current, 0, current_copy, 0, current.length);
			current_copy[i] = capacities[i];

			if(!Arrays.equals(current,current_copy)) {
				System.out.println(current_copy[0] + " " + current_copy[1] + " " + current_copy[2]);
			}
		}

		return 0;

	}

	public static int pourJugs(int[] capacities, int[] current) {

		for(int i = 0 ; i < current.length; i++)  {

			if(current[i] == capacities[i]) {
				continue; // nothing to pour from other jugs
			}
/*			System.arraycopy(capacities, 0, capacities_copy, 0, capacities.length);
*/
			// space available in jug
			int R = capacities[i] - current[i];
			int[] capacities_copy = new int[3];
			int[] current_copy = new int[3];

/*			System.out.println("i = " + i);
*/
			for(int j = 0 ; j < current.length;j++) {
		
				if(i == j) {
					continue;
				}
/*				System.out.println("--j = " + j);
*/
				System.arraycopy(current, 0, current_copy, 0, current.length);

/*				System.out.println("diff valus = "+ (R - current_copy[j]));
*/				// if the whole jug can empty its contents into another jug
				if(R - current_copy[j] == 0) {

					current_copy[i] = capacities[i];
					current_copy[j] = 0;

				} else if(R - current_copy[j] < 0) {
					// if another jug has more contents than neccessary to fill up
					// current jug, then only take until we fill current jug.

					current_copy[i] = current_copy[i] + R;
					current_copy[j] = -(R-current_copy[j]);

				} else if(R - current_copy[j] > 0) {

					// if another jug has less than the neccessary to fill up
					// the current jug, then take everything from other jug
					current_copy[i] = current_copy[i] + current_copy[j];
					current_copy[j] = 0;


				}

				if(!Arrays.equals(current,current_copy)) {
					System.out.println(current_copy[0] + " " + current_copy[1] + " " + current_copy[2]);
				}
			}





		}

		return 0;

	}

	public static void main(String args[])  throws IOException { 

		int JugA, JugB, JugC; // capacities of the jugs
		int a, b, c; // current amounts in jugs



			// parse the arguments
			JugA = Integer.parseInt(args[0]);
			JugB = Integer.parseInt(args[1]);
			JugC = Integer.parseInt(args[2]);
			a = Integer.parseInt(args[3]);
			b = Integer.parseInt(args[4]);
			c = Integer.parseInt(args[5]);
/*
			System.out.println("JugA = " +  JugA );
			System.out.println("JugB = " +  JugB );
			System.out.println("JugC = " +  JugC );
			System.out.println("a = " +  a );
			System.out.println("b = " +  b );
			System.out.println("c = " +  c );
*/
			checkConstraints(JugA, JugB, JugC, a, b, c);

			// store the values in array for easier manipultion.
			int[] capacities = new int[3];
			int[] current = new int[3];

			capacities[0] = JugA; capacities[1] = JugB; capacities[2] = JugC;
			current[0] = a; current[1] = b; current[2] = c;



		// generate successor states after emptying jugs
		emptyJugs(capacities, current);

		// generate successor states after filling all jugs
		fillJugs(capacities, current);

		// generate successor states after pouring between jugs
		pourJugs(capacities, current);



	}






}
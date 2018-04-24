import java.util.*;
import java.io.*;


public class Neural {

    private static Double [] weights = new Double[9]; // create a double array to hold the weights  
    private static Double x1, x2; // x1 and x2 hold the inputs


    private static double sigmoid(double x)
    {
        return 1 / (1 + Math.exp(-x));
    }
    
    //  RETURN VALUE FOR ALL NEURONS IS OF THE FORM {Ui,Vi}, 
    //  FOR OUTPUT NEURON, Vi is the output we want

    // Unit A neuron
    private static double [] unitA(Double[] weights, Double inputx1, Double inputx2) {

        double u1 = weights[0]*1 + weights[1]*inputx1 + weights[2]*inputx2;
        double v1 = Math.max(u1, 0);
        double [] results = {u1,v1}; 
        return results;

    }

    // Unit B neuron
    private static double [] unitB(Double[] weights, Double inputx1, Double inputx2) {
        
        double u2 = weights[3]*1 + weights[4]*inputx1 + weights[5]*inputx2;
        double v2 = Math.max(u2, 0);
        double [] results = {u2, v2};
        return results;
    }

    // Unit C neuron
    private static double [] unitC(Double[] weights, Double inputx1, Double inputx2) {
        
        double u3 = weights[6]*1 + weights[7]*inputx1 + weights[8]*inputx2;
        double v3 = sigmoid(u3);
        double [] results = {u3,v3};
        return results;
    }


    private static double calculateError(Double vc, Double y) {
        return 0.5*Math.pow(vc - y,2);
    }

    private static double partialDerivativeVc(Double vc, Double y) {
        return (vc - y);
    }

    private static double partialDerivativeUc(Double vc, Double y) {
        return (vc - y)*vc*(1 - vc);
    }

    public static void main(String args[]) {
        
        int option = Integer.parseInt(args[0]);
        

        
        if(option == 100) {

            for(int i = 1 ; i < args.length -2 ; i++) {
                weights[i-1] = Double.parseDouble(args[i]); 
            }
    
            x1 = Double.parseDouble(args[args.length-2]);
            x2 = Double.parseDouble(args[args.length-1]);
            
            double [] r1 = unitA(weights, x1, x2);
            double [] r2 = unitB(weights, x1, x2);
            double [] r3 = unitC(weights, r1[1] , r2[1]);
            
            System.out.println(String.format("%.5f ",r1[0]) +
                               String.format("%.5f ",r1[1]) + 
                               String.format("%.5f ",r2[0])  + 
                               String.format("%.5f ",r2[1]) + 
                               String.format("%.5f ",r3[0]) + 
                               String.format("%.5f ",r3[1]) );
            


            
        } else if(option == 200) {

            for(int i = 1 ; i < 10 ; i++) {
                weights[i-1] = Double.parseDouble(args[i]); 
            }

            x1 = Double.parseDouble(args[10]);
            x2 = Double.parseDouble(args[11]);
            Double y = Double.parseDouble(args[12]);

            double [] r1 = unitA(weights, x1, x2);
            double [] r2 = unitB(weights, x1, x2);
            double [] r3 = unitC(weights, r1[1] , r2[1]);

            System.out.println(String.format("%.5f ", calculateError(r3[1], y)) + 
                               String.format("%.5f ", partialDerivativeVc(r3[1], y)) + 
                               String.format("%.5f ", partialDerivativeUc(r3[1], y)));

        }







    }

    

}
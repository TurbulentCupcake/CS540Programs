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


    // The following functions calculate errors and derivatives with respect to the output unit C
    private static double calculateErrorVc(Double vc, Double y) {
        return 0.5*Math.pow(vc - y,2);
    }

    private static double partialDerivativeVc(Double vc, Double y) {
        return (vc - y);
    }

    private static double partialDerivativeUc(Double vc, Double y) {
        return (vc - y)*vc*(1 - vc);
    }

    // the following functions calculate partial derivatives with respect to the hidden layer units A and B

    private static double partialDerivativeVj(Double [] weights, Double vc, Double y, char unit) {
        if(unit == 'A') {    
            double result = weights[7]*partialDerivativeUc(vc, y);
            return result;
        } else if (unit == 'B') {
            double result = weights[8]*partialDerivativeUc(vc, y);
            return result;
        }
        return 0.0;
    }

    private static double partialDerivativeUj(Double [] weights, Double vc, Double y, Double inputx1, Double inputx2, char unit) {

        double result = 0;
        if(unit == 'A') {
            if(unitA(weights, inputx1, inputx2)[0] >= 0) {
                result = partialDerivativeVj(weights, vc, y, unit)*1;
            } else {
                result = partialDerivativeVj(weights, vc, y, unit)*0;
            }
            return result;
        } else if (unit == 'B') {
            if(unitB(weights, inputx1, inputx2)[0] >= 0) {
                result = partialDerivativeVj(weights, vc, y, unit)*1;
            } else {
                result = partialDerivativeVj(weights, vc, y, unit)*0;
            }
            return result;
        }
        return result;
    }

    // the following function calculates the partial derivates with respect to the edge weights
    private static double partialDerivativeWeights(Double [] weights, Double vc, Double y, Double inputx1, Double inputx2, int edge) {
        
        double result = 0;
        if(edge == 1) {
            result = 1*partialDerivativeUj(weights, vc, y, inputx1, inputx2, 'A');
        } else if (edge == 2) {
            result = inputx1*partialDerivativeUj(weights, vc, y, inputx1, inputx2, 'A');
        } else if (edge == 3) {
            result = inputx2*partialDerivativeUj(weights, vc, y, inputx1, inputx2, 'A');
        } else if (edge == 4) {
            result = 1*partialDerivativeUj(weights, vc, y, inputx1, inputx2, 'B');
        } else if (edge == 5) {
            result = inputx1*partialDerivativeUj(weights, vc, y, inputx1, inputx2, 'B');
        } else if (edge == 6) {
            result = inputx2*partialDerivativeUj(weights, vc, y, inputx1, inputx2, 'B');
        } else if (edge == 7) {
            result = 1*partialDerivativeUc(vc, y);
        } else if (edge == 8) {
            result = unitA(weights, inputx1, inputx2)[1]*partialDerivativeUc(vc, y);
        } else if (edge == 9) {
            result = unitB(weights, inputx1, inputx2)[1]*partialDerivativeUc(vc, y);
        }
        return result;

    }

    // returns a new array containing new weights after implementing stochasitic gradient descent for one iteration
    private static Double [] calculateSGD(Double [] weights, Double vc, Double y, Double inputx1, Double inputx2, Double mu) {
        Double [] new_weights = new Double[weights.length];
        for(int i = 0 ; i < weights.length ; i++) {
            new_weights[i] = weights[i] - mu*partialDerivativeWeights(weights, vc, y, inputx1, inputx2, i+1);
        }
        return new_weights;
    }

    private static Double SetEvaluationError(Double [] new_weights, ArrayList<Double> eval_x1, ArrayList<Double> eval_x2, ArrayList<Double> eval_y) {

        Double sum = 0.0;
        for(int i = 0 ; i < eval_x1.size() ; i++) {
            double [] r1 = unitA(new_weights, eval_x1.get(i), eval_x2.get(i));
            double [] r2 = unitB(new_weights, eval_x1.get(i), eval_x2.get(i));
            double [] r3 = unitC(new_weights, r1[1] , r2[1]);
            sum += 0.5*Math.pow(r3[1] - eval_y.get(i),2);
        }  
        return sum;
        
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

            System.out.println(String.format("%.5f ", calculateErrorVc(r3[1], y)) + 
                               String.format("%.5f ", partialDerivativeVc(r3[1], y)) + 
                               String.format("%.5f ", partialDerivativeUc(r3[1], y)));

        } else if (option == 300) {

            for(int i = 1 ; i < 10 ; i++) {
                weights[i-1] = Double.parseDouble(args[i]); 
            }

            x1 = Double.parseDouble(args[10]);
            x2 = Double.parseDouble(args[11]);
            Double y = Double.parseDouble(args[12]);
            
            double [] r1 = unitA(weights, x1, x2);
            double [] r2 = unitB(weights, x1, x2);
            double [] r3 = unitC(weights, r1[1] , r2[1]);

            System.out.println(String.format("%.5f ", partialDerivativeVj(weights, r3[1], y, 'A')) + 
                               String.format("%.5f ", partialDerivativeUj(weights, r3[1], y, x1, x2, 'A')) + 
                               String.format("%.5f ", partialDerivativeVj(weights, r3[1], y, 'B')) + 
                               String.format("%.5f ", partialDerivativeUj(weights, r3[1], y, x1, x2, 'B')));


            
        } else if (option == 400) {

            for(int i = 1 ; i < 10 ; i++) {
                weights[i-1] = Double.parseDouble(args[i]); 
            }

            x1 = Double.parseDouble(args[10]);
            x2 = Double.parseDouble(args[11]);
            Double y = Double.parseDouble(args[12]);
            
            double [] r1 = unitA(weights, x1, x2);
            double [] r2 = unitB(weights, x1, x2);
            double [] r3 = unitC(weights, r1[1] , r2[1]);

            for(int e = 1 ; e <= 9; e++) {
                System.out.print(String.format("%.5f ",partialDerivativeWeights(weights, r3[1], y, x1, x2, e)));
            }
            System.out.println();

        } else if (option == 500) {

            for(int i = 1 ; i < 10 ; i++) {
                weights[i-1] = Double.parseDouble(args[i]); 
            }

            x1 = Double.parseDouble(args[10]);
            x2 = Double.parseDouble(args[11]);
            Double y = Double.parseDouble(args[12]);
            Double mu = Double.parseDouble(args[13]);

            double [] r1 = unitA(weights, x1, x2);
            double [] r2 = unitB(weights, x1, x2);
            double [] r3 = unitC(weights, r1[1] , r2[1]);

            // print old weights
            for(Double w : weights) {
                System.out.print(String.format("%.5f ", w));
            }
            System.out.println();
            System.out.println(String.format("%.5f", calculateErrorVc(r3[1], y)));

            // perform SGD
            Double [] new_weights = calculateSGD(weights, r3[1], y, x1, x2, mu);

            // print new weights
            for(Double w : new_weights) {
                System.out.print(String.format("%.5f ", w));
            }

            r1 = unitA(new_weights, x1, x2);
            r2 = unitB(new_weights, x1, x2);
            r3 = unitC(new_weights, r1[1] , r2[1]);

            System.out.println();
            System.out.println(String.format("%.5f", calculateErrorVc(r3[1], y)));


        } else if (option == 600 || option == 700 || option == 800) {

            for(int i = 1 ; i < 10 ; i++) {
                weights[i-1] = Double.parseDouble(args[i]); 
            }

            Double mu = Double.parseDouble(args[10]);

            // initialize three arrays to hold the input and output variables
            ArrayList<Double>  x1 = new ArrayList<>();
            ArrayList<Double>  x2 = new ArrayList<>();
            ArrayList<Double>  y = new ArrayList<>();

            ArrayList<Double> eval_x1 = new ArrayList<>();
            ArrayList<Double> eval_x2 = new ArrayList<>();
            ArrayList<Double> eval_y = new ArrayList<>();


            // read the train file and store contents from training set
            try {
                File f = new File("./hw2_midterm_A_train.txt");
                Scanner sc = new Scanner(f);
                while(sc.hasNext()) {
                    x1.add(sc.nextDouble());
                    x2.add(sc.nextDouble());
                    y.add(sc.nextDouble());
                }
                sc.close();
            } catch (Exception  ex) {
                ex.printStackTrace();
            }        

            // read the evaluation file 
            try {
                File f = new File("./hw2_midterm_A_eval.txt");
                Scanner sc = new Scanner(f);
                while(sc.hasNext()) {
                    eval_x1.add(sc.nextDouble());
                    eval_x2.add(sc.nextDouble());
                    eval_y.add(sc.nextDouble());
                }
                sc.close();
            } catch (Exception  ex) {
                ex.printStackTrace();
            }



            if(option == 600) {

                Double [] new_weights = new Double[weights.length];
                System.arraycopy(weights, 0, new_weights, 0, weights.length);
                
                double [] r1, r2, r3;

                for(int i = 0 ; i < x1.size(); i++) {

                    System.out.println(String.format("%.5f ", x1.get(i)) + 
                                    String.format("%.5f ", x2.get(i)) + 
                                    String.format("%.5f ", y.get(i)));
                                    
                    // recalculate Vc
                    r1 = unitA(new_weights, x1.get(i), x2.get(i));
                    r2 = unitB(new_weights, x1.get(i), x2.get(i));
                    r3 = unitC(new_weights, r1[1] , r2[1]);
                
                    // calculate new weights
                    new_weights = calculateSGD(new_weights, r3[1], y.get(i),
                                x1.get(i) , x2.get(i), mu);

                    // display new weights
                    for(Double w : new_weights) {
                        System.out.print(String.format("%.5f ", w));
                    }
                    System.out.println();
                    // compute using evaluation
                    System.out.println(String.format("%.5f",SetEvaluationError(new_weights, eval_x1, eval_x2, eval_y)));
                }

            } else if (option == 700) {

                int epochs = Integer.parseInt(args[11]);

                Double [] new_weights = new Double[weights.length];
                System.arraycopy(weights, 0, new_weights, 0, weights.length);
                
                double [] r1, r2, r3;

                for(int epoch = 0 ; epoch < epochs ; epoch++) {

                    for(int i = 0 ; i < x1.size(); i++) {
                                       
                        // recalculate Vc
                        r1 = unitA(new_weights, x1.get(i), x2.get(i));
                        r2 = unitB(new_weights, x1.get(i), x2.get(i));
                        r3 = unitC(new_weights, r1[1] , r2[1]);
                    
                        // calculate new weights
                        new_weights = calculateSGD(new_weights, r3[1], y.get(i),
                                    x1.get(i) , x2.get(i), mu);
    
                        // compute using evaluation
                    }

                    //print weights after epoch
                    for(Double w : new_weights) {
                        System.out.print(String.format("%.5f ",w));
                    }
                    System.out.println();

                    // print evaluation error
                    System.out.println(String.format("%.5f",SetEvaluationError(new_weights, eval_x1, eval_x2, eval_y)));

                    
                }



            } else if(option == 800) {

                int epochs = Integer.parseInt(args[11]);

                Double [] new_weights = new Double[weights.length];
                System.arraycopy(weights, 0, new_weights, 0, weights.length);
                
                double [] r1, r2, r3;

                double delta = 9999; // set delta to some aribitarly large number
                int epoch;
                for(epoch = 0 ; epoch < epochs ; epoch++) {

                    for(int i = 0 ; i < x1.size(); i++) {
                                       
                        // recalculate Vc
                        r1 = unitA(new_weights, x1.get(i), x2.get(i));
                        r2 = unitB(new_weights, x1.get(i), x2.get(i));
                        r3 = unitC(new_weights, r1[1] , r2[1]);
                    
                        // calculate new weights
                        new_weights = calculateSGD(new_weights, r3[1], y.get(i),
                                    x1.get(i) , x2.get(i), mu);
    
                        // compute using evaluation
                    }

                    

                    if(SetEvaluationError(new_weights, eval_x1, eval_x2, eval_y) > delta){
                        break;
                    } else {
                        delta = SetEvaluationError(new_weights, eval_x1, eval_x2, eval_y);
                    }

                }

                // print number of epochs that happened
                System.out.println(epoch+1);

                // print the final w
                for(Double w : new_weights) {
                    System.out.print(String.format("%.5f ", w));
                }
                System.out.println();

                // print set evaluation error at the end
                System.out.println(String.format("%.5f", SetEvaluationError(new_weights, eval_x1, eval_x2, eval_y)));

                // PERFORMANCE ON TEST
                // THIS IS WHERE THE ACTUAL CLASSIFICATION OF TEST SET HAPPENS

                ArrayList<Double> test_x1 = new ArrayList<>();
                ArrayList<Double> test_x2 = new ArrayList<>();
                ArrayList<Integer> test_y = new ArrayList<>();

                try {
                    File f = new File("./hw2_midterm_A_test.txt");
                    Scanner sc = new Scanner(f);
                    while(sc.hasNext()) {
                        test_x1.add(sc.nextDouble());
                        test_x2.add(sc.nextDouble());
                        test_y.add(sc.nextInt());
                    }
                    sc.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                ArrayList<Integer> predictions = new ArrayList<>();

                for(int i = 0 ; i < test_x1.size() ; i++) {
                    r1 = unitA(new_weights, test_x1.get(i), test_x2.get(i));
                    r2 = unitB(new_weights, test_x1.get(i), test_x2.get(i));
                    r3 = unitC(new_weights, r1[1] , r2[1]);
                    if(r3[1] >= 0.5) {
                        predictions.add(1);
                    } else {
                        predictions.add(0);
                    }
                }

                double accuracy = 0;
                for(int i = 0 ; i < test_y.size() ; i++) {
                    if(predictions.get(i) == test_y.get(i)) {
                        accuracy++;
                    }
                }

                accuracy = accuracy/test_x1.size();
                System.out.println(String.format("%.5f ",accuracy));

            }
            
        } 


    }

    

}
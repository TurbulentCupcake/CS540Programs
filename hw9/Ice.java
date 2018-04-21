import java.util.*;
import java.io.*;
import java.time.Year;

public class Ice{
    private static String filename = "./data.txt";

    private static HashMap<Integer, Integer>  readFile() {
        
        HashMap<Integer, Integer> dataset = new HashMap<>();

        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()) {
                //System.out.println(sc.nextInt() + "-" + sc.nextInt());
                dataset.put(sc.nextInt(), sc.nextInt());
            }

        } catch(FileNotFoundException ex){
            System.out.println("File not found");
        }
        return dataset;
    }

    private static void showDataset(HashMap<Integer, Integer> dataset) {
        for( Integer key : dataset.keySet()){
            System.out.println(key + " " + dataset.get(key));
        }
    }

    private static int numDataPoints(HashMap<Integer, Integer> dataset) {
        return dataset.size();
    }

    private static double getMean(HashMap<Integer, Integer> dataset) {
        
        double sum = 0;
        for(Integer key : dataset.keySet()) {
            sum += dataset.get(key);
        }
        sum = sum/dataset.size();
        return sum;
    }

    private static double getStdDev(HashMap<Integer, Integer> dataset) {
        double mean = Ice.getMean(dataset);
        double sum = 0;
        for(Integer key : dataset.keySet()) {
            sum += Math.pow((dataset.get(key) - mean), 2);
        }
        sum = sum/(dataset.size() - 1);
        sum = Math.sqrt(sum);
        return sum;
    }

    private static double getMSE(HashMap<Integer, Integer> dataset, double b0, double b1) {
        
        double sum = 0;
        for(Integer key : dataset.keySet()) {
            sum += Math.pow(b0 + b1*key - dataset.get(key),2);
        }
        sum = sum/dataset.size();
        return sum;
    }

    private static double getMSE_Array(double [] moddedX, 
        HashMap<Integer, Integer> dataset, double b0, double b1) {
        
        double sum = 0;
        int i = 0;
        for(Integer key : dataset.keySet()) {
            sum += Math.pow(b0 + b1*moddedX[i] - dataset.get(key),2);
            i++;
        }
        sum = sum/dataset.size();
        return sum;
    }

    private static double getMSEPartialDerivativeb0(HashMap<Integer, Integer> dataset, double b0, double b1){
        
        double sum = 0;
        for(Integer key : dataset.keySet()) {
            sum += b0 + b1*key - dataset.get(key);
        }

        sum = 2*(sum/dataset.size());
        return sum;
    }


    private static double getMSEPartialDerivativeb1(HashMap<Integer, Integer> dataset, double b0, double b1){
        
        double sum = 0;
        for(Integer key : dataset.keySet()) {
            sum += (b0 + b1*key - dataset.get(key))*key;
        }

        sum = 2*(sum/dataset.size());
        return sum;
    }
    


    private static void printGradientDescent(HashMap<Integer, Integer> dataset, double n, Double t) {

        double b0 = 0, b1 = 0;
        for(int i = 0 ; i < t ; i++) {
            double oldb0 = b0;
            double oldb1 = b1;
            b0 = oldb0 - n*Ice.getMSEPartialDerivativeb0(dataset, oldb0, oldb1);
            b1 = oldb1 - n*Ice.getMSEPartialDerivativeb1(dataset, oldb0, oldb1);
            System.out.println((i+1) + " " + String.format("%.2f",b0) + " " + 
                                String.format("%.2f",b1) + " " + 
                                String.format("%.2f",getMSE(dataset, b0, b1)));
        }

    }

    private static void printCloseFormSoln(HashMap<Integer, Integer> dataset) {
        double xmean = 0;
        for(Integer key : dataset.keySet()) {
            xmean += key;
        }
        xmean = xmean/dataset.size();

        double ymean = getMean(dataset);

        double numerator = 0, denominator = 0;
        for(Integer key : dataset.keySet()) {
            numerator += (key - xmean)*(dataset.get(key) - ymean);
            denominator += Math.pow((key - xmean),2);
        }
        double b1 = numerator/denominator;

        double b0 = ymean - b1*xmean;
        
        System.out.println(String.format("%.2f", b0) + " " + 
        String.format("%.2f",b1) + " " + String.format("%.2f", getMSE(dataset, b0, b1)));

    }

    private static double predictIceDays(HashMap<Integer, Integer> dataset, int year) {
        double xmean = 0;
        for(Integer key : dataset.keySet()) {
            xmean += key;
        }
        xmean = xmean/dataset.size();

        double ymean = getMean(dataset);

        double numerator = 0, denominator = 0;
        for(Integer key : dataset.keySet()) {
            numerator += (key - xmean)*(dataset.get(key) - ymean);
            denominator += Math.pow((key - xmean),2);
        }
        double b1 = numerator/denominator;
        double b0 = ymean - b1*xmean;
        
        double y = b0 + year*b1;
        return y;
    }

    private static void inputNormalization(HashMap<Integer, Integer> dataset, double n, double t) {
        
        double [] moddedX = new double[dataset.size()];

        double xmean = 0;
        for(Integer key : dataset.keySet()) {
            xmean += key;
        }
        xmean = xmean/dataset.size();
        //System.out.println(xmean);
        double stdx = 0;
        for(Integer key : dataset.keySet()) {
            stdx += Math.pow((key - xmean),2);
        }
        stdx = stdx/(dataset.size() - 1);
        stdx = Math.sqrt(stdx);
        //System.out.println(stdx);

        Integer [] xvalues = dataset.keySet().toArray(new Integer[0]);
        for(int i = 0 ; i < dataset.size() ; i++) {
            moddedX[i] = (xvalues[i] - xmean)/stdx;
            //System.out.println(moddedX[i]);
        }

        double [] moddedY = new double[dataset.size()];
        for(int i = 0 ; i < moddedX.length ; i++) {
            moddedY[i] = dataset.get(xvalues[i]);
        }

        // now, our new normalized x values are in moddedX and our
        // y values from the dataset are present in moddedY, 
        // proceeed in similar fashion to flag == 500

        double b0 = 0, b1 = 0;
 
        for(int i = 0 ; i < t ; i++) {
            double oldb0 = b0;
            double oldb1 = b1;
            
            // calculate the partial derivative for b0
            double sum1 = 0;
            for(int j = 0 ; j < moddedX.length; j++) {
                sum1 += oldb0 + oldb1*moddedX[j] - moddedY[j];  
            }
            sum1 = 2*(sum1/moddedX.length);

            // calculate the partial derivate for b1
            double sum2 = 0;
            for(int j = 0 ; j < moddedX.length; j++) {
                sum2 += (oldb0 + oldb1*moddedX[j] - moddedY[j])*moddedX[j]; 
            }
            sum2 = 2*(sum2/moddedX.length);

            b0 = oldb0 - n*sum1;
            b1 = oldb1 - n*sum2;

            // calculate the mean squared error
            double mse = 0; 
            for(int j = 0 ; j < moddedX.length ; j++) {
                mse += Math.pow(b0 + b1*moddedX[j] - moddedY[j], 2);
            }
            mse = mse/moddedX.length;

            System.out.println((i+1) + " " + String.format("%.2f",b0) + " " + 
                                String.format("%.2f",b1) + " " + 
                                String.format("%.2f",mse));
            

        }
    
    }

    private static void SGD(HashMap<Integer, Integer> dataset, double n, double t) {
        
        double [] moddedX = new double[dataset.size()];

        double xmean = 0;
        for(Integer key : dataset.keySet()) {
            xmean += key;
        }
        xmean = xmean/dataset.size();
        //System.out.println(xmean);
        double stdx = 0;
        for(Integer key : dataset.keySet()) {
            stdx += Math.pow((key - xmean),2);
        }
        stdx = stdx/(dataset.size() - 1);
        stdx = Math.sqrt(stdx);
        //System.out.println(stdx);

        Integer [] xvalues = dataset.keySet().toArray(new Integer[0]);
        for(int i = 0 ; i < dataset.size() ; i++) {
            moddedX[i] = (xvalues[i] - xmean)/stdx;
            //System.out.println(moddedX[i]);
        }

        double [] moddedY = new double[dataset.size()];
        for(int i = 0 ; i < moddedX.length ; i++) {
            moddedY[i] = dataset.get(xvalues[i]);
        }

        // now, our new normalized x values are in moddedX and our
        // y values from the dataset are present in moddedY, 
        // proceeed in similar fashion to flag == 500

        double b0 = 0, b1 = 0;
 
        for(int i = 0 ; i < t ; i++) {
            double oldb0 = b0;
            double oldb1 = b1;
            
                
            // pick a random number
            Random rand = new Random();
            int r = rand.nextInt(moddedX.length);
            double sum1 = 2*(oldb0 + oldb1*moddedX[r] - moddedY[r]);
            double sum2 = 2*(oldb0 + oldb1*moddedX[r] - moddedY[r])*moddedX[r];

            b0 = oldb0 - n*sum1;
            b1 = oldb1 - n*sum2;

            // calculate the mean squared error
            double mse = 0; 
            for(int j = 0 ; j < moddedX.length ; j++) {
                mse += Math.pow(b0 + b1*moddedX[j] - moddedY[j], 2);
            }
            mse = mse/moddedX.length;

            System.out.println((i+1) + " " + String.format("%.2f",b0) + " " + 
                                String.format("%.2f",b1) + " " + 
                                String.format("%.2f",mse));
            

        }
    
    }




    public static void main(String[] args) {
        // read the file 
        HashMap<Integer, Integer> dataset = readFile();
        int flag = Integer.valueOf(args[0]);
         
        if(flag == 100) {
            Ice.showDataset(dataset);
        } else if (flag == 200) {
            System.out.println(Ice.numDataPoints(dataset));
            System.out.println(String.format("%.2f",Ice.getMean(dataset)));
            System.out.println(String.format("%.2f",Ice.getStdDev(dataset)));
        } else if (flag == 300) {
            double b0 = Double.valueOf(args[1]);
            double b1 = Double.valueOf(args[2]);
            System.out.println(String.format("%.2f", Ice.getMSE(dataset, b0, b1)));
        } else if (flag == 400) {
            double b0 = Double.valueOf(args[1]);
            double b1 = Double.valueOf(args[2]);
            System.out.println(String.format("%.2f", Ice.getMSEPartialDerivativeb0(dataset, b0, b1)));
            System.out.println(String.format("%.2f", Ice.getMSEPartialDerivativeb1(dataset, b0, b1)));
        } else if (flag == 500) {
            double b0 = Double.valueOf(args[1]);
            double b1 = Double.valueOf(args[2]);
            printGradientDescent(dataset, b0, b1); // here b0 = n and b1 = T
        } else if (flag == 600) {
            printCloseFormSoln(dataset);
        } else if (flag == 700) {
            int year = Integer.valueOf(args[1]);
            System.out.println(String.format("%.2f",predictIceDays(dataset, year)));
        } else if(flag == 800) {
            double b0 = Double.valueOf(args[1]);
            double b1 = Double.valueOf(args[2]);
            Ice.inputNormalization(dataset,b0,b1); // here b0 = n and b1 = T
        } else if(flag == 900) {
            double b0 = Double.valueOf(args[1]);
            double b1 = Double.valueOf(args[2]);
            Ice.SGD(dataset, b0, b1); // here b0 = n and b1 = T
        }

        
        return;
    }

}
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
        }

        
        return;
    }

}
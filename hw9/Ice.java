import java.util.*;
import java.io.*;

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
        } 
        
        return;
    }

}
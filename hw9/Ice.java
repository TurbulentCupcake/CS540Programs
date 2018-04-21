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

    private static float getMean(HashMap<Integer, Integer> dataset) {
        
        float sum = 0;
            


        return null;
    }



    public static void main(String[] args) {
        // read the file 
        HashMap<Integer, Integer> dataset = readFile();
        int flag = Integer.valueOf(args[0]);

        if(flag == 100) {
            Ice.showDataset(dataset);
        } else if (flag == 200) {

            
        }
        
        return;
    }

}
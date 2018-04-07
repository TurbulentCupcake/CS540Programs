
import java.util.*;
import java.io.*;

public class Chatbot{
    private static String filename = "./WARC201709_wid.txt";
    private static ArrayList<Integer> readCorpus(){
        ArrayList<Integer> corpus = new ArrayList<Integer>();
        try{
            File f = new File(filename);
            Scanner sc = new Scanner(f);
            while(sc.hasNext()){
                if(sc.hasNextInt()){
                    int i = sc.nextInt();
                    corpus.add(i);
                }
                else{
                    sc.next();
                }
            }
        }
        catch(FileNotFoundException ex){
            System.out.println("File Not Found.");
        }
        return corpus;
    }

    private static double getProbability(ArrayList<Integer> corpus, int w) {
        
        int count = 0;
        for(Integer word : corpus) {
            
            if(w == word) {
                count++;
            }
        }

        //System.out.println(count);

        double p = count/(double)corpus.size();
      //  System.out.println(p);
        return p;
    }

    static public void main(String[] args){
        ArrayList<Integer> corpus = readCorpus();
		int flag = Integer.valueOf(args[0]);
        
        if(flag == 100){
			int w = Integer.valueOf(args[1]);
            int count = 0;
            for(Integer word : corpus) {
                if(w == word) {
                    count++;
                }
            }
            System.out.println(count);
            System.out.println(String.format("%.7f",count/(double)corpus.size()));
        }
        else if(flag == 200){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            //TODO generate
            double r = (double)n1/(double)n2;

            ArrayList<Double> p_i = new ArrayList<>();

            // iterate through each word in the vocabulary
            // for(int i = 0 ; i < 4700 ; i++) {
            //     // add the probability into our vector
            //     int count = 0;
            //     for(Integer word : corpus) {
            //         if(i == word) {
            //             count++;
            //         }
            //     }
            //     double prob = count/(double)corpus.size();
            //     p_i.add(prob);
            // }


            for(int i = 0 ; i < 4700 ; i++) {
                // make sure p_i is nonzero
                p_i.add(getProbability(corpus, i));

                if(p_i.get(i) != 0) {
                    
                    double l_i = 0, r_i = 0;

                    if(i != 0) {
                        for(int j = 0 ; j < i ; j++) {
                            l_i += p_i.get(j);
                        }
                    } 

                    for(int j = 0; j <= i ; j++){
                        r_i +=  p_i.get(j);
                    }

                    if(r > l_i && r <= r_i) {
                        System.out.println(i);
                        System.out.println(String.format("%.7f",l_i));
                        System.out.println(String.format("%.7f",r_i));
                        System.exit(0);
                    }
                                       
                }
            }

        }
        else if(flag == 300){
            int h = Integer.valueOf(args[1]);
            int w = Integer.valueOf(args[2]);
            int count = 0;
            ArrayList<Integer> words_after_h = new ArrayList<Integer>();
            //TODO

            // iterate until the second last word
            // and add the next word into the arraylist
            for(int i = 0 ; i < corpus.size() - 1; i++) {
                if(corpus.get(i) == h) {
                    words_after_h.add(corpus.get(i+1));
                }
            }
            
            // look for the words that match w
            for(int i = 0 ; i < words_after_h.size(); i++) {
                if(words_after_h.get(i) == w) {
                    count++;
                }
            }
            //output 
            System.out.println(count);
            System.out.println(words_after_h.size());
            System.out.println(String.format("%.7f",count/(double)words_after_h.size()));
        }
        else if(flag == 400){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h = Integer.valueOf(args[3]);
            //TODO
            
        }
        else if(flag == 500){
            int h1 = Integer.valueOf(args[1]);
            int h2 = Integer.valueOf(args[2]);
            int w = Integer.valueOf(args[3]);
            int count = 0;
            ArrayList<Integer> words_after_h1h2 = new ArrayList<Integer>();
            //TODO

            //output 
            System.out.println(count);
            System.out.println(words_after_h1h2.size());
            if(words_after_h1h2.size() == 0)
                System.out.println("undefined");
            else
                System.out.println(String.format("%.7f",count/(double)words_after_h1h2.size()));
        }
        else if(flag == 600){
            int n1 = Integer.valueOf(args[1]);
            int n2 = Integer.valueOf(args[2]);
            int h1 = Integer.valueOf(args[3]);
            int h2 = Integer.valueOf(args[4]);
            //TODO
        }
        else if(flag == 700){
            int seed = Integer.valueOf(args[1]);
            int t = Integer.valueOf(args[2]);
            int h1=0,h2=0;

            Random rng = new Random();
            if (seed != -1) rng.setSeed(seed);

            if(t == 0){
                // TODO Generate first word using r
                double r = rng.nextDouble();
                System.out.println(h1);
                if(h1 == 9 || h1 == 10 || h1 == 12){
                    return;
                }

                // TODO Generate second word using r
                r = rng.nextDouble();
                System.out.println(h2);
            }
            else if(t == 1){
                h1 = Integer.valueOf(args[3]);
                // TODO Generate second word using r
                double r = rng.nextDouble();
                System.out.println(h2);
            }
            else if(t == 2){
                h1 = Integer.valueOf(args[3]);
                h2 = Integer.valueOf(args[4]);
            }

            while(h2 != 9 && h2 != 10 && h2 != 12){
                double r = rng.nextDouble();
                int w  = 0;
                // TODO Generate new word using h1,h2
                System.out.println(w);
                h1 = h2;
                h2 = w;
            }
        }

        return;
    }
}


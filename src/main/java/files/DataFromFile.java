package files;

import java.io.*;
import java.util.*;


public class DataFromFile {
    public List<String> orderFromFile = new ArrayList<>();

    public List<String> getOrderFromFile() {
        return orderFromFile;
    }

    public DataFromFile(String comand){
        BufferedReader reader = null;
        try{
            File myfile = new File(comand);
            FileReader fr = new FileReader(myfile);
            reader = new BufferedReader(fr);
            String line = "";
            while ((line = reader.readLine()) != null) {
                orderFromFile.add(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}


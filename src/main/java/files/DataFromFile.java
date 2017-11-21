package files;

import java.io.*;
import java.util.*;


public class DataFromFile {
    public List<String> orderFromFile = new ArrayList<>();

    public List<String> getOrderFromFile() {
        return orderFromFile;
    }

    public DataFromFile(String comand){
        File myfile = new File(comand);
        try(FileReader fr = new FileReader(myfile); BufferedReader reader = new BufferedReader(fr);){
            String line = "";
            while ((line = reader.readLine()) != null) {
                orderFromFile.add(line);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "DataFromFile{" +
                "orderFromFile=" + orderFromFile +
                '}';
    }
}


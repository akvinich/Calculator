package files;

import logic.Calculator;

import java.io.*;

public class DataToFile {

    public DataToFile(){};
    public void createFile(String filename){
        try(FileWriter fw = new FileWriter(filename)) {
            fw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void writeInfoFile(String filename, Calculator calc){
        try(PrintWriter pw = new PrintWriter(filename)) {
            for(String entry: calc.getResult()){
                pw.println(entry);
                pw.append("\n");
            }
            pw.close();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }


}

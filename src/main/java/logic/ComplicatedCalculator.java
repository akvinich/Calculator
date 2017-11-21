package logic;

import java.util.*;
import java.util.regex.*;


public class ComplicatedCalculator extends Calculator {

    final static String regexSin = "sin((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexCos = "cos((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexSqrt = "\\√((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
  //  final static String numberDot = "((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";

    final String[] regExpression = new String[]{regexBrackets, regexSin, regexCos, regexSqrt, regexElev, regexMult, regexDiv, regexAdd, regexSub};
    final String[] spliters = new String[]{"\\(\\)", "sin", "cos", "\\√", "\\^", "\\*", "\\/", "\\+", "\\-"};


    @Override
    protected String operation(String in) {
        StringBuffer bufferEntry = new StringBuffer(in);
        for (int i=0; i<regExpression.length; i++){
            while (findMatch(regExpression[i],bufferEntry.toString())) switch (spliters[i]) {
                case "sin":
                    bufferEntry = new StringBuffer(sinModule(bufferEntry.toString()));
                    break;

                case "cos":
                    bufferEntry = new StringBuffer(cosModule(bufferEntry.toString()));
                    break;

                case "\\+":
                    bufferEntry = new StringBuffer(addModule(bufferEntry.toString()));
                    break;

                case "\\-":
                    bufferEntry = new StringBuffer(subModule(bufferEntry.toString()));
                    break;

                case "\\*":
                    bufferEntry = new StringBuffer(multModule(bufferEntry.toString()));
                    break;

                case "\\/":
                    bufferEntry = new StringBuffer(divModule(bufferEntry.toString()));
                    break;

                case "\\^":
                    bufferEntry = new StringBuffer(elevateModule(bufferEntry.toString()));
                    break;

                case "\\(\\)":
                    bufferEntry = new StringBuffer(bracketsModule(bufferEntry.toString()));
                    break;

                case "\\√":
                    bufferEntry = new StringBuffer(sqrtModule(bufferEntry.toString()));
                    break;
            }
        }
        return  bufferEntry.toString();
    }

    // SIN
    protected String sinModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexSin, entry));
        Pattern p1 = Pattern.compile("sin");
        String[] values = p1.split(buf.toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            if (values[j].equals("")) {
                valueDouble[j] = 1;
            }
            else {
                valueDouble[j] = Double.parseDouble(values[j]);
            }
        }
        Formatter f= new Formatter();
        StringBuilder resultStr = new StringBuilder(changeExpression("\\,",f.format("%.5f",roundOfDouble( Math.sin(valueDouble[1]))).toString(),"."));
        buf = new StringBuffer(changeExpression("sin"+values[1], entry, clearCertain(resultStr.toString())));
        return buf.toString();
    }

    // COS
    protected String cosModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexCos, entry));
        Pattern p1 = Pattern.compile("cos");
        String[] values = p1.split(buf.toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            if (values[j].equals("")) {
                valueDouble[j] = 1;
            }
            else {
                valueDouble[j] = Double.parseDouble(values[j]);
            }
        }
        Formatter f= new Formatter();
        StringBuilder resultStr = new StringBuilder(changeExpression("\\,",f.format("%.5f",roundOfDouble( Math.cos(valueDouble[1]))).toString(),"."));
        buf = new StringBuffer(changeExpression("cos"+values[1], entry, clearCertain(resultStr.toString())));
        return buf.toString();
    }

    // КОРЕНЬ
    protected String sqrtModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexSqrt, entry));
        int position = startPositionMatch(regexSqrt,entry);
        Pattern p1 = Pattern.compile("\\√");
        String[] values = p1.split(buf.toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            if (values[j].equals("")) {
                valueDouble[j] = 1;
            }
            else {
                valueDouble[j] = Double.parseDouble(values[j]);
            }
        }
        if(valueDouble[1] < 0){
            buf = new StringBuffer("ИЗВЛЕЧЕНИЕ КОРНЯ ИЗ ОТРИЦАТЕЛЬНОГО ЧИСЛА НЕ ДОПУСТИМО");
        }
        else {
            Formatter f = new Formatter();
            StringBuilder resultStr = new StringBuilder(changeExpression("\\,", f.format("%.5f", roundOfDouble(Math.sqrt(valueDouble[1]))).toString(), "."));
            buf = new StringBuffer(changeExpression("\\√" + values[1], entry, clearCertain(resultStr.toString())));
        }
        return buf.toString();
    }
}

package logic;

import files.DataFromFile;

import java.util.*;
import java.math.*;
import java.util.regex.*;

public class Calculator {
    private List<String> result  = new ArrayList<>();

    final static String regexAdd = "((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))(\\+)((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexSub = "((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))(\\-)((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexMult = "((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))(\\*)((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexDiv = "((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))(\\/)((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexElev = "(((\\d+)(\\.?)(\\d+)|(\\d+)))(\\^)((\\-?)((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexElevRevers = "(((\\d+)(\\.?)(\\d+)|(\\d+))(\\-?))(\\^)(((\\d+)(\\.?)(\\d+)|(\\d+)))";
    final static String regexBrackets = "\\(([^\\(\\)]+)\\)";
    final static String regexSpace ="(\\p{Blank})+";


    final String[] regExpression = new String[]{regexBrackets, regexElev, regexMult, regexDiv, regexAdd, regexSub};
    final String[] spliters = new String[]{"\\(\\)", "\\^", "\\*", "\\/", "\\+", "\\-"};


    public List<String> getResult() {
        return result;
    }

    public Calculator(){}

    // ОКРУГЛЕНИЕ ДО 5-ГО ЗНАКА ПОСЛЕ ЗАПЯТОЙ
    protected double roundOfDouble(double d){
        return new BigDecimal(d).setScale(5, RoundingMode.HALF_UP).doubleValue();
    }

    protected boolean findMatch(String reg, String str){
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        return m1.find();
    }

    protected int startPositionMatch(String reg, String str){
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        m1.find();
        return m1.start();
    }
    protected int endPositionMatch(String reg, String str){
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        m1.find();
        return m1.end();
    }

    protected String findMatchExpression(String reg, String str){
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        m1.find();
        return m1.group().toString();
    }

    protected String clearCertain(String str){
        StringBuffer in = new StringBuffer(str);
        boolean flag = true;
        while ((in.charAt(in.length()-1)=='0' || in.charAt(in.length()-1)=='.')&& flag){
            in = new StringBuffer(in.deleteCharAt(in.length()-1));
            if(in.charAt(in.length()-1)=='.'){
                flag=false;
                in = new StringBuffer(in.deleteCharAt(in.length()-1));
            }
        }

        return in.toString();
    }

    protected String clearSpaces(String str){
        StringBuffer in = new StringBuffer(str);
        while (findMatch(regexSpace, in.toString())){
            in.delete(startPositionMatch(regexSpace, in.toString()), endPositionMatch(regexSpace, in.toString()));
        }


        return in.toString();
    }

    protected String changeExpression(String reg, String str, String result){
        String strR = "";
        Pattern p1 = Pattern.compile(reg);
        Matcher m1 = p1.matcher(str);
        strR = strR + m1.replaceFirst(result);
        return strR;
    }

    // СЛОЖЕНИЕ
    protected String addModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexAdd, entry));
        int position = startPositionMatch(regexAdd,entry);
        Pattern p1 = Pattern.compile("\\+");
        String[] values = p1.split(buf.toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            valueDouble[j] = Double.parseDouble(values[j]);
        }
        Formatter f= new Formatter();
        StringBuilder resultStr = new StringBuilder(changeExpression("\\,",f.format("%.5f",roundOfDouble(valueDouble[0] + valueDouble[1])).toString(),"."));
        double result = roundOfDouble(Double.parseDouble(resultStr.toString()));

        if (position!=0 && result>=0){
            buf = new StringBuffer(changeExpression(values[0]+"\\+"+values[1], entry, "+" + clearCertain(resultStr.toString())));
        }
        else {
            buf = new StringBuffer(changeExpression(values[0]+"\\+"+values[1], entry, clearCertain(resultStr.toString())));
        }
        return buf.toString();
    }
    // ВЫЧИТАНИЕ
    protected String subModule(String entry){
        int position = startPositionMatch(regexSub,entry);
        StringBuffer buf = new StringBuffer(findMatchExpression(regexSub, entry));
        Pattern p1 = Pattern.compile("\\-");
        String[] values = p1.split(buf.toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            valueDouble[j] = Double.parseDouble("0"  + values[j]);
        }
        Formatter f= new Formatter();
        StringBuilder resultStr = new StringBuilder();

        if(valueDouble.length==2) {
            resultStr = new StringBuilder(changeExpression("\\,",f.format("%.5f",roundOfDouble(valueDouble[0] - valueDouble[1])).toString(),"."));
            buf = new StringBuffer(changeExpression(values[0]+"\\-"+values[1], entry, clearCertain(resultStr.toString())));
        }
        if(valueDouble.length==3){
            if(values[0].equals("")) {
                resultStr = new StringBuilder(changeExpression("\\,", f.format("%.5f", roundOfDouble(0 - valueDouble[1] - valueDouble[2])).toString(), "."));
                buf = new StringBuffer(changeExpression("\\-" + values[1] + "\\-" + values[2], entry, clearCertain(resultStr.toString())));
            }
            else{
                resultStr = new StringBuilder(changeExpression("\\,", f.format("%.5f", roundOfDouble( valueDouble[0] + valueDouble[2])).toString(), "."));
                buf = new StringBuffer(changeExpression( values[0] + "\\-" + "\\-" + values[2], entry, clearCertain(resultStr.toString())));
            }
        }
        if (valueDouble.length==4){
            resultStr = new StringBuilder(changeExpression("\\,",f.format("%.5f",roundOfDouble(0-valueDouble[1] + valueDouble[3])).toString(),"."));
            buf = new StringBuffer(changeExpression("\\-" + values[1]+"\\-"+"\\-"+values[3], entry, clearCertain(resultStr.toString())));
        }
        return buf.toString();
    }

    //УМНОЖНИЕ
    protected String multModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexMult, entry));
        int position = startPositionMatch(regexMult,entry);
        Pattern p1 = Pattern.compile("\\*");
        String[] values = p1.split(buf.toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            valueDouble[j] = Double.parseDouble(values[j]);
        }
        Formatter f= new Formatter();
        StringBuilder resultStr = new StringBuilder(changeExpression("\\,",f.format("%.5f",roundOfDouble(valueDouble[0] * valueDouble[1])).toString(),"."));
        double result = roundOfDouble(Double.parseDouble(resultStr.toString()));

        if (position!=0 && result>=0 && (valueDouble[0]<=0 && valueDouble[1]<=0)){
            buf = new StringBuffer(changeExpression(values[0]+"\\*"+values[1], entry, "+" + clearCertain(resultStr.toString())));
        }
        else {
            buf = new StringBuffer(changeExpression(values[0]+"\\*"+values[1], entry, clearCertain(resultStr.toString())));
        }
        return buf.toString();
    }

    //ДЕЛЕНИЕ
    protected String divModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexDiv, entry));
        int position = startPositionMatch(regexDiv,entry);
        Pattern p1 = Pattern.compile("\\/");
        String[] values = p1.split(buf.toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            valueDouble[j] = Double.parseDouble(values[j]);
        }

        if(valueDouble[1] == 0){
            buf = new StringBuffer("ДЕЛЕНИЕ НА НОЛЬ НЕ ДОПУСТИМО");
        }
        else {
            Formatter f= new Formatter();
            StringBuilder resultStr = new StringBuilder(changeExpression("\\,",f.format("%.5f",roundOfDouble(valueDouble[0] / valueDouble[1])).toString(),"."));
            double result = roundOfDouble(Double.parseDouble(resultStr.toString()));

            if (position != 0 && result >= 0 && (valueDouble[0]<=0 && valueDouble[1]<=0)) {
                buf = new StringBuffer(changeExpression(values[0]+"\\/"+values[1], entry, "+" + clearCertain(resultStr.toString())));
            } else {
                buf = new StringBuffer(changeExpression(values[0]+"\\/"+values[1], entry, clearCertain(resultStr.toString())));
            }
        }
        return buf.toString();
    }


    //ВОЗВЕДЕНИЕ В СТЕПЕНЬ
    protected String elevateModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexElevRevers, new StringBuffer(entry).reverse().toString()));
        Pattern p1 = Pattern.compile("\\^");
        String[] values = p1.split(buf.reverse().toString());
        double[] valueDouble = new double[values.length];
        for (int j = 0; j < values.length; j++) {
            valueDouble[j] = Double.parseDouble(values[j]);
        }
        Formatter f= new Formatter();
        String resultStr = changeExpression("\\,",f.format("%.5f",roundOfDouble(Math.pow(valueDouble[0],valueDouble[1]))).toString(),".");

        buf = new StringBuffer(changeExpression(new StringBuffer(values[1]).reverse().toString()+"\\^"+new StringBuffer(values[0]).reverse().toString(),
                new StringBuffer(entry).reverse().toString(),
                new StringBuffer(clearCertain(resultStr)).reverse().toString() ));


        return buf.reverse().toString();
    }

    //СКОБКИ
    protected String bracketsModule(String entry){
        StringBuffer buf = new StringBuffer(findMatchExpression(regexBrackets, entry));
        buf = new StringBuffer(buf.deleteCharAt(buf.length()-1));
        buf = new StringBuffer(buf.deleteCharAt(0));

        String result = operation(buf.toString());
        buf = new StringBuffer(entry);
        int positionS = startPositionMatch(regexBrackets,entry);
        int positionE = endPositionMatch(regexBrackets,entry);
        buf.delete(positionS, positionE);
        buf = new StringBuffer(buf.insert(positionS, result));
        return buf.toString();
    }



    protected  String operation(String in){
        StringBuffer bufferEntry = new StringBuffer(in);
        for (int i=0; i<regExpression.length; i++){
            while (findMatch(regExpression[i],bufferEntry.toString())){
                switch (spliters[i]) {
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
                }
            }
        }
        return  bufferEntry.toString();
    }



    public void calculation(DataFromFile patternMatch){
        for( String entry : patternMatch.getOrderFromFile() ) {
            StringBuffer buf = new StringBuffer(clearSpaces(entry));
            result.add(entry + " = " + operation(buf.toString()));
        }
    }

}

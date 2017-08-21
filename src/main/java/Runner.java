public class Runner {
    public static void main(String[] args){

//---------------------input_1------------------------------
        Calculator example1 = new Calculator();
        DataFromFile data1 = new DataFromFile("E:\\input_1.txt");
        DataToFile dataTo1 = new DataToFile();

        example1.Calculation(data1);
        System.out.println(example1.getResult().toString());

        dataTo1.createFile("E:\\\\output_1.txt");
        dataTo1.writeInfoFile("E:\\\\output_1.txt", example1);
//-----------------------------------------------------------

//---------------------input_2------------------------------
        Calculator example2 = new Calculator();
        DataFromFile data2 = new DataFromFile("E:\\input_2.txt");
        DataToFile dataTo2 = new DataToFile();

        example2.Calculation(data2);
        System.out.println(example2.getResult().toString());

        dataTo2.createFile("E:\\\\output_2.txt");
        dataTo2.writeInfoFile("E:\\\\output_2.txt", example2);
//-----------------------------------------------------------

//---------------------input_3------------------------------
        Calculator example3 = new ComplicatedCalculator();
        DataFromFile data3 = new DataFromFile("E:\\input_3.txt");
        DataToFile dataTo3 = new DataToFile();

        example3.Calculation(data3);
        System.out.println(example3.getResult().toString());

        dataTo3.createFile("E:\\\\output_3.txt");
        dataTo3.writeInfoFile("E:\\\\output_3.txt", example3);
//-----------------------------------------------------------
    }
}
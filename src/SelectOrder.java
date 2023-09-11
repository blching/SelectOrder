import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class SelectOrder {
    static class arguments {
        public String name;
        public String arg;
        public String num;

        public String toString() {
            return name + ", " + arg + ", " + num;
        }
    }

    public static void error() {
        System.out.println("ERRROR");
        System.exit(0);
    } 

    public static void main(String[] args) throws IOException {
        ArrayList<ArrayList<String>> tuples = new ArrayList();

        if (args.length < 1) {
            error();
        }
     
        
        String fileName = args[0]; //Check if file exists
        File file = new File("./src/" + fileName);
        if (!file.exists()) {
            error();
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String topLine = reader.readLine();
        String columns[] = topLine.split("\\s+");
        ArrayList<String> column =  new ArrayList<String>(Arrays.asList(columns));

        String currentLine;
        while(reader.ready()) {
            currentLine = reader.readLine();
            String[] splitLine = currentLine.split("\\s+");
            ArrayList<String> tempTuple =  new ArrayList<String>(Arrays.asList(splitLine));
            tuples.add(tempTuple);
        }


        String where = args[1]; //Checks if second argument is where
        if (!where.equals("WHERE")) error();

        
        ArrayList<arguments> col_argument_value = new ArrayList<arguments>();
        

        int n = 2;
        
        while (!args[n].equals("ORDER_BY")) {
            String arg = args[n];
            String[] splitArguments = arg.split("\\.");
            arguments tempArguments = new arguments();

            tempArguments.name = splitArguments[0];
            tempArguments.arg = splitArguments[1];
            tempArguments.num = splitArguments[2];
            col_argument_value.add(tempArguments);
            
            n++;
        } 

        
        String sort_column = args[n+1];
        String sort_dirtion = args[n+2];
        
        System.out.println(column);
        System.out.println(tuples);
        System.out.println("Hello World!");

        for (arguments a : col_argument_value) {
            System.out.println(a);
        }
        reader.close();
    }
}

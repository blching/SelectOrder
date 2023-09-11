import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

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
        ArrayList<ArrayList<String>> passedTuples = new ArrayList();

        boolean dec, inc = false;

        if (args.length < 1) {
            error();
        }
     
        
        String fileName = args[0]; //Check if file exists
        File file = new File("./src/" + fileName);
        if (!file.exists()) {
            error();
        }
        BufferedReader reader = new BufferedReader(new FileReader(file)); 

        String topLine = reader.readLine(); //Reads file and seperates by all sapces
        String columns[] = topLine.split("\\s+");
        ArrayList<String> column =  new ArrayList<String>(Arrays.asList(columns));

        String currentLine; //Does same for tuples
        while(reader.ready()) {
            currentLine = reader.readLine();
            String[] splitLine = currentLine.split("\\s+");
            ArrayList<String> tempTuple =  new ArrayList<String>(Arrays.asList(splitLine));
            tuples.add(tempTuple);
        }


        String where = args[1]; //Checks if second argument is where
        if (!where.equals("WHERE")) error();

        ArrayList<arguments> col_argument_value = new ArrayList<arguments>(); //Gets all argument values and splits it
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

        
        String sort_column = args[n+1]; //Get which columnn to sort by

        String sort_direction = args[n+2]; //Get which direction to sort by
        if (sort_direction.equals("DESC")) { dec = true; } else if (sort_direction.equals("ASC")) {
            inc = true;
        } else { error(); }
        
        //Search through each argument and filter the tuples
        for (arguments a : col_argument_value) {
            //Get arguments from input
            String columnName = a.name;
            String arg = a.arg;
            int num = Integer.valueOf(a.num);

            //Translate to current catagories  
            int columnNum = column.indexOf(columnName);

            for (ArrayList<String> t : tuples) { //Checks all tuples for chose column and checks if it passes requirements
                String compareValueString = t.get(columnNum); 
                int compareValueInt = Integer.valueOf(compareValueString);
                if (arg.equals("eq") && compareValueInt == num) passedTuples.add(t);
                if (arg.equals("lt") && compareValueInt < num) passedTuples.add(t);
                if (arg.equals("gt") && compareValueInt > num) passedTuples.add(t);
                if (arg.equals("le") && compareValueInt <= num) passedTuples.add(t);
                if (arg.equals("le") && compareValueInt >= num) passedTuples.add(t);
            }
                tuples.clear();
                tuples.addAll(passedTuples);
                passedTuples.clear();
        }

        System.out.println(column);
        System.out.println(tuples);
        System.out.println(sort_column);

        for (arguments a : col_argument_value) {
            System.out.println(a);
        }
        reader.close();
    }
}

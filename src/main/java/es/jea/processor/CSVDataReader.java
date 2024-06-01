package es.jea.processor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map.Entry;

public class CSVDataReader implements DataReader{
    private static final String DEFAULT_SEPARATOR = ",";
    private String filename;
    private String separator;
    private boolean open = false;
    private boolean close = false;
    private BufferedReader br;

    public CSVDataReader(String filename, String separator){
        this.filename = filename;
        this.separator = separator;
    }

    public CSVDataReader(String filename){
        this.filename = filename;
        this.separator = DEFAULT_SEPARATOR;
    }


    @Override
    public void open() {
        try {
            br = new BufferedReader(new FileReader(filename));
            br.readLine(); //read the header line
        } catch (FileNotFoundException e) {
            System.err.println("File " + filename + " not found");
        } catch (IOException e) {
            System.err.println("Exception reading file " + filename);
            e.printStackTrace();
        }
        open = true;

        
    }

    @Override
    public Entry<Integer, Float> readValues() {
        if (open && !close){
            try{
               String line;
               synchronized(br){
                   line = br.readLine();
               }
               return getValuesFromLine(line);

            } catch (IOException e) {
               System.err.println("Exception reading file " + filename);
               e.printStackTrace();
               return null;   
            }
        }else {
            System.err.println("Reader could not read values because it is not open or already closed");
            return null;
        }
    }

    private Entry<Integer, Float> getValuesFromLine(String line){
        
        if (line != null){
            String[] values = line.split(separator);

            int intValue = Integer.parseInt(values[0]);
            float floatValue = Float.parseFloat(values[1]);

            return new AbstractMap.SimpleEntry<>(intValue, floatValue);
        } else {
            return null;
        }

    }

    @Override
    public void close() {
        try{
            br.close();
        } catch (IOException e) {
            System.err.println("Exception closing data reader");
            e.printStackTrace();
        }
        open = false;
        close = true;
        
    }
    

}

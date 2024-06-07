package es.jea.processor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVResultsWriter implements ResultWriter {

    private static final String DEFAULT_SEPARATOR = ",";
    private String filename;
    private String separator;
    private boolean open = false;
    private boolean close = false;
    private BufferedWriter bw;

    public CSVResultsWriter(String filename, String separator){
        this.filename = filename;
        this.separator = separator;
    }

    public CSVResultsWriter(String filename){
        this.filename = filename;
        this.separator = DEFAULT_SEPARATOR;
    }

    @Override
    public void open() {
        bw = new BufferedWriter(new FileWriter(filename, true));
        bw.write("#sum"+separator+"#prod\n"); //write header line
        open = true;
    }

    
    @Override
    public void writeValues(float sum, float prod) throws Exception {
        if (open && !close){
            String line = sum + "," + prod + "\n";
            synchronized(bw){
                bw.write(line);
            }
        }else{
            throw new Exception("ResultsWriter for " + filename + " is not open or is already closed");
        }
    }

    @Override
    public void close() {
        try{
            bw.close();
        } catch (IOException e) {
            System.err.println("Exception closing data reader");
            e.printStackTrace();
        }
        open = false;
        close = true;
    }
}

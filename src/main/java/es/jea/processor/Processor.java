package es.jea.processor;

import java.util.Map.Entry;

/**
 * Thread that performs the data processing
 */
public class Processor implements Runnable{

    private DataReader reader;
    private ResultWriter writer;

    public Processor (DataReader reader, ResultWriter writer){
        this.reader = reader;
        this.writer = writer;
    }

    
    @Override
    public void run() {
        // Read values form the reader and perform the computations 
        // until no data is read.
        try{
            Entry<Integer, Float> pair = reader.readValues();
            while (pair != null){ // null indicates no more data to read
                writer.writeValues(sumValues(pair), multiplyValues(pair));
                pair = reader.readValues();
            }
        }catch (Exception e){

        }
    }
    
    private Float sumValues(Entry<Integer, Float> pair){
        return pair.getKey() + pair.getValue();
    }

    private Float multiplyValues(Entry<Integer, Float> pair){
        return pair.getKey() * pair.getValue();
    }
    

}

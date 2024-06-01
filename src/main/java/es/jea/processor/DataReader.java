package es.jea.processor;

import java.util.Map.Entry;
/**
 * Interface for implementing a Data reader
 */
public interface DataReader {
    /**
     * Open the reader.
     */
    public void open();
    
    /**
     * Read the values from the reader
     * @return Espected data as a tuple of Integer and Float values. Nulll if no more data to read.
     */
    public Entry<Integer, Float> readValues();

    /**
     * Close de reader.
     */
    public void close();

}

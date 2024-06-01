package es.jea.processor;
/**
 * Interface to store the computation results
 */
public interface ResultWriter {
    /**
     * Open the writer.
     */
     public void open();
    
    /**
     * Writes the computations results.
     * @param sum Result of the sum.
     * @param prod Result of the product.
     * @throws Exception If reader is not open or already close or other problem while reading.
     */
    public void writeValues(float sum, float prod) throws Exception;
    
    /**
     * Close the writer.
     */
    public void close();
}

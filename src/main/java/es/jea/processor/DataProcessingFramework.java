package es.jea.processor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main class of the Data Processing function-
 */
public class DataProcessingFramework {
    private static final long TIMEOUT=20L;
    private ExecutorService executorService;
    private DataReader reader;
    private ResultWriter writer;
    private int threads;
    /**
     * Class Constructor.
     * 
     * @param reader Reader to get the values to process.
     * @param writer Writer where to store the results.
     * @param threads Number of threads to perform the parallel processing.
     */
    public DataProcessingFramework(DataReader reader, ResultWriter writer, int threads){
        this.reader = reader;
        this.writer = writer;
        this.threads = threads;
        executorService = Executors.newFixedThreadPool(threads);
    }

    /**
     * Start the processing of Data.
     */
    public void start(){
        reader.open();
        writer.open();
        for (int i = 0; i < this.threads; i++ ){
            executorService.execute(new Processor(reader, writer));
        }
    }

    
    /**
     * Wait to the end of the computation.
     *   
     * @throws InterruptedException
     */
    public void awaitTermination() throws InterruptedException{
        executorService.shutdown();
        /*
         * Shutdown schedules the stop once the current tasks are completed. I do not wait for the termination. 
         * The while below is to ensure all tasks are finished and executi service shutdowns.
         */ 
        while (!executorService.isTerminated()){
            executorService.awaitTermination(TIMEOUT, TimeUnit.SECONDS);
        }
        reader.close();;
        writer.close();
    }

    /**
     *  Executed the data processing funtion:
     * @param args Array of arguments expected: InputFile, OutputFile, number of threads.
     */
    public static void main(String[] args) {
        if (args.length < 3 || args.length > 4){
            System.err.println("Incorrect number of arguments <inputFile> <outputFile> [<numThreads>]");
            System.exit(1);
        }
        String inputFile = args[1];
        String outputFile = args[2];
        int threads = Runtime.getRuntime().availableProcessors();
        if (args.length == 4){
            // Threads provided by the user
            threads = Integer.parseInt(args[3]);
        }
        DataProcessingFramework dpf = new DataProcessingFramework(new CSVDataReader(inputFile), new CSVResultsWriter(outputFile), threads );
        System.out.println("Starting processing of " + inputFile);
        dpf.start();
        try {
            dpf.awaitTermination();
        } catch (InterruptedException e) {
            System.err.println("Executor interrupted");
        }

        System.out.println("Processing finished");
    }
}
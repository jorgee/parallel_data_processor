# Shared-memory data processor
Simple Java thread-based parallel data processor which read data from a CSV file and computes the sum and product of the read values.

## Compilation
To compile the code you require to install [maven](https://maven.apache.org/install). Once you have Maven installed run the following command:

```
mvn clean package
```
## Execution
To run the code use the 'run.sh' script indicating the location of the input CSV file, the location of the output file, and the number of threads to perform the computation.

```
run.sh <input_file> <output_file> <num_threads>
```



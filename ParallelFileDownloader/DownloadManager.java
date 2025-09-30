import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class DownloadManager {
    // responsible for breaking the file into chunks and configuring different workers to download chunks independently

    private int numChunks;
    private RandomAccessFile desRaf;
    private RandomAccessFile srcRaf;


    public DownloadManager(int numChunks, RandomAccessFile srcRaf, RandomAccessFile desRaf) {
        this.numChunks = numChunks;
        this.desRaf = desRaf;
        this.srcRaf = srcRaf;
    }

    public void download() throws IOException, InterruptedException {
        long fileSize = srcRaf.length();
        long chunksize = fileSize / numChunks;
        if (chunksize > Integer.MAX_VALUE) {
            // set numOfChunks won't work for streaming, will have to increase the number of threads working in tandem
            while (fileSize / numChunks > Integer.MAX_VALUE) {
                numChunks++;
            }
            chunksize = fileSize/numChunks;
            System.out.printf("Incremented numChunks to %s since the previous value couldn't accommodate the file of this size", numChunks);
        }

        ExecutorService executorService = Executors.newFixedThreadPool(numChunks);
        Chunk curr;

        List<Future> futures = new ArrayList<>();
        for (int i = 0; i < numChunks; i++) {
            curr = new Chunk(i);
            curr.setStartByte(i * chunksize);
            curr.setEndByte((i == numChunks - 1) ? fileSize : (curr.getStartByte() + chunksize));
            futures.add(executorService.submit(new ChunkReaderWriter(srcRaf, desRaf, curr)));
        }

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("File downloaded");
        for (Future future : futures) {
            System.out.println(future.isDone());
        }
    }

}

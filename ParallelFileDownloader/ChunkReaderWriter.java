import java.io.IOException;
import java.io.RandomAccessFile;

public class ChunkReaderWriter implements Runnable {
    private RandomAccessFile daf;  // destination
    private RandomAccessFile saf; // source
    private Chunk chunk;

    public ChunkReaderWriter(RandomAccessFile saf, RandomAccessFile daf, Chunk chunk) {
        this.saf = saf;
        this.daf = daf;
        this.chunk = chunk;
    }

    public void run() {
        // write chunk
        try {
            byte[] buffer = new byte[(int) (chunk.getEndByte() - chunk.getStartByte())];
            synchronized (saf) {
                saf.seek(chunk.getStartByte());
                saf.readFully(buffer);
            }
            synchronized (daf) {
                daf.seek(chunk.getStartByte());
                daf.write(buffer);
            }
            System.out.printf("Written %d bytes(chunk id : %d) to pos %d\n", buffer.length, chunk.getChunkId(), chunk.getStartByte());
        } catch (IOException e) {
            System.out.printf("Failure writing chunk %d, error : %s\n", chunk.getChunkId(), e.getMessage());
            throw new RuntimeException(e);
        }

    }
}

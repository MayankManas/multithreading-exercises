public class Chunk {
    private int chunkId; // for logging (optional)
    private long startByte;
    private long endByte;

    public Chunk(int chunkId) {
        this.chunkId = chunkId;
    }

    public int getChunkId() {
        return chunkId;
    }

    public long getStartByte() {
        return startByte;
    }

    public void setStartByte(long startByte) {
        this.startByte = startByte;
    }

    public long getEndByte() {
        return endByte;
    }

    public void setEndByte(long endByte) {
        this.endByte = endByte;
    }
}

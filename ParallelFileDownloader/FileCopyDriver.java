import java.io.File;
import java.io.RandomAccessFile;
import java.util.Objects;

public class FileCopyDriver {
    public static void main(String[] args) throws Exception {
        // Example usage:
        // java FileCopyDriver "C:\\bigfiles\\input.dat" "C:\\bigfiles\\output.dat" 4

        if (args.length < 2) {
            System.out.println("Usage: java FileCopyDriver <sourceFile> <destinationFile> <numChunks>");
            return;
        }

        String sourcePath = args[0];
        String destinationPath = args[1];
        int numChunks = (args.length == 3 && Objects.nonNull(args[2])) ? Integer.parseInt(args[2]) : 12;
        System.out.printf("Using %d threads for copying file", numChunks);
        copy(sourcePath, destinationPath, numChunks);
//        benchmarkCopy(sourcePath, destinationPath);
    }

    private static void copy(String sourcePath, String destinationPath, int numChunks) throws Exception {
        long startTime = System.nanoTime();
        File sourceFile = new File(sourcePath);
        File destFile = new File(destinationPath);
        try {

            // Ensure destination file exists and is same size as source
            if (destFile.isDirectory()) {
                String sourceName = sourceFile.getName();
                destFile = new File(destFile, sourceName + ".copy");
            }

            destFile.setWritable(true);

            try (RandomAccessFile srcRaf = new RandomAccessFile(sourceFile, "r");
                 RandomAccessFile desRaf = new RandomAccessFile(destFile, "rw")) {

                desRaf.setLength(srcRaf.length()); // allocate space in dest file

                DownloadManager manager = new DownloadManager(numChunks, srcRaf, desRaf);
                manager.download();

                System.out.println("✅ File copy complete.");


            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long endTime = System.nanoTime();
            double seconds = (endTime - startTime) / 1_000_000_000.0;
            System.out.printf("⏱ Time taken with %d chunks: %.2f seconds%n", numChunks, seconds);


            // === MD5 Validation ===
            String srcMd5 = ChecksumUtil.calculateMD5(sourceFile);
            String destMd5 = ChecksumUtil.calculateMD5(destFile);

            System.out.println("Source MD5      : " + srcMd5);
            System.out.println("Destination MD5 : " + destMd5);

            if (srcMd5.equals(destMd5)) {
                System.out.println("✅ Integrity check passed. Files are identical.");
            } else {
                System.out.println("❌ Integrity check failed. Files differ!");
            }
        }
    }

    /**
     * Run the same copy multiple times with different chunk counts
     * to see how throughput changes with multithreading.
     */
    private static void benchmarkCopy(String sourcePath, String destinationPath) throws Exception {
        int[] chunkOptions = {1, 2, 4, 8, 12, 16};

        System.out.println("==== Benchmarking File Copy with Different Thread Counts ====");
        for (int chunks : chunkOptions) {
            System.out.printf("%n--- Running with %d chunks --- %n", chunks);
            copy(sourcePath, destinationPath + ".chunks" + chunks, chunks);
        }
    }
}

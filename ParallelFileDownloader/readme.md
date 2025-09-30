# Multi-Threaded File Copier

A Java-based multi-threaded file copy utility that efficiently copies large files using multiple threads. It supports splitting files into chunks and writing them in parallel, while ensuring data integrity through MD5 checksums.

This tool is designed to handle very large files (larger than 2 GB) and can dynamically adjust the number of threads for optimal performance.

---

## Features

- **Multi-threaded file copy**: Split files into chunks and copy them in parallel using multiple threads.
- **Streaming for large files**: Reads and writes in 8 MB blocks to handle files larger than 2 GB without memory issues.
- **Dynamic chunk adjustment**: Automatically increases the number of threads if the file size exceeds the limit of a single chunk.
- **Data integrity verification**: Validates the copied file by comparing MD5 checksums of source and destination files.
- **Benchmark mode**: Test different numbers of threads to find the optimal configuration for file copy throughput.

---

## Classes Overview

- **`FileCopyDriver`**: Main driver class. Parses command-line arguments, initializes the copy process, and verifies integrity using MD5.
- **`DownloadManager`**: Handles splitting the file into chunks and managing thread pools for parallel copying.
- **`ChunkReaderWriter`**: Runnable class responsible for copying a specific chunk of the file.
- **`Chunk`**: Represents a chunk of the file with start and end byte positions.
- **`ChecksumUtil`**: Utility class to compute MD5 hash for files to validate integrity.

---

## Usage

### Command Line

```bash
  java FileCopyDriver <sourceFile> <destinationFile> [numChunks]
```
Also supports benchmarking different thread counts and their respective throughputs.

``` 
FileCopyDriver.benchmarkCopy(sourcePath, destinationPath);
```

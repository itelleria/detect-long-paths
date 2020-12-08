package demos.detectlongpaths;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.isDirectory;

public class FileExplorer {

    private final int pathLimit;

    private final int filenameLimit;

    public FileExplorer(int pathLimit, int filenameLimit) {
        this.pathLimit = pathLimit;
        this.filenameLimit = filenameLimit;
    }

    public ScanResult findFilesWithLongName(String dir) throws IOException {
        ScanResult scanResult = new ScanResult();

        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (!isDirectory(file)) {

                    if (file.getFileName().toString().length() >= filenameLimit) {
                        scanResult.addLongNameFile(file);
                    } else if (file.toString().length() >= pathLimit) {
                        scanResult.addLongPathFile(file);
                    }

                }
                return FileVisitResult.CONTINUE;
            }

        });

        return scanResult;
    }

    public static class ScanResult {

        private final List<String> longPathFileList = new ArrayList<>();

        private final List<String> longNameFileList = new ArrayList<>();

        void addLongPathFile(Path file) {
            this.longPathFileList.add(file.toString());
        }

        void addLongNameFile(Path file) {
            this.longNameFileList.add(file.toString());
        }

        public List<String> getLongNameFileList() {
            return longNameFileList;
        }

        public List<String> getLongPathFileList() {
            return longPathFileList;
        }

    }

}

package demos.detectlongpaths;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Set;

import static java.nio.file.Files.isDirectory;

public class FileExplorer {

    private final int limit;

    public FileExplorer(int limit) {
        this.limit = limit;
    }

    public Set<String> findFilesWithLongName(String dir) throws IOException {
        Set<String> fileList = new HashSet<>();

        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (!isDirectory(file) && file.toString().length() >= limit) {
                    fileList.add(file.toString());
                }
                return FileVisitResult.CONTINUE;
            }

        });

        return fileList;
    }

}

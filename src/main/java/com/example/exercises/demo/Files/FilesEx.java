package com.example.exercises.demo.Files;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.Stream;

@Component
public class FilesEx {

    public static void exercises() {

        Path path = Path.of("");
        System.out.println("cwd = " + path.toAbsolutePath());

        try (Stream<Path> paths = Files.list(path)) {
            System.out.println("Listing files in directory: " + path);
            paths
                    .peek(p -> System.out.println("Found path: " + p))
                    .map(FilesEx::listDir)
                    .peek(result -> System.out.println("Processed path: " + result))
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("---------------------------------------");

        try (Stream<Path> paths = Files.walk(path, 2)) {
            System.out.println("Walking through directory with depth 2: " + path);
            paths
                    .peek(p -> System.out.println("Found path: " + p))
                    .filter(Files::isRegularFile)
                    .peek(p -> System.out.println("Filtered regular file: " + p))
                    .map(FilesEx::listDir)
                    .peek(result -> System.out.println("Processed path: " + result))
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("---------------------------------------");

        try (Stream<Path> paths = Files.find(path, Integer.MAX_VALUE,
                (p, attr) -> attr.isRegularFile() && attr.size() > 300)) {
            System.out.println("Finding files larger than 300 bytes: " + path);
            paths
                    .peek(p -> System.out.println("Found path: " + p))
                    .map(FilesEx::listDir)
                    .peek(result -> System.out.println("Processed path: " + result))
                    .forEach(System.out::println);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        path = path.resolve(".idea");
        System.out.println("==============Directory Stream for XML files==============");

        try (var dirs = Files.newDirectoryStream(path, "*.xml")) {
            System.out.println("Listing XML files in directory: " + path);
            dirs.forEach(d -> {
                System.out.println("Found XML file: " + d);
                System.out.println("Processed path: " + FilesEx.listDir(d));
            });

        } catch (IOException e) {
            throw new RuntimeException(e);

        }

        System.out.println("==============Directory Stream with custom filter==============");

        try (var dirs = Files.newDirectoryStream(path,
                p -> p.getFileName().toString().endsWith(".xml")
                        && Files.isRegularFile(p) && Files.size(p) > 1000)) {
            System.out.println("Listing filtered XML files in directory: " + path);
            dirs.forEach(d -> {
                System.out.println("Found filtered XML file: " + d);
                System.out.println("Processed path: " + FilesEx.listDir(d));
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String listDir(Path path) {

        try {
            boolean isDir = Files.isDirectory(path);
            FileTime dateField = Files.getLastModifiedTime(path);
            LocalDateTime modDT = LocalDateTime.ofInstant(
                    dateField.toInstant(), ZoneId.systemDefault());
            String result = "%tD %tT %-5s %12s %s"
                    .formatted(modDT, modDT, (isDir ? "<DIR>" : ""),
                            (isDir ? "" : Files.size(path)), path);
            System.out.println("listDir result: " + result);
            return result;
        } catch (IOException e) {
            System.out.println("Whoops! Something went wrong with " + path);
            return path.toString();
        }
    }
}

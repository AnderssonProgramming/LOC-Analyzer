package edu.escuelaing.arem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/** * LOC Counter - A program to count lines of code in source files.
 * This class provides methods to count physical lines (phy) and 
 * lines of code excluding comments and blank lines (loc).
 * 
 * @author Andersson Sánchez
 * @version 2025
 */
public class LOCCounter {

    /**
     * Counts the physical lines in a file.
     * 
     * @param filePath the path to the file
     * @return the number of physical lines in the file
     * @throws IOException if an I/O error occurs
     */
    public static int countPhysicalLines(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return (int) Files.lines(path).count();
    }
    
    /**
     * Counts the lines of code (excluding comments and blank lines) in a file.
     * 
     * @param filePath the path to the file
     * @return the number of code lines in the file
     * @throws IOException if an I/O error occurs
     */
    public static int countLOC(String filePath) throws IOException {
        int locCount = 0;
        boolean inMultiLineComment = false;
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
                // Trim the line to remove leading and trailing whitespace
                line = line.trim();
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue;
                }
                
                // Handle multi-line comments
                if (inMultiLineComment) {
                    if (line.contains("*/")) {
                        inMultiLineComment = false;
                        // Check if there's code after the end of comment
                        String afterComment = line.substring(line.indexOf("*/") + 2).trim();
                        if (!afterComment.isEmpty() && !afterComment.startsWith("//")) {
                            locCount++;
                        }
                    }
                    continue;
                }
                
                // Check for single-line comment at the beginning
                if (line.startsWith("//")) {
                    continue;
                }
                
                // Check for multi-line comment at the beginning
                if (line.startsWith("/*")) {
                    inMultiLineComment = true;
                    // Check if the multi-line comment ends on the same line
                    if (line.contains("*/")) {
                        inMultiLineComment = false;
                        // Check if there's code after the end of comment
                        String afterComment = line.substring(line.indexOf("*/") + 2).trim();
                        if (!afterComment.isEmpty() && !afterComment.startsWith("//")) {
                            locCount++;
                        }
                    }
                    continue;
                }
                
                // Check for single-line comment in the middle of a line
                if (line.contains("//")) {
                    // Get the part before the comment
                    String beforeComment = line.substring(0, line.indexOf("//")).trim();
                    if (!beforeComment.isEmpty()) {
                        locCount++;
                    }
                    continue;
                }
                
                // Check for multi-line comment in the middle of a line
                if (line.contains("/*")) {
                    inMultiLineComment = true;
                    // Check if the multi-line comment ends on the same line
                    if (line.contains("*/")) {
                        inMultiLineComment = false;
                        // Get the parts before and after the comment
                        String beforeComment = line.substring(0, line.indexOf("/*")).trim();
                        String afterComment = line.substring(line.indexOf("*/") + 2).trim();
                        
                        if (!beforeComment.isEmpty() || (!afterComment.isEmpty() && !afterComment.startsWith("//"))) {
                            locCount++;
                        }
                    } else {
                        // If there's code before the comment, count it
                        String beforeComment = line.substring(0, line.indexOf("/*")).trim();
                        if (!beforeComment.isEmpty()) {
                            locCount++;
                        }
                    }
                    continue;
                }
                
                // If we get here, the line contains code
                locCount++;
            }
        }
        
        return locCount;
    }
    
    /**
     * Finds all files matching a pattern in a directory and its subdirectories.
     * 
     * @param directory the root directory to search in
     * @param pattern the file pattern to match (wildcard)
     * @return a list of file paths matching the pattern
     */
    public static List<String> findFiles(String directory, String pattern) {
        List<String> matchingFiles = new ArrayList<>();
        
        // Convert wildcard pattern to regex
        String regex = pattern
                .replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".");
        
        try {
            matchingFiles = Files.walk(Paths.get(directory))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .filter(path -> path.matches(".*" + regex))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error searching for files: " + e.getMessage());
        }
        
        return matchingFiles;
    }
    
    /**
     * Processes a file or directory with a pattern.
     * 
     * @param pathOrPattern the file path, directory, or pattern
     * @param countType the type of count ("phy" or "loc")
     * @throws IOException if an I/O error occurs
     */
    public static void processPath(String pathOrPattern, String countType) throws IOException {
        File file = new File(pathOrPattern);
        
        if (file.isFile()) {
            // Process a single file
            int count;
            if ("phy".equalsIgnoreCase(countType)) {
                count = countPhysicalLines(pathOrPattern);
                System.out.println("Physical lines in " + pathOrPattern + ": " + count);
            } else if ("loc".equalsIgnoreCase(countType)) {
                count = countLOC(pathOrPattern);
                System.out.println("Lines of code in " + pathOrPattern + ": " + count);
            } else {
                System.out.println("Invalid count type. Use 'phy' or 'loc'.");
            }
        } else if (file.isDirectory()) {
            // Process all files in directory
            System.out.println("Processing all files in directory: " + pathOrPattern);
            
            List<String> allFiles = Files.walk(Paths.get(pathOrPattern))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            
            for (String filePath : allFiles) {
                processPath(filePath, countType);
            }
        } else {
            // Check if it's a pattern with wildcards
            if (pathOrPattern.contains("*") || pathOrPattern.contains("?")) {
                String directory = "."; // Current directory by default
                String pattern = pathOrPattern;
                
                // Extract directory from pattern if present
                int lastSlashIndex = pathOrPattern.lastIndexOf(File.separator);
                if (lastSlashIndex >= 0) {
                    directory = pathOrPattern.substring(0, lastSlashIndex);
                    pattern = pathOrPattern.substring(lastSlashIndex + 1);
                }
                
                List<String> matchingFiles = findFiles(directory, pattern);
                
                if (matchingFiles.isEmpty()) {
                    System.out.println("No files matching the pattern were found.");
                } else {
                    for (String filePath : matchingFiles) {
                        processPath(filePath, countType);
                    }
                }
            } else {
                System.out.println("File not found: " + pathOrPattern);
            }
        }
    }
}

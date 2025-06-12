package edu.escuelaing.arem;

import java.io.IOException;

/** * LOC Analyzer - A command-line tool for counting lines of code in source files.
 * This program can count physical lines (phy) or lines of code (loc) excluding 
 * comments and blank lines.
 * 
 * Usage:
 *   countlines phy|loc &lt;file-path-or-pattern&gt;
 * 
 * Examples:
 *   countlines phy App.java
 *   countlines loc src/*.java
 */
public class App {
    
    /**
     * The main entry point for the LOC Analyzer application.
     * 
     * @param args command line arguments (countType and file/pattern)
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Usage: countlines phy|loc <file_or_pattern>");
            System.out.println("  phy: Count physical lines in the source code");
            System.out.println("  loc: Count lines of code (excluding comments and blank lines)");
            System.exit(1);
        }
        
        String countType = args[0].toLowerCase();
        String pathOrPattern = args[1];
        
        if (!countType.equals("phy") && !countType.equals("loc")) {
            System.out.println("Invalid count type. Use 'phy' or 'loc'.");
            System.exit(1);
        }
        
        try {
            LOCCounter.processPath(pathOrPattern, countType);
        } catch (IOException e) {
            System.err.println("Error processing file(s): " + e.getMessage());
            System.exit(1);
        }
    }
}

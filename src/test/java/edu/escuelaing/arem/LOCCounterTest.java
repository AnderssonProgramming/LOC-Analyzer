package edu.escuelaing.arem;

import junit.framework.TestCase;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Unit tests for the LOC Counter.
 */
public class LOCCounterTest extends TestCase {
    
    private static final String TEST_DIR = "src/test/resources";
    private File testJavaFile;
    private File testEmptyFile;
    private File testCommentsFile;
    private File testMixedFile;
    
    /**
     * Set up test files.
     */
    @Override
    protected void setUp() throws Exception {
        // Create test directory if it doesn't exist
        File dir = new File(TEST_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        // Create test files
        testJavaFile = new File(TEST_DIR + "/TestJavaFile.java");
        testEmptyFile = new File(TEST_DIR + "/TestEmptyFile.java");
        testCommentsFile = new File(TEST_DIR + "/TestCommentsFile.java");
        testMixedFile = new File(TEST_DIR + "/TestMixedFile.java");
        
        // Create a simple Java file
        try (FileWriter writer = new FileWriter(testJavaFile)) {
            writer.write("package edu.escuelaing.arem;\n\n");
            writer.write("public class TestClass {\n");
            writer.write("    private int number;\n\n");
            writer.write("    public TestClass(int number) {\n");
            writer.write("        this.number = number;\n");
            writer.write("    }\n\n");
            writer.write("    public int getNumber() {\n");
            writer.write("        return number;\n");
            writer.write("    }\n");
            writer.write("}\n");
        }
        
        // Create an empty file
        try (FileWriter writer = new FileWriter(testEmptyFile)) {
            writer.write("");
        }
        
        // Create a file with only comments
        try (FileWriter writer = new FileWriter(testCommentsFile)) {
            writer.write("// This is a comment\n");
            writer.write("// Another comment\n\n");
            writer.write("/* Multi-line comment\n");
            writer.write(" * Second line\n");
            writer.write(" * Third line\n");
            writer.write(" */\n\n");
            writer.write("// Final comment\n");
        }
        
        // Create a file with mixed code and comments
        try (FileWriter writer = new FileWriter(testMixedFile)) {
            writer.write("package edu.escuelaing.arem;\n\n");
            writer.write("/**\n");
            writer.write(" * This is a Javadoc comment\n");
            writer.write(" */\n");
            writer.write("public class MixedClass { // Class declaration\n\n");
            writer.write("    // Field declaration\n");
            writer.write("    private String name;\n\n");
            writer.write("    /* Constructor with parameter\n");
            writer.write("     * @param name - the name parameter\n");
            writer.write("     */\n");
            writer.write("    public MixedClass(String name) {\n");
            writer.write("        this.name = name; // Assign name\n");
            writer.write("    }\n\n");
            writer.write("    // Get method\n");
            writer.write("    public String getName() {\n");
            writer.write("        return name; /* Return the name */\n");
            writer.write("    }\n");
            writer.write("} // End of class\n");
        }
    }
    
    /**
     * Clean up test files.
     */
    @Override
    protected void tearDown() throws Exception {
        // Delete test files
        testJavaFile.delete();
        testEmptyFile.delete();
        testCommentsFile.delete();
        testMixedFile.delete();
    }    /**
     * Test counting physical lines.
     */
    public void testCountPhysicalLines() throws IOException {
        assertEquals(13, LOCCounter.countPhysicalLines(testJavaFile.getPath()));
        assertEquals(0, LOCCounter.countPhysicalLines(testEmptyFile.getPath()));
        assertEquals(9, LOCCounter.countPhysicalLines(testCommentsFile.getPath()));
        assertEquals(22, LOCCounter.countPhysicalLines(testMixedFile.getPath()));
    }    /**
     * Test counting lines of code.
     */
    public void testCountLOC() throws IOException {
        assertEquals(10, LOCCounter.countLOC(testJavaFile.getPath()));
        assertEquals(0, LOCCounter.countLOC(testEmptyFile.getPath()));
        assertEquals(0, LOCCounter.countLOC(testCommentsFile.getPath()));
        assertEquals(10, LOCCounter.countLOC(testMixedFile.getPath()));
    }
    
    /**
     * Test file finding with patterns.
     */
    public void testFindFiles() throws IOException {
        // Create temporary directory with test files
        Path tempDir = Files.createTempDirectory("loc_test");
        
        File file1 = new File(tempDir.toString(), "test1.java");
        File file2 = new File(tempDir.toString(), "test2.java");
        File file3 = new File(tempDir.toString(), "test.txt");
        
        file1.createNewFile();
        file2.createNewFile();
        file3.createNewFile();
        
        // Test finding .java files
        assertEquals(2, LOCCounter.findFiles(tempDir.toString(), "*.java").size());
        
        // Test finding specific files
        assertEquals(1, LOCCounter.findFiles(tempDir.toString(), "test1.java").size());
        
        // Clean up
        file1.delete();
        file2.delete();
        file3.delete();
        tempDir.toFile().delete();
    }
}

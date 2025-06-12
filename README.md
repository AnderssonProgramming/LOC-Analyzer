# LOC Analyzer

A Java command-line tool for counting lines of code in source files. This project follows the requirements for the "INTRODUCTION TO JAVA, MVN, AND GIT: LOC Counting" assignment.

*You can find the source file (pdf) in [LOC Counter](<src/site/resources/1. EnunciadoTareaMVNGit-LOC.pdf>)*

## Description

LOC Analyzer is a command-line utility that counts either physical lines (phy) or lines of code (loc) in source files. It can analyze individual files or recursively process directories and supports wildcard patterns.

* Physical lines (phy): All lines in the file, including blank lines and comments.
* Lines of code (loc): Only lines containing actual code, excluding blank lines and comments.

## Getting Started

### Prerequisites

* Java 8 or higher
* Maven 3.6 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/AnderssonProgramming/LOC-Analyzer.git
   ```

2. Build the project with Maven:
   ```bash
   cd LOC-Analyzer
   mvn clean package
   ```

### Usage

To count lines in a source file, use the following Java command:

```bash
java -cp target/psp0-1.0-SNAPSHOT-jar-with-dependencies.jar edu.escuelaing.arem.App phy|loc <file_or_pattern>
```

Alternatively, you can use the provided scripts (note that you might need to adjust permissions):

```bash
# On Windows
countlines.bat phy|loc <file_or_pattern>
countlines.ps1 phy|loc <file_or_pattern>

# On Unix/Linux/macOS
./countlines.sh phy|loc <file_or_pattern>
```

Examples:
```bash
# Count physical lines in a file
java -cp target/psp0-1.0-SNAPSHOT-jar-with-dependencies.jar edu.escuelaing.arem.App phy src/main/java/edu/escuelaing/arem/App.java

# Count code lines in a file
java -cp target/psp0-1.0-SNAPSHOT-jar-with-dependencies.jar edu.escuelaing.arem.App loc src/main/java/edu/escuelaing/arem/App.java

# Count lines in all Java files in a directory
java -cp target/psp0-1.0-SNAPSHOT-jar-with-dependencies.jar edu.escuelaing.arem.App loc "src/main/java/*.java"
```

**Note**: If you encounter issues with the batch or shell scripts, use the direct Java command method shown above. The scripts may require adjustments based on your operating system and environment. Common issues include execution policy restrictions in PowerShell or permission issues in Unix-like systems (which can be resolved with `chmod +x countlines.sh`).

## Class Diagram

```
┌─────────────┐           ┌────────────────────────┐
│    App      │           │  LOCCounter            │
├─────────────┤           ├────────────────────────┤
│             │           │                        │
├─────────────┤           ├────────────────────────┤
│ + main()    ├──────────>│ + countPhysicalLines() │
│             │           │ + countLOC()           │
│             │           │ + findFiles()          │
│             │           │ + processPath()        │
└─────────────┘           └────────────────────────┘
```

### Class Diagram Description

The application follows a simple architecture with two main classes:

1. **App Class**: The entry point of the application. It processes command-line arguments and delegates the actual counting to the LOCCounter class.

2. **LOCCounter Class**: Contains all the logic for counting lines of code, with the following methods:
   * `countPhysicalLines()`: Counts all lines in a file
   * `countLOC()`: Counts only lines containing code (excluding comments and blank lines)
   * `findFiles()`: Finds files matching a pattern in a directory and its subdirectories
   * `processPath()`: Processes a file path or a pattern to count lines

## Development Metrics

* Time Spent: 6 hours
* Lines of Code (LOC): 152 (App.java: 24 + LOCCounter.java: 128)
* Productivity: 25.33 LOC/h

## Running the Tests

To run the tests:

```bash
mvn test
```

## Built With

* [Java](https://www.java.com/) - Programming language
* [Maven](https://maven.apache.org/) - Dependency management and build tool
* [JUnit](https://junit.org/) - Testing framework

## POM.xml Dependencies and Plugins

The project uses Maven for dependency management and build automation. The following components are configured in the POM.xml file:

### Dependencies

* **JUnit (3.8.1)** - Used for unit testing the application.

### Plugins

* **Maven Compiler Plugin (3.8.1)** - Compiles the project source code using Java 8.
* **Maven Assembly Plugin (3.3.0)** - Creates a single JAR with all dependencies included, making the application easier to distribute and run.
* **Maven Javadoc Plugin (3.2.0)** - Generates Javadoc documentation for the project and packages it as a JAR.

These plugins ensure that the project can be easily built, tested, documented, and packaged for distribution with a single command (`mvn clean package`).

## Author

* Andersson Sánchez

## License

This project is licensed under the GPL-3.0 license - see the [LICENSE](LICENSE) file for details.

#!/bin/bash
# countlines.sh - Script to run the LOC-Analyzer

# Check if we have at least 2 arguments
if [ $# -lt 2 ]; then
    echo "Usage: countlines phy|loc <file_or_pattern>"
    echo "  phy: Count physical lines in the source code"
    echo "  loc: Count lines of code (excluding comments and blank lines)"
    exit 1
fi

# Run the LOC-Analyzer with the provided arguments
java -cp target/psp0-1.0-SNAPSHOT-jar-with-dependencies.jar edu.escuelaing.arem.App "$@"

@echo off
REM countlines.bat - Script to run the LOC-Analyzer

REM Check if we have at least 2 arguments
if "%~2"=="" (
    echo Usage: countlines phy^|loc ^<file_or_pattern^>
    echo   phy: Count physical lines in the source code
    echo   loc: Count lines of code (excluding comments and blank lines)
    exit /b 1
)

REM Get the absolute path to the jar file
set SCRIPT_DIR=%~dp0
set JAR_PATH=%SCRIPT_DIR%target\psp0-1.0-SNAPSHOT-jar-with-dependencies.jar

REM Run the LOC-Analyzer with the provided arguments
java -cp "%JAR_PATH%" edu.escuelaing.arem.App %*

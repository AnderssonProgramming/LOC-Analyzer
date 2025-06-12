@echo off
REM countlines.bat - Script to run the LOC-Analyzer

REM Check if we have at least 2 arguments
if "%~2"=="" (
    echo Usage: countlines phy^|loc ^<file_or_pattern^>
    echo   phy: Count physical lines in the source code
    echo   loc: Count lines of code (excluding comments and blank lines)
    exit /b 1
)

REM Set variables
set COUNT_TYPE=%~1
set FILE_PATTERN=%~2
set JAR_FILE=target\psp0-1.0-SNAPSHOT-jar-with-dependencies.jar

REM Check if JAR file exists
if not exist "%JAR_FILE%" (
    echo Error: JAR file not found: %JAR_FILE%
    echo Please build the project first with: mvn clean package
    exit /b 1
)

REM Run the LOC-Analyzer with the provided arguments
echo Running LOC analysis on %FILE_PATTERN% with type %COUNT_TYPE%...
java -cp "%JAR_FILE%" edu.escuelaing.arem.App %COUNT_TYPE% %FILE_PATTERN%

if %ERRORLEVEL% neq 0 (
    echo Error running the LOC Analyzer. Exit code: %ERRORLEVEL%
    exit /b %ERRORLEVEL%
)

exit /b 0

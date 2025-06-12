# countlines.ps1 - PowerShell Script to run the LOC-Analyzer

# Check if we have at least 2 arguments
param(
    [Parameter(Mandatory=$true)]
    [string]$countType,
    
    [Parameter(Mandatory=$true)]
    [string]$filePattern
)

# Validate count type
if ($countType -ne "phy" -and $countType -ne "loc") {
    Write-Host "Usage: .\countlines.ps1 phy|loc <file_or_pattern>"
    Write-Host "  phy: Count physical lines in the source code"
    Write-Host "  loc: Count lines of code (excluding comments and blank lines)"
    exit 1
}

# Set variables
$jarFile = "target\psp0-1.0-SNAPSHOT-jar-with-dependencies.jar"

# Check if JAR file exists
if (-not (Test-Path $jarFile)) {
    Write-Host "Error: JAR file not found: $jarFile"
    Write-Host "Please build the project first with: mvn clean package"
    exit 1
}

# Run the LOC-Analyzer with the provided arguments
Write-Host "Running LOC analysis on $filePattern with type $countType..."
java -cp "$jarFile" edu.escuelaing.arem.App $countType $filePattern

if ($LASTEXITCODE -ne 0) {
    Write-Host "Error running the LOC Analyzer. Exit code: $LASTEXITCODE"
    exit $LASTEXITCODE
}

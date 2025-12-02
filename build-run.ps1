# build-run.ps1
# PowerShell script to build and run Pokechess

# 1. Generate sources.txt
Write-Host "Generating sources.txt..."
Get-ChildItem -Recurse -Filter *.java | ForEach-Object { $_.FullName } | Set-Content sources.txt

# 2. Create output folder if it doesn't exist
if (-not (Test-Path "out")) {
    Write-Host "Creating out/ directory..."
    New-Item -ItemType Directory -Path out
}

# 3. Compile all Java sources
Write-Host "Compiling Java files..."
cmd /c 'javac -cp "lib\json.jar;src" -d out @sources.txt'

# 4. Run the Main class
Write-Host "Running program..."
cmd /c 'java -cp "out;lib\json.jar" game.Main'

Write-Host "Done!"

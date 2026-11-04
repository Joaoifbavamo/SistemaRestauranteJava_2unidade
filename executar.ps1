param([switch]$Demo, [switch]$Testar)
$ErrorActionPreference = 'Stop'
Set-Location $PSScriptRoot
$javac = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin\javac.exe' } else { 'javac' }
$java = if ($env:JAVA_HOME) { Join-Path $env:JAVA_HOME 'bin\java.exe' } else { 'java' }
New-Item -ItemType Directory -Force build | Out-Null
$fontes = @(Get-ChildItem src,test -Recurse -Filter '*.java' | ForEach-Object FullName)
& $javac --release 20 -encoding UTF-8 -Xlint:all -d build @fontes
if ($LASTEXITCODE -ne 0) { throw 'Falha na compilacao.' }
if ($Testar) { & $java -cp build restaurante.Testes }
elseif ($Demo) { & $java -cp build restaurante.app.Main --demo }
else { & $java -cp build restaurante.app.Main }
if ($LASTEXITCODE -ne 0) { throw 'Falha na execucao.' }

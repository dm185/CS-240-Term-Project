@echo off

if not exist javafx-sdk-18.0.1 (
@echo on
@echo "Installing JavaFX SDK ..."
@echo off
if not exist tmp.zip (
powershell -command Invoke-WebRequest https://download2.gluonhq.com/openjfx/18.0.1/openjfx-18.0.1_windows-x64_bin-sdk.zip -OutFile %CD%\tmp.zip
)
C:\WINDOWS\system32\tar.exe -xf tmp.zip
del /f tmp.zip
)

set JAVAFX_PATH=%CD%\javafx-sdk-18.0.1\lib


if not exist build (
mkdir build
)


javac -d build --module-path "%JAVAFX_PATH%" --add-modules javafx.graphics Main.java
javac -d build RoadNode.java
javac -d build Address.java  
javac -d build AddressQueue.java  
javac -d build BinarySearchTree.java  
javac -d build Connection.java

cd build
java --module-path "%JAVAFX_PATH%" --add-modules javafx.graphics Main
cd ..
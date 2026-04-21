Student Pauta Example in Java

Included files
--------------
- Student.java
- StudentList.java
- StudentFileHandler.java
- StudentDemo.java
- students_example.csv

What the program does
---------------------
1. Reads a CSV file with students (number,name)
2. Assigns a few example grades
3. Generates:
   - pauta.txt
   - pauta.csv
4. Serializes the full StudentList to:
   - students.bin
5. Reads the binary file again

How to compile
--------------
javac *.java

How to run
----------
java StudentDemo

Expected input format
---------------------
2021001,Ana Silva
2021002,Bruno Costa
2021003,Carla Sousa

Example TXT output
------------------
Data da pauta: 2026-01-20
Número | Nome | Nota
2021001 | Ana Silva | 14.0
2021002 | Bruno Costa | 16.5
2021003 | Carla Sousa | 12.0

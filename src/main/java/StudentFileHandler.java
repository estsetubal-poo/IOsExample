import java.io.*;
import java.time.LocalDate;

public class StudentFileHandler {
    private final StudentList list;

    public StudentFileHandler(StudentList list) {
        if (list == null) {
            throw new IllegalArgumentException("Student list cannot be null.");
        }
        this.list = list;
    }

    public void addStudentsFromFile(String filename) throws IOException {
        /**
          Não estamos a usar o classico BufferedReader reader = new BufferedReader(new FileReader(filename))
          e usamos InputStream porque o ficheiro está dentro do projeto (resources)
         e não num ficheiro normal do disco.”
         **/
        try ( InputStream is = getClass().getClassLoader().getResourceAsStream(filename); ){
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length != 2) {
                    throw new IOException("Invalid CSV format at line " + lineNumber + ": " + line);
                }

                try {
                    int number = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    list.addStudent(new Student(number, name));
                } catch (NumberFormatException e) {
                    throw new IOException(
                        "Invalid student number at line " + lineNumber + ": " + line, e
                    );
                } catch (IllegalArgumentException e) {
                    throw new IOException(
                        "Invalid student data at line " + lineNumber + ": " + e.getMessage(), e
                    );
                }
            }
        }
    }

    public void saveGradeReportTxt(String filename) throws IOException {


        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Data da pauta: " + LocalDate.now());
            writer.printf("%-10s | %-20s | %-5s%n", "Número", "Nome", "Nota");

            for (Student s : list.getStudents()) {
                String gradeText = s.hasGrade() ? String.valueOf(s.getGrade()) : "N/A";

                writer.printf("%-10d | %-20s | %-5s%n",
                        s.getNumber(),
                        s.getName(),
                        gradeText
                );
            }

            if (writer.checkError()) {
                throw new IOException("An error occurred while writing the TXT file.");
            }
        }
    }

    public void saveGradeReportCsv(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("Data da pauta: " + LocalDate.now());
            writer.println("Numero,Nome,Nota");

            for (Student student : list.getStudents()) {
                String gradeText = student.hasGrade() ? String.valueOf(student.getGrade()) : "";
                writer.println(
                    student.getNumber() + "," +
                    student.getName() + "," +
                    gradeText
                );
            }

            if (writer.checkError()) {
                throw new IOException("An error occurred while writing the CSV file.");
            }
        }
    }

    public void saveToBinaryFile(String filename) throws IOException {

        try (ObjectOutputStream output =
                 new ObjectOutputStream(new FileOutputStream(filename))) {
            output.writeObject(list);
        } catch (NotSerializableException e) {
            throw new IOException("A class in the object  is not serializable.", e);
        }
    }

    public static StudentList readFromBinaryFile(String filename)
            throws IOException {

        StudentList list;

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(filename))) {

            list = (StudentList) input.readObject();
        }catch (ClassCastException e) {
            throw new IOException(e.getMessage());
        }
     catch (ClassNotFoundException e) {
        throw new IOException(e.getMessage());
    }
        return list;
    }


   }

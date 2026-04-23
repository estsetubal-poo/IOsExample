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
            writer.println("Número | Nome | Nota");
            for (Student student : list.getStudents()) {
                String gradeText = student.hasGrade() ? String.valueOf(student.getGrade()) : "N/A";
                writer.println(
                    student.getNumber() + " | " +
                    student.getName() + " | " +
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
            throw new IOException("A class in the object graph is not serializable.", e);
        }
    }

    public static StudentList readFromBinaryFile(String filename)
            throws IOException, ClassNotFoundException {

        StudentList list;

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(filename))) {

            list = (StudentList) input.readObject();
        }

        return list;
    }


   }

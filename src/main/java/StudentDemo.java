import java.util.Scanner;

public class StudentDemo {

    private final static String studentFileCSV= "students_example.csv";
    private final static String studentPautaTxt="students_pauta.txt";
    private final static String studentPautaBin="students_pauta.bin";
    private StudentList list;

    public StudentDemo() {
        this.list = new StudentList();
    }

    public StudentList getList() {
        return list;
    }

    public void setList(StudentList list) {
        this.list = list;
    }

    public static void main(String[] args) {
        StudentDemo demo = new StudentDemo();
        StudentFileHandler handler = new StudentFileHandler(demo.getList());

        try {
            // 1. Read students from CSV
            Scanner sc= new Scanner(System.in);
            handler.addStudentsFromFile(studentFileCSV);

            // 2. Assign grades
            Student s1 = demo.getList().findByNumber(2021005);
            Student s2 = demo.getList().findByNumber(2021002);
            Student s3 = demo.getList().findByNumber(2021003);

            if (s1 != null) {
                s1.setGrade(20.0);
            }
            if (s2 != null) {
                s2.setGrade(15);
            }
            if (s3 != null) {
                s3.setGrade(12.0);
            }

            // 3. Save reports
            handler.saveGradeReportTxt(studentPautaTxt);

            // 4. Serialize the complete list
           handler.saveToBinaryFile(studentPautaBin);


            // 5. Read the serialized list
            StudentList recoveredList = StudentFileHandler.readFromBinaryFile(studentPautaBin);
            System.out.println("Recovered " + recoveredList.size() + " students from binary file.");

            System.out.println("Files generated successfully:");
            System.out.println("- students_pauta.bin");

            System.out.println("- students_pauta.txt");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

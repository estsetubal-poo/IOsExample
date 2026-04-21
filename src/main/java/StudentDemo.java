public class StudentDemo {
    private final StudentList list;

    public StudentDemo() {
        this.list = new StudentList();
    }

    public StudentList getList() {
        return list;
    }

    public static void main(String[] args) {
        StudentDemo demo = new StudentDemo();
        StudentFileHandler handler = new StudentFileHandler(demo.getList());

        try {
            // 1. Read students from CSV
            handler.addStudentsFromFile("students_example.csv");

            // 2. Assign grades
            Student s1 = demo.getList().findByNumber(2021001);
            Student s2 = demo.getList().findByNumber(2021002);
            Student s3 = demo.getList().findByNumber(2021003);

            if (s1 != null) {
                s1.setGrade(14.0);
            }
            if (s2 != null) {
                s2.setGrade(16.5);
            }
            if (s3 != null) {
                s3.setGrade(12.0);
            }

            // 3. Save reports
            handler.saveGradeReportTxt("pauta.txt");
            handler.saveGradeReportCsv("pauta.csv");

            // 4. Serialize the complete list
            handler.saveToBinaryFile("students.bin");

            // 5. Read the serialized list
            StudentList recoveredList = StudentFileHandler.readFromBinaryFile("students.bin");
            System.out.println("Recovered " + recoveredList.size() + " students from binary file.");

            System.out.println("Files generated successfully:");
            System.out.println("- pauta.txt");
            System.out.println("- pauta.csv");
            System.out.println("- students.bin");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StudentList implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<Student> students;

    public StudentList() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        if (findByNumber(student.getNumber()) != null) {
            throw new IllegalArgumentException(
                "A student with this number already exists: " + student.getNumber()
            );
        }
        students.add(student);
    }

    public List<Student> getStudents() {
        return Collections.unmodifiableList(students);
    }

    public Student findByNumber(int number) {
        for (Student student : students) {
            if (student.getNumber() == number) {
                return student;
            }
        }
        return null;
    }

    public int size() {
        return students.size();
    }
}

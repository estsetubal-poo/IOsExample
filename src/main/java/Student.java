import java.io.Serializable;

public class Student implements Serializable {
    private static final long serialVersionUID = 1L;

    private final int number;
    private final String name;
    private double grade;

    public Student(int number, String name) {
        if (number <= 0) {
            throw new IllegalArgumentException("Student number must be greater than 0.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be null or blank.");
        }

        this.number = number;
        this.name = name;
        this.grade = -1.0; // no grade assigned yet
    }

    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }

    public double getGrade() {
        return grade;
    }

    public boolean hasGrade() {
        return grade >= 0.0;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 20.0) {
            throw new IllegalArgumentException("Grade must be between 0 and 20.");
        }
        this.grade = grade;
    }

    @Override
    public String toString() {
        String gradeText = hasGrade() ? String.valueOf(grade) : "N/A";
        return number + " | " + name + " | " + gradeText;
    }
}

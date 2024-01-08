import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDelimitedFileDemo {

    public static class Student {
        private String name;
        private String id;
        private String course;

        public Student(String name, String id, String course) {
            this.name = name;
            this.id = id;
            this.course = course;
        }

        public String toDelimitedString() {
            return String.format("%s,%s,%s", name, id, course);
        }

        public static Student fromDelimitedString(String line) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                String name = parts[0];
                String id = parts[1];
                String course = parts[2];
                return new Student(name, id, course);
            } else {
                throw new IllegalArgumentException("Invalid delimited data: " + line);
            }
        }
    }

    public static void main(String[] args) {
        // Example data
        List<Student> students = new ArrayList<>();
        students.add(new Student("Alice", "1001", "Math"));
        students.add(new Student("Bob", "1002", "History"));

        // Write students to a delimited flat file (e.g., CSV)
        try (PrintWriter writer = new PrintWriter(new FileWriter("students.csv"))) {
            for (Student student : students) {
                writer.println(student.toDelimitedString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read students from the delimited flat file (e.g., CSV)
        List<Student> loadedStudents = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("students.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                loadedStudents.add(Student.fromDelimitedString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display loaded students
        for (Student student : loadedStudents) {
            System.out.println("Name: " + student.name + ", ID: " + student.id + ", Course: " + student.course);
        }
    }
}

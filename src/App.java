public class App {
    public static void main(String[] args) throws Exception {
        Student student = new Student();
        student.addStudents();

        System.out.println("\nStudent Information:");
        System.out.println("ID: " + student.studentId);
        System.out.println("Name: " + student.name);
        System.out.println("Age: " + student.age);
        System.out.println("Semester: " + student.semester);
        System.out.println("Subjects and Grades:");
        for (int i = 0; i < student.numSubjects; i++) {
            System.out.println(student.subjects.get(i) + ": " + student.grades.get(i) + " (" + student.gradeLevel.get(i) + ")");
        }
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends Person {
    private static Scanner scanner = new Scanner(System.in);

    public int studentId;
    public int numSubjects;
    public double gpa;
    public String semester;
    public ArrayList<String> subjects = new ArrayList<>();
    public ArrayList<Integer> grades = new ArrayList<>();
    public ArrayList<String> gradeLevel = new ArrayList<>();

    public void addStudents(){
        System.out.print("Enter Student ID: ");
        studentId = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Name: ");
        name = scanner.nextLine();

        System.out.print("Enter Age: ");
        age = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Semester: ");
        semester = scanner.nextLine();

        System.out.print("Enter GPA: ");
        gpa = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter number of subjects: ");
        numSubjects = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numSubjects; i++) {
            System.out.print("Enter subject name: ");
            String subject = scanner.nextLine();
            subjects.add(subject);

            System.out.print("Enter grade for " + subject + ": ");
            int grade = scanner.nextInt();
            grades.add(grade);
            scanner.nextLine();

            if (grade >= 95){
                gradeLevel.add("A+");
            } else if (grade >= 85) {
                gradeLevel.add("A");
            } else if (grade >= 75) {
                gradeLevel.add("B");
            } else if (grade >= 65) {
                gradeLevel.add("C");
            } else if (grade >= 60) {
                gradeLevel.add("D");
            } else {
                gradeLevel.add("F");
            }
        }
    }
    
}

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends Person {
    private static final Scanner scanner = new Scanner(System.in);
    
    public int studentId;
    public String semester;
    public ArrayList<String> subjects;
    public ArrayList<Integer> grades;
    public ArrayList<String> gradeLevels;
    
    // Default constructor
    public Student() {
        super();
        this.subjects = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.gradeLevels = new ArrayList<>();
    }
    
    // Constructor with parameters
    public Student(String username, String password, String name, int studentId, String semester) {
        super(username, password, name, "STUDENT");
        this.studentId = studentId;
        this.semester = semester;
        this.subjects = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.gradeLevels = new ArrayList<>();
    }
    
    // Add course and grade
    public void addSubjectAndGrade() {
        System.out.print("  Enter course name: ");
        String subject = scanner.nextLine();
        
        System.out.print("  Enter grade: ");
        int grade = scanner.nextInt();
        scanner.nextLine();
        
        // Validate grade range
        while (grade < 0 || grade > 100) {
            System.out.print("  [ERROR] Grade must be between 0-100, please re-enter: ");
            grade = scanner.nextInt();
            scanner.nextLine();
        }
        
        subjects.add(subject);
        grades.add(grade);
        gradeLevels.add(calculateGradeLevel(grade));
        
        System.out.println("\n  [SUCCESS] Course " + CLI.truncate(subject, 20) + " grade " + grade + " (" + calculateGradeLevel(grade) + ") added!");
    }
    
    // Calculate grade level
    public static String calculateGradeLevel(int grade) {
        if (grade >= 95) {
            return "A+";
        } else if (grade >= 85) {
            return "A";
        } else if (grade >= 75) {
            return "B";
        } else if (grade >= 65) {
            return "C";
        } else if (grade >= 60) {
            return "D";
        } else {
            return "F";
        }
    }
    
    // Calculate GPA
    public double calculateGPA() {
        if (grades.isEmpty()) {
            return 0.0;
        }
        
        double total = 0;
        for (int grade : grades) {
            total += gradeToGPA(grade);
        }
        return total / grades.size();
    }
    
    // Convert grade to GPA points
    private static double gradeToGPA(int grade) {
        if (grade >= 95) {
            return 4.0;
        } else if (grade >= 85) {
            return 4.0;
        } else if (grade >= 75) {
            return 3.0;
        } else if (grade >= 65) {
            return 2.0;
        } else if (grade >= 60) {
            return 1.0;
        } else {
            return 0.0;
        }
    }
    
    // Display student grades
    public void displayGrades() {
        System.out.println();
        CLI.printSeparator('-');
        System.out.println("  Student ID: " + studentId);
        System.out.println("  Name      : " + CLI.truncate(name, CLI.CLI_WIDTH - 15));
        System.out.println("  Semester  : " + CLI.truncate(semester, CLI.CLI_WIDTH - 15));
        CLI.printSeparator('-');
        
        if (subjects.isEmpty()) {
            System.out.println("  No grade records");
        } else {
            System.out.println(String.format("  %-30s %-10s %-10s", "Course", "Grade", "Level"));
            CLI.printSeparator('-');
            for (int i = 0; i < subjects.size(); i++) {
                System.out.println(String.format("  %-30s %-10s %-10s", 
                    CLI.truncate(subjects.get(i), 30), grades.get(i), gradeLevels.get(i)));
            }
            CLI.printSeparator('-');
            System.out.println("  GPA: " + String.format("%.2f", calculateGPA()));
        }
    }
    
    // Add student info (teacher function)
    public void addStudentInfo() {
        System.out.print("  Enter Student ID: ");
        studentId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("  Enter username: ");
        username = scanner.nextLine();
        
        System.out.print("  Enter password: ");
        password = scanner.nextLine();
        
        System.out.print("  Enter name: ");
        name = scanner.nextLine();
        
        System.out.print("  Enter semester: ");
        semester = scanner.nextLine();
        
        role = "STUDENT";
        
        System.out.println("\n  [SUCCESS] Student information added!");
    }
}

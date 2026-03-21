import java.util.ArrayList;
import java.util.Scanner;

// teacher user type in grade management system
public class Teacher extends Person {
    
    private static final Scanner scanner = new Scanner(System.in);
    
    // default constructor
    public Teacher() {
        super();
        this.role = "TEACHER";
    }
    
    // constructor with username, password, and name
    public Teacher(String username, String password, String name) {
        super(username, password, name, "TEACHER");
    }
    
    // show teacher menu and handle user choices
    public static boolean showTeacherMenu(Person currentUser, ArrayList<Student> students) {
        CLI.printHeader("Teacher Menu");
        System.out.println("  Welcome, " + CLI.truncate(currentUser.name, CLI.CLI_WIDTH - 15));
        CLI.printSeparator('-');
        System.out.println("  1. Add Student Information");
        System.out.println("  2. Add Course and Grade");
        System.out.println("  3. View All Student Grades");
        System.out.println("  4. Calculate Student GPA");
        System.out.println("  5. Save Data");
        System.out.println("  6. Logout");
        CLI.printSeparator('-');
        System.out.print("  Please choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                addStudent(students);
                break;
            case 2:
                addGrade(students);
                break;
            case 3:
                viewAllGrades(students);
                break;
            case 4:
                calculateStudentGPA(students);
                break;
            case 5:
                saveAllData(students);
                break;
            case 6:
                System.out.println("\n  [SUCCESS] Logged out successfully!");
                return true;
            default:
                System.out.println("\n  [ERROR] Invalid choice, please try again!");
        }
        return false;
    }
    
    // add a new student to the list
    public static void addStudent(ArrayList<Student> students) {
        CLI.printHeader("Add Student");
        Student newStudent = new Student();
        newStudent.addStudentInfo();
        
        for (Student s : students) {
            if (s.studentId == newStudent.studentId) {
                System.out.println("\n  [ERROR] Student ID already exists!");
                return;
            }
        }
        
        students.add(newStudent);
        System.out.println("\n  [SUCCESS] Student added successfully!");
    }
    
    // add course and grade for a student
    public static void addGrade(ArrayList<Student> students) {
        CLI.printHeader("Add Course and Grade");
        System.out.print("  Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        
        Student targetStudent = findStudentById(studentId, students);
        if (targetStudent == null) {
            System.out.println("\n  [ERROR] Student not found!");
            return;
        }
        
        targetStudent.addSubjectAndGrade();
    }
    
    // view all student grades
    public static void viewAllGrades(ArrayList<Student> students) {
        CLI.printHeader("All Student Grades");
        if (students.isEmpty()) {
            System.out.println("  No student records!");
            return;
        }
        
        for (Student student : students) {
            student.displayGrades();
        }
    }
    
    // calculate and display student GPA
    public static void calculateStudentGPA(ArrayList<Student> students) {
        CLI.printHeader("Calculate Student GPA");
        System.out.print("  Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        
        Student targetStudent = findStudentById(studentId, students);
        if (targetStudent == null) {
            System.out.println("\n  [ERROR] Student not found!");
            return;
        }
        
        System.out.println("\n  Student: " + CLI.truncate(targetStudent.name, CLI.CLI_WIDTH - 15));
        System.out.println("  GPA    : " + String.format("%.2f", targetStudent.calculateGPA()));
    }
    
    // find a student by their ID
    private static Student findStudentById(int studentId, ArrayList<Student> students) {
        for (Student s : students) {
            if (s.studentId == studentId) {
                return s;
            }
        }
        return null;
    }
    
    // save all student data to files
    private static void saveAllData(ArrayList<Student> students) {
        Person.saveGrades(students);
    }
}

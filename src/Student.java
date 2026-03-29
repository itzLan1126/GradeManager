import java.util.ArrayList;
import java.util.Scanner;

public class Student extends Person {
    private static final Scanner scanner = new Scanner(System.in);
    
    public int studentId;
    public String semester;
    public ArrayList<String> subjects;         // Course list
    public ArrayList<Integer> grades;          // Original grades (percentage)
    public ArrayList<Integer> ibGrades;       // IB grades (1-7 scale)
    public ArrayList<String> courseLevels;   // HL or SL
    
    // Default constructor
    public Student() {
        super();
        this.subjects = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.ibGrades = new ArrayList<>();
        this.courseLevels = new ArrayList<>();
    }
    
    // Constructor with parameters
    public Student(String username, String password, String name, int studentId, String semester) {
        super(username, password, name, "STUDENT");
        this.studentId = studentId;
        this.semester = semester;
        this.subjects = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.ibGrades = new ArrayList<>();
        this.courseLevels = new ArrayList<>();
    }
    
    // Add course and grade (IB system)
    public void addSubjectAndGrade() {
        System.out.print("  Enter course name: ");
        String subject = scanner.nextLine();
        
        // Enter course level (HL or SL)
        System.out.print("  Enter course level (HL/SL): ");
        String level = scanner.nextLine().toUpperCase();
        while (!level.equals("HL") && !level.equals("SL")) {
            System.out.print("  [ERROR] Please enter HL or SL: ");
            level = scanner.nextLine().toUpperCase();
        }
        
        // Enter IB grade (1-7)
        System.out.print("  Enter IB grade (1-7): ");
        int ibGrade = 0;
        try {
            ibGrade = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Invalid IB grade!");
            return;
        }
        
        // Validate IB grade range
        while (ibGrade < 1 || ibGrade > 7) {
            System.out.print("  [ERROR] IB grade must be between 1-7, please re-enter: ");
            try {
                ibGrade = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\n  [ERROR] Invalid IB grade!");
                return;
            }
        }
        
        subjects.add(subject);
        courseLevels.add(level);
        ibGrades.add(ibGrade);
        // Store percentage equivalent for compatibility
        grades.add(ibToPercentage(ibGrade));
        
        System.out.println("\n  [SUCCESS] Course " + CLI.truncate(subject, 20) + 
            " (Level: " + level + ") IB Grade: " + ibGrade + " added!");
    }
    
    // Calculate IB grade level (1-7)
    public static int calculateIBGradeLevel(int percentage) {
        if (percentage >= 95) return 7;
        else if (percentage >= 87) return 6;
        else if (percentage >= 77) return 5;
        else if (percentage >= 67) return 4;
        else if (percentage >= 57) return 3;
        else if (percentage >= 47) return 2;
        else return 1;
    }
    
    // Convert IB grade to percentage (uses parent class method)
    static int ibToPercentage(int ibGrade) {
        return Person.ibToPercentage(ibGrade);
    }
    
    // Calculate GPA (IB system)
    public double calculateGPA() {
        if (ibGrades.isEmpty()) {
            return 0.0;
        }
        
        double total = 0.0;
        double totalWeight = 0.0;
        
        for (int i = 0; i < ibGrades.size(); i++) {
            int ibGrade = ibGrades.get(i);
            double weight = courseLevels.get(i).equals("HL") ? 1.2 : 1.0;
            total += ibToGPA(ibGrade) * weight;
            totalWeight += weight;
        }
        
        return totalWeight > 0 ? total / totalWeight : 0.0;
    }
    
    // Convert IB grade to GPA points (4.0 scale)
    // 7 = 4.0, 6 = 4.0, 5 = 3.5, 4 = 3.0, 3 = 2.0, 2 = 1.0, 1 = 0.0
    private static double ibToGPA(int ibGrade) {
        switch (ibGrade) {
            case 7: return 4.0;
            case 6: return 4.0;
            case 5: return 3.5;
            case 4: return 3.0;
            case 3: return 2.0;
            case 2: return 1.0;
            default: return 0.0;
        }
    }
    
    // Display student grades (IB system)
    public void displayGrades() {
        System.out.println();
        CLI.printSeparator('-');
        System.out.println("  Student ID: " + studentId);
        System.out.println("  Name      : " + CLI.truncate(getName(), CLI.CLI_WIDTH - 15));
        System.out.println("  Semester  : " + CLI.truncate(semester, CLI.CLI_WIDTH - 15));
        CLI.printSeparator('-');
        
        if (subjects.isEmpty()) {
            System.out.println("  No grade records");
        } else {
            System.out.println(String.format("  %-25s %-8s %-8s %-8s", "Course", "Level", "IB", "GPA"));
            CLI.printSeparator('-');
            for (int i = 0; i < subjects.size(); i++) {
                int ibGrade = ibGrades.get(i);
                System.out.println(String.format("  %-25s %-8s %-8d %-8.2f", 
                    CLI.truncate(subjects.get(i), 25), 
                    courseLevels.get(i), 
                    ibGrade, 
                    ibToGPA(ibGrade)));
            }
            CLI.printSeparator('-');
            System.out.println("  GPA: " + String.format("%.2f", calculateGPA()));
        }
    }
    
    // Add student info (teacher function)
    public void addStudentInfo() {
        System.out.print("  Enter Student ID: ");
        try {
            studentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Invalid student ID!");
            return;
        }
        
        System.out.print("  Enter username: ");
        setUsername(scanner.nextLine());
        
        System.out.print("  Enter password: ");
        setPassword(scanner.nextLine());
        
        System.out.print("  Enter name: ");
        setName(scanner.nextLine());
        
        System.out.print("  Enter semester: ");
        semester = scanner.nextLine();
        
        setRole("STUDENT");
        
        System.out.println("\n  [SUCCESS] Student information added!");
    }
}

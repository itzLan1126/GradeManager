import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Base class for users in the grade management system
public class Person {
    public String username;
    public String password;
    public String name;
    public String role;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String USER_FILE = "data/users.txt";
    private static final String GRADE_FILE = "data/grades.txt";
    private static ArrayList<Person> users = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static Person currentUser = null;
    
    // CLI Formatting Utilities
    public static final int CLI_WIDTH = 56;

    public static void printSeparator(char c) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < CLI_WIDTH; i++) {
            sb.append(c);
        }
        System.out.println(sb.toString());
    }

    public static void printHeader(String title) {
        System.out.println();
        printSeparator('=');
        int padding = (CLI_WIDTH - title.length()) / 2;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < padding; i++) sb.append(" ");
        sb.append(title);
        System.out.println(sb.toString());
        printSeparator('=');
    }

    public static String truncate(String str, int length) {
        if (str == null) return "";
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
    
    // Default constructor
    public Person() {
    }
    
    // Parameterized constructor
    public Person(String username, String password, String name, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    
    // Main method
    public static void main(String[] args) {
        loadData();
        if (users.isEmpty()) {
            initializeTestData();
        }
        
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                if (currentUser.isTeacher()) {
                    showTeacherMenu();
                } else {
                    showStudentMenu();
                }
            }
        }
    }
    
    // Login method
    public static Person login(ArrayList<Person> users) {
        printHeader("Login System");
        System.out.print("  Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("  Enter password: ");
        String inputPassword = scanner.nextLine();
        
        for (Person user : users) {
            if (user != null && user.username != null) {
                if (user.username.equals(inputUsername) && user.password.equals(inputPassword)) {
                    System.out.println("\n  [SUCCESS] Login successful!");
                    System.out.println("  Welcome, " + truncate(user.name, 20) + " (" + user.role + ")");
                    return user;
                }
            }
        }
        System.out.println("\n  [ERROR] Invalid username or password!");
        return null;
    }
    
    // Register new user
    public static Person registerUser() {
        printHeader("Register New User");
        System.out.print("  Enter username: ");
        String username = scanner.nextLine();
        System.out.print("  Enter password: ");
        String password = scanner.nextLine();
        System.out.print("  Enter name: ");
        String name = scanner.nextLine();
        
        System.out.print("  Enter role (TEACHER/STUDENT): ");
        String role = scanner.nextLine().toUpperCase();
        
        while (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            System.out.print("  [ERROR] Invalid role. Please enter TEACHER or STUDENT: ");
            role = scanner.nextLine().toUpperCase();
        }
        
        Person newUser = new Person(username, password, name, role);
        System.out.println("\n  [SUCCESS] Registration successful!");
        return newUser;
    }
    
    // Display user information
    public void displayInfo() {
        System.out.println("  Username: " + truncate(username, CLI_WIDTH - 15));
        System.out.println("  Name    : " + truncate(name, CLI_WIDTH - 15));
        System.out.println("  Role    : " + role);
    }
    
    // Check if teacher
    public boolean isTeacher() {
        return "TEACHER".equals(role);
    }
    
    // Check if student
    public boolean isStudent() {
        return "STUDENT".equals(role);
    }
    
    // Load data
    private static void loadData() {
        users = loadUsers();
        for (Person user : users) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        loadGrades(students);
    }
    
    // Save data
    private static void saveData() {
        saveUsers(users);
        saveGrades(students);
    }
    
    // Initialize test data
    private static void initializeTestData() {
        Person teacher = new Person("teacher1", "pass123", "Mr. Lan", "TEACHER");
        users.add(teacher);
        
        Student student = new Student("student1", "pass123", "James", 1001, "2026-Fall");
        student.subjects.add("AP Calculus AB");
        student.grades.add(95);
        student.gradeLevels.add("A+");
        student.subjects.add("AP Computer Science A");
        student.grades.add(88);
        student.gradeLevels.add("A");
        users.add(student);
        students.add(student);
        
        saveData();
    }
    
    // Show login menu
    private static void showLoginMenu() {
        printHeader("AP Grade Management System");
        System.out.println("  1. Login");
        System.out.println("  2. Register New User");
        System.out.println("  3. Exit System");
        printSeparator('-');
        System.out.print("  Please choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                currentUser = Person.login(users);
                break;
            case 2:
                Person newUser = Person.registerUser();
                if (newUser.isStudent()) {
                    Student newStudent = new Student();
                    newStudent.username = newUser.username;
                    newStudent.password = newUser.password;
                    newStudent.name = newUser.name;
                    newStudent.role = newUser.role;
                    System.out.print("  Enter Student ID: ");
                    newStudent.studentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("  Enter Semester: ");
                    newStudent.semester = scanner.nextLine();
                    students.add(newStudent);
                }
                users.add(newUser);
                saveData();
                break;
            case 3:
                System.out.println("\n  Thank you for using the system. Goodbye!");
                System.exit(0);
            default:
                System.out.println("\n  [ERROR] Invalid choice, please try again!");
        }
    }
    
    // Show teacher menu
    private static void showTeacherMenu() {
        printHeader("Teacher Menu");
        System.out.println("  Welcome, " + truncate(currentUser.name, CLI_WIDTH - 15));
        printSeparator('-');
        System.out.println("  1. Add Student Information");
        System.out.println("  2. Add Course and Grade");
        System.out.println("  3. View All Student Grades");
        System.out.println("  4. Calculate Student GPA");
        System.out.println("  5. Save Data");
        System.out.println("  6. Logout");
        printSeparator('-');
        System.out.print("  Please choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                addStudent();
                break;
            case 2:
                addGrade();
                break;
            case 3:
                viewAllGrades();
                break;
            case 4:
                calculateStudentGPA();
                break;
            case 5:
                saveData();
                break;
            case 6:
                currentUser = null;
                System.out.println("\n  [SUCCESS] Logged out successfully!");
                break;
            default:
                System.out.println("\n  [ERROR] Invalid choice, please try again!");
        }
    }
    
    // Show student menu
    private static void showStudentMenu() {
        Student currentStudent = findStudent(currentUser.username);
        
        printHeader("Student Menu");
        System.out.println("  Welcome, " + truncate(currentUser.name, CLI_WIDTH - 15));
        printSeparator('-');
        System.out.println("  1. View My Grades");
        System.out.println("  2. View My GPA");
        System.out.println("  3. Logout");
        printSeparator('-');
        System.out.print("  Please choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                if (currentStudent != null) {
                    currentStudent.displayGrades();
                } else {
                    System.out.println("\n  [ERROR] Cannot find student information!");
                }
                break;
            case 2:
                if (currentStudent != null) {
                    System.out.println("\n  Your GPA: " + String.format("%.2f", currentStudent.calculateGPA()));
                } else {
                    System.out.println("\n  [ERROR] Cannot find student information!");
                }
                break;
            case 3:
                currentUser = null;
                System.out.println("\n  [SUCCESS] Logged out successfully!");
                break;
            default:
                System.out.println("\n  [ERROR] Invalid choice, please try again!");
        }
    }
    
    // Add student
    private static void addStudent() {
        printHeader("Add Student");
        Student newStudent = new Student();
        newStudent.addStudentInfo();
        
        for (Student s : students) {
            if (s.studentId == newStudent.studentId) {
                System.out.println("\n  [ERROR] Student ID already exists!");
                return;
            }
        }
        
        students.add(newStudent);
        users.add(newStudent);
        System.out.println("\n  [SUCCESS] Student added successfully!");
        saveData();
    }
    
    // Add grade
    private static void addGrade() {
        printHeader("Add Course and Grade");
        System.out.print("  Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        
        Student targetStudent = findStudentById(studentId);
        if (targetStudent == null) {
            System.out.println("\n  [ERROR] Student not found!");
            return;
        }
        
        targetStudent.addSubjectAndGrade();
        saveData();
    }
    
    // View all grades
    private static void viewAllGrades() {
        printHeader("All Student Grades");
        if (students.isEmpty()) {
            System.out.println("  No student records!");
            return;
        }
        
        for (Student student : students) {
            student.displayGrades();
        }
    }
    
    // Calculate GPA
    private static void calculateStudentGPA() {
        printHeader("Calculate Student GPA");
        System.out.print("  Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        
        Student targetStudent = findStudentById(studentId);
        if (targetStudent == null) {
            System.out.println("\n  [ERROR] Student not found!");
            return;
        }
        
        System.out.println("\n  Student: " + truncate(targetStudent.name, CLI_WIDTH - 15));
        System.out.println("  GPA    : " + String.format("%.2f", targetStudent.calculateGPA()));
    }
    
    // Find student by username
    private static Student findStudent(String username) {
        for (Student s : students) {
            if (s.username.equals(username)) {
                return s;
            }
        }
        return null;
    }
    
    // Find student by ID
    private static Student findStudentById(int studentId) {
        for (Student s : students) {
            if (s.studentId == studentId) {
                return s;
            }
        }
        return null;
    }
    
    // Save users to file
    public static void saveUsers(ArrayList<Person> users) {
        try {
            new File("data").mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE));
            writer.println("# username,password,name,role,studentId,semester");
            for (Person user : users) {
                if (user instanceof Student) {
                    Student student = (Student) user;
                    writer.println(user.username + "," + user.password + "," + 
                                   user.name + "," + user.role + "," + student.studentId + "," + student.semester);
                } else {
                    writer.println(user.username + "," + user.password + "," + 
                                   user.name + "," + user.role + ",0,");
                }
            }
            writer.close();
            System.out.println("  [INFO] User data saved!");
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to save user data: " + e.getMessage());
        }
    }
    
    // Load users from file
    public static ArrayList<Person> loadUsers() {
        ArrayList<Person> users = new ArrayList<>();
        File file = new File(USER_FILE);
        
        if (!file.exists()) {
            return users;
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(USER_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    String username = parts[0];
                    String password = parts[1];
                    String name = parts[2];
                    String role = parts[3];
                    
                    if (role.equals("STUDENT") && parts.length >= 6) {
                        int studentId = Integer.parseInt(parts[4]);
                        String semester = parts[5];
                        Student student = new Student(username, password, name, studentId, semester);
                        users.add(student);
                    } else {
                        users.add(new Person(username, password, name, role));
                    }
                }
            }
            reader.close();
            System.out.println("  [INFO] User data loaded!");
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to load user data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] User data format error: " + e.getMessage());
        }
        
        return users;
    }
    
    // Save grades to file
    public static void saveGrades(ArrayList<Student> students) {
        try {
            new File("data").mkdirs();
            PrintWriter writer = new PrintWriter(new FileWriter(GRADE_FILE));
            writer.println("# studentId,semester,subject,grade");
            for (Student student : students) {
                for (int i = 0; i < student.subjects.size(); i++) {
                    writer.println(student.studentId + "," + student.semester + "," + 
                                   student.subjects.get(i) + "," + student.grades.get(i));
                }
            }
            writer.close();
            System.out.println("  [INFO] Grade data saved!");
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to save grade data: " + e.getMessage());
        }
    }
    
    // Load grades from file
    public static void loadGrades(ArrayList<Student> students) {
        File file = new File(GRADE_FILE);
        
        if (!file.exists()) {
            return;
        }
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(GRADE_FILE));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int studentId = Integer.parseInt(parts[0]);
                    String semester = parts[1];
                    String subject = parts[2];
                    int grade = Integer.parseInt(parts[3]);
                    
                    for (Student student : students) {
                        if (student.studentId == studentId) {
                            student.subjects.add(subject);
                            student.grades.add(grade);
                            student.gradeLevels.add(Student.calculateGradeLevel(grade));
                            break;
                        }
                    }
                }
            }
            reader.close();
            System.out.println("  [INFO] Grade data loaded!");
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to load grade data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Grade data format error: " + e.getMessage());
        }
    }
}

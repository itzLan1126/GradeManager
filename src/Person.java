import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Base class for users in the grade management system
public class Person {
    private String username;
    private String password;
    private String name;
    private String role;
    private static final Scanner scanner = new Scanner(System.in);
    private static final String USER_FILE = "data/users.txt";
    private static final String GRADE_FILE = "data/grades.txt";
    static ArrayList<Person> users = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static Person currentUser = null;

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
                    boolean logout = Teacher.showTeacherMenu(currentUser, students);
                    if (logout) {
                        currentUser = null;
                    }
                } else {
                    showStudentMenu();
                }
            }
        }
    }

    // Login method
    public static Person login(ArrayList<Person> users) {
        CLI.printHeader("Login System");
        System.out.print("  Enter username: ");
        String inputUsername = scanner.nextLine();
        System.out.print("  Enter password: ");
        String inputPassword = scanner.nextLine();

        for (Person user : users) {
            if (user != null && user.username != null) {
                if (user.username.equals(inputUsername) && user.password.equals(inputPassword)) {
                    System.out.println("\n  [SUCCESS] Login successful!");
                    System.out.println("  Welcome, " + CLI.truncate(user.name, 20) + " (" + user.role + ")");
                    return user;
                }
            }
        }
        System.out.println("\n  [ERROR] Invalid username or password!");
        return null;
    }

    // Register new user
    public static Person registerUser() {
        CLI.printHeader("Register New User");
        System.out.print("  Enter username: ");
        String username = scanner.nextLine();

        // Check if username already exists
        for (Person user : users) {
            if (user.username.equals(username)) {
                System.out.println("\n  [ERROR] Username already exists!");
                return null;
            }
        }

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
        System.out.println("  Username: " + CLI.truncate(username, CLI.CLI_WIDTH - 15));
        System.out.println("  Name    : " + CLI.truncate(name, CLI.CLI_WIDTH - 15));
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
    public static void saveData() {
        saveUsers(users);
        saveGrades(students);
    }

    // Initialize test data (IB system)
    private static void initializeTestData() {
        Person teacher = new Person("teacher1", "pass123", "Mr. Lan", "TEACHER");
        users.add(teacher);

        Student student = new Student("student1", "pass123", "James", 1001, "2026-Fall");
        // IB courses with HL/SL levels
        student.subjects.add("Mathematics Analysis and Approaches HL");
        student.courseLevels.add("HL");
        student.ibGrades.add(6);
        student.grades.add(90); // percentage equivalent

        student.subjects.add("Physics HL");
        student.courseLevels.add("HL");
        student.ibGrades.add(5);
        student.grades.add(82); // percentage equivalent

        student.subjects.add("English A Literature SL");
        student.courseLevels.add("SL");
        student.ibGrades.add(6);
        student.grades.add(90); // percentage equivalent

        users.add(student);
        students.add(student);

        saveData();
    }

    // Show login menu
    private static void showLoginMenu() {
        CLI.printHeader("IB Grade Management System");
        System.out.println("  1. Login");
        System.out.println("  2. Register New User");
        System.out.println("  3. Exit System");
        CLI.printSeparator('-');
        System.out.print("  Please choose: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Please enter a valid number!");
            return;
        }

        switch (choice) {
            case 1:
                currentUser = Person.login(users);
                break;
            case 2:
                Person newUser = Person.registerUser();
                if (newUser != null) {
                    if (newUser.isStudent()) {
                        Student newStudent = new Student();
                        newStudent.setUsername(newUser.getUsername());
                        newStudent.setPassword(newUser.getPassword());
                        newStudent.setName(newUser.getName());
                        newStudent.setRole(newUser.getRole());
                        System.out.print("  Enter Student ID: ");
                        try {
                            newStudent.studentId = Integer.parseInt(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("\n  [ERROR] Invalid student ID!");
                            break;
                        }

                        // Check if student ID already exists
                        for (Student s : students) {
                            if (s.studentId == newStudent.studentId) {
                                System.out.println("\n  [ERROR] Student ID already exists!");
                                newUser = null; // Don't add the user
                                break;
                            }
                        }

                        if (newUser != null) {
                            System.out.print("  Enter Semester: ");
                            newStudent.semester = scanner.nextLine();
                            students.add(newStudent);
                        }
                    }
                    users.add(newUser);
                    saveData();
                }
                break;
            case 3:
                System.out.println("\n  Thank you for using the system. Goodbye!");
                System.exit(0);
            default:
                System.out.println("\n  [ERROR] Invalid choice, please try again!");
        }
    }

    // Show student menu
    private static void showStudentMenu() {
        Student currentStudent = findStudent(currentUser.username);

        CLI.printHeader("Student Menu");
        System.out.println("  Welcome, " + CLI.truncate(currentUser.name, CLI.CLI_WIDTH - 15));
        CLI.printSeparator('-');
        System.out.println("  1. View My Grades");
        System.out.println("  2. View My GPA");
        System.out.println("  3. Logout");
        CLI.printSeparator('-');
        System.out.print("  Please choose: ");

        int choice = 0;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("\n  [ERROR] Please enter a valid number!");
            return;
        }

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

    // Find student by username
    private static Student findStudent(String username) {
        for (Student s : students) {
            if (s.getUsername().equals(username)) {
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
            try (PrintWriter writer = new PrintWriter(new FileWriter(USER_FILE))) {
                writer.println("# username,password,name,role,studentId,semester");
                for (Person user : users) {
                    if (user instanceof Student) {
                        Student student = (Student) user;
                        writer.println(user.getUsername() + "," + user.getPassword() + "," +
                                user.getName() + "," + user.getRole() + "," + student.studentId + ","
                                + student.semester);
                    } else {
                        writer.println(user.getUsername() + "," + user.getPassword() + "," +
                                user.getName() + "," + user.getRole() + ",0,");
                    }
                }
            }
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

        try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
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
            System.out.println("  [INFO] User data loaded!");
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to load user data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] User data format error: " + e.getMessage());
        }

        return users;
    }

    // Save grades to file (IB system)
    public static void saveGrades(ArrayList<Student> students) {
        try {
            new File("data").mkdirs();
            try (PrintWriter writer = new PrintWriter(new FileWriter(GRADE_FILE))) {
                writer.println("# studentId,semester,subject,ibGrade,level");
                for (Student student : students) {
                    for (int i = 0; i < student.subjects.size(); i++) {
                        writer.println(student.studentId + "," + student.semester + "," +
                                student.subjects.get(i) + "," +
                                student.ibGrades.get(i) + "," +
                                student.courseLevels.get(i));
                    }
                }
            }
            System.out.println("  [INFO] Grade data saved!");
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to save grade data: " + e.getMessage());
        }
    }

    // Load grades from file (IB system)
    public static void loadGrades(ArrayList<Student> students) {
        File file = new File(GRADE_FILE);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(GRADE_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                // IB format: studentId,semester,subject,ibGrade,level
                // Legacy AP format: studentId,semester,subject,grade
                if (parts.length >= 4) {
                    int studentId = Integer.parseInt(parts[0]);
                    String semester = parts[1];
                    String subject = parts[2];

                    for (Student student : students) {
                        if (student.studentId == studentId) {
                            student.subjects.add(subject);

                            if (parts.length >= 5) {
                                // IB format: has ibGrade and level
                                int ibGrade = Integer.parseInt(parts[3]);
                                String level = parts[4];
                                student.ibGrades.add(ibGrade);
                                student.courseLevels.add(level);
                                student.grades.add(ibToPercentage(ibGrade));
                            } else {
                                // Legacy AP format: convert percentage to IB
                                int grade = Integer.parseInt(parts[3]);
                                int ibGrade = Student.calculateIBGradeLevel(grade);
                                student.ibGrades.add(ibGrade);
                                student.courseLevels.add("SL"); // default to SL
                                student.grades.add(grade);
                            }
                            break;
                        }
                    }
                }
            }
            System.out.println("  [INFO] Grade data loaded!");
        } catch (IOException e) {
            System.out.println("  [ERROR] Failed to load grade data: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("  [ERROR] Grade data format error: " + e.getMessage());
        }
    }

    // Convert IB grade to percentage
    static int ibToPercentage(int ibGrade) {
        switch (ibGrade) {
            case 7:
                return 97;
            case 6:
                return 90;
            case 5:
                return 82;
            case 4:
                return 72;
            case 3:
                return 62;
            case 2:
                return 52;
            default:
                return 42;
        }
    }
}

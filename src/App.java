import java.util.ArrayList;
import java.util.Scanner;

public class App {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Person> users = new ArrayList<>();
    private static ArrayList<Student> students = new ArrayList<>();
    private static Person currentUser = null;
    
    public static void main(String[] args) {
        // 确保 data 目录存在
        DataManager.ensureDataDirectory();
        
        // 加载数据
        loadData();
        
        // 如果没有用户数据，初始化测试数据
        if (users.isEmpty()) {
            initializeTestData();
        }
        
        // 主循环
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
    
    // 加载数据
    private static void loadData() {
        users = DataManager.loadUsers();
        
        // 分离学生用户
        for (Person user : users) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        
        // 加载成绩数据
        DataManager.loadGrades(students);
    }
    
    // 保存数据
    private static void saveData() {
        DataManager.saveUsers(users);
        DataManager.saveGrades(students);
    }
    
    // 初始化测试数据
    private static void initializeTestData() {
        // 添加一个老师账户
        Person teacher = new Person("teacher1", "pass123", "张老师", "TEACHER");
        users.add(teacher);
        
        // 添加一个学生账户
        Student student = new Student("student1", "pass123", "李同学", 1001, "2024-Fall");
        student.subjects.add("AP Calculus AB");
        student.grades.add(95);
        student.gradeLevels.add("A+");
        student.subjects.add("AP Computer Science A");
        student.grades.add(88);
        student.gradeLevels.add("A");
        users.add(student);
        students.add(student);
        
        // 保存初始数据
        saveData();
    }
    
    // 显示登录菜单
    private static void showLoginMenu() {
        System.out.println("\n=================================");
        System.out.println("    AP Grade Management System");
        System.out.println("=================================");
        System.out.println("1. Login");
        System.out.println("2. Register New User");
        System.out.println("3. Exit System");
        System.out.print("Please choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                currentUser = Person.login(users.toArray(new Person[0]));
                break;
            case 2:
                Person newUser = Person.registerUser();
                if (newUser.isStudent()) {
                    // 学生注册后需要添加学号和学期
                    Student newStudent = new Student();
                    newStudent.username = newUser.username;
                    newStudent.password = newUser.password;
                    newStudent.name = newUser.name;
                    newStudent.role = newUser.role;
                    System.out.print("Enter Student ID: ");
                    newStudent.studentId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Semester: ");
                    newStudent.semester = scanner.nextLine();
                    students.add(newStudent);
                }
                users.add(newUser);
                saveData();
                break;
            case 3:
                System.out.println("Thank you for using the system. Goodbye!");
                System.exit(0);
            default:
                System.out.println("Invalid choice, please try again!");
        }
    }
    
    // 显示老师菜单
    private static void showTeacherMenu() {
        System.out.println("\n=================================");
        System.out.println("    Teacher Menu");
        System.out.println("=================================");
        System.out.println("Welcome, " + currentUser.name);
        System.out.println("1. Add Student Information");
        System.out.println("2. Add Course and Grade");
        System.out.println("3. View All Student Grades");
        System.out.println("4. Calculate Student GPA");
        System.out.println("5. Save Data");
        System.out.println("6. Logout");
        System.out.print("Please choose: ");
        
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
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice, please try again!");
        }
    }
    
    // 显示学生菜单
    private static void showStudentMenu() {
        // 获取当前登录的学生对象
        Student currentStudent = findStudent(currentUser.username);
        
        System.out.println("\n=================================");
        System.out.println("    Student Menu");
        System.out.println("=================================");
        System.out.println("Welcome, " + currentUser.name);
        System.out.println("1. View My Grades");
        System.out.println("2. View My GPA");
        System.out.println("3. Logout");
        System.out.print("Please choose: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine();
        
        switch (choice) {
            case 1:
                if (currentStudent != null) {
                    currentStudent.displayGrades();
                } else {
                    System.out.println("Cannot find student information!");
                }
                break;
            case 2:
                if (currentStudent != null) {
                    System.out.println("Your GPA: " + String.format("%.2f", currentStudent.calculateGPA()));
                } else {
                    System.out.println("Cannot find student information!");
                }
                break;
            case 3:
                currentUser = null;
                System.out.println("Logged out successfully!");
                break;
            default:
                System.out.println("Invalid choice, please try again!");
        }
    }
    
    // 添加学生（老师功能）
    private static void addStudent() {
        Student newStudent = new Student();
        newStudent.addStudentInfo();
        
        // 检查学号是否已存在
        for (Student s : students) {
            if (s.studentId == newStudent.studentId) {
                System.out.println("Student ID already exists!");
                return;
            }
        }
        
        students.add(newStudent);
        users.add(newStudent);
        System.out.println("Student added successfully!");
        saveData();
    }
    
    // 添加课程和成绩（老师功能）
    private static void addGrade() {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        
        Student targetStudent = findStudentById(studentId);
        if (targetStudent == null) {
            System.out.println("Student not found!");
            return;
        }
        
        targetStudent.addSubjectAndGrade();
        saveData();
    }
    
    // 查看所有学生成绩（老师功能）
    private static void viewAllGrades() {
        if (students.isEmpty()) {
            System.out.println("No student records!");
            return;
        }
        
        for (Student student : students) {
            student.displayGrades();
            System.out.println();
        }
    }
    
    // 计算学生 GPA（老师功能）
    private static void calculateStudentGPA() {
        System.out.print("Enter Student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        
        Student targetStudent = findStudentById(studentId);
        if (targetStudent == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Student: " + targetStudent.name);
        System.out.println("GPA: " + String.format("%.2f", targetStudent.calculateGPA()));
    }
    
    // 根据用户名查找学生
    private static Student findStudent(String username) {
        for (Student s : students) {
            if (s.username.equals(username)) {
                return s;
            }
        }
        return null;
    }
    
    // 根据学号查找学生
    private static Student findStudentById(int studentId) {
        for (Student s : students) {
            if (s.studentId == studentId) {
                return s;
            }
        }
        return null;
    }
}

import java.util.ArrayList;
import java.util.Scanner;

public class Student extends Person {
    private static final Scanner scanner = new Scanner(System.in);
    
    public int studentId;              // 学号
    public String semester;            // 学期
    public ArrayList<String> subjects; // 课程列表
    public ArrayList<Integer> grades;  // 成绩列表
    public ArrayList<String> gradeLevels; // 等级 (A, B, C...)
    
    // 默认构造函数
    public Student() {
        super();
        this.subjects = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.gradeLevels = new ArrayList<>();
    }
    
    // 带参数构造函数
    public Student(String username, String password, String name, int studentId, String semester) {
        super(username, password, name, "STUDENT");
        this.studentId = studentId;
        this.semester = semester;
        this.subjects = new ArrayList<>();
        this.grades = new ArrayList<>();
        this.gradeLevels = new ArrayList<>();
    }
    
    // 添加课程和成绩
    public void addSubjectAndGrade() {
        System.out.print("请输入课程名称: ");
        String subject = scanner.nextLine();
        
        System.out.print("请输入成绩: ");
        int grade = scanner.nextInt();
        scanner.nextLine();
        
        // 验证成绩范围
        while (grade < 0 || grade > 100) {
            System.out.print("成绩必须在 0-100 之间，请重新输入: ");
            grade = scanner.nextInt();
            scanner.nextLine();
        }
        
        subjects.add(subject);
        grades.add(grade);
        gradeLevels.add(calculateGradeLevel(grade));
        
        System.out.println("课程 " + subject + " 成绩 " + grade + " (" + calculateGradeLevel(grade) + ") 已添加！");
    }
    
    // 计算等级
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
    
    // 计算 GPA
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
    
    // 将分数转换为 GPA 点数
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
    
    // 显示学生成绩
    public void displayGrades() {
        System.out.println("\n========== 学生成绩 ==========");
        System.out.println("学号: " + studentId);
        System.out.println("姓名: " + name);
        System.out.println("学期: " + semester);
        System.out.println("-----------------------------");
        
        if (subjects.isEmpty()) {
            System.out.println("暂无成绩记录");
        } else {
            System.out.println("课程名称\t\t成绩\t等级");
            System.out.println("-----------------------------");
            for (int i = 0; i < subjects.size(); i++) {
                System.out.println(subjects.get(i) + "\t\t" + grades.get(i) + "\t" + gradeLevels.get(i));
            }
            System.out.println("-----------------------------");
            System.out.println("GPA: " + String.format("%.2f", calculateGPA()));
        }
    }
    
    // 添加学生信息（老师功能）
    public void addStudentInfo() {
        System.out.print("请输入学号: ");
        studentId = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("请输入用户名: ");
        username = scanner.nextLine();
        
        System.out.print("请输入密码: ");
        password = scanner.nextLine();
        
        System.out.print("请输入姓名: ");
        name = scanner.nextLine();
        
        System.out.print("请输入学期: ");
        semester = scanner.nextLine();
        
        role = "STUDENT";
        
        System.out.println("学生信息已添加！");
    }
}

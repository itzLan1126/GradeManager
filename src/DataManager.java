import java.io.*;
import java.util.ArrayList;

public class DataManager {
    private static final String USER_DATA_FILE = "data/users.txt";
    private static final String GRADE_DATA_FILE = "data/grades.txt";
    
    // 保存用户数据到文件
    public static void saveUsers(ArrayList<Person> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USER_DATA_FILE))) {
            writer.println("# username,password,name,role,studentId");
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
            System.out.println("用户数据已保存！");
        } catch (IOException e) {
            System.out.println("保存用户数据失败: " + e.getMessage());
        }
    }
    
    // 从文件加载用户数据
    public static ArrayList<Person> loadUsers() {
        ArrayList<Person> users = new ArrayList<>();
        File file = new File(USER_DATA_FILE);
        
        if (!file.exists()) {
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 跳过注释行
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
            System.out.println("用户数据已加载！");
        } catch (IOException e) {
            System.out.println("加载用户数据失败: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("用户数据格式错误: " + e.getMessage());
        }
        
        return users;
    }
    
    // 保存成绩数据到文件
    public static void saveGrades(ArrayList<Student> students) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(GRADE_DATA_FILE))) {
            writer.println("# studentId,semester,subject,grade");
            for (Student student : students) {
                for (int i = 0; i < student.subjects.size(); i++) {
                    writer.println(student.studentId + "," + student.semester + "," + 
                                   student.subjects.get(i) + "," + student.grades.get(i));
                }
            }
            System.out.println("成绩数据已保存！");
        } catch (IOException e) {
            System.out.println("保存成绩数据失败: " + e.getMessage());
        }
    }
    
    // 从文件加载成绩数据
    public static void loadGrades(ArrayList<Student> students) {
        File file = new File(GRADE_DATA_FILE);
        
        if (!file.exists()) {
            return;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(GRADE_DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // 跳过注释行
                if (line.startsWith("#") || line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    int studentId = Integer.parseInt(parts[0]);
                    String semester = parts[1];
                    String subject = parts[2];
                    int grade = Integer.parseInt(parts[3]);
                    
                    // 查找对应的学生并添加成绩
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
            System.out.println("成绩数据已加载！");
        } catch (IOException e) {
            System.out.println("加载成绩数据失败: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("成绩数据格式错误: " + e.getMessage());
        }
    }
    
    // 确保 data 目录存在
    public static void ensureDataDirectory() {
        File dir = new File("data");
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
}

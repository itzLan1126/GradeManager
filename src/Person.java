import java.util.Scanner;

public class Person {
    public String username;    // 用户名
    public String password;   // 密码
    public String name;       // 姓名
    public String role;       // 角色: TEACHER / STUDENT
    private static final Scanner scanner = new Scanner(System.in);
    
    // 默认构造函数
    public Person() {
    }
    
    // 带参数构造函数
    public Person(String username, String password, String name, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.role = role;
    }
    
    // 登录方法
    public static Person login(Person[] users) {
        System.out.println("\n========== 登录系统 ==========");
        System.out.print("请输入用户名: ");
        String inputUsername = scanner.nextLine();
        System.out.print("请输入密码: ");
        String inputPassword = scanner.nextLine();
        
        for (Person user : users) {
            if (user != null && user.username != null) {
                if (user.username.equals(inputUsername) && user.password.equals(inputPassword)) {
                    System.out.println("登录成功！欢迎, " + user.name + " (" + user.role + ")");
                    return user;
                }
            }
        }
        System.out.println("用户名或密码错误！");
        return null;
    }
    
    // 注册新用户
    public static Person registerUser() {
        System.out.println("\n========== 注册新用户 ==========");
        System.out.print("请输入用户名: ");
        String username = scanner.nextLine();
        System.out.print("请输入密码: ");
        String password = scanner.nextLine();
        System.out.print("请输入姓名: ");
        String name = scanner.nextLine();
        
        System.out.print("请输入角色 (TEACHER/STUDENT): ");
        String role = scanner.nextLine().toUpperCase();
        
        // 验证角色
        while (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            System.out.print("角色输入错误，请输入 TEACHER 或 STUDENT: ");
            role = scanner.nextLine().toUpperCase();
        }
        
        Person newUser = new Person(username, password, name, role);
        System.out.println("注册成功！");
        return newUser;
    }
    
    // 显示用户信息
    public void displayInfo() {
        System.out.println("用户名: " + username);
        System.out.println("姓名: " + name);
        System.out.println("角色: " + role);
    }
    
    // 判断是否为老师
    public boolean isTeacher() {
        return "TEACHER".equals(role);
    }
    
    // 判断是否为学生
    public boolean isStudent() {
        return "STUDENT".equals(role);
    }
}

# AP 成绩管理系统 - 简化版 (课堂项目)

## 项目概述

这是一个简单的 AP CSA 课堂项目，用于管理国际学校学生的 AP 课程成绩。

## 技术规格

- **编程语言**: Java
- **用户界面**: 命令行界面 (CLI)
- **数据存储**: TXT 文本文件
- **核心类**: Person, Student (仅两个类)

## 数据模型

### Person 类 (用户)

```java
public class Person {
    public String username;    // 用户名
    public String password;    // 密码
    public String name;         // 姓名
    public String role;        // 角色: TEACHER / STUDENT
}
```

### Student 类 (学生)

```java
public class Student extends Person {
    public int studentId;              // 学号
    public String semester;            // 学期
    public ArrayList<String> subjects; // 课程列表
    public ArrayList<Integer> grades;  // 成绩列表
    public ArrayList<String> gradeLevels; // 等级 (A, B, C...)
}
```

## 功能列表

### 老师功能

1. 登录系统
2. 添加学生信息
3. 添加课程和成绩
4. 查看所有学生成绩
5. 计算学生 GPA

### 学生功能

1. 登录系统
2. 查看自己的成绩
3. 查看自己的 GPA

## 数据文件格式 (data.txt)

```
# 用户数据格式
username,password,name,role,studentId
teacher1,pass123,张老师,TEACHER,0
student1,pass123,李同学,STUDENT,1001

# 成绩数据格式
studentId,semester,subject,grade
1001,2024-Fall,AP Calculus AB,95
1001,2024-Fall,AP Computer Science A,88
```

## GPA计算

- A+ = 4.0 (95-100)
- A = 4.0 (85-94)
- B = 3.0 (75-84)
- C = 2.0 (65-74)
- D = 1.0 (60-64)
- F = 0.0 (<60)

GPA = 所有课程分数之和 / 课程数量

## 开发计划

1. **第一阶段**: 修改 Person 类，添加用户角色和登录功能
2. **第二阶段**: 扩展 Student 类，添加更多成绩管理功能
3. **第三阶段**: 实现 TXT 文件数据持久化
4. **第四阶段**: 实现命令行菜单界面
5. **第五阶段**: 测试和完善

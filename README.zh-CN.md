# IB 成绩管理系统

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/technologies/downloads/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-CLI-green.svg)](README.zh-CN.md)

**[English](README.md)**

一个用于国际学校 IB（国际文凭）课程成绩管理的命令行 Java 应用程序。

## 目录

- [项目简介](#项目简介)
- [功能特性](#功能特性)
- [技术栈](#技术栈)
- [项目结构](#项目结构)
- [安装步骤](#安装步骤)
- [使用方法](#使用方法)
- [GPA 计算](#gpa-计算)
- [数据存储](#数据存储)
- [类结构说明](#类结构说明)
- [贡献指南](#贡献指南)
- [许可证](#许可证)

---

## 项目简介

IB 成绩管理系统是一个专为 IB（国际文凭）课程设计的简易成绩管理系统。该系统允许教师管理学生信息和成绩，学生可以查看自己的成绩和 GPA。

此项目最初是作为 AP CSA 课堂项目开发的，后来重构为支持 IB 课程体系，引入了 HL（高级课程）和 SL（标准课程）级别权重。

---

## 功能特性

### 教师功能

| 功能     | 描述                                     |
| -------- | ---------------------------------------- |
| 用户登录 | 使用用户名和密码登录系统                 |
| 添加学生 | 添加新学生的基本信息和账户               |
| 添加成绩 | 为学生添加课程及对应的 IB 成绩和课程级别 |
| 查看成绩 | 查看所有学生的成绩信息                   |
| 计算 GPA | 根据 IB 成绩计算学生的 GPA               |

### 学生功能

| 功能     | 描述                     |
| -------- | ------------------------ |
| 用户登录 | 使用用户名和密码登录系统 |
| 查看成绩 | 查看自己的各科成绩       |
| 查看 GPA | 查看自己的 GPA 及排名    |

---

## 技术栈

| 技术         | 说明                              |
| ------------ | --------------------------------- |
| **编程语言** | Java 8+                           |
| **用户界面** | 命令行界面 (CLI)                  |
| **数据存储** | 文本文件 (TXT)                    |
| **开发工具** | VS Code / IntelliJ IDEA / Eclipse |

---

## 项目结构

```
GradeManager/
├── src/
│   ├── CLI.java         # 命令行界面格式化工具类
│   ├── Person.java      # 用户基类（包含主程序入口）
│   ├── Student.java     # 学生类，继承自 Person
│   └── Teacher.java     # 教师类，继承自 Person
├── data/
│   ├── users.txt        # 用户数据存储文件
│   └── grades.txt       # 成绩数据存储文件
├── docs/
│   └── PLAN.md          # 项目开发计划文档
├── LICENSE              # MIT 许可证文件
└── README_zh-CN.md      # 本文件
```

---

## 安装步骤

### 前置要求

- Java Development Kit (JDK) 8 或更高版本
- 文本编辑器（推荐 VS Code）或任意 Java IDE

### 安装 Java（如果尚未安装）

**macOS (使用 Homebrew):**

```bash
brew install openjdk@17
```

**Windows:**
从 [Oracle 官网](https://www.oracle.com/java/technologies/downloads/) 或 [Adoptium](https://adoptium.net/) 下载并安装 JDK。

**Linux (Ubuntu):**

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### 克隆项目

```bash
git clone https://github.com/itzLan1126/GradeManager.git
cd GradeManager
```

### 编译项目

```bash
cd GradeManager
javac -d out src/Person.java src/Student.java src/Teacher.java src/CLI.java
```

或者使用单条命令：

```bash
javac src/Person.java src/Student.java src/Teacher.java src/CLI.java
```

---

## 使用方法

### 运行应用程序

编译后，运行以下命令启动系统：

```bash
java -cp src Person
```

### 登录说明

系统首次启动时会自动初始化默认测试账户。登录后，教师和学生看到不同的菜单选项。

### 默认测试账户

| 角色 | 用户名   | 密码    |
| ---- | -------- | ------- |
| 教师 | teacher1 | pass123 |
| 学生 | student1 | pass123 |

### 操作流程

#### 教师操作流程

1. 使用教师账户登录
2. 选择操作菜单：
   - **添加学生**：输入学生信息创建新账户
   - **添加成绩**：选择学生并添加课程、IB 成绩、课程级别
   - **查看成绩**：浏览所有学生的成绩
   - **计算 GPA**：查看指定学生的 GPA

#### 学生操作流程

1. 使用学生账户登录
2. 选择操作菜单：
   - **查看成绩**：查看个人各科成绩
   - **查看 GPA**：查看个人 GPA

### 退出系统

在主菜单选择退出选项即可安全退出系统。

---

## GPA 计算

### IB 成绩等级与分数范围

| IB 成绩 | 分数范围 | GPA 分值 |
| ------- | -------- | -------- |
| 7       | 95-100   | 4.0      |
| 6       | 87-94    | 4.0      |
| 5       | 77-86    | 3.5      |
| 4       | 67-76    | 3.0      |
| 3       | 57-66    | 2.0      |
| 2       | 47-56    | 1.0      |
| 1       | 47 以下  | 0.0      |

### GPA 计算公式

```
GPA = Σ(课程 GPA 分值 × 课程权重) / Σ(课程权重)
```

其中：

- **HL（高级课程）** 权重：1.2
- **SL（标准课程）** 权重：1.0

### 示例

假设学生修了以下课程：

| 课程              | IB 成绩 | 级别 | 基础 GPA | 权重 | 加权 GPA |
| ----------------- | ------- | ---- | -------- | ---- | -------- |
| 数学分析与方法 HL | 6       | HL   | 4.0      | 1.2  | 4.8      |
| 物理 HL           | 5       | HL   | 3.5      | 1.2  | 4.2      |
| 英语 A 文学 SL    | 6       | SL   | 4.0      | 1.0  | 4.0      |

**GPA = (4.8 + 4.2 + 4.0) / (1.2 + 1.2 + 1.0) = 13.0 / 3.4 = 3.82**

---

## 数据存储

系统使用纯文本文件进行数据持久化：

### 用户数据 (data/users.txt)

```
# username,password,name,role,studentId,semester
teacher1,pass123,Mr. Lan,TEACHER,0,
student1,pass123,James,STUDENT,1001,2026-Fall
```

### 成绩数据 (data/grades.txt)

```
# studentId,semester,subject,ibGrade,level
1001,2026-Fall,Mathematics Analysis and Approaches HL,6,HL
1001,2026-Fall,Physics HL,5,HL
1001,2026-Fall,English A Literature SL,6,SL
```

---

## 类结构说明

### Person 类

用户基类，包含以下属性：

| 属性     | 类型   | 描述                         |
| -------- | ------ | ---------------------------- |
| username | String | 用户登录名                   |
| password | String | 用户密码                     |
| name     | String | 用户全名                     |
| role     | String | 用户角色 (TEACHER / STUDENT) |

### Student 类 (继承自 Person)

学生类，包含以下额外属性：

| 属性         | 类型               | 描述             |
| ------------ | ------------------ | ---------------- |
| studentId    | int                | 学生唯一标识符   |
| semester     | String             | 当前学期         |
| subjects     | ArrayList<String>  | 已选课程列表     |
| grades       | ArrayList<Integer> | 百分制成绩列表   |
| ibGrades     | ArrayList<Integer> | IB 成绩 (1-7)    |
| courseLevels | ArrayList<String>  | 课程级别 (HL/SL) |

### Teacher 类 (继承自 Person)

教师类，提供以下功能方法：

| 方法                  | 描述                       |
| --------------------- | -------------------------- |
| showTeacherMenu()     | 显示教师菜单并处理用户选择 |
| addStudent()          | 添加新学生信息             |
| addGrade()            | 为学生添加课程和成绩       |
| viewAllGrades()       | 查看所有学生成绩           |
| calculateStudentGPA() | 计算学生 GPA               |

### CLI 类

命令行界面格式化工具类：

| 常量/方法                        | 描述                     |
| -------------------------------- | ------------------------ |
| CLI_WIDTH                        | 控制台宽度常量 (56 字符) |
| printSeparator(char c)           | 打印分隔线               |
| printHeader(String title)        | 打印居中标题             |
| truncate(String str, int length) | 截断字符串并添加省略号   |

---

## 贡献指南

欢迎对项目进行贡献！请按照以下步骤参与：

### 提交问题

如果您发现任何问题或有功能建议，请创建 GitHub Issue。

### 提交代码

1. Fork 本仓库
2. 创建您的特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交您的更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

### 代码规范

- 使用清晰的变量命名
- 添加必要的注释说明
- 保持代码风格一致

---

## 许可证

本项目基于 MIT 许可证开源 - 详见 [LICENSE](LICENSE) 文件。

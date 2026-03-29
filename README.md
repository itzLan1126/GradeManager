# IB Grade Management System

[![Java](https://img.shields.io/badge/Java-8%2B-orange.svg)](https://www.oracle.com/java/technologies/downloads/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/Platform-CLI-green.svg)](README.md)

**[中文文档](README.zh-CN.md)**

A command-line Java application for managing IB (International Baccalaureate) course grades in an international school setting.

## Table of Contents

- [Project Overview](#project-overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [GPA Calculation](#gpa-calculation)
- [Data Storage](#data-storage)
- [Class Structure](#class-structure)
- [Contributing](#contributing)
- [License](#license)

---

## Project Overview

The IB Grade Management System is a simple grade management system designed for IB (International Baccalaureate) courses. It allows teachers to manage student information and grades, while students can view their own grades and GPA.

This project was originally developed as an AP CSA classroom project and later refactored to support the IB curriculum system, introducing HL (Higher Level) and SL (Standard Level) course weightings.

---

## Features

### Teacher Features

| Feature       | Description                                            |
| ------------- | ------------------------------------------------------ |
| User Login    | Login to the system with username and password         |
| Add Student   | Add new student information and create accounts        |
| Add Grades    | Add courses, IB grades, and course levels for students |
| View Grades   | View all students' grade information                   |
| Calculate GPA | Calculate student GPA based on IB grades               |

### Student Features

| Feature     | Description                                    |
| ----------- | ---------------------------------------------- |
| User Login  | Login to the system with username and password |
| View Grades | View personal course grades                    |
| View GPA    | View personal GPA and ranking                  |

---

## Tech Stack

| Technology               | Description                       |
| ------------------------ | --------------------------------- |
| **Programming Language** | Java 8+                           |
| **User Interface**       | Command Line Interface (CLI)      |
| **Data Storage**         | Plain Text Files (TXT)            |
| **Development Tools**    | VS Code / IntelliJ IDEA / Eclipse |

---

## Project Structure

```
GradeManager/
├── src/
│   ├── CLI.java         # CLI formatting utilities
│   ├── Person.java      # Base user class (contains main entry point)
│   ├── Student.java     # Student class, extends Person
│   └── Teacher.java     # Teacher class, extends Person
├── data/
│   ├── users.txt        # User data storage file
│   └── grades.txt       # Grade data storage file
├── docs/
│   └── PLAN.md          # Project development plan
├── LICENSE              # MIT License file
└── README.md            # This file
```

---

## Installation

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A text editor (VS Code recommended) or any Java IDE

### Install Java (if not already installed)

**macOS (using Homebrew):**

```bash
brew install openjdk@17
```

**Windows:**
Download and install JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [Adoptium](https://adoptium.net/).

**Linux (Ubuntu):**

```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

### Clone the Project

```bash
git clone https://github.com/itzLan1126/GradeManager.git
cd GradeManager
```

### Compile the Project

```bash
cd GradeManager
javac -d out src/Person.java src/Student.java src/Teacher.java src/CLI.java
```

Or use a single command:

```bash
javac src/Person.java src/Student.java src/Teacher.java src/CLI.java
```

---

## Usage

### Run the Application

After compiling, run the following command to start the system:

```bash
java -cp src Person
```

### Login

The system automatically initializes default test accounts on first startup. Teachers and students see different menu options after logging in.

### Default Test Accounts

| Role    | Username | Password |
| ------- | -------- | -------- |
| Teacher | teacher1 | pass123  |
| Student | student1 | pass123  |

### Operation Flow

#### Teacher Operation Flow

1. Login with teacher account
2. Choose from menu options:
   - **Add Student**: Enter student information to create new account
   - **Add Grade**: Select student and add course, IB grade, course level
   - **View Grades**: Browse all students' grades
   - **Calculate GPA**: View specific student's GPA

#### Student Operation Flow

1. Login with student account
2. Choose from menu options:
   - **View Grades**: View personal course grades
   - **View GPA**: View personal GPA

### Exit System

Select the exit option from the main menu to safely exit the system.

---

## GPA Calculation

### IB Grade Scale and Score Range

| IB Grade | Score Range | GPA Points |
| -------- | ----------- | ---------- |
| 7        | 95-100      | 4.0        |
| 6        | 87-94       | 4.0        |
| 5        | 77-86       | 3.5        |
| 4        | 67-76       | 3.0        |
| 3        | 57-66       | 2.0        |
| 2        | 47-56       | 1.0        |
| 1        | Below 47    | 0.0        |

### GPA Calculation Formula

```
GPA = Σ(Course GPA Points × Course Weight) / Σ(Course Weight)
```

Where:

- **HL (Higher Level)** weight: 1.2
- **SL (Standard Level)** weight: 1.0

### Example

Assuming a student has the following courses:

| Course                                 | IB Grade | Level | Base GPA | Weight | Weighted GPA |
| -------------------------------------- | -------- | ----- | -------- | ------ | ------------ |
| Mathematics Analysis and Approaches HL | 6        | HL    | 4.0      | 1.2    | 4.8          |
| Physics HL                             | 5        | HL    | 3.5      | 1.2    | 4.2          |
| English A Literature SL                | 6        | SL    | 4.0      | 1.0    | 4.0          |

**GPA = (4.8 + 4.2 + 4.0) / (1.2 + 1.2 + 1.0) = 13.0 / 3.4 = 3.82**

---

## Data Storage

The system uses plain text files for data persistence:

### User Data (data/users.txt)

```
# username,password,name,role,studentId,semester
teacher1,pass123,Mr. Lan,TEACHER,0,
student1,pass123,James,STUDENT,1001,2026-Fall
```

### Grade Data (data/grades.txt)

```
# studentId,semester,subject,ibGrade,level
1001,2026-Fall,Mathematics Analysis and Approaches HL,6,HL
1001,2026-Fall,Physics HL,5,HL
1001,2026-Fall,English A Literature SL,6,SL
```

---

## Class Structure

### Person Class

Base user class with the following properties:

| Property | Type   | Description                   |
| -------- | ------ | ----------------------------- |
| username | String | User login name               |
| password | String | User password                 |
| name     | String | User's full name              |
| role     | String | User role (TEACHER / STUDENT) |

### Student Class (extends Person)

Student class with additional properties:

| Property     | Type               | Description               |
| ------------ | ------------------ | ------------------------- |
| studentId    | int                | Unique student identifier |
| semester     | String             | Current semester          |
| subjects     | ArrayList<String>  | List of enrolled courses  |
| grades       | ArrayList<Integer> | Percentage grades         |
| ibGrades     | ArrayList<Integer> | IB grades (1-7 scale)     |
| courseLevels | ArrayList<String>  | Course levels (HL/SL)     |

### Teacher Class (extends Person)

Teacher class with the following methods:

| Method                | Description                                  |
| --------------------- | -------------------------------------------- |
| showTeacherMenu()     | Display teacher menu and handle user choices |
| addStudent()          | Add new student information                  |
| addGrade()            | Add course and grade for students            |
| viewAllGrades()       | View all student grades                      |
| calculateStudentGPA() | Calculate student GPA                        |

### CLI Class

Command-line interface formatting utilities:

| Constant/Method                  | Description                            |
| -------------------------------- | -------------------------------------- |
| CLI_WIDTH                        | Console width constant (56 characters) |
| printSeparator(char c)           | Print a separator line                 |
| printHeader(String title)        | Print a centered header                |
| truncate(String str, int length) | Truncate string with ellipsis          |

---

## Contributing

Contributions are welcome! Please follow these steps:

### Submit Issues

If you find any issues or have feature suggestions, please create a GitHub Issue.

### Submit Code

1. Fork this repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Code Standards

- Use clear variable naming
- Add necessary comments
- Maintain consistent code style

---

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

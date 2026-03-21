# IB Grade Management System

A command-line Java application for managing IB course grades in an international school setting.

## Project Overview

This is a simple grade management system designed for IB (International Baccalaureate) courses. It allows teachers to manage student information and grades, while students can view their own grades and GPA.

## Features

### Teacher Features

- Login to the system
- Add new student information
- Add courses and grades for students
- View all student grades
- Calculate student GPA

### Student Features

- Login to the system
- View own grades
- View own GPA

## Project Structure

```
GradeManager/
├── src/
│   ├── Person.java      # Base class for users (Teacher/Student)
│   ├── Student.java     # Student class with grade management
│   ├── Teacher.java     # Teacher class with teacher-specific functionality
│   └── CLI.java         # CLI formatting utilities
├── data/
│   ├── users.txt        # User data storage
│   └── grades.txt       # Grade data storage
├── docs/
│   └── PLAN.md          # Project plan
└── README.md            # This file
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- VS Code (recommended) or any Java IDE

### Running the Application

1. Compile the Java files:

   ```bash
   javac src/Person.java src/Student.java src/Teacher.java src/CLI.java
   ```

2. Run the application:
   ```bash
   java -cp src Person
   ```

### Default Test Accounts

| Role    | Username | Password |
| ------- | -------- | -------- |
| Teacher | teacher1 | pass123  |
| Student | student1 | pass123  |

## GPA Calculation

| IB Grade | Score Range | GPA Points |
| -------- | ----------- | ---------- |
| 7        | 95-100      | 4.0        |
| 6        | 87-94       | 4.0        |
| 5        | 77-86       | 3.5        |
| 4        | 67-76       | 3.0        |
| 3        | 57-66       | 2.0        |
| 2        | 47-56       | 1.0        |
| 1        | Below 47    | 0.0        |

**GPA Formula**: Weighted average of all course GPA points (HL weight: 1.2, SL weight: 1.0)

## Data Storage

The system uses plain text files for data persistence:

- **users.txt**: Stores user accounts (username, password, name, role, studentId, semester)
- **grades.txt**: Stores grade records (studentId, semester, subject, ibGrade, level)

## Class Structure

### Person Class

Base class containing:

- `username` - User login name
- `password` - User password
- `name` - Full name
- `role` - TEACHER or STUDENT

### Student Class (extends Person)

Contains additional fields:

- `studentId` - Unique student identifier
- `semester` - Current semester
- `subjects` - List of enrolled courses
- `grades` - List of numeric grades
- `ibGrades` - IB grades (1-7 scale)
- `courseLevels` - Course levels (HL or SL)

### Teacher Class (extends Person)

Teacher-specific functionality:

- `showTeacherMenu()` - Display teacher menu and handle choices
- `addStudent()` - Add new student information
- `addGrade()` - Add course and grade for students
- `viewAllGrades()` - View all student grades
- `calculateStudentGPA()` - Calculate student GPA

### CLI Class

Command-line interface formatting utilities:

- `CLI_WIDTH` - Console width constant (56 characters)
- `printSeparator(char c)` - Print a separator line
- `printHeader(String title)` - Print a centered header
- `truncate(String str, int length)` - Truncate string with ellipsis

## License

This project is for educational purposes.

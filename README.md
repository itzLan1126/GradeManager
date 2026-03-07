# AP Grade Management System

A command-line Java application for managing AP course grades in an international school setting.

## Project Overview

This is a simple grade management system designed for AP (Advanced Placement) courses. It allows teachers to manage student information and grades, while students can view their own grades and GPA.

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
│   └── Student.java     # Student class with grade management
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
   javac src/Person.java src/Student.java
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

| Grade Level | Score Range | GPA Points |
| ----------- | ----------- | ---------- |
| A+          | 95-100      | 4.0        |
| A           | 85-94       | 4.0        |
| B           | 75-84       | 3.0        |
| C           | 65-74       | 2.0        |
| D           | 60-64       | 1.0        |
| F           | Below 60    | 0.0        |

**GPA Formula**: Sum of all course GPA points / Number of courses

## Data Storage

The system uses plain text files for data persistence:

- **users.txt**: Stores user accounts (username, password, name, role, studentId, semester)
- **grades.txt**: Stores grade records (studentId, semester, subject, grade)

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
- `gradeLevels` - Letter grades (A, B, C, etc.)

## License

This project is for educational purposes.

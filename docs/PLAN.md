# AP Grade Management System

## Project Overview

This is a simple AP CSA classroom project for managing AP course grades for international school students.

## Technical Specifications

- **Programming Language**: Java
- **User Interface**: Command Line Interface (CLI)
- **Data Storage**: TXT text file
- **Core Classes**: Person, Student (only two classes)

## Data Model

### Person Class (User)

```java
public class Person {
    public String username;    // Username
    public String password;    // Password
    public String name;         // Name
    public String role;        // Role: TEACHER / STUDENT
}
```

### Student Class

```java
public class Student extends Person {
    public int studentId;              // Student ID
    public String semester;            // Semester
    public ArrayList<String> subjects; // Course list
    public ArrayList<Integer> grades;  // Grade list
    public ArrayList<String> gradeLevels; // Grade levels (A, B, C...)
}
```

## Feature List

### Teacher Features

1. Login to system
2. Add student information
3. Add courses and grades
4. View all student grades
5. Calculate student GPA

### Student Features

1. Login to system
2. View own grades
3. View own GPA

## Data File Format (data.txt)

```
# User data format
username,password,name,role,studentId
teacher1,pass123,Mr. Zhang,TEACHER,0
student1,pass123,Li Tongxue,STUDENT,1001

# Grade data format
studentId,semester,subject,grade
1001,2024-Fall,AP Calculus AB,95
1001,2024-Fall,AP Computer Science A,88
```

## GPA Calculation

- A+ = 4.0 (95-100)
- A = 4.0 (85-94)
- B = 3.0 (75-84)
- C = 2.0 (65-74)
- D = 1.0 (60-64)
- F = 0.0 (<60)

GPA = Sum of all course scores / Number of courses

## Development Plan

1. **Phase 1**: Modify Person class, add user roles and login functionality
2. **Phase 2**: Extend Student class, add more grade management features
3. **Phase 3**: Implement TXT file data persistence
4. **Phase 4**: Implement command line menu interface
5. **Phase 5**: Testing and refinement

---

## Refactoring (Phase 6)

### Reason for Refactoring

The original `Person.java` class violated the Single Responsibility Principle (SRP) by combining:
- User authentication and management
- CLI formatting utilities
- Teacher-specific business logic

### Changes Made

1. **Created `CLI.java`**: Extracted CLI formatting utilities into a dedicated utility class
   - `CLI_WIDTH` constant
   - `printSeparator(char c)` method
   - `printHeader(String title)` method
   - `truncate(String str, int length)` method

2. **Created `Teacher.java`**: Extracted teacher-specific functionality into a separate class
   - Teacher menu handling
   - Student management (add/view)
   - Grade management
   - GPA calculation

3. **Modified `Person.java`**: Removed extracted code, delegated to new classes

4. **Modified `Student.java`**: Updated to use `CLI` class methods directly

### Benefits

- **Better code organization**: Each class has a clear, single responsibility
- **Improved maintainability**: Changes to CLI formatting won't affect business logic
- **Enhanced reusability**: CLI utilities can be used across the application
- **Easier testing**: Each component can be tested independently

### Class Structure After Refactoring

```
GradeManager/src/
├── CLI.java         # CLI formatting utilities
├── Person.java      # Base user class
├── Student.java     # Student class (extends Person)
└── Teacher.java     # Teacher class (extends Person)
```

### Risks and Mitigations

| Risk | Mitigation |
|------|------------|
| Breaking existing functionality | Comprehensive testing after refactoring |
| Student.java compatibility | Updated to use CLI class directly |
| Main method entry point | Remains in Person.java, delegates to appropriate classes |

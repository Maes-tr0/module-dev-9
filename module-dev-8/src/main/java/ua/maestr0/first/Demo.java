package ua.maestr0.first;

import ua.maestr0.first.service.StudentService;
import ua.maestr0.first.model.Student;

public class Demo {
    public static void main(String[] args) {
        StudentService studentService = new StudentService();
        Student student = Student.builder()
                .firstName("      John      ")
                .lastName("    Jones   ")
                .grade(6.65)
                .build();
        studentService.printClint(student);
    }
}

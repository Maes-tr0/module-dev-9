package ua.maestr0.first.service;

import lombok.SneakyThrows;
import ua.maestr0.first.anotation.RoundNumber;
import ua.maestr0.first.anotation.Trim;
import ua.maestr0.first.model.Student;

import java.lang.reflect.Field;

public class StudentService {
    public void printClint(Student student) {
        processingTrim(student);
        processingInt(student);
        System.out.println(student);
    }

    @SneakyThrows
    private void processingInt(Student student) {
        Field[] declaredFields = student.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            if(field.isAnnotationPresent(RoundNumber.class)){
                field.setAccessible(true);
                double value = Double.parseDouble(field.get(student).toString());
                if((value - (int)value) * 10 >= 5){
                    field.set(student, (int)value + 1);
                } else {
                    field.set(student, (int)value);
                }
                field.setAccessible(false);
            }
        }
    }

    @SneakyThrows
    private void processingTrim(Student student) {
        Field[] fields = student.getClass().getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(Trim.class)) {
                field.setAccessible(true);
                String filedValue = field.get(student).toString();
                field.set(student, filedValue.trim());
                field.setAccessible(false);
            }
        }
    }
}

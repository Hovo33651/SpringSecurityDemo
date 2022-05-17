package com.example.demo.student;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Student {

    private final Integer studentId;
    private final String studentName;

}

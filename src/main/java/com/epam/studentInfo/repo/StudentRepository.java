package com.epam.studentInfo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.epam.studentInfo.model.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
	List<Student> findByAge(int age);
}
	
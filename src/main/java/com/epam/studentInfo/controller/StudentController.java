package com.epam.studentInfo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.epam.studentInfo.model.Student;
import com.epam.studentInfo.repo.StudentRepository;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	StudentRepository repository;

	@GetMapping("/students")
	public List<Student> getAllStudents() {
		System.out.println("Get all Students...");

		List<Student> students = new ArrayList<>();
		repository.findAll().forEach(students::add);

		return students;
	}

	@PostMapping(value = "/students/create")
	public Student postStudent(@RequestBody Student student) {

		Student _student = repository.save(new Student(student.getName(), student.getAge(),student.getSubject()));
		return _student;
	}

	@DeleteMapping("/students/{id}")
	public ResponseEntity<String> deleteStudent(@PathVariable("id") long id) {
		System.out.println("Delete Student with ID = " + id + "...");

		repository.deleteById(id);

		return new ResponseEntity<>("Student has been deleted!", HttpStatus.OK);
	}

	@DeleteMapping("/students/delete")
	public ResponseEntity<String> deleteAllStudents() {
		System.out.println("Delete All Students...");

		repository.deleteAll();

		return new ResponseEntity<>("All Students have been deleted!", HttpStatus.OK);
	}

	@GetMapping(value = "students/age/{age}")
	public List<Student> findByAge(@PathVariable int age) {

		List<Student> students = repository.findByAge(age);
		return students;
	}

	@PutMapping("/students/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable("id") long id, @RequestBody Student student) {
		System.out.println("Update Student with ID = " + id + "...");

		Optional<Student> studentData = repository.findById(id);

		if (studentData.isPresent()) {
			Student _student = studentData.get();
			_student.setName(student.getName());
			_student.setAge(student.getAge());
			_student.setSubject(student.getSubject());
			return new ResponseEntity<>(repository.save(_student), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}

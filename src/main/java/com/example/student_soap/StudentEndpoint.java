package com.example.student_soap;

import com.example.student_soap.entities.StudentEntity;
import com.example.student_soap.repositories.StudentRepository;
import com.example.student_soap.students.*; // JAXB generated classes
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class StudentEndpoint {

    private static final String NAMESPACE_URI = "http://schoolappsoap.com/webservice/students";
    private final StudentRepository studentRepository;

    @Autowired
    public StudentEndpoint(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddStudentRequest")
    @ResponsePayload
    public AddStudentResponse addStudent(@RequestPayload AddStudentRequest request) {
        StudentEntity entity = new StudentEntity(request.getNom(), request.getEmail());
        StudentEntity savedEntity = studentRepository.save(entity);

        AddStudentResponse response = new AddStudentResponse();
        response.setStudent(mapToJaxbStudent(savedEntity));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetStudentByIdRequest")
    @ResponsePayload
    public GetStudentByIdResponse getStudentById(@RequestPayload GetStudentByIdRequest request) {
        GetStudentByIdResponse response = new GetStudentByIdResponse();
        studentRepository.findById(request.getId()).ifPresent(entity ->
                response.setStudent(mapToJaxbStudent(entity))
        );
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllStudentsRequest")
    @ResponsePayload
    public GetAllStudentsResponse getAllStudents(@RequestPayload GetAllStudentsRequest request) {
        GetAllStudentsResponse response = new GetAllStudentsResponse();
        List<StudentEntity> entities = studentRepository.findAll();

        entities.forEach(entity ->
                response.getStudent().add(mapToJaxbStudent(entity))
        );

        return response;
    }

    // Internal mapping utility: Entity -> JAXB Object
    private Student mapToJaxbStudent(StudentEntity entity) {
        Student student = new Student();
        BeanUtils.copyProperties(entity, student);
        return student;
    }
}
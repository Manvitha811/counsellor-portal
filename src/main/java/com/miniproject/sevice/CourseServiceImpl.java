package com.miniproject.sevice;

import com.miniproject.entity.Course;
import com.miniproject.repo.CourseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepo courseRepo;
    @Override
    public List<Course> getCourses() {
        return courseRepo.findAll();
    }
}

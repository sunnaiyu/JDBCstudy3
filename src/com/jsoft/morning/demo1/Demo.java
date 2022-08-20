package com.jsoft.morning.demo1;

import org.junit.Test;

public class Demo {

    @Test
    public void test01() {

        TeacherDao teacherDao = new TeacherDao();
        System.out.println(teacherDao.findAllTeacher());

    }
}

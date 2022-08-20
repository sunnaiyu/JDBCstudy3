package com.jsoft.afternoon.demo;

import org.junit.Test;

import java.lang.invoke.VarHandle;
import java.util.List;

public class TeacherDaoTest {
    TeacherDao teacherDao = new TeacherDao();
    @Test
    public void test02(){

        try {
            List<Teacher> teachers = teacherDao.getForList("select * from teacher");
//            System.out.println(teachers);
            Teacher teacher = teacherDao.get("select id,name from teacher where id = 1");
//            System.out.println(teacher);
            long r = teacherDao.getForValue("select count(*) from teacher where name = ?", "Rose");
            System.out.println(r);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @Test
    public void test01() {

        try {
            int i = teacherDao.update("insert into teacher(name) values (?)", "李四");
            System.out.println(i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

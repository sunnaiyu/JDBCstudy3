package com.jsoft.morning.demo2;

public class Demo {

    public static void main(String[] args) {
//        System.out.println(Demo.class.getName());
//        UserDao userDao = new UserDao();
//        System.out.println(userDao.findAll(User.class));
//        userDao.update(new User(2,"xyz","654321"),"id",2);


        TeacherDao teacherDao = new TeacherDao();
//        System.out.println(teacherDao.findAll(Teacher.class));
//        teacherDao.save(new Teacher(10,"HH"));
//        teacherDao.update(new Teacher(10,"zzz"),"id",10);
//        teacherDao.delete(Teacher.class,"id",10);
        System.out.println(teacherDao.findOne(Teacher.class, "id", 5));
    }
}

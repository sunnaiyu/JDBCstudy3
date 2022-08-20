package com.jsoft.morning.demo1;

import com.jsoft.util.BaseDao;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDao extends BaseDao {

    public int saveTeacher(Teacher teacher) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "insert into teacher (name) values (?)";

        try {
            conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacher.getName());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            release(pstmt, null);
        }

    }

    public int updateTeacher(Teacher teacher) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "update teacher set name = ? where id = ?";

        try {
            conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, teacher.getName());
            pstmt.setInt(2, teacher.getId());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            release(pstmt, null);
        }
    }

    public int deleteTeacher(Integer id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "delete from teacher where id = ?";

        try {
            conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            release(pstmt, null);
        }
    }

    public List<Teacher> findAllTeacher() {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String sql = "select id,name from teacher";

        List<Teacher> teachers = new ArrayList<>(16);

        try {
            conn = DATA_SOURCE.getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while(rs.next()) {
                Teacher teacher = new Teacher(rs.getInt(1),rs.getString(2));
                teachers.add(teacher);
            }
            return teachers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询某一个老师
     */
    public Teacher findOneTeacher(Integer id) {

        return null;
    }

    /**
     * 查询某一列的数据：结果是一行一列
     */
    public String findTeacherName(Integer id) {

        return null;
    }
}

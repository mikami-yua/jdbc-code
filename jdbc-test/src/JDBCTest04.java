import java.sql.*;

/**
 * PreparedStatement完成增删改 insert
 */
public class JDBCTest04 {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        //ResultSet rs=null；不是查询不需要写

        try {
            //1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取数据库操作对象
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learningtest","root","333");
            //3.获取预编译的sql命令
            String sql="insert into dept(deptno,dname,loc) values(?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setInt(1,60);
            ps.setString(2,"销售部");
            ps.setString(3,"上海");
            //4.执行sql
            int count=ps.executeUpdate();
            System.out.println(count);
            //5.获取结果集（DML没有这步）
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //6.释放资源
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }


    }
}

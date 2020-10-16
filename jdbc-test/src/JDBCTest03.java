import java.sql.*;
import java.util.Scanner;

public class JDBCTest03 {
    public static void main(String[] args) {
        /*
        //用户在控制台输入desc就是降序，asc就是升序
        Scanner s=new Scanner(System.in);
        System.out.println("please input desc/asc :");
        String keyWords=s.nextLine();

        //执行sql
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learningtest","root","333");
            //获取预编译的数据库操作对象
            String sql="select ename from emp order by ename ?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,keyWords);
            //执行sql
            rs=ps.executeQuery();
            //遍历结果集
            while (rs.next()){
                System.out.println(rs.getString("ename"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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

         */
        //用户在控制台输入desc就是降序，asc就是升序
        Scanner s=new Scanner(System.in);
        System.out.println("please input desc/asc :");
        String keyWords=s.nextLine();

        //执行sql
        Connection conn=null;
        Statement stmt=null;
        ResultSet rs=null;

        try {
            //注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learningtest","root","333");
            //获取预编译的数据库操作对象
            stmt=conn.createStatement();
            //执行sql
            String sql="select ename from emp order by ename "+keyWords;
            rs=stmt.executeQuery(sql);
            //遍历结果集
            while (rs.next()){
                System.out.println(rs.getString("ename"));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
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

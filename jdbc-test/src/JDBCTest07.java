import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *  JDBC中的事务机制
 *      JDBC中的事物是自动提交的（执行任意一条DML语句提交一次）
 *      在实际业务中通常是n条DML语句共同联合才能完成，必须保证他们要么同时成功要么同时失败
 *
 *      以下验证JDBC的事物提交机制
 *          测试结果JDBC中执行任意一条DML语句就提交一次`
 */
public class JDBCTest07 {
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
            String sql="update dept set dname=? where deptno=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"a部门");
            ps.setInt(2,30);
            int count=ps.executeUpdate();
            System.out.println(count);

            //重新给占位符传值
            ps.setString(1,"b部门");
            ps.setInt(2,20);
            count=ps.executeUpdate();
            System.out.println(count);

            //4.执行sql
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

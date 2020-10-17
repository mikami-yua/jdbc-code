package lock;

import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 程序开启一个事务，专门用于查询，使用行级锁（悲观锁）
 */
public class JDBCLock1 {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            conn= DBUtil.getConnection();
            //开启事务
            conn.setAutoCommit(false);

            String sql="select ename,sal,job from emp where job=? for update ";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"manager");
            rs=ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("ename")+","+rs.getString("sal")+","+rs.getString("job"));
            }

            //提交事务
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            DBUtil.close(conn,ps,rs);
        }
    }
}

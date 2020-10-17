package lock;

import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 复制修改被锁定的记录
 */
public class JDBCLock2 {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn= DBUtil.getConnection();
            conn.setAutoCommit(false);

            String sql="update emp set sal=sal*1.1 where job=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,"manager");
            int count=ps.executeUpdate();
            System.out.println(count);

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            DBUtil.close(conn,ps,null);
        }

    }

}

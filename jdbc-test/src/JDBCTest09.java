import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 1.测试DBUtil
 * 2.模糊查询
 */
public class JDBCTest09 {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        ResultSet rs=null;

        try {
            //获取连接
            conn= DBUtil.getConnection();

            //获取预编译的数据库操作对象
            String sql="select ename from emp where ename like ?";//模糊查询整个位置是？
            ps=conn.prepareStatement(sql);
            ps.setString(1,"_A%");//查询第二个字母是a的
            rs=ps.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString("ename"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放资源
            DBUtil.close(conn,ps,rs);
        }

    }
}

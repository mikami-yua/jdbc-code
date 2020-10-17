import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 模拟银行转账
 * sql脚本：
 * drop table if exists t_act;
 * create table t_act(
 *      actno int,
 *      balance double(7,2)
 * );
 * insert into t_act(actno,balance) values(111,20000);
 * insert into t_act(actno,balance) values(222,0);
 * commit;
 * select * from t_act;
 * balance double(7,2) 7个有效数字，两个小数位
 */
public class JDBCTest08 {
    public static void main(String[] args) {
        Connection conn=null;
        PreparedStatement ps=null;
        //ResultSet rs=null；不是查询不需要写

        try {
            //1.注册驱动
            Class.forName("com.mysql.jdbc.Driver");
            //2.获取数据库操作对象
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learningtest","root","333");
            //【将自动提交机制改为手动提交】-----------------------------------开启事务
            conn.setAutoCommit(false);

            //3.获取预编译的sql命令
            String sql="update t_act set balance=? where actno=?";
            ps=conn.prepareStatement(sql);//预编译
            //给问号传值
            ps.setDouble(1,10000);
            ps.setInt(2,111);
            int count=ps.executeUpdate();

            String s=null;
            s.toString();//人为制造空指针异常，会进入catch语句块，数据发生丢失，转账失败，丢失10000


            //再给问号传值
            ps.setDouble(1,10000);
            ps.setInt(2,222);
            count+=ps.executeUpdate();
            //count==2 转账成功
            System.out.println(count==2? "转账成功":"转账失败");


            //4.执行sql
            //5.获取结果集（DML没有这步）

            //【事物执行至此处说明没有异常手动提交即可】----------------------------提交事务
            conn.commit();
        } catch (Exception e) {
            //【遇到异常手动回滚】-------------------------------------------------回滚事务
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        }  finally {
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

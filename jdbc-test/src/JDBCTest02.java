import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 解决sql注入问题
 *      用户提供的信息不参与sql语句的编译过程
 *      即使用户提供的信息中含有sql语句的关键字，但是没有参与编译，不起作用【关键】
 *      使用java.spl.PreparedStatement
 *      PreparedStatement继承了Statement
 *      PreparedStatement属于预编译的数据库操作对象
 *          原理：预先对sql语句框架进行编译，再对sql语句传值
 *
 *      please input username:
 *      fdsa
 *      please input password:
 *      fdsa' or '1'='1
 *      login fail!
 *
 *  对比statement和preparedstatement
 *      statement存在sql注入    preparedstatement解决了sql注入
 *      statement编译一次执行一次  preparedstatement编译一次执行很多次（效率较高）（sql语句如果没有任何变换只会编译一次，再次输入相同的sql不会编译，直接执行）
 *                                                  会在编译阶段进行类型的安全检查
 *      preparedstatement使用较多
 *      仅在业务要求进行sql注入的时候使用statement
 */
public class JDBCTest02 {
    public static void main(String[] args) {
        //1.初始化一个界面
        Map<String,String> userLoginInfo=initUI();
        //2.验证用户名密码
        boolean loginState=login(userLoginInfo);
        //3.输出结果
        System.out.println(loginState ? "login success! ":"login fail! ");
    }

    /**
     * 用户登录
     * @param userLoginInfo 用户登录信息
     * @return true成功 false失败
     */
    private static boolean login(Map<String, String> userLoginInfo) {
        //打标记
        boolean loginstate=false;
        //jdbc代码
        Connection conn=null;
        PreparedStatement ps=null;//PreparedStatement预编译的数据库操作对象
        ResultSet rs=null;
        String loginname=userLoginInfo.get("loginName");
        String password=userLoginInfo.get("password");

        try {
            /*
            1、注册驱动
            原理使用类加载机制，使静态代码块执行，在静态代码块中实现了驱动的注册
             */
            //Class.forName("com.mysql.cj.jdbc.Driver");//com.mysql.cj.jdbc.Driver新的包，原来的com.mysql.jdbc.Driver已经不再使用
            Class.forName("com.mysql.jdbc.Driver");//5.5之前可用
            //2.获取连接
            conn= DriverManager.getConnection("jdbc:mysql://localhost:3306/learningtest","root","333");
            //3.获取数据库操作对象，使用预编译的sql，sql语句需要提前。获取预编译的数据库操作对象
            String sql="select * from t_user where loginname=? and loginpwd=?";//两个问好称为占位符。这个是sql语句的框架，一个问号代表一个占位符
            ps=conn.prepareStatement(sql);//程序执行到此处会发送sql语句，框子给DBMS，DBMS对sql语句进行预先编译
            //给占位符传值。第一个问号下标是1，第二个问号下标是2（JDBC中所有下标从1开始）,都是string类型使用setstring
            ps.setString(1,loginname);
            ps.setString(2,password);
            //4.执行sql
            rs=ps.executeQuery();
            //5.处理结果集
            if(rs.next()){
                loginstate=true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }finally {
            //6.释放资源
            if (rs == null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps == null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn == null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return loginstate;
    }

    /**
     * 初始化用户界面
     * @return 返回用户输入的用户名密码登录信息
     */
    private static Map<String, String> initUI() {
        Scanner s=new Scanner(System.in);
        System.out.println("please input username: ");
        String userName=s.nextLine();//一读读一行
        System.out.println("please input password: ");
        String passwd=s.nextLine();
        Map<String,String> userLoginInfo=new HashMap<>();
        userLoginInfo.put("loginName",userName);
        userLoginInfo.put("password",passwd);
        return userLoginInfo;
    }
}

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/*
实现功能：
    1.模拟用户登录功能的实现
    2.描述：
        运行时提供一个输入的入口，可以让用户输入用户名和密码
        提交信息
        java连接数据库验证用户名和密码是否正确
    3.数据准备：
        使用专门的建模工具建表（参见userlogin脚本）

存在的问题：SQL注入
    please input username:
    fdsa
    please input password:
    fdsa' or '1'='1
    login success!

    select * from t_user where loginname='fdsa' and loginpwd='fdsa' or '1'='1'
    mysql> select * from t_user where loginname='fdsa' and loginpwd='fdsa' or '1'='1';
    +----+-----------+----------+----------+
    | id | loginName | loginPwd | realName |
    +----+-----------+----------+----------+
    |  1 | zhangsan  | 123      | 寮犱笁     |
    |  2 | jack      | 123      | 鏉庡洓     |
    |  3 | wangwu    | 123      | 鐜嬩簲     |
    |  4 | rose      | 123      | 璧靛叚     |
    |  5 | aaaaa     | 123      | 寮犵寷     |
    +----+-----------+----------+----------+

    根本原因：用户输入的信息中含有sql语句的关键字，并且这些关键字参与了sql的编译过程，导致sql的原意被扭曲
 */
public class JDBCTest01 {
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
        Statement stmt=null;
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
            //3.获取数据库操作对象
            stmt=conn.createStatement();
            //4.执行sql
            String sql="select * from t_user where loginname='"+loginname+"' and loginpwd='"+password+"'";
            rs=stmt.executeQuery(sql);
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
            if (stmt == null) {
                try {
                    stmt.close();
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

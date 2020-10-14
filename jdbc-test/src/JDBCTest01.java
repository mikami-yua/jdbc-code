/*
实现功能：
    1.模拟用户登录功能的实现
    2.描述：
        运行时提供一个输入的入口，可以让用户输入用户名和密码
        提交信息
        java连接数据库验证用户名和密码是否正确
    3.数据准备：
        使用专门的建模工具建表
 */
public class JDBCTest01 {
    public static void main(String[] args) {
        try {
            /*
            1、注册驱动
            原理使用类加载机制，使静态代码块执行，在静态代码块中实现了驱动的注册
             */
            Class.forName("com.mysql.cj.jdbc.Driver");//com.mysql.cj.jdbc.Driver新的包，原来的com.mysql.jdbc.Driver已经不再使用


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}

package com.company;


import com.sun.javafx.binding.StringFormatter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
public class JDBCDemo {
    private Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String dbURL = "jdbc:mysql://127.0.0.1:3306/hnb11";
            try {
                Connection connection = DriverManager.getConnection(dbURL,
                        "root","yizhibanjie0000");
                return connection;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void insertData(int id,String name,String publisher,String author) {
        Connection connection = null;
        Statement statement = null;

        try {
            connection = getConnection();
            String sql = "insert into shuji " +
                    "values(" + id + ",'" + name + "','" + publisher + "','" + author + "')";
            statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            System.out.println("所影响的行数为：" + rows);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,statement,null);
        }
    }

    private void deleteData(int id) {
        Connection connection = null;
        Statement statement = null;
        try {

            connection = getConnection();
            String sql = "delete from shuji where id=" + id;
            statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            System.out.println("有" + rows + "行被删除成功！");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,statement,null);
        }
    }

    private void updateData(int id, String name, String author,String publisher) {
        Connection connection = null;
        Statement statement = null;

        try {

            connection = getConnection();
            String sql = "update shuji set shuji_name='" + name +
                    "',shuji_author='" + author + "',shuji_publisher='"+publisher+"' where id=" + id;
            statement = connection.createStatement();
            int rows = statement.executeUpdate(sql);
            System.out.println("更新结果为：" + (rows > 0));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,statement,null);
        }
    }

    private String [][] bestFindAllData() {
        String [][] datas = new String [100][3];
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select shuji_name,shuji_author,shuji_publisher,id from shuji";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            int index = 0;
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("shuji_name");
                String publisher = resultSet.getString("shuji_publisher");
                String author = resultSet.getString("shuji_author");
                datas[index][0] = id + "";
                datas[index][1] = name;
                datas[index][2] = publisher;
                datas[index][3] =author;
                        index ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,statement,resultSet);
        }
        return datas;
    }
    private void findAllDataFormatOutput() {
        String [][] datas = bestFindAllData();
        StringBuffer buffer = new StringBuffer();
        buffer.append("------------------------------------------------------------------------------------------------" + System.lineSeparator());
        buffer.append("id\t\t\tname\t\t\tpublisher\t\t\tauthor\t\t\t" + System.lineSeparator());
        buffer.append("------------------------------------------------------------------------------------------------" + System.lineSeparator());
        for(int i = 0; i < datas.length; i ++) {
            String [] values = datas[i];
            if(values[0] != null && values[1] != null && values[2] != null && values[3] != null) {
                buffer.append(
                        String.format("%s\t|%s\t|%s\t|%s", values[0], values[1], values[2]));
                buffer.append(System.lineSeparator());
            }
        }
        System.out.println(buffer.toString());
    }


    private void findAccountDataById(int id) {
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select shuji_name,shuji_publisher,shuji_author,id from shuji where id=" + id;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            StringBuffer buffer = new StringBuffer();
            buffer.append("------------------------------------------------------------------------------------------------" + System.lineSeparator());
            buffer.append("id\t\t\tname\t\t\tpublisher\t\t\tauthor\t\t\t" +  System.lineSeparator());
            buffer.append("------------------------------------------------------------------------------------------------" + System.lineSeparator());
            while(resultSet.next()) {
                String name = resultSet.getString("shuji_name");
                String publisher = resultSet.getString("shuji_publisher");
                String author = resultSet.getString("shuji_author");
                buffer.append(id + "\t| " + name + "| \t" + publisher+ "| \t" + author + "|" + System.lineSeparator());
            }

            System.out.println(buffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,statement,resultSet);
        }
    }
    private void findAccountDataLikeKeyWord(String keyWord) {
        Connection connection = getConnection();
        Statement statement = null;
        ResultSet resultSet = null;
        String sql = "select shuji_name,shuji_publisher,shuji_author,id from shuji " +
                "where shuji_name like '%" + keyWord + "%' or shuji_publisher like '%" + keyWord + "%'or shuji_author like '%" + keyWord + "%'";
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            StringBuffer buffer = new StringBuffer();
            buffer.append("------------------------------------------------------------------------------------------------"
                    + System.lineSeparator());
            buffer.append("id\t\t\tname\t\t\tpublisher\t\t\tauthor\t\t\t" + System.lineSeparator());
            buffer.append("------------------------------------------------------------------------------------------------"
                    + System.lineSeparator());
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("shuji_name");
                String publisher = resultSet.getString("shuji_punlisher");
                String author = resultSet.getString("shuji_author");
                buffer.append(id + "\t| " + name + "| \t" + publisher+ "| \t" + author + "|" +System.lineSeparator());
            }

            System.out.println(buffer.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(connection,statement,resultSet);
        }
    }

    private void close(Connection connection,
                       Statement statement,
                       ResultSet resultSet) {
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String [] args) {
        Scanner scanner = new Scanner(System.in);
        JDBCDemo jdbcDemo = new JDBCDemo();
        while (true) {
            System.out.println("=================================================================================================");
            System.out.println("|                    欢迎使用秦东文书籍管理系统  请选择你要进行的操作:                                 |");
            System.out.println("| 1.添加书籍   2.修改书籍   3.删除书籍   4. 查看书籍 5.按关键字对书籍名，出版商，作者进行模糊搜索 6.退出系统 |");
            System.out.println("=================================================================================================");
            int select = 0;
            select = scanner.nextInt();
            while (select < 1 || select > 4) {
                System.out.println("选择的操作不能识别，请重新选择：");
                select = scanner.nextInt();
            }
            String value = null;

            if (select == 1) {
                System.out.println("请输入要添加的书籍，中间用逗号分隔.");
                value = scanner.next();
                String[] values = value.split(",");
                jdbcDemo.insertData((int) System.currentTimeMillis(),
                        values[0], values[1],values[2]);
            } else if (select == 2) {
                System.out.println("请输入要修改的id、书籍名，出版商和作者。逗号分隔。系统将根据id进行数据的更新。id本身不会更新请放心..");
                value = scanner.next();
                String[] values = value.split(",");
                jdbcDemo.updateData(Integer.parseInt(values[0]),
                        values[1],values[2],values[3]);
            } else if (select == 3) {
                System.out.println("请输入要删除的id");
                value = scanner.next();
                jdbcDemo.deleteData(Integer.parseInt(value));
            } else if (select == 4) {
                jdbcDemo.findAllDataFormatOutput();
            } else if (select == 5) {
                System.exit(-1);
            }
        }
    }
}

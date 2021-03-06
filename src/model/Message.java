package model;

import Utils.JDBCUtils;
import entity.MessageInfo;
import entity.PageMessageList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * @version 1.0
 * <p>获取Message的 模型层</p>
 * <p>获取留言的模型</p>
 * @className Message
 * @author: Mango
 * @date: 2020-09-08 01:39
 */
public class Message {

    /**
     * 获取所有留言信息分页集合
     * <p>此方法为网站主页展示message所使用，此方法查询数据库返回留言信息集合分页对象</p>
     * @param
     * @return 返回一个留言信息分页集合对象PageMessageList
     */
    public static PageMessageList pageMessageListAll(int pageNum) {


        //默认在用户个人信息页面中获取其发布的message，默认每页有15个message
        PageMessageList pageMessageList = new PageMessageList(pageNum,10);
        LinkedList<MessageInfo> messageList = pageMessageList.getMessageList();

        Connection conn = null;
        PreparedStatement psQuitList = null;    //分页查询的ps
        PreparedStatement psQuitCount = null;   //获取记录条数的ps
        ResultSet rs = null;
        int count = -1;

        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(true);

            //分页查询数据
            psQuitList = conn.prepareStatement("select SQL_CALC_FOUND_ROWS u.uid,m.mid,m.target,m.date,left(m.msg,60) msg,m.color,m.anony,u.nickname from message m LEFT JOIN user u on m.uid=u.uid order by m.mid desc limit ?,10");
            psQuitList.setInt(1,pageMessageList.getIndex());
            rs = psQuitList.executeQuery();
            while (rs.next()) {
                messageList.add(new MessageInfo(
                        rs.getInt("mid"),
                        rs.getInt("uid"),
                        rs.getString("nickname"),
                        rs.getString("target"),
                        rs.getString("date"),
                        rs.getString("msg"),
                        rs.getInt("color"),
                        rs.getInt("anony")
                ));
            }

            //获取总条数，此函数FOUND_ROWS()可以查询到上条语句如果不使用limit所返回的条数
            psQuitCount = conn.prepareStatement("select FOUND_ROWS() count");
            rs = psQuitCount.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            // System.out.println("count = "+count);

            pageMessageList.setAllRowCount(count);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeAll(conn,psQuitList,rs);
            JDBCUtils.closeStatement(psQuitCount);
        }

        return pageMessageList;

    }



    /**
     * 通过uid获取留言信息分页集合
     * <p>此方法为个人信息页面展示个人所发布的message所使用，此方法通过uid作为条件查询数据库返回留言信息集合</p>
     * @param
     * @return 返回一个留言信息集合
     */
    public static PageMessageList pageMessageListFromUid(int pageNum, int uid) {


        //默认在用户个人信息页面中获取其发布的message，默认每页有15个message
        PageMessageList pageMessageList = new PageMessageList(pageNum, 15);
        LinkedList<MessageInfo> messageList = pageMessageList.getMessageList();

        Connection conn = null;
        PreparedStatement psQuitList = null;    //分页查询的ps
        PreparedStatement psQuitCount = null;   //获取记录条数的ps
        ResultSet rs = null;
        int count = -1;

        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(true);

            //分页查询数据
            psQuitList = conn.prepareStatement("select SQL_CALC_FOUND_ROWS u.uid,m.mid,m.target,m.date,left(m.msg,30) msg,m.color,m.anony,u.nickname from message m LEFT JOIN user u on m.uid=u.uid where m.uid=? order by m.mid desc limit ?,15");
            psQuitList.setInt(1,uid);
            psQuitList.setInt(2,pageMessageList.getIndex());
            rs = psQuitList.executeQuery();
            while (rs.next()) {
                messageList.add(new MessageInfo(
                        rs.getInt("mid"),
                        rs.getInt("uid"),
                        rs.getString("nickname"),
                        rs.getString("target"),
                        rs.getString("date"),
                        rs.getString("msg"),
                        rs.getInt("color"),
                        rs.getInt("anony")
                ));
            }

            //获取总条数，此函数FOUND_ROWS()可以查询到上条语句如果不使用limit所返回的条数
            psQuitCount = conn.prepareStatement("select FOUND_ROWS() count");
            rs = psQuitCount.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            pageMessageList.setAllRowCount(count);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeAll(conn,psQuitList,rs);
            JDBCUtils.closeStatement(psQuitCount);
        }

        return pageMessageList;

    }



    /**
     * 通过uid获取非匿名的留言信息分页集合
     * <p>此方法为在访问其他用户详情信息页面的留言message时使用，此方法通过uid、anony作为条件查询数据库返回留言信息集合</p>
     * @param
     * @return 返回一个留言信息集合
     */
    public static PageMessageList pageMessageListFromUidWhereNotAnony(int pageNum, int uid) {


        //默认在用户个人信息页面中获取其发布的message，默认每页有15个message
        PageMessageList pageMessageList = new PageMessageList(pageNum, 15);
        LinkedList<MessageInfo> messageList = pageMessageList.getMessageList();

        Connection conn = null;
        PreparedStatement psQuitList = null;    //分页查询的ps
        PreparedStatement psQuitCount = null;   //获取记录条数的ps
        ResultSet rs = null;
        int count = -1;

        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(true);

            //分页查询数据
            psQuitList = conn.prepareStatement("select SQL_CALC_FOUND_ROWS m.mid,m.target,m.date,left(m.msg,30) msg from message m LEFT JOIN user u on m.uid=u.uid where m.uid=? and anony=1 order by m.mid desc limit ?,15");
            psQuitList.setInt(1,uid);
            psQuitList.setInt(2,pageMessageList.getIndex());
            rs = psQuitList.executeQuery();
            while (rs.next()) {
                messageList.add(new MessageInfo(
                        rs.getInt("mid"),
                        rs.getString("target"),
                        rs.getString("date"),
                        rs.getString("msg")
                ));
            }

            //获取总条数，此函数FOUND_ROWS()可以查询到上条语句如果不使用limit所返回的条数
            psQuitCount = conn.prepareStatement("select FOUND_ROWS() count");
            rs = psQuitCount.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }
            pageMessageList.setAllRowCount(count);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeAll(conn,psQuitList,rs);
            JDBCUtils.closeStatement(psQuitCount);
        }

        return pageMessageList;

    }



    /**
     * 搜索关键字获取所有留言信息分页集合
     * <p>此方法为搜索message时使用，此方法查询数据库返回留言信息集合分页对象</p>
     * @param
     * @return 返回一个留言信息分页集合对象PageMessageList
     */
    public static PageMessageList pageMessageListSearchAll(int pageNum, String search) {


        //默认在用户个人信息页面中获取其发布的message，默认每页有30个message
        PageMessageList pageMessageList = new PageMessageList(pageNum,30);
        LinkedList<MessageInfo> messageList = pageMessageList.getMessageList();

        Connection conn = null;
        PreparedStatement psQuitList = null;    //分页查询的ps
        PreparedStatement psQuitCount = null;   //获取记录条数的ps
        ResultSet rs = null;
        int count = -1;

        try {
            conn = JDBCUtils.getConnection();
            conn.setAutoCommit(true);

            //分页查询数据
            //查询的msg最大长度为25
            psQuitList = conn.prepareStatement("select SQL_CALC_FOUND_ROWS u.uid,m.mid,m.target,m.date,left(m.msg,25) msg,m.color,m.anony,u.nickname from message m LEFT JOIN user u on m.uid=u.uid where m.msg like ? order by m.mid desc limit ?,30");
            psQuitList.setString(1,"%"+search+"%");
            psQuitList.setInt(2,pageMessageList.getIndex());
            rs = psQuitList.executeQuery();
            while (rs.next()) {
                messageList.add(new MessageInfo(
                        rs.getInt("mid"),
                        rs.getInt("uid"),
                        rs.getString("nickname"),
                        rs.getString("target"),
                        rs.getString("date"),
                        rs.getString("msg"),
                        rs.getInt("color"),
                        rs.getInt("anony")
                ));
            }

            //获取总条数，此函数FOUND_ROWS()可以查询到上条语句如果不使用limit所返回的条数
            psQuitCount = conn.prepareStatement("select FOUND_ROWS() count");
            rs = psQuitCount.executeQuery();
            while (rs.next()) {
                count = rs.getInt("count");
            }

            pageMessageList.setAllRowCount(count);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            JDBCUtils.closeAll(conn,psQuitList,rs);
            JDBCUtils.closeStatement(psQuitCount);
        }

        return pageMessageList;

    }


}

package controller;

import entity.UserLRInfo;
import model.Login;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0
 * @Description:
 * <p>登陆控制</p>
 * <p></p>
 * @className LoginServlet
 * @author: Mango
 * @date: 2020-09-05 22:37
 */

@WebServlet("/login")
public class LoginCtrl extends HttpServlet {
    /**
     * 用户登陆控制层
     * <p>用户登陆成功返回0，失败返回-1</p>
     * @param request 请求
     * @param response 响应
     * @return void
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UserLRInfo userLRInfo = new UserLRInfo(email, password);

        response.setContentType("text/plain;charset=UTF-8");

        if(Login.canLogin(userLRInfo)){
            response.getWriter().write("1");

        } else {
            response.getWriter().write("-1");
        }
    }
}

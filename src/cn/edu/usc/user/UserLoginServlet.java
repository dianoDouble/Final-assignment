package cn.edu.usc.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/userLogin")
public class UserLoginServlet extends HttpServlet {

    public final static String LOGIN_TOKEN = "USER_LOGIN_TOKEN";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        if (userName != "" && password != "") {
            this.doLogin(request, response);
        } else {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(UserLoginServlet.LOGIN_TOKEN) != Boolean.TRUE) {
                response.sendRedirect("./user-login.html");
            } else {
                response.sendRedirect("./mycart.html");
            }
        }
    }
    private void doLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String code = request.getParameter("code");
        String verifyCode = (String) request.getSession(false).getAttribute(ValidateCodeServlet.LOGIN_VERIFY_CODE);
        if (code == null || !code.equalsIgnoreCase(verifyCode)) {
            System.out.println("验证码错误");
            response.sendRedirect("./user-login.html");
            return;
        }
        try {
            User user = UserRepo.getInstance().auth(userName, password);
            if (user != null) {
                System.out.println("用户名密码正确");
                HttpSession session = request.getSession(true);
                session.setAttribute(LOGIN_TOKEN, Boolean.TRUE);
                response.sendRedirect("./index.html");
            } else {
                response.sendRedirect("./user-login.html");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

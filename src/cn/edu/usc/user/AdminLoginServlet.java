package cn.edu.usc.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/adminLogin")
public class AdminLoginServlet extends HttpServlet {

    public final static String LOGIN_TOKEN = "ADMIN_LOGIN_TOKEN";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("开始验证管理员身份");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        System.out.println((userName==null) + " " + (password!=""));
        System.out.println("userName=" + userName + "over");


        if (userName != "" && password != "") {
            this.doLogin(request, response);
        } else {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(AdminLoginServlet.LOGIN_TOKEN) != Boolean.TRUE) {
                response.sendRedirect("./admin-login.html");
            } else {
                response.sendRedirect("./admin.html");
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
            response.sendRedirect("./admin-login.html");
            return;
        }
        System.out.println("验证码验证成功");

        try {
            Admin admin = AdminRepo.getInstance().auth(userName, password);
            if (admin != null) {
                System.out.println("管理员用户名密码正确");
                HttpSession session = request.getSession(true);
                session.setAttribute(LOGIN_TOKEN, Boolean.TRUE);
                response.sendRedirect("./admin.html");
            } else {
                response.sendRedirect("./admin-login.html");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

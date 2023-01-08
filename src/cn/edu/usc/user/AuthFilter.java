package cn.edu.usc.user;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uri = request.getRequestURI();
        if ( uri.endsWith("index.html")
                || uri.endsWith("-login.html")
                || uri.endsWith("Login")
                || uri.endsWith("listRole")
                || uri.endsWith("new-user.html")
                || uri.endsWith("verifyCode")
                || uri.endsWith("png")
                || uri.endsWith("jpg")
                || uri.endsWith("css")
                || uri.endsWith("/")) {
            // 对于不需要进行登录认证的资源，直接放行，继续往后执行
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("无权限登录");
            response.sendRedirect("./user-login.html");
            System.out.println("进行登录");
        } else {
            Boolean adminToke = (Boolean) session.getAttribute(AdminLoginServlet.LOGIN_TOKEN);
            Boolean userToke = (Boolean) session.getAttribute(UserLoginServlet.LOGIN_TOKEN);
            if (adminToke == Boolean.TRUE) {
                System.out.println("管理员已登录");
                chain.doFilter(request, response);
            } else if (userToke == Boolean.TRUE) {
                System.out.println("获得用户标记但无管理员标记");
                if (!uri.endsWith("admin.html") &&
                    !uri.endsWith("submit-role.html") &&
                    !uri.endsWith("delete-role.html") &&
                    !uri.endsWith("saveRole") &&
                    !uri.endsWith("deleteRole") &&
                    !uri.endsWith("listUser")) {
                    chain.doFilter(request, response);
                } else {
                    response.sendRedirect("./admin-login.html");
                }
            }
            else {
                System.out.println("未获得任何登录标记");
                response.sendRedirect("./user-login.html");
            }
        }
    }

}

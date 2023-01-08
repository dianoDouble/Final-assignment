package cn.edu.usc.user;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet("/saveUser")
public class SaveUserServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");

        cn.edu.usc.user.User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(name);
        user.setUserName(userName);
        user.setPassword(password);

        String message;
        try {
            UserRepo.getInstance().save(user);
            message = "提交信息保存成功";
        } catch (SQLException e) {
            message = "提交信息保存失败";
        }
        response.sendRedirect("./success.html");

    }
}

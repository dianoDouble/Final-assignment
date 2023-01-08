package cn.edu.usc.user;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/listUser")
public class ListUserServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<cn.edu.usc.user.User> users = UserRepo.getInstance().getAll();
            response.setContentType("text/html; charset=UTF-8");
            try(Writer writer = response.getWriter()) {
                writer.write("<head><title>查看用户信息</title></head>");
                writer.write("<center style=\"margin-top:5em\">\n");
                writer.write("<h1>查看用户信息</h1>\n");

                writer.write("<table width='55%' border='0' cellpadding=4>");
                for(int i=0; i<users.size(); i++) {
                    User user = users.get(i);
                    if (i % 2 == 0) {
                        writer.write("<tr style='background-color:#F5F5F5;height:2em'>");
                    } else {
                        writer.write("<tr style='background-color:#D6E6F2;height:2em'>");
                    }
                    writer.write(String.format("<td width='30px'>%s</td>", user.getId()));
                    writer.write(String.format("<td width='150px'>%s</td>", user.getName()));
                    writer.write(String.format("<td width='100px'>%s</td>", user.getUserName()));
                    writer.write(String.format("<td width='60px'>%s</td>", user.getPassword()));
                    writer.write("</tr>");
                }
                writer.write("</table><br><br>\n\n");

                writer.write("<a href='./admin.html'>返回管理员界面</a>\n");
                writer.write("</center>\n");

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

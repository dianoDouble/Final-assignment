package cn.edu.usc.role;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;

@WebServlet("/updateRole")
public class UpdateRoleServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        Role role;
        try {
            role = RoleRepo.getInstance().getById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String html = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Role</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <center>\n" +
                "    <div style=\"margin-top:5em; padding: 2em;text-align:center; width:60%; background-color:#EEEEEE\">\n" +
                "      <h2>编辑角色信息</h2>\n" +
                "      <form action=\"./saveRole\" method=\"post\" enctype=\"multipart/form-data\">\n" +
                "        <input type=\"hidden\" name=\"id\" value=\"" + role.getId() + "\"><br><br>\n" +
                "        中文名： <input type=\"text\" name=\"cname\" value=\"" + role.getCname() + "\"><br><br>\n" +
                "        英文名： <input type=\"text\" name=\"ename\" value=\"" + role.getEname() + "\"><br><br>\n" +
                "<span style=\"padding-left:80px\"/>图 片： <input type=\"file\" name=\"picture\"> <br><br>" +
                "        <input type=\"submit\" value=\" 提 交 信 息\">\n" +
                "      </form>\n" +
                "    </div>\n" +
                "  </center>\n" +
                "</body>\n" +
                "</html>";

        response.setContentType("text/html; Charset=utf8");
        try(Writer writer = response.getWriter()) {
            writer.write(html);
        }
    }

}

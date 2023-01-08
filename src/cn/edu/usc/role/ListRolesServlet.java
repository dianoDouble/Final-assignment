package cn.edu.usc.role;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/listRoles")
public class ListRolesServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<cn.edu.usc.role.Role> roles = RoleRepo.getInstance().getAll();
            response.setContentType("text/html; charset=UTF-8");
            try(Writer writer = response.getWriter()) {
                writer.write("<head><title>查看角色信息</title></head>");
                writer.write("<center style=\"margin-top:5em\">\n");
                writer.write("<h1>查看角色信息</h1>\n");

                writer.write("<table width='55%' border='0' cellpadding=4>");
                writer.write("<tr style='background-color:#D6E6F2;height:2em'>");
                writer.write("<td width='30px'>id</td>");
                writer.write("<td width='150px'>中文名</td>");
                writer.write("<td width='100px'>英文名</td>");
                writer.write("<td>图片</td>");
                writer.write("<td>删除</td>");
                writer.write("<td>修改</td>");
                for(int i=0; i<roles.size(); i++) {
                    Role role = roles.get(i);
                    if (i % 2 == 0) {
                        writer.write("<tr style='background-color:#F5F5F5;height:2em'>");
                    } else {
                        writer.write("<tr style='background-color:#D6E6F2;height:2em'>");
                    }
                    writer.write(String.format("<td width='30px'>%s</td>", role.getId()));
                    writer.write(String.format("<td width='150px'>%s</td>", role.getCname()));
                    writer.write(String.format("<td width='100px'>%s</td>", role.getEname()));
                    writer.write(String.format("<td><img src='./upload/%s' style='width:50px'/></td>", role.getPicture()));
                    writer.write(String.format("<td><a href='./deleteRole?id=%s'>" +
                            "<img src='./images/trash.png' width='20px'></a></td>", role.getId()));
                    writer.write(String.format("<td><a href='./updateRole?id=%s'>" +
                            "<img src='./images/edit.png' width='20px'></a></td>", role.getId()));
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

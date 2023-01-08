package cn.edu.usc.role;



import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/deleteRole")
public class DeleteRoleServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String id = request.getParameter("id");
        try {
            RoleRepo.getInstance().deleteRole(Long.valueOf(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        response.sendRedirect("./listRoles");
    }
}

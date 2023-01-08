package cn.edu.usc.role;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;


@WebServlet("/listRole")
public class ListRoleServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<cn.edu.usc.role.Role> roles = RoleRepo.getInstance().getAll();
            response.setContentType("application/json; charset=UTF-8");
            try (Writer writer = response.getWriter()) {
                this.writeJsonByJackson(response.getWriter(), roles);
            }
        } catch (SQLException e) {
            throw new IOException(e);
        }
    }

    private void writeJsonByJackson(Writer writer, List<Role> roles)  throws IOException  {
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(roles);
        writer.write(json);
    }

}

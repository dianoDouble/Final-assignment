package cn.edu.usc.role;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getRolesInCart")
public class GetBooksInCartServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        List<Long> cart = (List<Long>) session.getAttribute(AddToCartServlet.CART);
        List<cn.edu.usc.role.Role> roles = new ArrayList<>();
        if (cart != null) {
            try {
                roles = RoleRepo.getInstance().getByIds(cart);
            } catch (SQLException e) {
                throw new IOException(e.getMessage());
            }
        }

        response.setContentType("application/json; charset=UTF-8");
        try (Writer writer = response.getWriter()) {
            this.writeJsonByJackson(response.getWriter(), roles);
        }
    }

    private void writeJsonByJackson(Writer writer, List<Role> roles)  throws IOException  {
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(roles);
        writer.write(json);
    }
}

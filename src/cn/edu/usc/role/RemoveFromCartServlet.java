package cn.edu.usc.role;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/removeFromCart")
public class RemoveFromCartServlet extends HttpServlet {

    public final static String CART = "cart";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Long roleId = Long.valueOf(request.getParameter("roleId"));
        List<Long> cart = this.getCart(request);
        for (int i = 0 ; i < cart.size(); i++) {
            if (cart.get(i).equals(roleId)) {
                cart.remove(i);
            }
        }
        response.setContentType("application/json; charset=UTF-8");
        try (Writer writer = response.getWriter()) {
            writer.write("{\"code\": 200, \"message\": \"success\"}");
        }
    }

    private List<Long> getCart(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        List<Long> cart = (List<Long>) session.getAttribute(CART);
        if (cart == null) {
            cart = new ArrayList<Long>();
            session.setAttribute(CART, cart);
        }
        return cart;
    }
}

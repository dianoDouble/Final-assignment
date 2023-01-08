package cn.edu.usc.user;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

@WebServlet("/verifyCode")
public class ValidateCodeServlet extends HttpServlet {

    private final static String CODES = "abcdefghijklmnopqrstyvwxyz1234567890ABCDEFGHIJKLMNOPQRSTYVWXYZ";
    private final static int WIDTH = 100;
    private final static int HEIGHT = 40;
    private final static String IMAGE_TYPE = "png";
    private final static int CODE_SIZE = 6;
    public final static String LOGIN_VERIFY_CODE = "login_verify_code";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String code = this.randomCode(CODE_SIZE);

        HttpSession session  = request.getSession(true);
        session.setAttribute(LOGIN_VERIFY_CODE, code);

        BufferedImage image = this.drawImage(WIDTH, HEIGHT, code);
        try (OutputStream outputStream = response.getOutputStream()) {
            ImageIO.write(image, IMAGE_TYPE, outputStream);
        }
    }

    public BufferedImage drawImage(int width, int height, String code) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g2d = image.getGraphics();

        g2d.setColor(Color.LIGHT_GRAY);
        g2d.fillRect(0, 0, width, height);

        g2d.setColor(Color.GRAY);
        g2d.drawRect(0, 0, width-1, height-1);

        Font font = new Font("Arial", Font.BOLD|Font.ITALIC, 20);
        g2d.setFont(font);
        g2d.setColor(Color.RED);
        g2d.drawString(code, 20, 30);

        Random random = new Random();
        for (int i = 0; i < 60; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width / 4);
            int y2 = random.nextInt(width / 4);
            g2d.setColor(this.randomColor());
            g2d.drawLine(x1, y1, x1 + x2, y1 + y2);
        }

        return image;
    }

    private Color randomColor() {
        Random random = new Random();
        int red = 0, green = 0, blue = 0;
        red   = random.nextInt(255);
        green = random.nextInt(255);
        blue  = random.nextInt(255);
        return new Color(red, green, blue);
    }

    public String randomCode(int size) {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i=0; i<size; i++) {
            sb.append(CODES.charAt(random.nextInt(CODES.length())));
        }
        return sb.toString();
    }

}

package cn.edu.usc.role;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet("/saveRole")
public class SaveRoleServlet extends HttpServlet {

    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";

    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {


        cn.edu.usc.role.Role role ;
        try {
            role = this.getRoleFromRequest(request);
            System.out.println("执行成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String message = null;
        try {
//            String id = request.getParameter("id");
            System.out.println(role.getId());
//            if (role.getId() != 0) {
//                RoleRepo.getInstance().deleteRole(role.getId());
//            }
            RoleRepo.getInstance().saveRole(role);

            message = "提交信息保存成功";
            System.out.println(message);
        } catch (SQLException e) {
            message = "提交信息保存失败";
            System.out.println(message);
        }

        response.setContentType("text/html;charset=utf-8");
        try (Writer writer = response.getWriter()) {
            String html = "<head><title>%s</title></head>" +
                    "<center><h1>%s</h1><br><br>" +
                    "<a href='./listRoles'>返回角色列表</a><br><br>" +
                    "<a href='./index.html'>返回首页</a><br><br>" +
                    "<a href='./admin.html'>返回管理员界面</a><br><br>" +
                    "</center>";
            writer.write(String.format(html, message, message));
        }
    }

    private cn.edu.usc.role.Role getRoleFromRequest(HttpServletRequest request) throws Exception {
        cn.edu.usc.role.Role role = new Role();

        if (!ServletFileUpload.isMultipartContent(request)) {
            return null;
        }

        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8");

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath(".") + File.separator + UPLOAD_DIRECTORY;

        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // 创建路径上所有文件夹
        }

        List<FileItem> formItems = upload.parseRequest(request);

        if (formItems != null && formItems.size() > 0) {
            // 迭代表单数据
            for (FileItem item : formItems) {
                // 处理不在表单中的字段
                if (!item.isFormField()) {
                    String fileName = new File(item.getName()).getName();
                    fileName = UUID.randomUUID().toString() + fileName.substring(fileName.indexOf("."));
                    String filePath = uploadPath + File.separator + fileName;
                    File storeFile = new File(filePath);
                    // 在控制台输出文件的上传路径
                    System.out.println(filePath);
                    // 保存文件到硬盘
                    item.write(storeFile);
                    role.setPicture(fileName);
                    request.setAttribute("message","文件上传成功!");
                } else {
                    String encoding = "UTF-8";
                    if (item.getFieldName().equals("id")) {
                        String id = item.getString(encoding);
                        if (id != null) {
                            role.setId(Long.valueOf(id));
                        }
                    } else if (item.getFieldName().equals("cname")) {
                        role.setCname(item.getString(encoding));
                    } else if (item.getFieldName().equals("ename")) {
                        role.setEname(item.getString(encoding));
                    }
                }
            }
        }

        return role;
    }
}

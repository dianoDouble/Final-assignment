package cn.edu.usc.user;

import cn.edu.usc.db.DBEngine;
import cn.edu.usc.db.RecordVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminRepo {

    private static AdminRepo instance = new AdminRepo();

    private AdminRepo() {
    }

    public static AdminRepo getInstance() {
        return instance;
    }

    public void save(cn.edu.usc.user.Admin admin) throws SQLException {
        String template =
                "INSERT INTO admin(id, adminName, userName, password) " +
                        "VALUES (\"%s\", \"%s\", \"%s\", MD5(\"%s\"))";
        String sql = String.format(template, admin.getId(), admin.getAdminName(), admin.getUserName(), admin.getPassword());
        DBEngine.getInstance().execute(sql);
    }

    public cn.edu.usc.user.Admin auth(String user, String password) throws SQLException {
        String template = "SELECT * FROM admin WHERE userName = \"%s\" AND password = MD5(\"%s\")";
        List<cn.edu.usc.user.Admin> admins = DBEngine.getInstance().query(
            String.format(template, user, password), new RecordVisitor<cn.edu.usc.user.Admin>() {
            @Override
            public cn.edu.usc.user.Admin visit(ResultSet rs) throws SQLException {
                return AdminRepo.getAdminByResultSet(rs);
            }
        });
        return admins.size() == 0 ? null : admins.get(0);
    }



    private static cn.edu.usc.user.Admin getAdminByResultSet(ResultSet rs) throws SQLException {
        cn.edu.usc.user.Admin admin = new Admin();
        admin.setId(rs.getString("id"));
        admin.setAdminName(rs.getString("adminName"));
        admin.setUserName(rs.getString("userName"));
        admin.setPassword(rs.getString("password"));
        return admin;
    }
}

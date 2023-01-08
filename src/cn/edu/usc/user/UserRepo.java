package cn.edu.usc.user;

import cn.edu.usc.db.DBEngine;
import cn.edu.usc.db.RecordVisitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserRepo {

    private static UserRepo instance = new UserRepo();

    private UserRepo() {
    }

    public static UserRepo getInstance() {
        return instance;
    }

    public void save(cn.edu.usc.user.User user) throws SQLException {
        String template =
                "INSERT INTO user(id, name, userName, password) VALUES (\"%s\", \"%s\", \"%s\", MD5(\"%s\"))";
        String sql = String.format(template, user.getId(), user.getName(), user.getUserName(), user.getPassword());
        DBEngine.getInstance().execute(sql);
    }

    public cn.edu.usc.user.User auth(String user, String password) throws SQLException {
        String template = "SELECT * FROM user WHERE userName = \"%s\" AND password = MD5(\"%s\")";
        List<cn.edu.usc.user.User> users = DBEngine.getInstance().query(
            String.format(template, user, password), new RecordVisitor<cn.edu.usc.user.User>() {
            @Override
            public cn.edu.usc.user.User visit(ResultSet rs) throws SQLException {
                return UserRepo.getUserByResultSet(rs);
            }
        });
        return users.size() == 0 ? null : users.get(0);
    }

    public void delete(cn.edu.usc.user.User user) throws SQLException {
        String template = "DELETE FROM user WHERE id = \"%s\"";
        DBEngine.getInstance().execute(String.format(template, user.getId()));
    }

    public List<cn.edu.usc.user.User> getAll() throws SQLException {
        String sql = "SELECT id, name, userName, password FROM user";
        return DBEngine.getInstance().query(sql, new RecordVisitor<cn.edu.usc.user.User>() {
            @Override
            public cn.edu.usc.user.User visit(ResultSet rs) throws SQLException {
                return UserRepo.getUserByResultSet(rs);
            }
        });
    }

    private static cn.edu.usc.user.User getUserByResultSet(ResultSet rs) throws SQLException {
        cn.edu.usc.user.User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("password"));
        return user;
    }
}

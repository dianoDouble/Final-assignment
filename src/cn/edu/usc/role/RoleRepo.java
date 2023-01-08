package cn.edu.usc.role;

import cn.edu.usc.db.DBEngine;
import cn.edu.usc.db.RecordVisitor;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RoleRepo {
    private static RoleRepo instance = new RoleRepo();

    private RoleRepo() {}

    public static RoleRepo getInstance() {
        return instance;
    }

    public List<cn.edu.usc.role.Role> getAll() throws SQLException {
        String sql = "SELECT id, cname, ename, picture FROM role`";
        return DBEngine.getInstance().query(sql, new RecordVisitor<cn.edu.usc.role.Role>() {
            @Override
            public cn.edu.usc.role.Role visit(ResultSet rs) throws SQLException {
                return RoleRepo.getRoleFromResultSet(rs);
            }
        });
    }



    public void saveRole(cn.edu.usc.role.Role role) throws SQLException {
        if (role.getId() > 0) {
//            System.out.println("准备进行更新操作");
            this.updateRole(role);
        } else {
//            System.out.println("准备进行插入操作");
            this.insertNewRole(role);
        }

    }

    private void insertNewRole(cn.edu.usc.role.Role role) throws SQLException {
        String template = "INSERT INTO role(cname, ename, picture)" +
                " VALUE (\"%s\", \"%s\", \"%s\")";
        String sql = String.format(template, role.getCname(), role.getEname(), role.getPicture());
//        System.out.println(sql);
        DBEngine.getInstance().execute(sql);

    }

    private void updateRole(cn.edu.usc.role.Role role) throws SQLException {
        String template = "UPDATE role SET cname = \"%s\", ename = \"%s\", picture = \"%s\" WHERE id = %s";
        String sql = String.format(template, role.getCname(), role.getEname(), role.getPicture(), role.getId());
        System.out.println(sql);
        DBEngine.getInstance().execute(sql);
    }

    public void deleteRole(Long id) throws SQLException {
        String template = "DELETE FROM role WHERE id = %s";
        String sql = String.format(template, id);
        DBEngine.getInstance().execute(sql);
    }


    public cn.edu.usc.role.Role getById(String id) throws SQLException {
        String sql = String.format("SELECT * FROM role WHERE id = %s", id);
        List<cn.edu.usc.role.Role> roles = DBEngine.getInstance().query(sql, new RecordVisitor<cn.edu.usc.role.Role>() {

            @Override
            public cn.edu.usc.role.Role visit(ResultSet rs) throws SQLException {
                return RoleRepo.getRoleFromResultSet(rs);
            }
        });
        return roles.size() == 0 ? null : roles.get(0);
    }

    public List<cn.edu.usc.role.Role> getByIds(List<Long> ids) throws SQLException {
        String sql = "SELECT * FROM role WHERE id IN (%s)";
        String strId = "";
        for (int i=0; i<ids.size(); i++) {
            strId += ((i > 0) ? "," : "") + ids.get(i);
        }

//        System.out.println(String.format(sql, strId));
        List<cn.edu.usc.role.Role> roles = DBEngine.getInstance().query(
                String.format(sql, strId), new RecordVisitor<cn.edu.usc.role.Role>() {
                    @Override
                    public cn.edu.usc.role.Role visit(ResultSet rs) throws SQLException {
                        return RoleRepo.getRoleFromResultSet(rs);
                    }
                });
        return roles;
    }

    private static cn.edu.usc.role.Role getRoleFromResultSet(ResultSet rs) throws SQLException {
        cn.edu.usc.role.Role role = new Role();
        role.setId(rs.getLong("id"));
        role.setCname(rs.getString("cname"));
        role.setEname(rs.getString("ename"));
        role.setPicture(rs.getString("picture"));
        return role;
    }
}

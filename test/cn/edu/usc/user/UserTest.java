package cn.edu.usc.user;

import cn.edu.usc.role.Role;

import java.sql.SQLException;

public class UserTest {

    public static void main(String[] args) throws SQLException {


//        User guest = new User();
//        guest.setId(UUID.randomUUID().toString());
//        guest.setName("lxy");
//        guest.setUserName("1");
//        guest.setPassword("1");
//        UserRepo.getInstance().save(guest);

//        Admin admin = new Admin();
//        admin.setId(UUID.randomUUID().toString());
//        admin.setAdminName("lxy");
//        admin.setUserName("8");
//        admin.setPassword("8");
//        AdminRepo.getInstance().save(admin);

        cn.edu.usc.role.Role role = new Role();
        System.out.println(role.getId());


//        List<User> users = UserRepo.getInstance().getAll();
//        for (User user : users) {
//            System.out.println("删除" + user.getName());
//            UserRepo.getInstance().delete(user);
//        }
    }
}

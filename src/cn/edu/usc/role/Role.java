package cn.edu.usc.role;

public class Role {
    private long id;
    private String cname;

    private String ename;

    private String picture;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEname() {
        return ename;
    }

    public void setEname(String ename) {
        this.ename = ename;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Role() {}

    public Role(long id, String cname, String ename, String picture) {
        this.id = id;
        this.cname = cname;
        this.ename = ename;
        this.picture = picture;
    }
}

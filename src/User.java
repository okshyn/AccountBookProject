public class User{
    private String id;
    private String pw;
    private boolean admin;

    public User(String id, String pw, boolean admin){
        this.id = id;
        this.pw = pw;
        this.admin = admin;
    }

    public String getId(){
        return id;
    }

    public String getPw(){
        return pw;
    }

    public boolean isAdmin(){
        return admin;
    }
}

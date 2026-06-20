import java.util.ArrayList;
import java.io.*;

public class UserManager{
    private ArrayList<User> users = new ArrayList<>();

    public UserManager(){
        loadUsers();

        if(users.isEmpty()){
            User admin = new User("admin", "admin1234", true);
            users.add(admin);
            saveUsers();
        }
    }

    public void register(String id, String pw, boolean admin){
        if(id.length() < 4){
            System.out.println("ID는 4글자 이상으로 입력해주시기 바랍니다.");
            return;
        }

        if(pw.length() < 4){
            System.out.println("비밀번호는 4글자 이상으로 입력해주시기 바랍니다.");
            return;
        }

        for(User user : users){
            if(user.getId().equals(id)){
                System.out.println("이미 존재하는 ID입니다.");
                return;
            }
        }

        User user = new User(id, pw, admin);
        users.add(user);
        saveUsers();
        System.out.println("회원가입이 완료되었습니다.");
    }

    public User login(String id, String pw){
        for(User user : users){
            if(user.getId().equals(id) && user.getPw().equals(pw)){
                return user;
            }
        }
        return null;
    }

    public void saveUsers(){
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"));
            for(User user : users){
                bw.write(user.getId() + "," + user.getPw() + "," + user.isAdmin());
                bw.newLine();
            }
            bw.close();
        }
        catch(IOException e){
            System.out.println("회원 정보 저장 오류");
        }
    }

    public void loadUsers(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("users.txt"));
            String line;
            while((line = br.readLine()) != null){
                String[] data = line.split(",");

                String id = data[0];
                String pw = data[1];
                boolean admin = Boolean.parseBoolean(data[2]);

                User user = new User(id, pw, admin);
                users.add(user);
            }
            br.close();
        }
        catch(IOException e){
            System.out.println("저장된 회원 정보가 없습니다.");
        }
    }

    public void showUsers(){
        System.out.println("\n===== 회원 목록 =====");

        for(User user : users){
            if(user.isAdmin()){
                System.out.println("ID : " + user.getId() + " [관리자 계정]");
            }
            else{
                System.out.println("ID : " + user.getId());
            }
        }
    }

    public boolean deleteUser(String id) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);

            if (user.getId().equals(id)) {
                if (user.getId().equals("admin")) {
                    return false;
                }

                users.remove(i);
                saveUsers();
                return true;
            }
        }

        return false;
    }
    
    public ArrayList<User> getUserList() {
        return users;
    }
}

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private UserManager userManager;

    private JTextField idField;
    private JPasswordField pwField;

    public LoginFrame(){
        userManager = new UserManager();

        setTitle("가계부 시스템");
        setSize(420, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(null);
        add(mainPanel);

        JLabel titleLabel = new JLabel("My Account Book");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(95, 35, 250, 35);
        mainPanel.add(titleLabel);

        JLabel subLabel = new JLabel("로그인 후 가계부를 관리하세요");
        subLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        subLabel.setForeground(new Color(120, 120, 120));
        subLabel.setBounds(115, 70, 220, 25);
        mainPanel.add(subLabel);

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.BOLD, 13));
        idLabel.setBounds(70, 120, 80, 25);
        mainPanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(70, 145, 280, 35);
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(idField);

        JLabel pwLabel = new JLabel("Password");
        pwLabel.setFont(new Font("Arial", Font.BOLD, 13));
        pwLabel.setBounds(70, 190, 100, 25);
        mainPanel.add(pwLabel);

        pwField = new JPasswordField();
        pwField.setBounds(70, 215, 280, 35);
        pwField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(pwField);

        JButton loginButton = new JButton("로그인");
        loginButton.setBounds(70, 275, 130, 38);
        loginButton.setBackground(new Color(80, 120, 200));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        mainPanel.add(loginButton);

        JButton registerButton = new JButton("회원가입");
        registerButton.setBounds(220, 275, 130, 38);
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(80, 120, 200));
        registerButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        mainPanel.add(registerButton);

        loginButton.addActionListener(e -> login());

        registerButton.addActionListener(e ->{
            new RegisterFrame(userManager);
            dispose();
        });

        setVisible(true);
    }

    private void login(){
        String id = idField.getText();
        String pw = new String(pwField.getPassword());

        User loginUser = userManager.login(id, pw);

        if (loginUser == null){
            JOptionPane.showMessageDialog(this, "ID 또는 비밀번호가 틀렸습니다.");
            return;
        }

        JOptionPane.showMessageDialog(this, "로그인 성공!");

        if(loginUser.isAdmin()){
            new AdminFrame(userManager);
            dispose();
        }
        else{
            AccountManager accountManager = new AccountManager(loginUser.getId());
            new UserMainFrame(loginUser, accountManager);
            dispose();
        }
    }

    public static void main(String[] args) {
        new LoginFrame();
    }
}
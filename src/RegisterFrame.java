import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private UserManager userManager;

    private JTextField idField;
    private JPasswordField pwField;

    public RegisterFrame(UserManager userManager){
        this.userManager = userManager;

        setTitle("회원가입");
        setSize(420, 360);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 420, 330);
        add(mainPanel);

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(105, 35, 250, 35);
        mainPanel.add(titleLabel);

        JLabel subLabel = new JLabel("새 계정을 생성하세요");
        subLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 13));
        subLabel.setForeground(new Color(120, 120, 120));
        subLabel.setBounds(145, 70, 180, 25);
        mainPanel.add(subLabel);

        JLabel idLabel = new JLabel("ID");
        idLabel.setFont(new Font("Arial", Font.BOLD, 13));
        idLabel.setBounds(70, 115, 80, 25);
        mainPanel.add(idLabel);

        idField = new JTextField();
        idField.setBounds(70, 140, 280, 35);
        idField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(idField);

        JLabel pwLabel = new JLabel("Password");
        pwLabel.setFont(new Font("Arial", Font.BOLD, 13));
        pwLabel.setBounds(70, 185, 100, 25);
        mainPanel.add(pwLabel);

        pwField = new JPasswordField();
        pwField.setBounds(70, 210, 280, 35);
        pwField.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(pwField);

        JButton registerButton = new JButton("회원가입");
        registerButton.setBounds(70, 260, 130, 38);
        registerButton.setBackground(new Color(80, 120, 200));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        registerButton.setFocusPainted(false);
        mainPanel.add(registerButton);

        JButton cancelButton = new JButton("취소");
        cancelButton.setBounds(220, 260, 130, 38);
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(new Color(80, 120, 200));
        cancelButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        cancelButton.setFocusPainted(false);
        mainPanel.add(cancelButton);

        registerButton.addActionListener(e -> register());
        cancelButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void register() {
        String id = idField.getText();
        String pw = new String(pwField.getPassword());

        userManager.register(id, pw, false);

        JOptionPane.showMessageDialog(this, "회원가입 처리가 완료되었습니다.");
        new LoginFrame();
        dispose();
    }
}
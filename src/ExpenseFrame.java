import javax.swing.*;
import java.awt.*;

public class ExpenseFrame extends JFrame {
    private UserManager userManager;
    private JFrame parentFrame;

    public ExpenseFrame(UserManager userManager, JFrame parentFrame) {
        this.userManager = userManager;
        this.parentFrame = parentFrame;

        setTitle("가족 지출 현황");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel("가족 지출 현황");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(210, 30, 250, 40);
        mainPanel.add(titleLabel);

        JLabel infoLabel = new JLabel("조회할 구성원을 선택하세요.");
        infoLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        infoLabel.setBounds(205, 75, 220, 25);
        mainPanel.add(infoLabel);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(0, 1, 10, 10));
        userPanel.setBackground(new Color(245, 247, 250));

        for (User user : userManager.getUserList()) {
            if (!user.isAdmin()) {
                JButton userButton = createBlueButton(user.getId());

                userButton.addActionListener(e -> {
                    new ExpenseDetailFrame(user.getId(), this);
                    setVisible(false);
                });

                userPanel.add(userButton);
            }
        }

        JScrollPane scrollPane = new JScrollPane(userPanel);
        scrollPane.setBounds(150, 120, 280, 220);
        mainPanel.add(scrollPane);

        JButton backButton = createGrayButton("뒤로가기");
        backButton.setBounds(235, 380, 120, 38);
        mainPanel.add(backButton);

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JButton createBlueButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(80, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    private JButton createGrayButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(120, 120, 120));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }
}
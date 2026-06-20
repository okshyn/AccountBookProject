import javax.swing.*;
import java.awt.*;

public class BudgetManagementFrame extends JFrame {

    private UserManager userManager;
    private JFrame parentFrame;

    public BudgetManagementFrame(UserManager userManager, JFrame parentFrame) {
        this.userManager = userManager;
        this.parentFrame = parentFrame;

        setTitle("구성원별 예산 설정");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel("구성원별 예산 설정");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 26));
        titleLabel.setBounds(175, 35, 300, 40);
        mainPanel.add(titleLabel);

        JLabel yearLabel = new JLabel("연도");
        yearLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        yearLabel.setBounds(130, 110, 80, 30);
        mainPanel.add(yearLabel);

        JTextField yearField = new JTextField("2026");
        yearField.setBounds(210, 110, 230, 32);
        mainPanel.add(yearField);

        JLabel monthLabel = new JLabel("월");
        monthLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        monthLabel.setBounds(130, 155, 80, 30);
        mainPanel.add(monthLabel);

        JTextField monthField = new JTextField("6");
        monthField.setBounds(210, 155, 230, 32);
        mainPanel.add(monthField);

        JLabel userListLabel = new JLabel("가족 회원 목록");
        userListLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        userListLabel.setBounds(225, 220, 200, 30);
        mainPanel.add(userListLabel);

        JPanel userPanel = new JPanel();
        userPanel.setLayout(new GridLayout(0, 1, 10, 10));
        userPanel.setBackground(new Color(245, 247, 250));

        for (User user : userManager.getUserList()) {
            if (!user.isAdmin()) {
                JButton userButton = createMenuButton(user.getId());

                userButton.addActionListener(e -> {
                    String yearText = yearField.getText();
                    String monthText = monthField.getText();

                    if (!isValidYearMonth(yearText, monthText)) {
                        JOptionPane.showMessageDialog(this, "연도와 월을 올바르게 입력하세요.");
                        return;
                    }

                    int year = Integer.parseInt(yearText);
                    int month = Integer.parseInt(monthText);

                    new BudgetDetailFrame(userManager, user.getId(), year, month, this);
                    setVisible(false);
                });

                userPanel.add(userButton);
            }
        }

        JScrollPane scrollPane = new JScrollPane(userPanel);
        scrollPane.setBounds(130, 265, 330, 150);
        mainPanel.add(scrollPane);

        JButton backButton = createBackButton("뒤로가기");
        backButton.setBounds(230, 445, 130, 38);
        mainPanel.add(backButton);

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(80, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    private JButton createBackButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(120, 120, 120));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }

    private boolean isValidYearMonth(String yearText, String monthText) {
        try {
            int year = Integer.parseInt(yearText);
            int month = Integer.parseInt(monthText);

            return year >= 2000 && month >= 1 && month <= 12;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
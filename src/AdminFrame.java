import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    private UserManager userManager;

    public AdminFrame(UserManager userManager) {
        this.userManager = userManager;

        setTitle("관리자 페이지");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel("관리자 페이지");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(155, 45, 250, 40);
        mainPanel.add(titleLabel);

        JButton familyManageButton = createMenuButton("회원 관리");
        familyManageButton.setBounds(90, 125, 300, 45);
        mainPanel.add(familyManageButton);

        JButton budgetButton = createMenuButton("회원별 예산 설정");
        budgetButton.setBounds(90, 195, 300, 45);
        mainPanel.add(budgetButton);

        JButton expenseStatusButton = createMenuButton("회원별 지출 현황");
        expenseStatusButton.setBounds(90, 265, 300, 45);
        mainPanel.add(expenseStatusButton);

        JButton yearlySettlementButton = createMenuButton("연말 정산");
        yearlySettlementButton.setBounds(90, 335, 300, 45);
        mainPanel.add(yearlySettlementButton);

        JButton logoutButton = createLogoutButton("로그아웃");
        logoutButton.setBounds(170, 440, 140, 38);
        mainPanel.add(logoutButton);

        familyManageButton.addActionListener(e -> {
            new UserListFrame(userManager, this);
            setVisible(false);
        });

        budgetButton.addActionListener(e -> {
            new BudgetManagementFrame(userManager, this);
        });

        expenseStatusButton.addActionListener(e -> {
            new ExpenseFrame(userManager, this);
            setVisible(false);
        });

        yearlySettlementButton.addActionListener(e -> {
            new YearEndSettlementFrame(userManager, this);
            setVisible(false);
        });

        logoutButton.addActionListener(e -> {
            new LoginFrame();
            dispose();
        });

        setVisible(true);
    }

    private JButton createMenuButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(80, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        button.setFocusPainted(false);
        return button;
    }

    private JButton createLogoutButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(new Color(120, 120, 120));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        button.setFocusPainted(false);
        return button;
    }
}
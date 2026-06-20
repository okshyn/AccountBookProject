import javax.swing.*;
import java.awt.*;

public class UserMainFrame extends JFrame {

    private User loginUser;
    private AccountManager accountManager;

    public UserMainFrame(User loginUser, AccountManager accountManager) {
        this.loginUser = loginUser;
        this.accountManager = accountManager;

        setTitle("가계부 메인");
        setSize(500, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(null);
        add(mainPanel);

        JLabel titleLabel = new JLabel(loginUser.getId() + "님의 가계부");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(120, 35, 300, 40);
        mainPanel.add(titleLabel);

        JLabel subLabel = new JLabel("원하는 메뉴를 선택하세요");
        subLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        subLabel.setForeground(new Color(120, 120, 120));
        subLabel.setBounds(165, 75, 220, 30);
        mainPanel.add(subLabel);

        JButton addButton = createMenuButton("수입/지출 입력");
        addButton.setBounds(90, 130, 310, 50);
        mainPanel.add(addButton);

        JButton showButton = createMenuButton("내역 조회");
        showButton.setBounds(90, 200, 310, 50);
        mainPanel.add(showButton);

        JButton budgetButton = createMenuButton("내 예산 확인");
        budgetButton.setBounds(90, 270, 310, 50);
        mainPanel.add(budgetButton);

        JButton logoutButton = new JButton("로그아웃");
        logoutButton.setBounds(170, 385, 150, 40);
        logoutButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        logoutButton.setBackground(Color.WHITE);
        logoutButton.setForeground(new Color(80, 120, 200));
        logoutButton.setFocusPainted(false);
        mainPanel.add(logoutButton);

        addButton.addActionListener(e -> {
            new AddAccountFrame(loginUser, accountManager, this);
            setVisible(false);
        });

        showButton.addActionListener(e -> {
            new AllAccountFrame(loginUser, accountManager, this);
            setVisible(false);
        });

        budgetButton.addActionListener(e -> {
            new MyBudgetFrame(loginUser, this);
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
        button.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        button.setBackground(new Color(80, 120, 200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}
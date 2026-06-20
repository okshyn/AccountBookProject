import javax.swing.*;
import java.awt.*;

public class AddAccountFrame extends JFrame {

    private User loginUser;
    private AccountManager accountManager;
    private JFrame parentFrame;

    public AddAccountFrame(User loginUser, AccountManager accountManager, JFrame parentFrame){

        this.loginUser = loginUser;
        this.accountManager = accountManager;
        this.parentFrame = parentFrame;

        setTitle("수입/지출 입력");
        setSize(500, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245,247,250));
        mainPanel.setLayout(null);

        add(mainPanel);

        JLabel titleLabel = new JLabel("수입 / 지출 입력");

        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(120,40,300,40);
        mainPanel.add(titleLabel);

        JLabel subLabel = new JLabel("카테고리를 선택하세요");

        subLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));

        subLabel.setForeground(new Color(120,120,120));
        subLabel.setBounds(150,80,250,30);
        mainPanel.add(subLabel);

        JButton foodButton = createCategoryButton("식비");
        foodButton.setBounds(90,150,140,60);
        mainPanel.add(foodButton);

        JButton trafficButton = createCategoryButton("교통");
        trafficButton.setBounds(250,150,140,60);
        mainPanel.add(trafficButton);

        JButton shoppingButton = createCategoryButton("쇼핑");
        shoppingButton.setBounds(90,240,140,60);
        mainPanel.add(shoppingButton);

        JButton salaryButton = createCategoryButton("월급");
        salaryButton.setBounds(250,240,140,60);
        mainPanel.add(salaryButton);

        JButton allowanceButton = createCategoryButton("용돈");
        allowanceButton.setBounds(90,330,140,60);
        mainPanel.add(allowanceButton);

        JButton etcButton = createCategoryButton("기타");
        etcButton.setBounds(250,330,140,60);
        mainPanel.add(etcButton);

        JButton backButton = new JButton("뒤로가기");
        backButton.setBounds(150,500,180,45);
        backButton.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(80,120,200));
        backButton.setFocusPainted(false);
        mainPanel.add(backButton);

        foodButton.addActionListener(e -> {
            new InputFrame(loginUser, accountManager, "식비");
        });

        trafficButton.addActionListener(e -> {
            new InputFrame(loginUser, accountManager, "교통");
        });

        shoppingButton.addActionListener(e -> {
            new InputFrame(loginUser, accountManager, "쇼핑");
        });

        salaryButton.addActionListener(e -> {
            new InputFrame(loginUser, accountManager, "월급");
        });

        allowanceButton.addActionListener(e -> {
            new InputFrame(loginUser, accountManager, "용돈");
        });

        etcButton.addActionListener(e -> {
            new InputFrame(loginUser, accountManager, "기타");
        });

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
        setVisible(true);
    }

    private JButton createCategoryButton(String text){
        JButton button = new JButton(text);
        button.setFont(new Font("Malgun Gothic", Font.BOLD,18));
        button.setBackground(new Color(80,120,200));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        return button;
    }
}
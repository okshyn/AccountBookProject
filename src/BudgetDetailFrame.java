import javax.swing.*;
import java.awt.*;

public class BudgetDetailFrame extends JFrame {

    private String userId;
    private int year;
    private int month;
    private JFrame parentFrame;

    private JTextField foodField;
    private JTextField trafficField;
    private JTextField shoppingField;
    private JTextField etcField;

    public BudgetDetailFrame(UserManager userManager, String userId, int year, int month, JFrame parentFrame) {
        this.userId = userId;
        this.year = year;
        this.month = month;
        this.parentFrame = parentFrame;

        setTitle(userId + " 예산 설정");
        setSize(600, 520);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel(userId + " 예산 설정");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 26));
        titleLabel.setBounds(200, 35, 300, 40);
        mainPanel.add(titleLabel);

        JLabel dateLabel = new JLabel(year + "년 " + month + "월");
        dateLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 17));
        dateLabel.setBounds(250, 80, 150, 30);
        mainPanel.add(dateLabel);

        foodField = addBudgetRow(mainPanel, "식비", 150);
        trafficField = addBudgetRow(mainPanel, "교통", 205);
        shoppingField = addBudgetRow(mainPanel, "쇼핑", 260);
        etcField = addBudgetRow(mainPanel, "기타", 315);

        JButton saveButton = createBlueButton("저장");
        saveButton.setBounds(160, 400, 120, 38);
        mainPanel.add(saveButton);

        JButton backButton = createGrayButton("뒤로가기");
        backButton.setBounds(310, 400, 120, 38);
        mainPanel.add(backButton);

        saveButton.addActionListener(e -> saveBudget());

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private JTextField addBudgetRow(JPanel panel, String category, int y) {
        JLabel label = new JLabel(category);
        label.setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        label.setBounds(150, y, 80, 30);
        panel.add(label);

        JTextField field = new JTextField();
        field.setBounds(240, y, 180, 32);
        panel.add(field);

        JLabel wonLabel = new JLabel("원");
        wonLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        wonLabel.setBounds(430, y, 40, 30);
        panel.add(wonLabel);

        return field;
    }

    private void saveBudget() {
        if (!isNumber(foodField.getText()) ||
                !isNumber(trafficField.getText()) ||
                !isNumber(shoppingField.getText()) ||
                !isNumber(etcField.getText())) {

            JOptionPane.showMessageDialog(this, "예산 금액은 숫자로만 입력하세요.");
            return;
        }

        BudgetManager budgetManager = new BudgetManager();

        budgetManager.saveBudget(year, month, userId, "식비", Integer.parseInt(foodField.getText()));
        budgetManager.saveBudget(year, month, userId, "교통", Integer.parseInt(trafficField.getText()));
        budgetManager.saveBudget(year, month, userId, "쇼핑", Integer.parseInt(shoppingField.getText()));
        budgetManager.saveBudget(year, month, userId, "기타", Integer.parseInt(etcField.getText()));

        JOptionPane.showMessageDialog(this, "예산이 저장되었습니다.");
    }

    private boolean isNumber(String text) {
        if (text == null || text.equals("")) {
            return false;
        }

        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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
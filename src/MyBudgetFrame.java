import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class MyBudgetFrame extends JFrame {

    private User loginUser;
    private JFrame parentFrame;

    private JComboBox<Integer> yearCombo;
    private JComboBox<Integer> monthCombo;

    private JTable table;
    private DefaultTableModel model;

    public MyBudgetFrame(User loginUser, JFrame parentFrame) {

        this.loginUser = loginUser;
        this.parentFrame = parentFrame;

        setTitle("내 예산 확인");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel("내 예산 확인");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 26));
        titleLabel.setBounds(210, 30, 250, 40);
        mainPanel.add(titleLabel);

        JLabel yearLabel = new JLabel("연도");
        yearLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        yearLabel.setBounds(120, 100, 50, 30);
        mainPanel.add(yearLabel);

        yearCombo = new JComboBox<>();

        BudgetManager budgetManager = new BudgetManager();

        ArrayList<String> yearList = budgetManager.getUserBudgetYears(loginUser.getId());

        for(String year : yearList) {
            yearCombo.addItem(Integer.parseInt(year));
        }

        yearCombo.setBounds(170, 100, 100, 32);
        mainPanel.add(yearCombo);

        JLabel monthLabel = new JLabel("월");
        monthLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        monthLabel.setBounds(320, 100, 30, 30);
        mainPanel.add(monthLabel);

        monthCombo = new JComboBox<>();

        for(int i = 1; i <= 12; i++) {
            monthCombo.addItem(i);
        }

        monthCombo.setBounds(360, 100, 80, 32);
        mainPanel.add(monthCombo);

        JButton searchButton = createBlueButton("조회");
        searchButton.setBounds(460, 98, 80, 36);
        mainPanel.add(searchButton);

        model = new DefaultTableModel();
        model.addColumn("카테고리");
        model.addColumn("예산");

        table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(90, 170, 400, 180);
        mainPanel.add(scrollPane);

        JButton backButton = createGrayButton("뒤로가기");
        backButton.setBounds(220, 390, 140, 40);
        mainPanel.add(backButton);

        searchButton.addActionListener(e -> loadBudget());

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadBudget() {

        if(yearCombo.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "설정된 예산이 없습니다.");
            return;
        }

        int year = (int) yearCombo.getSelectedItem();
        int month = (int) monthCombo.getSelectedItem();

        BudgetManager budgetManager = new BudgetManager();

        model.setRowCount(0);

        String[] categories = {"식비", "교통", "쇼핑", "기타"};

        for(String category : categories) {

            int budget = budgetManager.getMonthlyBudget(
                    year,
                    month,
                    loginUser.getId(),
                    category
            );

            model.addRow(new Object[]{
                    category,
                    budget + "원"
            });
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
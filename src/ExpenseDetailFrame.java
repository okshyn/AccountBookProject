import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ExpenseDetailFrame extends JFrame {
    private String userId;
    private JFrame parentFrame;
    private AccountManager accountManager;

    private JComboBox<Integer> yearCombo;
    private JComboBox<Integer> monthCombo;

    private JTable table;
    private DefaultTableModel model;
    private JLabel totalLabel;

    public ExpenseDetailFrame(String userId, JFrame parentFrame) {
        this.userId = userId;
        this.parentFrame = parentFrame;
        this.accountManager = new AccountManager(userId);

        setTitle(userId + " 지출 현황");
        setSize(650, 570);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel(userId + " 지출 현황");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 26));
        titleLabel.setBounds(225, 30, 300, 40);
        mainPanel.add(titleLabel);

        JLabel yearLabel = new JLabel("연도");
        yearLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        yearLabel.setBounds(100, 95, 50, 30);
        mainPanel.add(yearLabel);

        yearCombo = new JComboBox<>();
        ArrayList<String> yearList = new ArrayList<>();

        for(Account item : accountManager.getItems()){
            String date = item.getDate();

            if(date.length() >= 4){
                String year = date.substring(0, 4);

                if(!yearList.contains(year)){
                    yearList.add(year);
                    yearCombo.addItem(Integer.parseInt(year));
                }
            }
        }

        yearCombo.setBounds(150, 95, 100, 32);
        mainPanel.add(yearCombo);

        JLabel monthLabel = new JLabel("월");
        monthLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        monthLabel.setBounds(285, 95, 40, 30);
        mainPanel.add(monthLabel);

        monthCombo = new JComboBox<>();
        for (int m = 1; m <= 12; m++) {
            monthCombo.addItem(m);
        }
        monthCombo.setSelectedItem(5);
        monthCombo.setBounds(320, 95, 80, 32);
        mainPanel.add(monthCombo);

        JButton checkButton = createBlueButton("확인");
        checkButton.setBounds(430, 93, 90, 36);
        mainPanel.add(checkButton);

        model = new DefaultTableModel();
        model.addColumn("카테고리");
        model.addColumn("지출 총액");

        table = new JTable(model);
        table.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(230, 235, 242));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(85, 155, 480, 250);
        mainPanel.add(scrollPane);

        totalLabel = new JLabel("총 지출액 : 0원", SwingConstants.CENTER);
        totalLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        totalLabel.setBounds(160, 420, 330, 35);
        mainPanel.add(totalLabel);

        JButton backButton = createGrayButton("뒤로가기");
        backButton.setBounds(260, 475, 130, 38);
        mainPanel.add(backButton);

        checkButton.addActionListener(e -> loadExpense());

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadExpense() {
        int year = (int) yearCombo.getSelectedItem();
        int month = (int) monthCombo.getSelectedItem();

        model.setRowCount(0);

        String[] categories = {"식비", "교통", "쇼핑", "기타"};

        for (String category : categories) {
            int amount = accountManager.getCategoryExpense(year, month, category);

            model.addRow(new Object[]{
                    category,
                    amount + "원"
            });
        }

        int total = accountManager.getTotalExpense(year, month);
        totalLabel.setText("총 지출액 : " + total + "원");
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
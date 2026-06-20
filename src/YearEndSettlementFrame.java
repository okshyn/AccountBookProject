import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class YearEndSettlementFrame extends JFrame {

    private UserManager userManager;
    private JFrame parentFrame;

    private JTextField yearField;
    private JTable summaryTable;
    private JTable budgetTable;
    private DefaultTableModel summaryModel;
    private DefaultTableModel budgetModel;

    private String[] expenseCategories = {"식비", "교통", "쇼핑", "기타"};
    private String[] incomeCategories = {"월급", "용돈"};

    public YearEndSettlementFrame(UserManager userManager, JFrame parentFrame) {
        this.userManager = userManager;
        this.parentFrame = parentFrame;

        setTitle("연말 정산");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel("연말 정산");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(385, 25, 200, 40);
        mainPanel.add(titleLabel);

        JLabel yearLabel = new JLabel("연도");
        yearLabel.setBounds(310, 85, 50, 30);
        mainPanel.add(yearLabel);

        yearField = new JTextField("2025");
        yearField.setBounds(360, 85, 120, 32);
        mainPanel.add(yearField);

        JButton searchButton = createBlueButton("조회");
        searchButton.setBounds(500, 83, 90, 36);
        mainPanel.add(searchButton);

        summaryModel = new DefaultTableModel();
        summaryModel.addColumn("사용자");

        for (String category : expenseCategories) {
            summaryModel.addColumn(category);
        }

        for (String category : incomeCategories) {
            summaryModel.addColumn(category);
        }

        summaryModel.addColumn("총수입");
        summaryModel.addColumn("총지출");

        summaryTable = new JTable(summaryModel);

        JScrollPane summaryScroll = new JScrollPane(summaryTable);
        summaryScroll.setBounds(40, 160, 800, 190);
        mainPanel.add(summaryScroll);

        budgetModel = new DefaultTableModel();
        budgetModel.addColumn("사용자");
        budgetModel.addColumn("카테고리");
        budgetModel.addColumn("연간 예산");
        budgetModel.addColumn("실제 지출");
        budgetModel.addColumn("차이");
        budgetModel.addColumn("결과");

        budgetTable = new JTable(budgetModel);

        JScrollPane budgetScroll = new JScrollPane(budgetTable);
        budgetScroll.setBounds(40, 410, 800, 185);
        mainPanel.add(budgetScroll);

        JLabel summaryLabel = new JLabel("연간 가족 정산표");
        summaryLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        summaryLabel.setBounds(40, 130, 200, 30);
        mainPanel.add(summaryLabel);

        JLabel budgetLabel = new JLabel("예산 대비 사용량");
        budgetLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 18));
        budgetLabel.setBounds(40, 380, 200, 30);
        mainPanel.add(budgetLabel);

        JButton backButton = createGrayButton("뒤로가기");
        backButton.setBounds(380, 615, 130, 38);
        mainPanel.add(backButton);

        searchButton.addActionListener(e -> loadSettlement());

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadSettlement() {
        int year;

        try {
            year = Integer.parseInt(yearField.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "연도를 올바르게 입력하세요.");
            return;
        }

        summaryModel.setRowCount(0);
        budgetModel.setRowCount(0);

        int[] expenseTotals = new int[expenseCategories.length];
        int[] incomeTotals = new int[incomeCategories.length];

        int familyIncomeTotal = 0;
        int familyExpenseTotal = 0;

        BudgetManager budgetManager = new BudgetManager();

        for (User user : userManager.getUserList()) {
            if (user.isAdmin()) continue;

            String userId = user.getId();
            AccountManager accountManager = new AccountManager(userId);

            Object[] row = new Object[expenseCategories.length + incomeCategories.length + 3];
            row[0] = userId;

            int index = 1;

            for (int i = 0; i < expenseCategories.length; i++) {
                int expense = accountManager.getYearlyCategoryExpense(year, expenseCategories[i]);
                row[index++] = expense + "원";
                expenseTotals[i] += expense;
            }

            for (int i = 0; i < incomeCategories.length; i++) {
                int income = accountManager.getYearlyCategoryIncome(year, incomeCategories[i]);
                row[index++] = income + "원";
                incomeTotals[i] += income;
            }

            int incomeTotal = accountManager.getYearlyIncome(year);
            int expenseTotal = accountManager.getYearlyExpense(year);

            row[index++] = incomeTotal + "원";
            row[index] = expenseTotal + "원";

            familyIncomeTotal += incomeTotal;
            familyExpenseTotal += expenseTotal;

            summaryModel.addRow(row);

            for (String category : expenseCategories) {
                int yearlyBudget = budgetManager.getYearlyBudget(year, userId, category);
                int actualExpense = accountManager.getYearlyCategoryExpense(year, category);

                if (yearlyBudget == 0 && actualExpense == 0) continue;

                int difference = yearlyBudget - actualExpense;
                String result = difference >= 0 ? "절약" : "초과";

                budgetModel.addRow(new Object[]{
                        userId,
                        category,
                        yearlyBudget + "원",
                        actualExpense + "원",
                        Math.abs(difference) + "원",
                        result
                });
            }
        }

        Object[] totalRow = new Object[expenseCategories.length + incomeCategories.length + 3];
        totalRow[0] = "합계";

        int index = 1;

        for (int total : expenseTotals) {
            totalRow[index++] = total + "원";
        }

        for (int total : incomeTotals) {
            totalRow[index++] = total + "원";
        }

        totalRow[index++] = familyIncomeTotal + "원";
        totalRow[index] = familyExpenseTotal + "원";

        summaryModel.addRow(totalRow);
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
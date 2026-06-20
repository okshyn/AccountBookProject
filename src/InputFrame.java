import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InputFrame extends JFrame {
    private User loginUser;
    private AccountManager accountManager;
    private String category;
    private String type;

    private JTable table;
    private DefaultTableModel tableModel;

    public InputFrame(User loginUser, AccountManager accountManager, String category) {
        this.loginUser = loginUser;
        this.accountManager = accountManager;
        this.category = category;

        if(category.equals("월급") || category.equals("용돈")){
            this.type = "수입";
        } 
        else{
            this.type = "지출";
        }

        setTitle(category + " 입력");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 650);
        add(mainPanel);

        JLabel titleLabel = new JLabel(category + " 입력");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 300, 40);
        mainPanel.add(titleLabel);

        JLabel infoLabel = new JLabel("구분: " + type + " / 날짜, 금액, 메모를 입력하세요.");
        infoLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        infoLabel.setForeground(new Color(120, 120, 120));
        infoLabel.setBounds(30, 70, 700, 30);
        mainPanel.add(infoLabel);

        JButton addRowButton = new JButton("행 추가");
        addRowButton.setBounds(30, 115, 120, 40);
        addRowButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        addRowButton.setBackground(new Color(80, 120, 200));
        addRowButton.setForeground(Color.WHITE);
        addRowButton.setFocusPainted(false);
        mainPanel.add(addRowButton);

        JButton saveButton = new JButton("저장");
        saveButton.setBounds(165, 115, 120, 40);
        saveButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        saveButton.setBackground(new Color(80, 120, 200));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);
        mainPanel.add(saveButton);

        JButton backButton = new JButton("뒤로가기");
        backButton.setBounds(730, 115, 120, 40);
        backButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(80, 120, 200));
        backButton.setFocusPainted(false);
        mainPanel.add(backButton);

        String[] columns = {"날짜", "금액", "메모"};

        tableModel = new DefaultTableModel(columns, 0){
            public boolean isCellEditable(int row, int column){
                return true;
            }
        };

        table = new JTable(tableModel);
        table.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Malgun Gothic", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 180, 820, 360);
        mainPanel.add(scrollPane);

        for (int i = 0; i < 5; i++){
            tableModel.addRow(new Object[]{"", "", ""});
        }

        addRowButton.addActionListener(e -> {
            tableModel.addRow(new Object[]{"", "", ""});
        });

        saveButton.addActionListener(e -> saveAccounts());

        backButton.addActionListener(e -> dispose());

        setVisible(true);
    }

    private void saveAccounts() {
        if(table.isEditing()){
            table.getCellEditor().stopCellEditing();
        }

        for(int i = 0; i < tableModel.getRowCount(); i++){

            Object dateObj = tableModel.getValueAt(i, 0);
            Object amountObj = tableModel.getValueAt(i, 1);
            Object memoObj = tableModel.getValueAt(i, 2);

            String date = dateObj == null ? "" : dateObj.toString().trim();
            String amountText = amountObj == null ? "" : amountObj.toString().trim();
            String memo = memoObj == null ? "" : memoObj.toString().trim();

            if(date.equals("") && amountText.equals("") && memo.equals("")){
                continue;
            }

            if(date.equals("") || amountText.equals("")){
                JOptionPane.showMessageDialog(this, "날짜와 금액은 반드시 입력해야 합니다.");
                return;
            }

            int amount;
            try{
                amount = Integer.parseInt(amountText);
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(this, "금액은 숫자로 입력해야 합니다.");
                return;
            }
            accountManager.addItem(date, type, amount, category, memo);
        }
        accountManager.saveAccounts();

        JOptionPane.showMessageDialog(this, "입력한 내역이 저장되었습니다.");
        dispose();
    }
}
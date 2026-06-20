import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AllAccountFrame extends JFrame {
    private User loginUser;
    private AccountManager accountManager;
    private JFrame parentFrame;

    private JTable table;
    private DefaultTableModel tableModel;

    private JComboBox<String> yearBox;
    private JComboBox<String> monthBox;
    private JComboBox<String> categoryBox;

    private JLabel incomeLabel;
    private JLabel expenseLabel;
    private JLabel balanceLabel;

    private boolean editMode = false;
    private JButton saveEditButton;

    public AllAccountFrame(User loginUser, AccountManager accountManager, JFrame parentFrame){
        this.loginUser = loginUser;
        this.accountManager = accountManager;
        this.parentFrame = parentFrame;

        setTitle("내역 조회");
        setSize(900, 750);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 247, 250));
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 750);
        add(mainPanel);

        JLabel titleLabel = new JLabel(loginUser.getId() + "님의 내역 조회");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(30, 30, 400, 40);
        mainPanel.add(titleLabel);

        JButton backButton = new JButton("뒤로가기");
        backButton.setBounds(730, 40, 120, 40);
        backButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(80, 120, 200));
        backButton.setFocusPainted(false);
        mainPanel.add(backButton);

        JLabel yearLabel = new JLabel("년도");
        yearLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        yearLabel.setBounds(30, 105, 50, 30);
        mainPanel.add(yearLabel);

        java.util.ArrayList<String> yearList = new java.util.ArrayList<>();
        yearList.add("전체");

        for(Account item : accountManager.getItems()){
            String date = item.getDate();

            if(date.length() >= 4){
                String year = date.substring(0, 4);

                if(!yearList.contains(year)){
                    yearList.add(year);
                }
            }
        }

        yearBox = new JComboBox<>(yearList.toArray(new String[0]));
        yearBox.setBounds(80, 105, 100, 30);
        mainPanel.add(yearBox);

        JLabel monthLabel = new JLabel("월");
        monthLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        monthLabel.setBounds(205, 105, 40, 30);
        mainPanel.add(monthLabel);

        String[] months = {
                "전체", "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "10", "11", "12"
        };

        monthBox = new JComboBox<>(months);
        monthBox.setBounds(240, 105, 90, 30);
        mainPanel.add(monthBox);

        JLabel categoryLabel = new JLabel("카테고리");
        categoryLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        categoryLabel.setBounds(355, 105, 70, 30);
        mainPanel.add(categoryLabel);

        String[] categories = {
                "전체", "식비", "교통", "쇼핑", "월급", "용돈", "기타"
        };

        categoryBox = new JComboBox<>(categories);
        categoryBox.setBounds(425, 105, 110, 30);
        mainPanel.add(categoryBox);

        JButton searchButton = new JButton("조회");
        searchButton.setBounds(560, 105, 100, 30);
        searchButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        searchButton.setBackground(new Color(80, 120, 200));
        searchButton.setForeground(Color.WHITE);
        searchButton.setFocusPainted(false);
        mainPanel.add(searchButton);

        JButton deleteButton = new JButton("내역 삭제");
        deleteButton.setBounds(670, 105, 90, 30);
        deleteButton.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
        deleteButton.setBackground(Color.WHITE);
        deleteButton.setForeground(new Color(200, 80, 80));
        deleteButton.setFocusPainted(false);
        mainPanel.add(deleteButton);

        JButton editButton = new JButton("내역 수정");
        editButton.setBounds(765, 105, 90, 30);
        editButton.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
        editButton.setBackground(Color.WHITE);
        editButton.setForeground(new Color(80, 120, 200));
        editButton.setFocusPainted(false);
        mainPanel.add(editButton);

        saveEditButton = new JButton("수정 저장");
        saveEditButton.setBounds(765, 140, 90, 30);
        saveEditButton.setFont(new Font("Malgun Gothic", Font.BOLD, 13));
        saveEditButton.setBackground(new Color(80, 120, 200));
        saveEditButton.setForeground(Color.WHITE);
        saveEditButton.setFocusPainted(false);
        saveEditButton.setVisible(false);
        mainPanel.add(saveEditButton);

        String[] columns = {"원본번호", "날짜", "구분", "금액", "카테고리", "메모"};

        tableModel = new DefaultTableModel(columns, 0){
            public boolean isCellEditable(int row, int column){
                return editMode && column != 0;
            }
        };

        table = new JTable(tableModel);

        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);

        table.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
        table.setRowHeight(35);
        table.getTableHeader().setFont(new Font("Malgun Gothic", Font.BOLD, 16));

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 180, 820, 410);
        mainPanel.add(scrollPane);

        incomeLabel = new JLabel("총 수입: 0원");
        incomeLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        incomeLabel.setBounds(30, 630, 250, 30);
        mainPanel.add(incomeLabel);

        expenseLabel = new JLabel("총 지출: 0원");
        expenseLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        expenseLabel.setBounds(300, 630, 250, 30);
        mainPanel.add(expenseLabel);

        balanceLabel = new JLabel("잔액: 0원");
        balanceLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 16));
        balanceLabel.setBounds(570, 630, 250, 30);
        mainPanel.add(balanceLabel);

        loadFilteredData();

        searchButton.addActionListener(e -> loadFilteredData());

        deleteButton.addActionListener(e -> deleteSelectedRow());

        editButton.addActionListener(e -> {
            editMode = true;
            saveEditButton.setVisible(true);
            JOptionPane.showMessageDialog(this, "수정할 셀을 클릭해서 내용을 바꾼 후 수정 저장을 누르세요.");
        });

        saveEditButton.addActionListener(e -> saveEditedData());

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadFilteredData(){
        tableModel.setRowCount(0);

        int totalIncome = 0;
        int totalExpense = 0;

        String selectedYear = (String) yearBox.getSelectedItem();
        String selectedMonth = (String) monthBox.getSelectedItem();
        String selectedCategory = (String) categoryBox.getSelectedItem();

        for(int i = 0; i < accountManager.getItems().size(); i++){
            Account item = accountManager.getItems().get(i);

            String date = item.getDate();

            boolean yearMatch = selectedYear.equals("전체") || date.startsWith(selectedYear);

            boolean monthMatch = true;

            if(!selectedMonth.equals("전체")){
                String month = String.format("%02d", Integer.parseInt(selectedMonth));

                if(date.length() >= 7){
                    monthMatch = date.substring(5, 7).equals(month);
                }
                else{
                    monthMatch = false;
                }
            }

            boolean categoryMatch = selectedCategory.equals("전체")
                    || item.getCategory().equals(selectedCategory);

            if(yearMatch && monthMatch && categoryMatch){
                Object[] row = {
                        i,
                        item.getDate(),
                        item.getType(),
                        item.getAmount(),
                        item.getCategory(),
                        item.getMemo()
                };

                tableModel.addRow(row);

                if(item.getType().equals("수입")){
                    totalIncome += item.getAmount();
                }
                else if(item.getType().equals("지출")){
                    totalExpense += item.getAmount();
                }
            }
        }

        incomeLabel.setText("총 수입: " + totalIncome + "원");
        expenseLabel.setText("총 지출: " + totalExpense + "원");
        balanceLabel.setText("잔액: " + (totalIncome - totalExpense) + "원");
    }

    private void deleteSelectedRow(){
        int selectedRow = table.getSelectedRow();

        if(selectedRow == -1){
            JOptionPane.showMessageDialog(this, "삭제할 행을 선택하세요.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                "선택한 내역을 삭제하시겠습니까?",
                "삭제 확인",
                JOptionPane.YES_NO_OPTION
        );

        if(result != JOptionPane.YES_OPTION){
            return;
        }

        int originalIndex = Integer.parseInt(tableModel.getValueAt(selectedRow, 0).toString());

        accountManager.removeItem(originalIndex);
        accountManager.saveAccounts();

        JOptionPane.showMessageDialog(this, "삭제되었습니다.");
        loadFilteredData();
    }

    private void saveEditedData(){
        if(table.isEditing()){
            table.getCellEditor().stopCellEditing();
        }

        for(int i = 0; i < tableModel.getRowCount(); i++){
            int originalIndex = Integer.parseInt(tableModel.getValueAt(i, 0).toString());

            String date = tableModel.getValueAt(i, 1).toString().trim();
            String type = tableModel.getValueAt(i, 2).toString().trim();
            String amountText = tableModel.getValueAt(i, 3).toString().trim();
            String category = tableModel.getValueAt(i, 4).toString().trim();
            String memo = tableModel.getValueAt(i, 5).toString().trim();

            if(date.equals("") || type.equals("") || amountText.equals("") || category.equals("")){
                JOptionPane.showMessageDialog(this, "날짜, 구분, 금액, 카테고리는 비울 수 없습니다.");
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

            Account updatedAccount = new Account(date, type, amount, category, memo);
            accountManager.updateItem(originalIndex, updatedAccount);
        }

        accountManager.saveAccounts();

        editMode = false;
        saveEditButton.setVisible(false);

        JOptionPane.showMessageDialog(this, "수정 내용이 저장되었습니다.");
        loadFilteredData();
    }
}
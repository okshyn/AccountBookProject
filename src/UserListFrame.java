import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class UserListFrame extends JFrame {

    private UserManager userManager;
    private JTable table;
    private DefaultTableModel model;
    private JFrame parentFrame;

    public UserListFrame(UserManager userManager, JFrame parentFrame) {
        this.userManager = userManager;
        this.parentFrame = parentFrame;

        setTitle("회원 관리");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);
        mainPanel.setBackground(new Color(245, 247, 250));
        add(mainPanel);

        JLabel titleLabel = new JLabel("회원 관리");
        titleLabel.setFont(new Font("Malgun Gothic", Font.BOLD, 28));
        titleLabel.setBounds(245, 30, 250, 40);
        mainPanel.add(titleLabel);

        JLabel infoLabel = new JLabel("회원 목록을 확인하고 선택한 회원을 삭제할 수 있습니다.");
        infoLabel.setFont(new Font("Malgun Gothic", Font.PLAIN, 14));
        infoLabel.setBounds(145, 75, 450, 25);
        mainPanel.add(infoLabel);

        model = new DefaultTableModel();
        model.addColumn("아이디");
        model.addColumn("역할");

        table = new JTable(model);
        table.setFont(new Font("Malgun Gothic", Font.PLAIN, 15));
        table.setRowHeight(32);
        table.getTableHeader().setFont(new Font("Malgun Gothic", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(230, 235, 242));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        loadUsers();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(60, 120, 580, 250);
        mainPanel.add(scrollPane);

        JButton deleteButton = new JButton("선택 회원 삭제");
        deleteButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        deleteButton.setBounds(185, 395, 150, 40);
        mainPanel.add(deleteButton);

        JButton backButton = new JButton("뒤로가기");
        backButton.setFont(new Font("Malgun Gothic", Font.BOLD, 14));
        backButton.setBounds(365, 395, 150, 40);
        mainPanel.add(backButton);

        deleteButton.addActionListener(e -> deleteSelectedUser());

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        setVisible(true);
    }

    private void loadUsers() {
        model.setRowCount(0);

        for (User user : userManager.getUserList()) {
            String role;

            if (user.getId().equals("admin")) {
                role = "관리자";
            } else {
                role = "일반 회원";
            }

            model.addRow(new Object[]{
                    user.getId(),
                    role
            });
        }
    }

    private void deleteSelectedUser() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "삭제할 회원을 선택하세요.");
            return;
        }

        String id = model.getValueAt(row, 0).toString();

        if (id.equals("admin")) {
            JOptionPane.showMessageDialog(this, "관리자 계정은 삭제할 수 없습니다.");
            return;
        }

        int result = JOptionPane.showConfirmDialog(
                this,
                id + " 회원을 정말 삭제하시겠습니까?",
                "회원 삭제",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            userManager.deleteUser(id);
            loadUsers();
            JOptionPane.showMessageDialog(this, "회원이 삭제되었습니다.");
        }
    }
}
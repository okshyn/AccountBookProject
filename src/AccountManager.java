import java.io.*;
import java.util.ArrayList;

public class AccountManager {
    private ArrayList<Account> items = new ArrayList<>();
    private String userId;

    public AccountManager(String userId) {
        this.userId = userId;
        loadAccounts();
    }

    public void addItem(String date, String type, int amount, String category, String memo) {
        Account item = new Account(date, type, amount, category, memo);
        items.add(item);
        saveAccounts();
        System.out.println("가계부 내역이 추가되었습니다.");
    }

    public void saveAccounts() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(userId + "account.txt"));

            for (Account account : items) {
                bw.write(account.getDate() + ","
                        + account.getType() + ","
                        + account.getAmount() + ","
                        + account.getCategory() + ","
                        + account.getMemo());
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("가계부 저장 중 오류가 발생했습니다.");
        }
    }

    public void loadAccounts() {
        items.clear();

        try {
            BufferedReader br = new BufferedReader(new FileReader(userId + "account.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",", -1);

                if (data.length < 5) continue;

                String date = data[0];
                String type = data[1];
                int amount = Integer.parseInt(data[2]);
                String category = data[3];
                String memo = data[4];

                Account account = new Account(date, type, amount, category, memo);
                items.add(account);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("저장된 가계부 내역이 없습니다.");
        }
    }

    public void showItems() {
        if (items.isEmpty()) {
            System.out.println("등록된 가계부 내역이 없습니다.");
            return;
        }

        System.out.println("===== 전체 가계부 내역 =====");
        for (Account item : items) {
            item.printInfo();
        }
    }

    public String getItemsText() {
        if (items.isEmpty()) {
            return "등록된 가계부 내역이 없습니다.";
        }

        String result = "===== 전체 가계부 내역 =====\n";

        for (Account item : items) {
            result += item.getDate() + " | "
                    + item.getType() + " | "
                    + item.getAmount() + "원 | "
                    + item.getCategory() + " | "
                    + item.getMemo() + "\n";
        }

        return result;
    }

    public ArrayList<Account> getItems() {
        return items;
    }

    public void clearItems() {
        items.clear();
        saveAccounts();
    }

    public void removeItem(int index) {
        items.remove(index);
        saveAccounts();
    }

    public void updateItem(int index, Account account) {
        items.set(index, account);
        saveAccounts();
    }

    public int getCategoryExpense(int year, int month, String category) {
        int total = 0;
        for(Account item : items) {
            String[] dateParts = item.getDate().split("-");

            int itemYear = Integer.parseInt(dateParts[0]);
            int itemMonth = Integer.parseInt(dateParts[1]);

            if(itemYear == year &&
                    itemMonth == month &&
                    item.getType().equals("지출") &&
                    item.getCategory().equals(category)) {

                total += item.getAmount();
            }
        }
        return total;
    }
    
    public int getTotalExpense(int year, int month) {
        int total = 0;
        for(Account item : items) {
            String[] dateParts = item.getDate().split("-");

            int itemYear = Integer.parseInt(dateParts[0]);
            int itemMonth = Integer.parseInt(dateParts[1]);

            if(itemYear == year &&
                    itemMonth == month &&
                    item.getType().equals("지출")) {

                total += item.getAmount();
            }
        }
        return total;
    }

    public int getYearlyCategoryExpense(int year, String category) {
        int total = 0;

        for (Account item : items) {
            String date = item.getDate();

            if (date.length() >= 4) {
                int itemYear = Integer.parseInt(date.substring(0, 4));

                if (itemYear == year &&
                        item.getType().equals("지출") &&
                        item.getCategory().equals(category)) {
                    total += item.getAmount();
                }
            }
        }
        return total;
    }

    public int getYearlyIncome(int year) {
        int total = 0;

        for (Account item : items) {
            String date = item.getDate();

            if (date.length() >= 4) {
                int itemYear = Integer.parseInt(date.substring(0, 4));

                if (itemYear == year && item.getType().equals("수입")) {
                    total += item.getAmount();
                }
            }
        }
        return total;
    }

    public int getYearlyExpense(int year) {
        int total = 0;

        for (Account item : items) {
            String date = item.getDate();

            if (date.length() >= 4) {
                int itemYear = Integer.parseInt(date.substring(0, 4));

                if (itemYear == year && item.getType().equals("지출")) {
                    total += item.getAmount();
                }
            }
        }
        return total;
    }

    public int getYearlyCategoryIncome(int year, String category) {
        int total = 0;
        for (Account item : items) {
            String date = item.getDate();

            if (date.length() >= 4) {
                int itemYear = Integer.parseInt(date.substring(0, 4));

                if (itemYear == year &&
                        item.getType().equals("수입") &&
                        item.getCategory().equals(category)) {
                    total += item.getAmount();
                }
            }
        }
        return total;
    }
}
import java.io.*;
import java.util.ArrayList;

public class BudgetManager {

    private ArrayList<String> budgets = new ArrayList<>();

    public BudgetManager() {
        loadBudgets();
    }

    public void saveBudget(int year, int month, String userId, String category, int amount) {
        String newData = year + "," + month + "," + userId + "," + category + "," + amount;

        for (int i = 0; i < budgets.size(); i++) {
            String[] data = budgets.get(i).split(",");

            if (data[0].equals(String.valueOf(year)) &&
                    data[1].equals(String.valueOf(month)) &&
                    data[2].equals(userId) &&
                    data[3].equals(category)) {

                budgets.set(i, newData);
                saveBudgets();
                return;
            }
        }

        budgets.add(newData);
        saveBudgets();
    }

    private void loadBudgets() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("budgets.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                budgets.add(line);
            }

            br.close();
        } catch (IOException e) {
            System.out.println("저장된 예산 정보가 없습니다.");
        }
    }

    private void saveBudgets() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("budgets.txt"));

            for (String budget : budgets) {
                bw.write(budget);
                bw.newLine();
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("예산 정보 저장 오류");
        }
    }

    public int getYearlyBudget(int year, String userId, String category) {
        int total = 0;

        for (String budget : budgets) {
            String[] data = budget.split(",", -1);

            if (data.length < 5) continue;

            int savedYear = Integer.parseInt(data[0]);
            String savedUserId = data[2];
            String savedCategory = data[3];
            int amount = Integer.parseInt(data[4]);

            if (savedYear == year &&
                    savedUserId.equals(userId) &&
                    savedCategory.equals(category)) {
                total += amount;
            }
        }

        return total;
    }

    public int getMonthlyBudget(int year, int month, String userId, String category) {
        for (String budget : budgets) {
            String[] data = budget.split(",", -1);

            if (data.length < 5) continue;

            int savedYear = Integer.parseInt(data[0]);
            int savedMonth = Integer.parseInt(data[1]);
            String savedUserId = data[2];
            String savedCategory = data[3];
            int amount = Integer.parseInt(data[4]);

            if (savedYear == year &&
                    savedMonth == month &&
                    savedUserId.equals(userId) &&
                    savedCategory.equals(category)) {
                return amount;
            }
        }

        return 0;
    }

    public ArrayList<String> getUserBudgetYears(String userId) {
        ArrayList<String> yearList = new ArrayList<>();

        for(String budget : budgets) {
            String[] data = budget.split(",", -1);

            String year = data[0];
            String savedUserId = data[2];

            if(savedUserId.equals(userId)) {

                if(!yearList.contains(year)) {
                    yearList.add(year);
                }
            }
        }
        return yearList;
    }
}
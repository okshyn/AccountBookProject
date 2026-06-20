public class Account {
    private String date;
    private String type;
    private int amount;
    private String category;
    private String memo;

    public Account(String date, String type,  int amount, String category, String memo){
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.memo = memo;
    }

    public String getDate(){
        return date;
    }

    public String getType(){
        return type;
    }

    public int getAmount(){
        return amount;
    }

    public String getCategory(){
        return category;
    }

    public String getMemo(){
        return memo;
    }

    public void printInfo(){
        System.out.println(date + " | " + type + " | " + amount + "원 | " + category + " | " + memo);
    }
}

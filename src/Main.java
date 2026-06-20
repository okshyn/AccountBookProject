import java.util.Scanner;

public class Main{

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        UserManager userManager = new UserManager();

        while(true){
            System.out.println("===== 가계부 시스템 =====");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("0. 프로그램 종료");
            System.out.print("선택: ");

            int choice = sc.nextInt();

            switch (choice){

                case 1:
                    System.out.print("\nID 입력: ");
                    String id = sc.next();

                    System.out.print("PW 입력: ");
                    String pw = sc.next();

                    userManager.register(id, pw, false);
                    break;

                case 2:
                    System.out.print("\nID 입력: ");
                    String loginId = sc.next();

                    System.out.print("PW 입력: ");
                    String loginPw = sc.next();

                    User loginUser = userManager.login(loginId, loginPw);

                    if(loginUser != null){
                        System.out.println("로그인 성공!");

                        if(loginUser.isAdmin()){
                            System.out.println("관리자 계정입니다.");
                            showAdminMenu(sc, userManager);
                        }
                        else{
                            AccountManager accountManager = new AccountManager(loginUser.getId());
                            showUserMenu(sc,accountManager,loginUser);
                        }
                    }
                    else{
                        System.out.println("ID 또는 Password가 틀렸습니다.");
                    }
                    break;

                case 0:
                    System.out.println("프로그램 종료");
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
            }

            System.out.println();
        }
    }

    public static void showAdminMenu(Scanner sc, UserManager userManager){
        while(true){
            System.out.println("\n===== 관리자 메뉴 =====");
            System.out.println("1. 회원 목록 조회");
            System.out.println("2. 회원 삭제");
            System.out.println("0. 로그아웃");
            System.out.print("선택: ");
            System.out.println();

            int choice = sc.nextInt();
            sc.nextLine();

            switch(choice){
                case 1:
                    userManager.showUsers();
                    break;

                case 2:
                    System.out.print("\n삭제할 ID: ");
                    String deleteId = sc.next();
                    userManager.deleteUser(deleteId);
                    break;

                case 0:
                    System.out.println("로그아웃합니다.");
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
            }

            System.out.println();
        }
    }

    public static void showUserMenu(Scanner sc, AccountManager accountManager, User loginUser){
        while(true){
            System.out.println("\n===== 가계부 메뉴 =====");
            System.out.println("1. 수입/지출 입력");
            System.out.println("2. 전체 내역 조회");
            System.out.println("3. 월별 내역 조회");
            System.out.println("4. 총 수입/지출 보기");
            System.out.println("5. 카테고리별 조회");
            System.out.println("6. 카테고리별 합계 보기");
            System.out.println("0. 로그아웃");
            System.out.print("선택: ");
            int choice = sc.nextInt();
            sc.nextLine();
            System.out.println();

            switch(choice){
                case 1:
                    System.out.print("날짜 입력(예: 2026-05-25): ");
                    String date = sc.next();

                    System.out.println("1. 수입");
                    System.out.println("2. 지출");
                    System.out.print("선택: ");

                    int typeChoice = sc.nextInt();
                    sc.nextLine();
                    System.out.println();

                    String type;

                    if(typeChoice == 1){
                        type = "수입";
                    }
                    else{
                        type = "지출";
                    }

                    System.out.print("금액 입력: ");
                    int amount = sc.nextInt();
                    System.out.println();

                    System.out.println("카테고리 선택");
                    System.out.println("1. 식비");
                    System.out.println("2. 교통");
                    System.out.println("3. 쇼핑");
                    System.out.println("4. 월급");
                    System.out.println("5. 용돈");
                    System.out.println("6. 기타");
                    System.out.print("선택: ");
                    int categoryChoice = sc.nextInt();
                    sc.nextLine();
                    System.out.println();

                    String category;

                    switch (categoryChoice) {
                        case 1:
                            category = "식비";
                            break;
                        case 2:
                            category = "교통";
                            break;
                        case 3:
                            category = "쇼핑";
                            break;
                        case 4:
                            category = "월급";
                            break;
                        case 5:
                            category = "용돈";
                            break;
                        default:
                            category = "기타";
                    }
                    System.out.print("메모 입력: ");
                    String memo = sc.nextLine();

                    accountManager.addItem(date, type, amount, category, memo);
                    accountManager.saveAccounts();
                    break;

                case 2:
                    accountManager.showItems();
                    break;

                case 3:
                    System.out.println("월별 내역 조회 기능");
                    break;

                case 4:
                    System.out.println("총 수입/지출 보기 기능");
                    break;

                case 5:
                    System.out.println("카테고리별 조회 기능");
                    break;

                case 6:
                    System.out.println("카테고리별 합계 보기 기능");
                    break;

                case 0:
                    System.out.println("로그아웃합니다.");
                    return;

                default:
                    System.out.println("잘못된 입력입니다.");
            }

            System.out.println();
        }
    }
}
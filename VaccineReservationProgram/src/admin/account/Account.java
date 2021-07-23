package admin.account;

import java.util.Scanner;

public class Account {
	Scanner sc = new Scanner(System.in);
	
	public static final String LOGINID = "admin";
	public static final String LOGINPWD = "1234";
	private String id;
	private String pwd;
	
	public boolean loginAdminAccount() {
		System.out.println("\n-----[관리자 로그인]-----");
		System.out.print("＊ 아이디 입력 : ");
		id = sc.next();
		System.out.print("＊ 비밀번호 입력 : ");
		pwd = sc.next();
		System.out.println("---------------------\n");
		
		if(LOGINID.equals(id) && LOGINPWD.equals(pwd)) {
			System.out.println(LOGINID + "님 로그인 되었습니다.");
			return true;
			
		}else if(!LOGINID.equals(id)){
			System.out.println(id +"는 존재하지 않습니다.");
			System.out.println("메뉴로 다시 돌아갑니다.");
			return false;
		
		}else if(!LOGINPWD.equals(pwd)){
			System.out.println("비밀번호 입력 오류");
			System.out.println("메뉴로 다시 돌아갑니다.");
			return false;
		}else {
			System.out.println("로그인 실패하였습니다.");
			return false;
		}
	}
	
	public void logoutAccount() {
		id = null;
		pwd = null;
		System.out.println("\n로그아웃 되었습니다.");
	}
	
	
	

}

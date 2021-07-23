package view;

import java.util.InputMismatchException;
import java.util.Scanner;

import admin.account.Account;
import controller.AdminManager;
import controller.VaccineManager;
import io.VaccineIO;

/**
 * 
 * view 클래스
 * @author HyejinKwon
 *
 */
public class Menu {
	Scanner sc = new Scanner(System.in);
	Account acc = new Account();
	VaccineIO io = new VaccineIO();
	
	/**
	 * 메인메뉴1 출력메소드
	 * - 예약하기
	 * 	: VaccineManager클래스의 해당 메소드로 이동. 입력값 바탕으로 io 진행.
	 * - 예약조회
	 * 	: 해당 아이디값을 가진 요소 리턴해서 예약 정보 출력.
	 * - 예약변경
	 * 	: 해당 아이디값을 가진 요소 리턴해서 예약 정보 수정.
	 * - 예약취소
	 * 	: 해당 아이디값을 가진 요소 리턴해서 해당 정보 삭제.
	 * - 관리자 모드 
	 * - 종료
	 */
	public void mainMenu() {
		VaccineManager vm = new VaccineManager();
		
		String main =  "\n------[백신 예약 메뉴]-------\n"
						+ "1. 예약하기\n"
						+ "2. 예약조회\n"
						+ "3. 예약변경\n"
						+ "4. 예약취소\n"
						+ "9. 관리자 모드\n"
						+ "0. 종료\n"
						+ "-------------------------";
		
		System.out.println("====== 백신 예약 프로그램을 실행합니다 ======" );
		
		while(true) {
			try {
				System.out.println(main);
				
				System.out.print("▶ 메뉴 입력 : ");
				int num = sc.nextInt();

				switch (num) {
				case 1:
					vm.bookReservation();
					break;
				case 2:
					vm.lookUpReservation();
					break;
				case 3:
					vm.changeReservation();
					break;
				case 4:
					vm.cancelReservation();
					break;
				case 9:
					boolean bool = acc.loginAdminAccount();
					if(bool == true) {
						reserveAdmin();
					}
					break;
				 //이전 메뉴로 돌아갈 필요 없이 바로 프로그램 종료하기 위해 exit 사용. 메인으로 수정되었으니 return으로 수정.
				case 0:
					System.out.println();
					System.out.println("====== 백신 예약 프로그램을 종료합니다 ======");
					return;
				default:
					System.out.println("잘못 입력하셨습니다. 다시 입력하세요");
					break;
				}
			}catch(InputMismatchException e) {
				System.out.println("숫자를 입력해 주세요");
				sc.nextLine();
			}
		}
	}
	
	/**
	 * 서브메뉴2 출력메소드(관리자 대상)
	 * - 예약자 명단 확인하기
	 * 	: AdminManager클래스의 메소드를 통해 io 활용해서 명단 출력
	 * 	ObjectInput/Output 이용해서 transient 키워드 활용. 주민번호 가리기.
	 * - 백신 재고 관리하기
	 * 	: 백신 재고 확인 후 수정할 곳 key값 확인해서 value값 수정
	 * 	Map활용 가능한지 시도
	 * - 종료
	 */
	public void reserveAdmin() {
		AdminManager am = new AdminManager();
		
		String sub = "\n--------[ 관리자 메뉴 ]--------\n"
						+ "1. 예약자 명단 확인하기\n"
						+ "2. 예약자 명단 내보내기\n"
						+ "3. 백신 재고 관리하기\n"
						+ "4. 백신 재고 리스트 내보내기\n"
						+ "9. 로그아웃\n"
						+ "0. 종료\n"
						+ "---------------------------";
		
		while(true) {
			try {
				System.out.println(sub);
				System.out.print("▶ 입력 : ");
				int num = sc.nextInt();

				switch (num) {
				case 1:
					am.checkMemberList();
					break;
				case 2:
					am.outputMembers();
					break;
				case 3:
					am.vaccineManage();
					break;
				case 4:
					am.outputVaccines();
					break;
				case 9:
					acc.logoutAccount();
					return;
				// 이전 메뉴로 돌아갈 필요 없이 바로 프로그램 종료하기 위해 exit 사용.
				case 0:
					System.out.println();
					System.out.println("====== 백신 예약 프로그램을 종료합니다 ======");
					System.exit(0);
				default:
					System.out.println("잘못 입력하셨습니다. 다시 입력하세요");
					break;
				}
			} catch (InputMismatchException e) {
				System.out.println("숫자를 입력해 주세요");
				sc.nextLine();
			}
		}
	}
}

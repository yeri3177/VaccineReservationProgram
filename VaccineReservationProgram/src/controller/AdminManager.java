package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import io.VaccineIO;
import model.vo.Hospital;

/**
 * 관리자 매니징 클래스
 * 
 * @author HyejinKwon
 *
 */
public class AdminManager {
	private VaccineIO vio = new VaccineIO();
	private Scanner sc = new Scanner(System.in);
	Hospital h = new Hospital();
	
	public AdminManager(){
		try {
		h = vio.vaccineRead();
		}catch(NullPointerException e) {
			return;
		}
	}
	
	/**
	 * 예약자 명단 콘솔 출력 메소드
	 */
	public void checkMemberList() {
		System.out.println("\n----------------------------------------------[ 예 약 자 명 단 ]--------------------------------------------------");
		vio.memberRead();
		System.out.println("-----------------------------------------------------------------------------------------------------------------");
	}
	
	/**
	 * 백신 수량 수정 메소드
	 * 백신 재고 현황 출력 후, yn 입력 받아 재고 수정할 지, 하지 않을지 선택
	 * setter이용해 수정 => 이걸 io에서 진행, getter 이용해 수정된 값 출력
	 * save 메소드로 수정된 값 io데이터에 저장
	 */
	public void vaccineManage() {
		//시작 전 보유 중인 재고 확인
		checkVaccineList();
			try {
				// 재고 수정 여부 선택
				System.out.print("백신 재고량을 수정하시겠습니까?(y/n) : ");
				char ch = Character.toLowerCase(sc.next().charAt(0));
				
				if (ch == 'y') {
					System.out.print("\n보유 중인 백신 재고량을 입력하세요 : ");
					int cnt = sc.nextInt();
					
					//입력 받은 수정 수량을 io클래스에 전달
					h = vio.vaccineSave(cnt);
					
					System.out.println("재고 수정이 완료되었습니다.");
					//수정 수량 다시 확인
					checkVaccineList();
				} else if (ch == 'n') {
					System.out.println("\n백신 재고 변경을 취소하였습니다.");
					System.out.println("관리자메뉴로 돌아갑니다.");
				} else {
					System.out.println("잘못 입력하셨습니다. 관리자 메뉴로 돌아갑니다.");
				}
				
			} catch (InputMismatchException e) {
				System.out.println("잘못 입력하셨습니다. 관리자 메뉴로 돌아갑니다.");
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * 백신 재고 리스트 콘솔 출력 메소드
	 * Hospital 객체에 있는 기본 수량 값 불러오기
	 */
	public void checkVaccineList() {
		try {
		Hospital h = vio.vaccineRead();
		System.out.println("\n현재 보유 중인 백신 수량은 " + h.getVaccineCount() + "개입니다.");
		} catch(NullPointerException e) {
			System.out.println("현재 보유한 백신 재고 파일이 없습니다.");
		}

	}
	
	/**
	 * 예약자 명단 파일로 내보내기 메소드
	 * 가능하면 파일명 입력도 진행해보고 싶음
	 */
	public void outputMembers() {
		vio.memberSave();
		System.out.println("\n예약자 명단을 파일로 저장하였습니다. ");
	}

	/**
	 * 백신 재고 리스트 파일로 내보내기 메소드
	 * 가능하면 파일명 입력도 진행해보고 싶음
	 */
	public void outputVaccines() {
		vio.vaccineSave();
		System.out.println("\n백신 정보를 파일로 저장하였습니다.");
		
	}
}

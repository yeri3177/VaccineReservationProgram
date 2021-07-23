package controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

import exception.ExceptionProcess;
import exception.InputInfoExceptionProcess;
import exception.VaccineInfoException;
import io.VaccineIO;
import model.vo.Hospital;
import model.vo.Member;
/**
 * 
 * << 백신 예약 추가/조회/변경/삭제 클래스 >>
 * @author 정예리
 * 
 */
public class VaccineManager {
	
	private Scanner sc = new Scanner(System.in);
	
	//환자정보 관리할 Hashmap 객체 선언 
	Map<String, Member> map = new HashMap<>();
	
	//내용확인을 위해 map 객체에 기본값 요소들 추가해둠  
	public VaccineManager() {
		
		try {
		ArrayList<Member> members = new VaccineIO().memberList();
		
			for(int i = 0; i < members.size(); i++) {
				map.put(members.get(i).getSsn(), members.get(i));
			}
		}catch (NullPointerException e){
			return;
		}
		
	}
	
	/**
	 * map 참조 변수 내보내기
	 */
	public Map<String, Member> memberList(){
		
		return map;
	}

	/**
	 * 백신 예약하기 
	 */
	public void bookReservation() {
		try {
			//백신정보 읽어오기 
			VaccineIO vio = new VaccineIO();
			
			//Hospital 내용 불러오기 
			Hospital ho = vio.vaccineRead();
			
			//백신개수 확인
			int n = ho.getVaccineCount();
			
			//백신 잔여개수 유효성 검사 
			VaccineInfoException ve = new VaccineInfoException();
			ve.chkVcCnt(n);
			
			//Member 참조변수 선언 
			Member newMember;
			
			//개인정보 입력값 받기 
			System.out.println("\n-------[개인정보 입력]-------");
			
			//예외 클래스 객체 생성
			InputInfoExceptionProcess ex = new InputInfoExceptionProcess();
			
			//이름 입력받기 
			System.out.print("＊ 이름 : ");
			String name = sc.next();
			
			//이름 유효성 검사 
			name = ex.chkName(name);
			
			//나이 입력받기
			System.out.print("＊ 나이 : ");
			String s_age = sc.next();
	
			//나이 유효성 검사
			int i_age = ex.chkAge(s_age);
			
			// 주민등록번호 입력받기
			System.out.print("＊ 주민등록번호 : ");
			String ssn = sc.next();
	
			//주민등록번호 유효성 검사
			ssn = ex.chkSsn(ssn, map);
			
			//휴대폰번호 입력받기
			System.out.print("＊ 휴대폰번호 : ");
			String phone = sc.next();
	
			//휴대폰번호 유효성검사
			phone = ex.chkPhoneNumber(phone);
			
			//예약날짜 입력받기
			System.out.print("＊ 예약 날짜 : ");
			String day = sc.next();
	
			//예약날짜 유효성검사
			day = ex.chkRsvDate(day);
			
			//예약시간 입력받기
			System.out.print("＊ 예약 시간 : ");
			String time = sc.next();
	
			//예약시간 유효성검사 
			time = ex.chkRsvTime(time);
			
			//
			Calendar cal = calSetting(day,time);
			
			cal = ex.chkRsvBooking(cal, map);
			//
						
			//member변수에 입력값 넣어 Member객체 생성하기 
			newMember = new Member(name, i_age, ssn, phone, cal);
			
			//map에 요소 추가 
			map.put(ssn, newMember);
			
			
			System.out.println("------------------------");
			System.out.println(name + "님 예약완료 하였습니다.");
			
			//백신개수 1개 감소 
			ho.setVaccineCount(--n);
			
			//백신 내용 저장
			vio.vaccineSave(n);
			
			//멤버 내용 저장
			vio.memberSave(map);
		
			
		//사용자입력 예외 처리 클래스로 처리 
		} catch(ExceptionProcess e) {
			System.out.println(e.getMessage());
			
		//숫자 형식 오류 (문자열이 포함된 변수를 숫자로 변경하는경우)
		} catch(NumberFormatException e) {
			System.out.println("**NumberFormatException 발생!");
			
		} catch(NullPointerException e) {
			return;
		//정수를 입력해야 하는데 문자를 입력한 경우 발생 	
		} catch(InputMismatchException e) {
			System.out.println("**InputMismatchException 발생!");

		//모든 예외 처리 
		} catch(Exception e) {
			System.out.println("**Exception 발생!");
		}
		
	}
	
	/**
	 * 백신 예약조회
	 */
	public void lookUpReservation() {
		//주민등록번호 입력받기 
		String ssn = inputSsn();
		
		//입력받은 주민등록번호로 예약명단에 있는지 확인하기 
		if(!isSsnExist(ssn)) {
			System.out.println("예약자 명단에 없습니다.");
			return;
		}
		
		//주민등록번호로 key값 찾기 
		String key = findKey(ssn);
		
		//날짜,시간 출력 포맷 
		SimpleDateFormat dayFormat = new SimpleDateFormat("YY년 MM월 dd일");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH시 mm분");
		
		//예약내용 출력하기 
		System.out.println("\n---------예약 조회---------");
		System.out.printf("＊ 이름 : %s%n",map.get(key).getName());
		System.out.printf("＊ 병원명 : %s%n", Hospital.getHospitalname());
		System.out.printf("＊ 백신명 : %s%n", Hospital.getVaccinename());
		System.out.printf("＊ 예약날짜 : %s%n",dayFormat.format(map.get(key).getReservationDate().getTime()));
		System.out.printf("＊ 예약시간 : %s%n",timeFormat.format(map.get(key).getReservationDate().getTime()));
		System.out.println("-------------------------");
	}

	/**
	 * 백신 예약변경
	 */
	public void changeReservation() {
		
		
		//주민등록번호 입력받기 
		String ssn = inputSsn();
				
		//입력받은 주민등록번호로 예약명단에 있는지 확인하기 
		if(!isSsnExist(ssn)) {
			System.out.println("예약자 명단에 없습니다.");
			return;
		}
				
		//주민등록번호로 key값 찾기 
		String key = findKey(ssn);
		
		//찾은 key값으로 해당 Member 참조변수 찾기 
		Member member = map.get(key);
						
		//날짜,시간 출력 포맷 
		SimpleDateFormat dateFormat = new SimpleDateFormat("YY년 MM월 dd일 HH시 mm분");
		
		//변경전 예약날짜 출력하기 
		System.out.printf("\n[%s]님의 예약하신 날짜는 %s입니다.%n", member.getName(), dateFormat.format(map.get(key).getReservationDate().getTime()));
		System.out.println("변경할 날짜를 입력해주세요.\n");
		
		try {
			//예외 클래스 객체 생성
			InputInfoExceptionProcess ex = new InputInfoExceptionProcess();
		
			//예약날짜 입력받기
			System.out.print("＊ 예약 날짜 : ");
			String day = sc.next();
	
			//예약날짜 유효성검사
			day = ex.chkRsvDate(day);
			
			//예약시간 입력받기
			System.out.print("＊ 예약 시간 : ");
			String time = sc.next();
	
			//예약시간 유효성검사 
			time = ex.chkRsvTime(time);
			
			//해당 member 참조변수의 reservationDate 필드값만 수정 
			member.setReservationDate(calSetting(day,time));
			
			//변경된 예약정보 저장하기
			VaccineIO io = new VaccineIO();
			io.memberSave(map);
			
			//예약완료 메세지 출력 
			System.out.println(member.getName() + "님의 예약을 변경 했습니다.");
		
		}catch(ExceptionProcess e) {
			System.out.println(e.getMessage());
		} catch(NumberFormatException e) {
			System.out.println("**NumberFormatException 발생!");
		} catch(InputMismatchException e) {
			System.out.println("**InputMismatchException 발생!");
		} catch(Exception e) {
			System.out.println("**Exception 발생!");
		}
		
	}
	
	/**
	 * 백신 예약취소
	 */
	public void cancelReservation() {
		//주민등록번호 입력받기 
		String ssn = inputSsn();
						
		//입력받은 주민등록번호로 예약명단에 있는지 확인하기 
		if(!isSsnExist(ssn)) {
			System.out.println("예약자 명단에 없습니다.");
			return;
		}
						
		//주민등록번호로 key값 찾기 
		String key = findKey(ssn);
		
		//백신정보 읽어오기
		VaccineIO io = new VaccineIO();
		Hospital ho = io.vaccineRead();
		int n = ho.getVaccineCount();
		
		//찾은 key값으로 해당 Member 참조변수 찾기 
        Member member = map.get(key);
		
        //예약취소 안내 출력 
        System.out.println("\n" + member.getName() + "님의 예약이 취소 되었습니다.");
	
        //해당 map 삭제하기
        map.remove(key);
        
        //member객체 저장
      	io.memberSave(map);
      		
      	//백신개수 저장
      	io.vaccineSave(++n);
	}
	
	/**
	 * 주민등록번호 입력 받기   
	 */
	public String inputSsn() {
		System.out.print("\n＊ 주민등록번호를 입력 : ");
		
		String ssn = sc.next();
		
		return ssn;
	}
	
	/**
	 * 주민등록번호 입력받아 예약자명단에 있는지 확인하기  
	 */
	public boolean isSsnExist(String ssn) {
		
		return map.containsKey(ssn);
	}
	
	/**
	 * 주민등록번호로 map에서 key값 찾기 
	 */
	public String findKey(String ssn) {
		Iterator<String> iter = map.keySet().iterator();
		String findKey = null;
		
		while(iter.hasNext()) {
			String iterKey = iter.next();
			
			if(map.get(iterKey).getSsn().equals(ssn)) {
				findKey = iterKey;
			}
		}
		return findKey;
	}


	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return super.equals(obj);
	}
	
	/**
	 * 날짜,시간값으로 셋팅된 Calendar 객체 리턴하기 
	 */
	public Calendar calSetting(String day, String time) {
		//달력 객체 생성
		Calendar cal = Calendar.getInstance();
		
		//날짜 분할
		int yy = Integer.parseInt(day.substring(0, 2));
		int mm = Integer.parseInt(day.substring(3, 5));
		int dd = Integer.parseInt(day.substring(6, 8));
		
		//시간 분할
		int hh = Integer.parseInt(time.substring(0, 2));
		int mi = Integer.parseInt(time.substring(3, 5));
		
		//캘린더 객체에 날짜,시간 셋팅(연/월/일/시/분)
		cal.set(yy, mm-1, dd, hh, mi);
		
		return cal;
	}
	
}
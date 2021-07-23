package model.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 * 
 * << 예약 회원 클래스 >>
 * @author 정예리
 *
 */
public class Member implements Comparable<Member>, Serializable{

	private static final long serialVersionUID = 1L;
	
	/**
	 * field
	 */
	private String name; // 이름
	private int age; // 나이
	private final String ssn; // 주민등록번호
	private String phoneNumber; // 핸드폰번호
	private Calendar ReservationDate; //예약날짜
	
	/**
	 * constructor
	 */
	public Member(String name, int age, String ssn, String phoneNumber, Calendar reservationDate) {
		this.name = name;
		this.age = age;
		this.ssn = ssn;
		this.phoneNumber = phoneNumber;
		ReservationDate = reservationDate;
	}

	/**
	 * getter,setter
	 */
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
				
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		
		this.age = age;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		
		this.phoneNumber = phoneNumber;
	}

	public Calendar getReservationDate() {
		return ReservationDate;
	}
	
	
	public void setReservationDate(Calendar reservationDate) {
		ReservationDate = reservationDate;
	}

	public String getSsn() {
		return ssn;
	}
	
	/**
	 * method
	 */
	@Override
    public String toString() {
        SimpleDateFormat dayFormat = new SimpleDateFormat("YY년 MM월 dd일");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH시 mm분");

        return "이름 : " + name 
                + ", 나이 : " + age 
                + ", 주민등록번호 : " + ssn
                + ", 핸드폰번호 : " + phoneNumber 
                + ", 예약날짜 : " + dayFormat.format(ReservationDate.getTime())
                + ", 예약시간 : " + timeFormat.format(ReservationDate.getTime());
    }

	/**
	 * name sort
	 */
	@Override
	public int compareTo(Member o) {
		return this.getName().compareTo(o.getName());
	}
}

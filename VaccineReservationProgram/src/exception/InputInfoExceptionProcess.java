package exception;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import model.vo.Member;

/**
 * 
 * << 예외 처리 클래스 >>
 * @author 정예리
 *
 */
public class InputInfoExceptionProcess {

	/**
	 * 이름 유효성 검사 
	 */
	public String chkName(String name) throws ExceptionProcess {
		
		if(!Pattern.matches("[a-zA-Z가-힣]*$", name)) {
			throw new ExceptionProcess("\n※ 이름은 한글 또는 영문으로 입력해주세요.");
		}
		
		return name;
	}
	
	/**
	 * 나이 유효성 검사 
	 */
	public int chkAge(String s_age) throws ExceptionProcess {
		
		
		if(!Pattern.matches("^[0-9]*$", s_age)) {
			throw new ExceptionProcess("\n※ 나이는 숫자로 입력해 주세요.");
		}
		
		int i_age = Integer.parseInt(s_age);
		
		if(i_age<0) {
			throw new ExceptionProcess("\n※ 나이는 0이상으로 입력해주세요.");
		}
		
		return i_age;
	}

	/**
	 * 주민등록번호 유효성 검사
	 */
	public String chkSsn(String ssn, Map<String, Member> map) throws ExceptionProcess {
		
		if(!Pattern.matches("^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$", ssn)) {
			throw new ExceptionProcess("\n※ 주민등록형식으로 입력해주세요.");
		}
		
		
		Set<Map.Entry<String, Member>> mSet = map.entrySet();
		Iterator<Map.Entry<String, Member>> iter = mSet.iterator();
		
		while(iter.hasNext()) {
			Map.Entry<String, Member> m = iter.next();
			if(ssn.equals(m.getValue().getSsn())) {
				throw new ExceptionProcess("\n※ 동일한 주민등록번호가 있습니다. 중복 예약은 불가능합니다.");
			}
		}
		
		
		return ssn;
	}
	
	/**
	 * 휴대폰번호 유효성 검사
	 */
	public String chkPhoneNumber(String num) throws ExceptionProcess {
		
		if(!Pattern.matches("^01([0|1|6|7|8|9])-([0-9]{3,4})-([0-9]{4})$", num)) {
			throw new ExceptionProcess("\n※ 핸드폰 번호 형식으로 입력해주세요.");
		}
		
		return num;
	}
	
	/**
	 * 예약날짜 유효성 검사
	 * 
	 */
	public String chkRsvDate(String date) throws ExceptionProcess {
		
		if(!Pattern.matches("\\d{2}/(0[1-9]|1[012])/(0[1-9]|[12][0-9]|3[01])", date)) {
			throw new ExceptionProcess("\n※ 날짜 형식에 맞게 입력해주세요.");
		}
		
		int yy = Integer.parseInt("20" + date.substring(0, 2));
		int mm = Integer.parseInt(date.substring(3, 5));
		int dd = Integer.parseInt(date.substring(6, 8));
		
		Calendar cal = new GregorianCalendar(yy, mm-1, dd, 0, 0, 0);
		Calendar now = Calendar.getInstance();
		
		long calMillis = cal.getTimeInMillis();
		long nowMillis = now.getTimeInMillis();
		long millis = calMillis - nowMillis;
		
		double result = (double) millis/1000/60/60/24;
		
		if(Math.ceil(result) <= 0)
			throw new ExceptionProcess("\n※ 오늘 이전의 날짜는 지정할 수 없습니다.");
		
		else if(mm > now.get(Calendar.MONTH) + 1 || yy > now.get(Calendar.YEAR))
			throw new ExceptionProcess("\n※ "+(now.get(Calendar.MONTH) + 1 ) + "월 이후의 예약 스케줄은 추후 오픈됩니다.");
		
		return date;
	}
	
	/**
	 * 예약시간 유효성 검사
	 */
	public String chkRsvTime(String time) throws ExceptionProcess {
		
		if(!Pattern.matches("^([01][0-9]|2[0-3]):([0-5][0-9])$", time)) {
			throw new ExceptionProcess("\n※ 시간 형식에 맞게 입력해주세요.");
		}
		
		int HH = Integer.parseInt(time.substring(0, 2));
		String mm = time.substring(3, 5);
		
		if(!(("00".equals(mm) || "30".equals(mm)) && (HH >= 9 && HH <= 17))) {
			throw new ExceptionProcess("\n※ 예약 가능 시간 : 9시 ~ 5시 반 (30분 간격)");
		}
		
		
		return time;
	}

	/**
	 * 예약날짜 유효성 검사
	 */
	public Calendar chkRsvBooking(Calendar cal, Map<String, Member> map) {
		
		Set<Map.Entry<String, Member>> mSet = map.entrySet();
		Iterator<Map.Entry<String, Member>> iter = mSet.iterator();
		
		LocalDateTime d1 = LocalDateTime.of(
				cal.get(Calendar.YEAR), 
				cal.get(Calendar.MONTH) + 1,
				cal.get(Calendar.DATE),
				cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE));
		
		while(iter.hasNext()) {
			Map.Entry<String, Member> m = iter.next();
			LocalDateTime d2 = LocalDateTime.of(
					m.getValue().getReservationDate().get(Calendar.YEAR),
					m.getValue().getReservationDate().get(Calendar.MONTH) + 1,
					m.getValue().getReservationDate().get(Calendar.DATE),
					m.getValue().getReservationDate().get(Calendar.HOUR_OF_DAY),
					m.getValue().getReservationDate().get(Calendar.MINUTE));
			if(d2.compareTo(d1) == 0) {
				throw new ExceptionProcess("\n※ 해당 시간은 예약이 마감 되었습니다. 다른 시간을 선택해주세요.");
			}
		}
		
		return cal;
	}
}

package exception;
/**
 * << 백신 유효성 검사 클래스 >>
 * @author 정예리
 *
 */
public class VaccineInfoException {

	/**
	 * 백신 잔여개수 유효성 검사 메소드 
	 */
	public void chkVcCnt(int cnt) throws ExceptionProcess {
		
		if(cnt<1) {
			throw new ExceptionProcess("백신 잔여개수가 없습니다. 다음에 예약해주세요.");
		}
		
	}
	
}


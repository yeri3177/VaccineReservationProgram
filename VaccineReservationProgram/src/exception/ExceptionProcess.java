package exception;
/**
 * 
 * << 예외 클래스 >>
 * @author 정예리
 *
 */
public class ExceptionProcess extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ExceptionProcess() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ExceptionProcess(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public ExceptionProcess(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ExceptionProcess(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ExceptionProcess(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
}

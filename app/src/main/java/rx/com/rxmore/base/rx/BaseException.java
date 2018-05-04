package rx.com.rxmore.base.rx;


public class BaseException extends Throwable{
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BaseException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;



}

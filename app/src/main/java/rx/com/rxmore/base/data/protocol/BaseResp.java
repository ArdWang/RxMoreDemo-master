package rx.com.rxmore.base.data.protocol;


/**
    服务器返回回来的数据 统一处理
    比如我的服务器返回回来的数据为 {"code":"200","message":"登录成功"，"data":{"username":"123456",...}}
    象这种json就需要另外定义一个Base基类来处理Json数据
    T为任意对象  了解泛型
 */
public class BaseResp<T> {

    private String code;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    private String message;

    private T data;
}

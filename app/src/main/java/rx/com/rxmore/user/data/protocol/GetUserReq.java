package rx.com.rxmore.user.data.protocol;

/**
    传入的参数 手机号码 用户密码 pushid可以为空
 */
public class GetUserReq {
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushid() {
        return pushid;
    }

    public void setPushid(String pushid) {
        this.pushid = pushid;
    }

    private String phone;

    //构造方法
    public GetUserReq(String phone, String password, String pushid) {
        this.phone = phone;
        this.password = password;
        this.pushid = pushid;
    }

    private String password;
    private String pushid;
}

package rx.com.rxmore.user.data.api;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.com.rxmore.base.data.protocol.BaseResp;
import rx.com.rxmore.user.data.protocol.GetUserReq;
import rx.com.rxmore.user.model.User;


/**
   服务器采用ResultFul 风格 域名+接口地址
 */

public interface UserApi {
    @POST("user/getUser")
    Observable<BaseResp<User>> getUser(@Body GetUserReq req);
}

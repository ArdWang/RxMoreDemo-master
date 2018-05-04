package rx.com.rxmore.user.data.repository;


import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import io.reactivex.Observable;
import rx.com.rxmore.base.data.net.RetrofitFactory;
import rx.com.rxmore.base.data.repository.BaseRepository;
import rx.com.rxmore.base.rx.BaseFunction;
import rx.com.rxmore.user.data.api.UserApi;
import rx.com.rxmore.user.data.protocol.GetUserReq;
import rx.com.rxmore.user.model.User;

public class UserRepository extends BaseRepository{

    public Observable<User> getUser(String phone, String password, String pushid, LifecycleProvider<ActivityEvent> lifeProvider){
        return observeat(RetrofitFactory.getInstance()
                .create(UserApi.class)
                .getUser(new GetUserReq(phone,password,pushid)),lifeProvider)
                .map(new BaseFunction());
    }
}

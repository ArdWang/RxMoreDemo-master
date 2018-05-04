package rx.com.rxmore.user.presenter;


import rx.com.rxmore.base.presenter.BasePresenter;
import rx.com.rxmore.base.rx.BaseObserver;
import rx.com.rxmore.user.data.repository.UserRepository;
import rx.com.rxmore.user.model.User;
import rx.com.rxmore.user.presenter.view.UserView;

public class UserPresenter extends BasePresenter<UserView>{

    private UserRepository userRepository;

    public void getUser(String phone, String password, String pushid){

        userRepository = new UserRepository();

        if(!checkNetWork()){
            return;
        }

        mView.showLoading();

        userRepository.getUser(phone,password,pushid,lifeAProvider).subscribe(new BaseObserver<User>(mView){
            @Override
            public void onNext(User user) {
                mView.onGetUserResult(user);
            }
        });
    }
}

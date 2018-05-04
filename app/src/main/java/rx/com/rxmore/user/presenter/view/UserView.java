package rx.com.rxmore.user.presenter.view;


import rx.com.rxmore.base.presenter.view.BaseView;
import rx.com.rxmore.user.model.User;

public interface UserView extends BaseView{
    //获取成功
    void onGetUserResult(User user);
}

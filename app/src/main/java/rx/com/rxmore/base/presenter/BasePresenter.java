package rx.com.rxmore.base.presenter;


import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;

import rx.com.rxmore.base.common.MainApplication;
import rx.com.rxmore.base.presenter.view.BaseView;
import rx.com.rxmore.utils.NetworkUtils;

public class BasePresenter<T extends BaseView>{

    public T mView;

    public LifecycleProvider<ActivityEvent> lifeAProvider;

    public LifecycleProvider<FragmentEvent> lifeFProvider;

    /**
        检查网络是否可用
     */
    public Boolean checkNetWork(){
        if(NetworkUtils.isNetworkAvailable(MainApplication.getContext())){
            return true;
        }
        mView.onError("网络不可用");
        return false;
    }

}

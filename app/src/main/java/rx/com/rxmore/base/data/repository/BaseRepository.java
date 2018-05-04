package rx.com.rxmore.base.data.repository;

import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 配置Rxjava2 里面Observable运行线程配置以及Rxlifecycer注入
 */
public class BaseRepository {
    //Activity
    protected <T> Observable<T> observeat(Observable<T> observable,LifecycleProvider<ActivityEvent> lifecycleProvider){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }

    //Fragment
    protected <T> Observable<T> observefg(Observable<T> observable, LifecycleProvider<FragmentEvent> lifecycleProvider){
        return observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<T>bindToLifecycle())
                .observeOn(AndroidSchedulers.mainThread()); //指定在主线程中
    }



}

package rx.com.rxmore.base.rx;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import rx.com.rxmore.base.presenter.view.BaseView;

public class BaseObserver<T> implements Observer<T> {

    private BaseView baseView;

    public BaseObserver(BaseView v){
        baseView = v;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable e) {
        baseView.hideLoading();
        if(e instanceof BaseException){
            baseView.onError(e.getMessage());
        }
    }


    @Override
    public void onComplete() {
        baseView.hideLoading();
    }
}


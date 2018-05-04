package rx.com.rxmore.base.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import rx.com.rxmore.base.ext.CommonExt;
import rx.com.rxmore.base.presenter.BasePresenter;
import rx.com.rxmore.base.presenter.view.BaseView;
import rx.com.rxmore.utils.PMUtils;
import rx.com.rxmore.widgets.ProgressLoading;

public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity implements BaseView,
        View.OnClickListener{
    public P mPresenter;

    private ProgressLoading progressLoading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    protected void init(){
        setContentView(getLayoutResID());
        initView();
        mPresenter = PMUtils.getT(this,0);
        progressLoading = ProgressLoading.create(this);

    }

    protected abstract int getLayoutResID();


    protected void initView(){

    }

    @Override
    public void showLoading() {
        progressLoading.showLoading();
    }

    @Override
    public void hideLoading() {
        progressLoading.hideLoading();
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onError(String message) {
        CommonExt.toast(message);
    }
}

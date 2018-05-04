package rx.com.rxmore.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.com.rxmore.base.ext.CommonExt;
import rx.com.rxmore.base.presenter.BasePresenter;
import rx.com.rxmore.base.presenter.view.BaseView;
import rx.com.rxmore.utils.PMUtils;
import rx.com.rxmore.widgets.ProgressLoading;


/**
 * Created by rnd on 2018/3/15.
 * fragment子类必须要spuer才可以继承他
 *
 */

public abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseView,
        View.OnClickListener{

    public P mPresenter;

    private ProgressLoading progressLoading;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        init();
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    protected void init(){
        mPresenter = PMUtils.getT(this,0);
        progressLoading = ProgressLoading.create(getActivity());
    }

    @Override
    public void onClick(View v) {

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
    public void onError(String message) {
        CommonExt.toast(message);
    }
}

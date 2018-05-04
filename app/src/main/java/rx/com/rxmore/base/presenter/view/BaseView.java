package rx.com.rxmore.base.presenter.view;



public interface BaseView {
    void showLoading();
    void hideLoading();
    void onError(String message);
}

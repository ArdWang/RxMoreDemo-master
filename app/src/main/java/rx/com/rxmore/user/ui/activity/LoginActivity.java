package rx.com.rxmore.user.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import rx.com.rxmore.MainActivity;
import rx.com.rxmore.R;
import rx.com.rxmore.base.ui.activity.BaseMvpActivity;
import rx.com.rxmore.user.model.User;
import rx.com.rxmore.user.presenter.UserPresenter;
import rx.com.rxmore.user.presenter.view.UserView;


public class LoginActivity extends BaseMvpActivity<UserPresenter> implements UserView{

    private EditText mPhoneEt,mPwdEt;
    private Button mLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    protected int getLayoutResID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        mPhoneEt = findViewById(R.id.mPhoneEt);
        mPwdEt = findViewById(R.id.mPwdEt);
        mLogin = findViewById(R.id.mLogin);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUser();
            }
        });
    }

    private void initData(){
        mPresenter.mView = this;
        mPresenter.lifeAProvider = this;
    }

    private void getUser(){
        mPresenter.getUser(mPhoneEt.getText().toString(),mPwdEt.getText().toString(),"111");
    }

    @Override
    public void onGetUserResult(User user) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("user_data", user);
        startActivity(intent);
    }
}

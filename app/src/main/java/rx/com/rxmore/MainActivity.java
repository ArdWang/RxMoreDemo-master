package rx.com.rxmore;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import rx.com.rxmore.user.model.User;

public class MainActivity extends AppCompatActivity {
    private TextView mUserTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUserTv = findViewById(R.id.mUserTv);
        getData();
    }

    @SuppressLint("SetTextI18n")
    private void getData(){
        User user = (User) getIntent().getSerializableExtra("user_data");
        mUserTv.setText(user.getPhone()+"/"+user.getPassword()+"/"+user.getSex());
    }

}

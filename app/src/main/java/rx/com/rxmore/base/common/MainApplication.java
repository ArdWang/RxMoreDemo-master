package rx.com.rxmore.base.common;

import android.app.Application;
import android.content.Context;


public class MainApplication extends Application{

    private static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    //获取全局变量的Context
    public static Context getContext(){
        return context;
    }
}

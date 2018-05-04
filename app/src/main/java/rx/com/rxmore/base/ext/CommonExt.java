package rx.com.rxmore.base.ext;



import android.view.View;
import android.widget.Toast;
import rx.com.rxmore.base.common.MainApplication;

/**
 * Created by rnd on 2018/4/9.
 * 扩展一些方法
 */

public class CommonExt {

    /**
     * 弹出消息框
     * @param msg
     */
    public static void toast(String msg){
        Toast.makeText(MainApplication.getContext(),msg,Toast.LENGTH_SHORT).show();
    }


    /**
     扩展视图可见性
     */
    public static void setVisible (View view,Boolean visible){
        if(visible){
            view.setVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.GONE);
        }

    }

}

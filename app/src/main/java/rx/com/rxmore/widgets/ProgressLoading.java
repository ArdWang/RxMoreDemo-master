package rx.com.rxmore.widgets;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.ImageView;
import rx.com.rxmore.R;


/**
 * Created by rnd on 2018/4/8.
 * 自定义加载框
 */

public class ProgressLoading extends Dialog{

    private static AnimationDrawable animationDrawable;

    public ProgressLoading(@NonNull Context context) {
        super(context);
    }

    public ProgressLoading(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }


    /**
     * 创建Dialog
     * @param context
     * @return
     */
    public static ProgressLoading create(Context context){
        ProgressLoading mDialog = new ProgressLoading(context, R.style.LightProgressDialog);
        mDialog.setContentView(R.layout.progress_dialog);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(false);
        // 设置居中
        mDialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        mDialog.getWindow().setAttributes(lp);
        ImageView loadingView = mDialog.findViewById(R.id.iv_loading);
        animationDrawable = (AnimationDrawable) loadingView.getBackground();
        return mDialog;
    }


    /**
     * 显示加载对话框,动画开始
     */
    public void showLoading(){
        super.show();
        animationDrawable.start();
    }

    /**
     * 隐藏加载对话框,动画停止
     */
    public void hideLoading(){
        super.dismiss();
        animationDrawable.stop();
    }

}

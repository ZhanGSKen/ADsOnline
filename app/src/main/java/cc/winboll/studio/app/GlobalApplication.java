package cc.winboll.studio.app;

import android.app.Application;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.WhiteToastStyle;
import android.view.Gravity;

public class GlobalApplication extends Application {

    public static final String TAG = "GlobalApplication";

    // 应用调试标志
    volatile static boolean _mIsDebug = true;

    //
    // 读取调试标志
    //
    public static void setIsDebug(boolean isDebug) {
        _mIsDebug = isDebug;
    }

    //
    // 设置调试标志
    //
    public static boolean isDebug() {
        return _mIsDebug;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // 应用环境初始化
        //
        CrashHandler.init(this);
        LogUtils.init(this);
        // 初始化 Toast 框架
        ToastUtils.init(this);
        // 设置 Toast 布局样式
        ToastUtils.setView(R.layout.view_toast);
        //ToastUtils.setStyle(new WhiteToastStyle());
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 200);
    }
}

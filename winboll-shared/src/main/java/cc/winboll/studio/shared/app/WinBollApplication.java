package cc.winboll.studio.shared.app;

import android.app.Application;
import android.view.Gravity;
import cc.winboll.studio.R;
import cc.winboll.studio.shared.log.LogUtils;
import com.hjq.toast.ToastUtils;

public class WinBollApplication extends Application {

    public static final String TAG = "WinBollApplication";

    public static enum WinBollUI_TYPE {
        Aplication, // 退出应用后，保持最近任务栏任务记录主窗口
        Service // 退出应用后，清理所有最近任务栏任务记录窗口
    };

    // 应用调试标志
    volatile static boolean _mIsDebug = true;
    // 应用调试标志
    volatile static WinBollUI_TYPE _mWinBollUI_TYPE = WinBollUI_TYPE.Service;

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
    
    //
    // 设置 WinBoll 应用 UI 类型
    //
    public static void setWinBollUI_TYPE(WinBollUI_TYPE mWinBollUI_TYPE) {
        _mWinBollUI_TYPE = mWinBollUI_TYPE;
    }

    //
    // 获取 WinBoll 应用 UI 类型
    //
    public static WinBollUI_TYPE getWinBollUI_TYPE() {
        return _mWinBollUI_TYPE;
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
        
        // 设置默认 WinBoll 应用 UI 类型
        setWinBollUI_TYPE(WinBollUI_TYPE.Service);
        //ToastUtils.show("WinBollUI_TYPE " + getWinBollUI_TYPE());
    }
}

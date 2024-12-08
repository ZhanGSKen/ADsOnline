package cc.winboll.studio.shared.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import cc.winboll.studio.shared.util.ServiceUtils;
import android.content.Context;
import android.content.ComponentName;
import android.content.ServiceConnection;
import com.hjq.toast.ToastUtils;
import cc.winboll.studio.shared.log.LogUtils;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/12/08 19:42:07
 * @Describe WinBoll 服务进程
 */
public class WinBollService extends Service {

    public static final String TAG = "WinBollService";

    WinBollServiceBean mWinBollServiceBean;
    MyServiceConnection mMyServiceConnection;
    static volatile boolean _isServiceAlive;

    public static void setIsServiceAlive(boolean isServiceAlive) {
        _isServiceAlive = isServiceAlive;
    }

    public static boolean isServiceAlive() {
        //return true;
        return _isServiceAlive;
    }

    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWinBollServiceBean = WinBollServiceBean.loadWinBollServiceBean(this);
        if (mMyServiceConnection == null) {
            mMyServiceConnection = new MyServiceConnection();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        run();
        return mWinBollServiceBean.isEnable() ? Service.START_STICKY: super.onStartCommand(intent, flags, startId);
    }

    private void run() {
        mWinBollServiceBean = WinBollServiceBean.loadWinBollServiceBean(this);
        if (mWinBollServiceBean.isEnable()) {
            if (_isServiceAlive == false) {
                // 设置运行状态
                _isServiceAlive = true;

                // 唤醒守护进程
                wakeupAndBindAssistant();
                
                while(mWinBollServiceBean.isEnable()) {
                    // 显示运行状态
                    ToastUtils.show(TAG + " is running.");
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
                    }
                    mWinBollServiceBean = WinBollServiceBean.loadWinBollServiceBean(this);
                }
            }
        }
    }


    // 唤醒和绑定守护进程
    //
    void wakeupAndBindAssistant() {
        if (ServiceUtils.isServiceAlive(getApplicationContext(), AssistantService.class.getName()) == false) {
            startService(new Intent(WinBollService.this, AssistantService.class));
            //LogUtils.d(TAG, "call wakeupAndBindAssistant() : Binding... AssistantService");
            bindService(new Intent(WinBollService.this, AssistantService.class), mMyServiceConnection, Context.BIND_IMPORTANT);
        }
    }

    // 主进程与守护进程连接时需要用到此类
    //
    private class MyServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {            
            mWinBollServiceBean = WinBollServiceBean.loadWinBollServiceBean(WinBollService.this);
            if (mWinBollServiceBean.isEnable()) {
                // 唤醒守护进程
                wakeupAndBindAssistant();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 显示运行状态
        ToastUtils.show(TAG + " is start.");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    void setWinBollServiceEnableStatus(boolean isEnable) {
        WinBollServiceBean bean = WinBollServiceBean.loadWinBollServiceBean(this);
        bean.setIsEnable(isEnable);
        WinBollServiceBean.saveWinBollServiceBean(this, bean);
    }

    boolean getWinBollServiceEnableStatus(Context context) {
        mWinBollServiceBean = WinBollServiceBean.loadWinBollServiceBean(context);
        return mWinBollServiceBean.isEnable();
    }
}

package cc.winboll.studio.shared.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import cc.winboll.studio.shared.log.LogUtils;
import cc.winboll.studio.shared.util.ServiceUtils;
import com.hjq.toast.ToastUtils;
import java.io.FileDescriptor;
import android.graphics.drawable.Drawable;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/12/08 19:42:07
 * @Describe WinBoll 服务进程
 */
public class WinBollService extends Service implements IWinBollServiceBinder {

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
    public WinBollService getService() {
        return WinBollService.this;
    }

    @Override
    public Drawable getCurrentStatusIconDrawable() {
        return _isServiceAlive ?
            getDrawable(EWUIStatusIconDrawable.getIconDrawableId(EWUIStatusIconDrawable.NORMAL))
            : getDrawable(EWUIStatusIconDrawable.getIconDrawableId(EWUIStatusIconDrawable.NEWS));
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
        //run();
        ToastUtils.show("onStartCommand");
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

                while (mWinBollServiceBean.isEnable()) {
                    // 显示运行状态
                    ToastUtils.show(TAG + " is running.");
                    try {
                        Thread.sleep(2 * 1000);
                    } catch (InterruptedException e) {
                        LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
                    }
                    mWinBollServiceBean = WinBollServiceBean.loadWinBollServiceBean(this);
                }

                // 服务退出
                _isServiceAlive = false;
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
        ToastUtils.show("onDestroy");
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

    /*public interface OnServiceStatusChangeListener {
        void onServerStatusChange(boolean isServiceAlive);
    }

    public void setOnServerStatusChangeListener(OnServiceStatusChangeListener l) {
        mOnServerStatusChangeListener = l;
    }*/
}

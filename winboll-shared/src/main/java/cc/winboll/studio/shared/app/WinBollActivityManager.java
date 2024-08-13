package cc.winboll.studio.shared.app;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/08/12 16:09:15
 * @Describe 应用活动窗口管理器
 * 参考 ：
 * android 类似微信小程序多任务窗口 及 设置 TaskDescription 修改 icon 和 label
 * https://blog.csdn.net/qq_29364417/article/details/109379915?app_version=6.4.2&code=app_1562916241&csdn_share_tail=%7B%22type%22%3A%22blog%22%2C%22rType%22%3A%22article%22%2C%22rId%22%3A%22109379915%22%2C%22source%22%3A%22weixin_38986226%22%7D&uLinkId=usr1mkqgl919blen&utm_source=app
 */
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.TaskStackBuilder;
import cc.winboll.studio.shared.log.LogUtils;
import com.hjq.toast.ToastUtils;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WinBollActivityManager {

    public static final String TAG = "WinBollActivityManager";

    Context mContext;
    static WinBollActivityManager _mWinBollActivityManager;
    static Map<String, WinBollActivity> _mWinBollActivityArrayMap;

    public WinBollActivityManager(Context context) {
        mContext = context;
        LogUtils.d(TAG, "WinBollActivityManager()");
        _mWinBollActivityArrayMap = new HashMap<String, WinBollActivity>();
    }

    public static synchronized WinBollActivityManager getInstance(Context context) {
        LogUtils.d(TAG, "getInstance");
        if (_mWinBollActivityManager == null) {
            LogUtils.d(TAG, "_mWinBollActivityManager == null");
            _mWinBollActivityManager = new WinBollActivityManager(context);
        }
        return _mWinBollActivityManager;
    }

    /**
     * 把Activity添加到管理中
     */
    public <T extends WinBollActivity> void add(T activity) {
        LogUtils.d(TAG, "add activity.getTag() " + activity.getTag());
        //ToastUtils.show("add activity.getTag() " + activity.getTag());
        if (activity != null) {
            LogUtils.d(TAG, "activity != null");
            //如果是 BaseActivity 则缓存起来
            String tag = activity.getTag();
            //存入内存中
            LogUtils.d(TAG, "_mWinBollActivityManager.put(tag, activity); " + tag);
            //ToastUtils.show("_mWinBollActivityManager.put(tag, activity); " + tag);
            _mWinBollActivityArrayMap.put(tag, activity);
            //printAvtivityListInfo();
        }
    }

    /**
     * 判断 tag绑定的 MyActivity是否存在
     */
    public boolean isActive(String tag) {
        //ToastUtils.show("isActive tag " + tag);
        LogUtils.d(TAG, "isActive " + tag);
        //printAvtivityListInfo();
        WinBollActivity activity = _mWinBollActivityArrayMap.get(tag);
        if (activity != null) {
            LogUtils.d(TAG, "activity != null tag " + tag);
            //ToastUtils.show("activity != null tag " + tag);
            //判断是否为 BaseActivity,如果已经销毁，则移除
            if (activity.isFinishing() || activity.isDestroyed()) {
                _mWinBollActivityArrayMap.remove(tag);
                LogUtils.d(TAG, "_mWinBollActivityManager.remove(tag);");
                return false;
            } else {
                LogUtils.d(TAG, activity.TAG + " Exist.");
                return true;
            }
        } else {
            LogUtils.d(TAG, "activity is none.");
            return false;
        }
    }

    /**
     * 找到tag 绑定的 BaseActivity ，通过 getTaskId() 移动到前台
     */
    public <T extends WinBollActivity> void resumeActivity(Context context, String tag) {
        LogUtils.d(TAG, "resumeActivty");
        T activity = (T)_mWinBollActivityArrayMap.get(tag);
        LogUtils.d(TAG, "activity " + activity.getTag());
        if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
            ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
            //返回启动它的根任务（home 或者 MainActivity）
            Intent intent = new Intent(context, activity.getClass());
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addNextIntentWithParentStack(intent);
            stackBuilder.startActivities();
            //moveTaskToFront(YourTaskId, 0);
            LogUtils.d(TAG, "am.moveTaskToFront");
            am.moveTaskToFront(activity.getTaskId(), ActivityManager.MOVE_TASK_NO_USER_ACTION);
        }
    }


    /**
     * 结束所有 Activity
     */
    public void finishAll() {
        try {
            Iterator<Map.Entry<String, WinBollActivity>> iterator = _mWinBollActivityArrayMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, WinBollActivity> entry = iterator.next();
                WinBollActivity activity = entry.getValue();
                if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                    if (WinBollApplication.getWinBollUI_TYPE() == WinBollApplication.WinBollUI_TYPE.Service) {
                        // 结束窗口和最近任务栏, 建议前台服务类应用使用，可以方便用户再次调用 UI 操作。
                        activity.finishAndRemoveTask();
                    } else if (WinBollApplication.getWinBollUI_TYPE() == WinBollApplication.WinBollUI_TYPE.Aplication) {
                        // 结束窗口保留最近任务栏，建议前台服务类应用使用，可以保持应用的系统自觉性。
                        activity.finish();
                    } else {
                        ToastUtils.show("WinBollApplication.WinBollUI_TYPE error.");
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
        }
    }

    /**
     * 结束指定Activity
     */
    public <T extends WinBollActivity> void finish(T activity) {
        try {
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                //根据tag 移除 MyActivity
                String tag= activity.getTag();
                _mWinBollActivityArrayMap.remove(tag);
                //ToastUtils.show("remove");
                //ToastUtils.show("_mWinBollActivityArrayMap.size() " + Integer.toString(_mWinBollActivityArrayMap.size()));
                Iterator<Map.Entry<String, WinBollActivity>> iterator = _mWinBollActivityArrayMap.entrySet().iterator();
                if (iterator.hasNext()) {
                    Map.Entry<String, WinBollActivity> entry = iterator.next();
                    //sb.append("\nKey: " + entry.getKey() + ", \nValue: " + entry.getValue().getTag());
                    //ToastUtils.show("\nKey: " + entry.getKey() + ", Value: " + entry.getValue().getTag());
                    resumeActivity(mContext, entry.getValue().getTag());
                }
                activity.finish();
            }
        } catch (Exception e) {
            LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
        }
    }

    public static void printAvtivityListInfo() {
        //LogUtils.d(TAG, "printAvtivityListInfo");
        if (!_mWinBollActivityArrayMap.isEmpty()) {
            StringBuilder sb = new StringBuilder("Map entries : " + Integer.toString(_mWinBollActivityArrayMap.size()));
            Iterator<Map.Entry<String, WinBollActivity>> iterator = _mWinBollActivityArrayMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, WinBollActivity> entry = iterator.next();
                sb.append("\nKey: " + entry.getKey() + ", \nValue: " + entry.getValue().getTag());
                //ToastUtils.show("\nKey: " + entry.getKey() + ", Value: " + entry.getValue().getTag());
            }
            sb.append("\nMap entries end.");
            LogUtils.d(TAG, sb.toString());
            //ToastUtils.show(sb.toString());
        } else {
            LogUtils.d(TAG, "The map is empty.");
        }
    }


}

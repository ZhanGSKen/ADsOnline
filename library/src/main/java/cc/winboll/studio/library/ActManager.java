package cc.winboll.studio.library;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ActManager {

    public static final String TAG = "ActManager";

    static ActManager _mActManager;
    static Map<String, BaseActivity> _mArrayMap;

    public ActManager() {
        LogUtils.d(TAG, "ActManager()");
        _mArrayMap = new HashMap<String, BaseActivity>();
    }

    public static synchronized ActManager getInstance() {
        LogUtils.d(TAG, "getInstance");
        if (_mActManager == null) {
            LogUtils.d(TAG, "_mActManager == null");
            _mActManager = new ActManager();
        }
        return _mActManager;
    }

    /**
     * 把Activity添加到管理中
     */
    public <T extends BaseActivity> void add(T activity) {
        LogUtils.d(TAG, "add");
        if (activity != null) {
            //如果是 BaseActivity 则缓存起来
            if (activity instanceof BaseActivity) {
                String tag = activity.getTag();
                //存入内存中
                LogUtils.d(TAG, "_mArrayMap.put(tag, activity); " + tag);
                _mArrayMap.put(tag, activity);
            }
        }
    }

    /**
     * 判断 tag绑定的 MyActivity是否存在
     */
    public boolean isActive(String tag) {
        LogUtils.d(TAG, "isActive");
        printAvtivityListInfo();
//        for (int i = 0; i < _mArrayMap.size(); i++) {
//            LogUtils.d(TAG, _mArrayMap.toString());
//        }
        BaseActivity activity = _mArrayMap.get(tag);
        if (activity != null) {
            //判断是否为 BaseActivity,如果已经销毁，则移除
            if (activity.isFinishing() || activity.isDestroyed()) {
                _mArrayMap.remove(tag);
                LogUtils.d(TAG, "mArrayMap.remove(tag);");
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
    public <T extends BaseActivity> void resumeActivity(Context context, String tag) {
        LogUtils.d(TAG, "resumeActivty");
        T activity = (T)_mArrayMap.get(tag);
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
     * 结束指定Activity
     */
    public <T extends BaseActivity> void finish(T activity) {
        try {
            if (activity != null && !activity.isFinishing() && !activity.isDestroyed()) {
                //根据tag 移除 MyActivity
                if (activity instanceof BaseActivity) {
                    String tag= activity.TAG;
                    _mArrayMap.remove(tag);
                }
                activity.finish();
            }
        } catch (Exception e) {
            LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
        }
    }

    public static void printAvtivityListInfo() {
        // 假设我们有一个名为myMap的Map实例
        //Map<String, String> _mArrayMap = 
        // 这里可以根据实际情况填充map的内容
        // 例如: Map<String, String> _mArrayMap = new HashMap<>();

        if (!_mArrayMap.isEmpty()) {
            LogUtils.d(TAG, "Map entries:");
            Iterator<Map.Entry<String, BaseActivity>> iterator = _mArrayMap.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, BaseActivity> entry = iterator.next();
                LogUtils.d(TAG, "Key: " + entry.getKey() + ", Value: " + entry.getValue().getTag());
            }
            LogUtils.d(TAG, "Map entries end.");
        } else {
            LogUtils.d(TAG, "The map is empty.");
        }
    }


}

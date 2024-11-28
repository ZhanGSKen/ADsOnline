package cc.winboll.studio.shared.app;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/08/12 14:32:08
 * @Describe WinBoll 活动窗口基础类
 */
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cc.winboll.studio.R;
import cc.winboll.studio.shared.log.LogActivity;
import cc.winboll.studio.shared.log.LogUtils;
import com.hjq.toast.ToastUtils;
import android.view.View;
import android.view.MenuInflater;
import android.app.Application;
import android.content.Context;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.ActionBar;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.app.Activity;
import android.content.IntentSender;
import androidx.appcompat.view.ActionMode.Callback;
import androidx.appcompat.view.ActionMode;
import android.content.ComponentName;
import android.content.res.Resources.Theme;
import android.content.res.Resources;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.content.SharedPreferences;

abstract public class WinBollActivity extends AppCompatActivity {

    public static final String TAG = "WinBollActivity";

    public static final int REQUEST_LOG_ACTIVITY = 0;

    Toolbar mToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mToolBar = initToolBar();
        setSupportActionBar(mToolBar);
        if (isEnableDisplayHomeAsUp() && mToolBar != null) {
            // 显示后退按钮
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // 缓存当前 activity
        LogUtils.d(TAG, "ActManager.getInstance().add(this);");
        //ToastUtils.show("getTag() " + getTag());
        WinBollActivityManager.getInstance(this).add(this);
        //WinBollActivityManager.getInstance().printAvtivityListInfo();
        //ToastUtils.show("WinBollUI_TYPE " + WinBollApplication.getWinBollUI_TYPE());
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public <T extends View> T findViewById(int id) {
        return super.findViewById(id);
    }

    @Override
    public MenuInflater getMenuInflater() {
        return super.getMenuInflater();
    }
    
    public WinBollActivity getActivity() {
        return this;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public <T extends FragmentManager> T getWinBollActivitySupportFragmentManager() {
        return (T)super.getSupportFragmentManager();
    }

    @Override
    public ActionBar getSupportActionBar() {
        return super.getSupportActionBar();
    }

    @Override
    public void setSupportActionBar(Toolbar toolbar) {
        super.setSupportActionBar(toolbar);
    }

    @Override
    public void setActionBar(android.widget.Toolbar toolbar) {
        super.setActionBar(toolbar);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void setTitle(int titleId) {
        super.setTitle(titleId);
    }

    @Override
    public void setTheme(Resources.Theme theme) {
        super.setTheme(theme);
    }

    @Override
    public void setTheme(int resid) {
        super.setTheme(resid);
    }

    @Override
    public void setTitleColor(int textColor) {
        super.setTitleColor(textColor);
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
    }

    @Override
    public void recreate() {
        super.recreate();
    }

    @Override
    public SharedPreferences getSharedPreferences(String name, int mode) {
        return super.getSharedPreferences(name, mode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean releaseInstance() {
        return super.releaseInstance();
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, int flags) {
        return super.registerReceiver(receiver, filter, flags);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler) {
        return super.registerReceiver(receiver, filter, broadcastPermission, scheduler);
    }

    @Override
    public Intent registerReceiver(BroadcastReceiver receiver, IntentFilter filter, String broadcastPermission, Handler scheduler, int flags) {
        return super.registerReceiver(receiver, filter, broadcastPermission, scheduler, flags);
    }

    @Override
    public void startActivities(Intent[] intents) {
        super.startActivities(intents);
    }

    @Override
    public void startActivityFromFragment(Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
    }

    @Override
    public void startActivityFromFragment(android.app.Fragment fragment, Intent intent, int requestCode, Bundle options) {
        super.startActivityFromFragment(fragment, intent, requestCode, options);
    }

    @Override
    public void startActivityFromChild(Activity child, Intent intent, int requestCode, Bundle options) {
        super.startActivityFromChild(child, intent, requestCode, options);
    }

    @Override
    public void startActivities(Intent[] intents, Bundle options) {
        super.startActivities(intents, options);
    }

    @Override
    public boolean startActivityIfNeeded(Intent intent, int requestCode) {
        return super.startActivityIfNeeded(intent, requestCode);
    }

    @Override
    public boolean startActivityIfNeeded(Intent intent, int requestCode, Bundle options) {
        return super.startActivityIfNeeded(intent, requestCode, options);
    }

    @Override
    public void startActivityFromChild(Activity child, Intent intent, int requestCode) {
        super.startActivityFromChild(child, intent, requestCode);
    }

    @Override
    public ComponentName startService(Intent service) {
        return super.startService(service);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode, Bundle options) {
        super.startActivityForResult(intent, requestCode, options);
    }

    @Override
    public void startActivityFromFragment(android.app.Fragment fragment, Intent intent, int requestCode) {
        super.startActivityFromFragment(fragment, intent, requestCode);
    }

    @Override
    public void startActivityFromFragment(Fragment requestIndex, Intent fragment, int intent, Bundle requestCode) {
        super.startActivityFromFragment(requestIndex, fragment, intent, requestCode);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        super.startActivity(intent, options);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isAddWinBollToolBar()) {
            getMenuInflater().inflate(R.menu.toolbar_winboll_shared_main, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.d(TAG, "onOptionsItemSelected");
        if (item.getItemId() == R.id.item_log) {
            //WinBollActivityManager.getInstance().printAvtivityListInfo();
            try {
                WinBollActivity clazzActivity = LogActivity.class.newInstance();
                String tag = clazzActivity.getTag();
                //ToastUtils.show("String tag = clazzActivity.getTag(); tag " + tag);
                LogUtils.d(TAG, "String tag = clazzActivity.getTag(); tag " + tag);
                //ToastUtils.show("String tag = clazzActivity.getTag(); tag " + tag);
                Intent intent = new Intent(this, LogActivity.class);
                startWinBollActivity(intent, tag);
            } catch (IllegalAccessException e) {
                LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
            } catch (InstantiationException e) {
                LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
            }
        } else if (item.getItemId() == R.id.item_exit) {
            WinBollActivityManager.getInstance(this).finishAll();
            
        }
        // else if (item.getItemId() == android.R.id.home) {
        // 回到主窗口速度缓慢，方法备用。现在用 WinBollActivityManager resumeActivity 和 finish 方法管理。
        // _mMainWinBollActivity 是 WinBollActivity 的静态属性
        // onCreate 函数下 _mMainWinBollActivity 为空时就用 _mMainWinBollActivity = this 赋值。
            //startWinBollActivity(new Intent(_mMainWinBollActivity, _mMainWinBollActivity.getClass()), _mMainWinBollActivity.getTag(), _mMainWinBollActivity);
        //}
        return super.onOptionsItemSelected(item);
    }

    protected <T extends WinBollActivity> void startWinBollActivity(Intent intent, String tag) {
        startWinBollActivity(intent, tag, null);
    }

    public void getInstanse() {
        startWinBollActivity(new Intent(), getTag(), null);
    }
    //
    // activity: 为 null 时，
    // intent.putExtra 函数 "tag" 参数为 tag
    // activity: 不为 null 时，
    // intent.putExtra 函数 "tag" 参数为 activity.getTag()
    //
    protected <T extends WinBollActivity> void startWinBollActivity(Intent intent, String tag, T activity) {
        LogUtils.d(TAG, "startWinBollActivityForResult tag " + tag);
        //ToastUtils.show("startWinBollActivityForResult tag " + tag);
        //打开多任务窗口 flags
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
        intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        if (activity != null) {
            intent.putExtra("tag", activity.getTag());
        } else {
            intent.putExtra("tag", tag);
        }
        //ToastUtils.show("super.startActivityForResult(intent, requestCode); tag " + tag);
        LogUtils.d(TAG, "startActivityForResult(intent, requestCode);" + tag);
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent) {
        //绑定唯一标识 tag，存在 则根据 taskId 移动到前台
        String tag = intent.getStringExtra("tag");
        //ToastUtils.show("startActivityForResult tag " + tag);
        WinBollActivityManager.getInstance(this).printAvtivityListInfo();
        if (WinBollActivityManager.getInstance(this).isActive(tag)) {
            //ToastUtils.show("resumeActivity");
            LogUtils.d(TAG, "resumeActivity");
            WinBollActivityManager.getInstance(this).resumeActivity(this, tag);
        } else {
            //ToastUtils.show("super.startActivity(intent);");
            super.startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int targetFragment, Intent data) {
        LogUtils.d(TAG, "onActivityResult");
        switch (requestCode) {
            case REQUEST_LOG_ACTIVITY : {
                    LogUtils.d(TAG, "REQUEST_LOG_ACTIVITY");
                    break;
                }
            default : {
                    super.onActivityResult(requestCode, targetFragment, data);
                }
        }
    }

    abstract public String getTag();
    abstract protected Toolbar initToolBar();
    abstract protected boolean isEnableDisplayHomeAsUp();
    abstract protected boolean isAddWinBollToolBar();
}

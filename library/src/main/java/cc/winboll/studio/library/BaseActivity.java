package cc.winboll.studio.library;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/08/12 14:32:08
 * @Describe WinBoll 活动窗口基础类
 */
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import cc.winboll.studio.R;

abstract public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    public static final int REQUEST_LOG_ACTIVITY = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Toolbar toolbar = initToolBar();
        if(toolbar != null) {
            setSupportActionBar(toolbar);
        }
        
        // 缓存当前 activity
        LogUtils.d(TAG, "ActManager.getInstance().add(this);");
        LogUtils.d(TAG, "getTag() " + getTag());
        ActManager.getInstance().add(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_library, menu);
        return super.onCreateOptionsMenu(menu);

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_log) {
            //绑定唯一标识 tag，存在 则根据 taskId 移动到前台
            Context context = this;
            String tag = LogActivity.TAG;
            LogUtils.d(TAG, "tag " + tag);
            if (ActManager.getInstance().isActive(tag)) {
                LogUtils.d(TAG, "ActManager.getInstance().resumeActivty(tag);");
                ActManager.getInstance().resumeActivity(this, tag);
            } else {
                Intent intent = new Intent(context, LogActivity.class);
                //打开多任务窗口 flags
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                intent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("tag", tag);
                LogUtils.d(TAG, "context.startActivity(intent);");
                context.startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    abstract protected Toolbar initToolBar();
    abstract protected String getTag();
}

package cc.winboll.studio.library;

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

abstract public class BaseActivity extends AppCompatActivity {

    public static final String TAG = "BaseActivity";

    public static final int REQUEST_LOG_ACTIVITY = 0;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSupportActionBar(initToolBar());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_library, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_log) {
            Intent intent = new Intent(this, LogActivity.class);
            startActivityForResult(intent, REQUEST_LOG_ACTIVITY);
        }

        return super.onOptionsItemSelected(item);
    }

    abstract protected Toolbar initToolBar();
}

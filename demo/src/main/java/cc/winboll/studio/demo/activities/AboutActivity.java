package cc.winboll.studio.demo.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;
import cc.winboll.studio.demo.R;
import cc.winboll.studio.shared.app.WinBollActivity;
import com.hjq.toast.ToastUtils;
import cc.winboll.studio.shared.app.WinBollActivityManager;
import cc.winboll.studio.demo.MainActivity;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/07/14 13:20:33
 * @Describe AboutFragment Test
 */
final public class AboutActivity extends WinBollActivity {

    public static final String TAG = "AboutFragmentActivity";

    @Override
    public String getTag() {
        return TAG;
    }

    @Override
    protected boolean isEnableDisplayHomeAsUp() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected boolean isAddWinBollToolBar() {
        return true;
    }

    @Override
    protected Toolbar initToolBar() {
        return findViewById(R.id.activityaboutToolbar1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_about, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_help) {
            ToastUtils.show("R.id.item_help");
        } else if (item.getItemId() == android.R.id.home) {
            WinBollActivityManager.getInstance(getApplicationContext()).finish(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

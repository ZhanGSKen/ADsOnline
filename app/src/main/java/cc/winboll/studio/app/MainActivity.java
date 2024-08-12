package cc.winboll.studio.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import cc.winboll.studio.app.activities.AboutActivity;
import cc.winboll.studio.app.activities.BaseActivity;
import androidx.appcompat.widget.Toolbar;
import com.hjq.toast.ToastUtils;
import android.os.Handler;
import android.os.Looper;

public class MainActivity extends BaseActivity {

	public static final String TAG = "MainActivity";

    static final int REQUEST_AOUT_ACTIVITY = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected Toolbar initToolBar() {
        return findViewById(R.id.activitymainToolbar1);
    }

    
    public void onTestAPPCrashHandler(View view) {
        ToastUtils.show("onTestAPPCrashHandler in 3 seconds");
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){

                @Override
                public void run() {
                    for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
                        getString(i);
                    }
                }
            }, 3000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_main, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_about) {
            Intent intent = new Intent(this, AboutActivity.class);
            startActivityForResult(intent, REQUEST_AOUT_ACTIVITY);
        }
        
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case REQUEST_AOUT_ACTIVITY : {
                    ToastUtils.show("REQUEST_LOGACTIVITY");
                    break;
                }
            default : {
                    super.onActivityResult(requestCode, resultCode, data);
                }
        }
    }
}

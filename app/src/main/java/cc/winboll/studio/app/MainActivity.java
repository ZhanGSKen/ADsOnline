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
        Toast.makeText(getApplication(), "onTestAPPCrashHandler in 3 seconds", Toast.LENGTH_SHORT).show();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {}
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
            getString(i);
        }
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
                    Toast.makeText(getApplication(), "REQUEST_LOGACTIVITY", Toast.LENGTH_SHORT).show();
                    break;
                }
            default : {
                    super.onActivityResult(requestCode, resultCode, data);
                }
        }
    }
}

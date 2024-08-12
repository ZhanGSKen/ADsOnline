package cc.winboll.studio.library;

import android.app.Activity;
import android.os.Bundle;
import cc.winboll.studio.R;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/08/12 15:07:58
 * @Describe WinBoll 应用日志窗口
 */
public class LogActivity extends Activity {

    public static final String TAG = "LogActivity";

    LogView mLogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        mLogView = findViewById(R.id.logview);
        mLogView.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLogView.start();
    }
}

package cc.winboll.studio.library;

import android.app.Activity;
import android.os.Bundle;
import cc.winboll.studio.R;
import androidx.appcompat.widget.Toolbar;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/08/12 15:07:58
 * @Describe WinBoll 应用日志窗口
 */
public class LogActivity extends BaseActivity {

    

    public static final String TAG = "LogActivity";

    LogView mLogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        mLogView = findViewById(R.id.logview);
        mLogView.start();
        
    }

    @Override
    protected void onResume() {
        LogUtils.d(TAG, "onResume");
        super.onResume();
        mLogView.start();
    }
    
    @Override
    protected Toolbar initToolBar() {
        LogUtils.d(TAG, "initToolBar");
        return null;
    }

    @Override
    protected String getTag() {
        LogUtils.d(TAG, "getTag");
        return TAG;
    }
}

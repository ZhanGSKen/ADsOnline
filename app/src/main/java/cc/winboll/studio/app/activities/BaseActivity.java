package cc.winboll.studio.app.activities;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/08/12 12:58:10
 * @Describe WinBoll 活动窗口基础类
 */
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

abstract public class BaseActivity extends AppCompatActivity {
    
    public static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setSupportActionBar(initToolBar());
    }
    
    abstract protected Toolbar initToolBar();
}

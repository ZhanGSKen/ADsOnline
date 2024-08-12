package cc.winboll.studio.app.activities;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import cc.winboll.studio.app.R;
import cc.winboll.studio.library.BaseActivity;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/07/14 13:20:33
 * @Describe AboutFragment Test
 */
final public class AboutActivity extends BaseActivity {

    public static final String TAG = "AboutFragmentActivity";

    @Override
    protected String getTag()
    {
        return TAG;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    @Override
    protected Toolbar initToolBar() {
        return findViewById(R.id.activityaboutToolbar1);
    }
}

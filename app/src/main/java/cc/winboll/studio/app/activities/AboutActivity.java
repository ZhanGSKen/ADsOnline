package cc.winboll.studio.app.activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import cc.winboll.studio.app.R;
import androidx.appcompat.widget.Toolbar;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/07/14 13:20:33
 * @Describe AboutFragment Test
 */
final public class AboutActivity extends BaseActivity {

    public static final String TAG = "AboutFragmentActivity";

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

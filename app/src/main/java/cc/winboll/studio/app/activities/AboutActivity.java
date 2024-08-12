package cc.winboll.studio.app.activities;

import android.app.Activity;
import android.os.Bundle;
import cc.winboll.studio.app.R;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/07/14 13:20:33
 * @Describe AboutFragment Test
 */
final public class AboutActivity extends Activity {

    public static final String TAG = "AboutFragmentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

}

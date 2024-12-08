package cc.winboll.studio.app.activities;

import android.app.assist.AssistStructure;
import android.os.Bundle;
import android.service.autofill.Dataset;
import android.service.autofill.FillCallback;
import android.service.autofill.FillContext;
import android.service.autofill.FillRequest;
import android.service.autofill.FillResponse;
import android.view.Menu;
import android.view.MenuItem;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.widget.EditText;
import android.widget.RemoteViews;
import androidx.appcompat.widget.Toolbar;
import androidx.core.os.CancellationSignal;
import cc.winboll.studio.app.R;
import cc.winboll.studio.shared.app.WinBollActivity;
import cc.winboll.studio.shared.app.WinBollActivityManager;
import com.hjq.toast.ToastUtils;
import java.util.List;
import cc.winboll.studio.shared.view.AboutView;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/07/14 13:20:33
 * @Describe AboutFragment Test
 */
final public class AboutActivity extends WinBollActivity {

    public static final String TAG = "AboutActivity";

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
        /*AboutView aboutView = findViewById(R.id.activityaboutAboutView1);
        aboutView.setOnRequestDevUserInfoAutofillListener(new AboutView.OnRequestDevUserInfoAutofillListener(){

                @Override
                public void requestAutofill(EditText etDevUserName, EditText etDevUserPassword) {
                     AutofillManager autofillManager = (AutofillManager) getSystemService(AutofillManager.class);
                    if (autofillManager!= null) {
                        //ToastUtils.show("0");
                        autofillManager.requestAutofill(findViewById(R.id.usernameEditText));
                        autofillManager.requestAutofill(findViewById(R.id.passwordEditText));
                    }
                }
            });*/
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
        getMenuInflater().inflate(R.menu.toolbar_winboll_app_about, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_help) {
            ToastUtils.show("R.id.item_help");
        } else if (item.getItemId() == android.R.id.home) {
            WinBollActivityManager.getInstance(this).finish(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

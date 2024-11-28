package cc.winboll.studio.shared.view;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/08/12 14:38:03
 * @Describe AboutView
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import cc.winboll.studio.R;
import cc.winboll.studio.shared.app.AppVersionUtils;
import cc.winboll.studio.shared.log.LogUtils;
import com.hjq.toast.ToastUtils;
import java.io.IOException;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import cc.winboll.studio.shared.app.WinBollApplication;
import okhttp3.Credentials;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import android.view.LayoutInflater;
import android.widget.EditText;
import cc.winboll.studio.shared.util.PrefUtils;

public class AboutView extends LinearLayout {

    public static final String TAG = "AboutView";

    public static final int MSG_APPUPDATE_CHECKED = 0;

    Context mContext;
    String mszAppName = "";
    String mszAppProjectName = "";
    String mszAppVersionName = "";
    String mszCurrentAppPackageName = "";
    volatile String mszNewestAppPackageName = "";
    String mszAppDescription = "";
    String mszHomePage = "";
    String mszGitea = "";
    int mnAppIcon = 0;
    String mszWinBollServerHost;
    String mszReleaseAPKName;
    EditText metDevUserName;
    EditText metDevUserPassword;

    public AboutView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    void initView(Context context, AttributeSet attrs) {
        mContext = context;
        mszWinBollServerHost = WinBollApplication.isDebug()?  "dev.winboll.cc":"www.winboll.cc";
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AboutView);
        mszAppName = typedArray.getString(R.styleable.AboutView_appname);
        mszAppProjectName = typedArray.getString(R.styleable.AboutView_appprojectname);
        mszAppDescription = typedArray.getString(R.styleable.AboutView_appdescription);
        mnAppIcon = typedArray.getResourceId(R.styleable.AboutView_appicon, R.drawable.ic_winboll);
        // 返回一个绑定资源结束的信号给资源
        typedArray.recycle();

        try {
            mszAppVersionName = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
        }
        mszCurrentAppPackageName = mszAppName + "_" + mszAppVersionName + ".apk";
        mszHomePage = "https://" + mszWinBollServerHost + "/studio/details.php?app=" + mszAppName;
        mszGitea = "https://gitea.winboll.cc/WinBoll/" + mszAppProjectName + ".git";
        
        if (WinBollApplication.isDebug()) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View addedView = inflater.inflate(R.layout.view_about_dev, this, false);
            LinearLayout llMain = addedView.findViewById(R.id.viewaboutdevLinearLayout1);
            metDevUserName = addedView.findViewById(R.id.viewaboutdevEditText1);
            metDevUserPassword = addedView.findViewById(R.id.viewaboutdevEditText2);
            metDevUserName.setText(PrefUtils.getString(mContext, "metDevUserName", ""));
            metDevUserPassword.setText(PrefUtils.getString(mContext, "metDevUserPassword", ""));
            llMain.addView(createAboutPage());
            addView(addedView);
        } else {
            addView(createAboutPage());
        }
        
        // 初始化标题栏
        //setSubtitle(getContext().getString(R.string.text_about));
        //LinearLayout llMain = findViewById(R.id.viewaboutLinearLayout1);
        //llMain.addView(createAboutPage());
        
        // 就读取正式版应用包版本号，设置 Release 应用包文件名
        String szReleaseAppVersionName = "";
        try {
            szReleaseAppVersionName = mContext.getPackageManager().getPackageInfo(subBetaSuffix(mContext.getPackageName()), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
        }
        mszReleaseAPKName = mszAppName + "_" + szReleaseAppVersionName + ".apk";
        
        
    }
    
    public static String subBetaSuffix(String input) {
        if (input.endsWith(".beta")) {
            return input.substring(0, input.length() - ".beta".length());
        }
        return input;
    }

    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_APPUPDATE_CHECKED : {
                        /*//检查当前应用包文件名是否是测试版，如果是就忽略检查
                        if(mszCurrentAppPackageName.matches(".*_\\d+\\.\\d+\\.\\d+-beta.*\\.apk")) {
                            ToastUtils.show("APP is the beta Version. Version check ignore.");
                            return;
                        }*/
                        
                        if (!AppVersionUtils.isHasNewStageReleaseVersion(mszReleaseAPKName, mszNewestAppPackageName)) {
                            ToastUtils.delayedShow("Current app is the newest.", 5000);
                        } else {
                            String szMsg = "Current app is :\n[ " + mszReleaseAPKName
                                + " ]\nThe last app is :\n[ " + mszNewestAppPackageName
                                + " ]\nIs download the last app?";
                            YesNoAlertDialog.show(mContext, "Application Update Prompt", szMsg, mIsDownlaodUpdateListener);
                        }
                        break;
                    }
            }
        }
    };

    protected View createAboutPage() {
        // 定义 GitWeb 按钮
        //
        Element elementGitWeb = new Element(mContext.getString(R.string.gitea_home), R.drawable.ic_winboll);
        elementGitWeb.setOnClickListener(mGitWebOnClickListener);
        // 定义检查更新按钮
        //
        Element elementAppUpdate = new Element(mContext.getString(R.string.app_update), R.drawable.ic_winboll);
        elementAppUpdate.setOnClickListener(mAppUpdateOnClickListener);

        String szAppInfo = "";
        try {
            szAppInfo = mszAppName + " "
                + mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName
                + "\n" + mszAppDescription;
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
        }
        View aboutPage = new AboutPage(mContext)
            .setDescription(szAppInfo)
            //.isRTL(false)
            //.setCustomFont(String) // or Typeface
            .setImage(mnAppIcon)
            //.addItem(versionElement)
            //.addItem(adsElement)
            //.addGroup("Connect with us")
            .addEmail("ZhanGSKen@QQ.COM")
            .addWebsite(mszHomePage)
            .addItem(elementGitWeb)
            .addItem(elementAppUpdate)
            //.addFacebook("the.medy")
            //.addTwitter("medyo80")
            //.addYoutube("UCdPQtdWIsg7_pi4mrRu46vA")
            //.addPlayStore("com.ideashower.readitlater.pro")
            //.addGitHub("medyo")
            //.addInstagram("medyo80")
            .create();
        return aboutPage;
    }

    View.OnClickListener mGitWebOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mszGitea));
            mContext.startActivity(browserIntent);
        }
    };

    View.OnClickListener mAppUpdateOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View view) {
            ToastUtils.show("Start app update checking.");
            new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String szUrl = "https://" + mszWinBollServerHost + "/studio/details.php?app=" + mszAppName;
                        // 构建包含认证信息的请求
                        String credential = "";
                        if(WinBollApplication.isDebug()) {
                            credential = Credentials.basic(metDevUserName.getText().toString(), metDevUserPassword.getText().toString());
                            PrefUtils.saveString(mContext, "metDevUserName", metDevUserName.getText().toString());
                            PrefUtils.saveString(mContext, "metDevUserPassword", metDevUserPassword.getText().toString());
                        }
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder()
                            .url(szUrl)
                            .header("Accept", "text/plain") // 设置正确的Content-Type头
                            .header("Authorization", credential)
                            .build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    // 处理网络请求失败
                                    LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                    if (!response.isSuccessful()) {
                                        LogUtils.d(TAG, "Unexpected code " + response, Thread.currentThread().getStackTrace());
                                        return;
                                    }

                                    try {
                                        // 读取响应体作为字符串，注意这里可能需要解码
                                        String text = response.body().string();
                                        org.jsoup.nodes.Document doc = org.jsoup.Jsoup.parse(text);
                                        LogUtils.v(TAG, doc.text());

                                        // 使用id选择器找到具有特定id的元素
                                        org.jsoup.nodes.Element elementWithId = doc.select("#LastRelease").first(); // 获取第一个匹配的元素

                                        // 提取并打印元素的文本内容
                                        mszNewestAppPackageName = elementWithId.text();
                                        ToastUtils.delayedShow(text + "\n" + mszNewestAppPackageName, 5000);

                                        mHandler.sendMessage(mHandler.obtainMessage(MSG_APPUPDATE_CHECKED));
                                    } catch (Exception e) {
                                        LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
                                    }
                                }
                            });
                    }
                }).start();
        }
    };

    YesNoAlertDialog.OnDialogResultListener mIsDownlaodUpdateListener = new YesNoAlertDialog.OnDialogResultListener() {
        @Override
        public void onYes() {
            String szUrl = "https://" + mszWinBollServerHost + "/studio/download.php?appname=" + mszAppName + "&apkname=" + mszNewestAppPackageName;
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(szUrl));
            mContext.startActivity(browserIntent);
        }

        @Override
        public void onNo() {
        }
    };
}

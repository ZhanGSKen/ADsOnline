package cc.winboll.studio.shared.view;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/12/07 20:15:47
 * @Describe 开发主机连接状态视图
 */
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import cc.winboll.studio.R;
import cc.winboll.studio.shared.log.LogUtils;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import okhttp3.Authenticator;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import com.hjq.toast.ToastUtils;

public class DevelopHostConnectionStatusView extends LinearLayout {

    public static final String TAG = "DevelopHostConnectionStatusView";

    public static final int MSG_CONNECTION_INFO = 0;
    public static final int MSG_UPDATE_CONNECTION_STATUS = 1;

    Context mContext;
    //boolean mIsConnected;
    ConnectionThread mConnectionThread;
    String mszWinBollServerHost;
    ImageView mImageView;
    TextView mTextView;
    DevelopHostConnectionStatusViewHandler mDevelopHostConnectionStatusViewHandler;
    //WebView mWebView;
    static volatile DevelopHostConnectionStatus mDevelopHostConnectionStatus;
    View.OnClickListener mViewOnClickListener;
    static String _mUserName;
    static String _mPassword;

    static enum DevelopHostConnectionStatus { DISCONNECTED, START_CONNECT, CONNECTING, CONNECTED,  };

    public DevelopHostConnectionStatusView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public DevelopHostConnectionStatusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public DevelopHostConnectionStatusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    public DevelopHostConnectionStatusView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        initView();
    }

    void initView() {
        mImageView = new ImageView(mContext);
        mDevelopHostConnectionStatus = DevelopHostConnectionStatus.DISCONNECTED;
        //mIsConnected = false;
        setConnectionStatusView(false);

        mViewOnClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //isConnected = !isConnected;
                if (mDevelopHostConnectionStatus == DevelopHostConnectionStatus.CONNECTED) {
                    //ToastUtils.show("CONNECTED");
                    setConnectionStatusView(false);
                    mDevelopHostConnectionStatusViewHandler.postMessageText("");
                    if (mConnectionThread != null) {
                        mConnectionThread.mIsExist = true;
                        mConnectionThread = null;
                        mDevelopHostConnectionStatus = DevelopHostConnectionStatus.DISCONNECTED;
                    }
                } else if (mDevelopHostConnectionStatus == DevelopHostConnectionStatus.DISCONNECTED) {
                    //ToastUtils.show("DISCONNECTED");
                    setConnectionStatusView(true);

                    if (mConnectionThread == null ) {
                        ToastUtils.show("mConnectionThread == null");
                        mConnectionThread = new ConnectionThread();
                        mDevelopHostConnectionStatus = DevelopHostConnectionStatus.START_CONNECT;
                        mConnectionThread.start();
                    }
                }

                /*if (isConnected) {
                 mWebView.loadUrl("https://dev.winboll.cc");
                 } else {
                 mWebView.stopLoading();
                 }*/
                //ToastUtils.show(mDevelopHostConnectionStatus);
                LogUtils.d(TAG, "mDevelopHostConnectionStatus : " + mDevelopHostConnectionStatus);
            }
        };
        setOnClickListener(mViewOnClickListener);
        addView(mImageView);
        mTextView = new TextView(mContext);
        mDevelopHostConnectionStatusViewHandler = new DevelopHostConnectionStatusViewHandler(this);
        addView(mTextView);
        /*mWebView = new WebView(mContext);
         mWebView.setWebViewClient(new WebViewClient() {
         @Override
         public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
         // 弹出系统基本HTTP验证窗口
         handler.proceed("username", "password");
         }
         });
         addView(mWebView);*/
    }

    public void setServerHost(String szWinBollServerHost) {
        mszWinBollServerHost = szWinBollServerHost;
    }

    public void setAuthInfo(String username, String password) {
        _mUserName = username;
        _mPassword = password;
    }

    void setConnectionStatusView(boolean isConnected) {
        //mIsConnected = isConnected;
        // 获取vector drawable
        Drawable drawable = ContextCompat.getDrawable(mContext, isConnected ? R.drawable.ic_dev_connected : R.drawable.ic_dev_disconnected);
        if (drawable != null) {
            mImageView.setImageDrawable(drawable);
        }
    }

    void requestWithBasicAuth(DevelopHostConnectionStatusViewHandler textViewHandler, String targetUrl, final String username, final String password) {
        // 用户名和密码，替换为实际的认证信息
        //String username = "your_username";
        //String password = "your_password";

        OkHttpClient client = new OkHttpClient.Builder()
            .authenticator(new Authenticator() {
                @Override
                public Request authenticate(Route route, Response response) throws IOException {
                    String credential = Credentials.basic(username, password);
                    return response.request().newBuilder()
                        .header("Authorization", credential)
                        .build();
                }
            })
            .build();

        Request request = new Request.Builder()
            .url(targetUrl) // 替换为实际要请求的网页地址
            .build();

        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                //System.out.println(response.body().string());
                //ToastUtils.show("Develop Host Connection IP is : " + response.body().string());
                // 获取当前时间
                LocalDateTime now = LocalDateTime.now();

                // 定义时间格式
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                // 按照指定格式格式化时间并输出
                String formattedDateTime = now.format(formatter);
                //System.out.println(formattedDateTime);
                textViewHandler.postMessageText("ClientIP<" + formattedDateTime + ">: " + response.body().string());
            } else {
                String sz = "请求失败，状态码: " + response.code();
                textViewHandler.postMessageText(sz);
                textViewHandler.postMessageConnectionStatus(false);
                LogUtils.d(TAG, sz);
            }
        } catch (IOException e) {
            textViewHandler.postMessageText(e.getMessage());
            textViewHandler.postMessageConnectionStatus(false);
            LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
        }
    }

    class DevelopHostConnectionStatusViewHandler extends Handler {
        DevelopHostConnectionStatusView mDevelopHostConnectionStatusView;

        public DevelopHostConnectionStatusViewHandler(DevelopHostConnectionStatusView view) {
            mDevelopHostConnectionStatusView = view;
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_CONNECTION_INFO) {
                mDevelopHostConnectionStatusView.mTextView.setText((String)msg.obj);
            } else if (msg.what == MSG_UPDATE_CONNECTION_STATUS) {
                mDevelopHostConnectionStatusView.setConnectionStatusView((boolean)msg.obj);
                mDevelopHostConnectionStatusView.mDevelopHostConnectionStatus = ((boolean)msg.obj) ? DevelopHostConnectionStatus.CONNECTED : DevelopHostConnectionStatus.DISCONNECTED;
            }
            super.handleMessage(msg);
        }

        void postMessageText(String szMSG) {
            Message msg = new Message();
            msg.what = MSG_CONNECTION_INFO;
            msg.obj = szMSG;
            sendMessage(msg);
        }

        void postMessageConnectionStatus(boolean isConnected) {
            Message msg = new Message();
            msg.what = MSG_UPDATE_CONNECTION_STATUS;
            msg.obj = isConnected;
            sendMessage(msg);
        }
    }

    class ConnectionThread extends Thread {

        public volatile boolean mIsExist;

        //DevelopHostConnectionStatusViewHandler mDevelopHostConnectionStatusViewHandler;

        //public ConnectionThread(DevelopHostConnectionStatusViewHandler developHostConnectionStatusViewHandler) {
        //mDevelopHostConnectionStatusViewHandler = developHostConnectionStatusViewHandler;
        //}
        public ConnectionThread() {
            mIsExist = false;
        }

        @Override
        public void run() {
            super.run();
            while (mIsExist == false) {
                if (mDevelopHostConnectionStatus == DevelopHostConnectionStatus.START_CONNECT) {
                    mDevelopHostConnectionStatus = DevelopHostConnectionStatus.CONNECTING;
                    ToastUtils.show("Develop Host Connection Start.");
                    //LogUtils.d(TAG, "Develop Host Connection Start.");
                    String targetUrl = "https://" + mszWinBollServerHost + "/cip/?simple=true";  // 这里替换成你实际要访问的网址
                    requestWithBasicAuth(mDevelopHostConnectionStatusViewHandler, targetUrl, _mUserName, _mPassword);
                }
                
                try {
                    Thread.sleep(5 * 1000);
                } catch (InterruptedException e) {
                    LogUtils.d(TAG, e, Thread.currentThread().getStackTrace());
                }
            }
            //ToastUtils.show("ConnectionThread exit.");
            LogUtils.d(TAG, "ConnectionThread exit.");
        }
    }
}

package cc.winboll.studio.shared.view;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/12/07 20:15:47
 * @Describe 开发主机连接状态视图
 */
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import cc.winboll.studio.R;
import android.widget.ImageView;
import android.graphics.drawable.Drawable;
import androidx.core.content.ContextCompat;
import android.view.View.OnClickListener;
import com.hjq.toast.ToastUtils;

public class DevelopHostConnectionStatusView extends ImageView {

    public static final String TAG = "DevelopHostConnectionStatusView";

    Context mContext;
    DevelopHostConnectionStatus mDevelopHostConnectionStatus;
    View.OnClickListener mViewOnClickListener;
    
    enum DevelopHostConnectionStatus { CONNECTED, DISCONNECTED };

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

    static boolean isConnected = false;
    void initView() {
        setStatusView(isConnected);
        mViewOnClickListener = new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                isConnected = !isConnected;
                setStatusView(isConnected);
                ToastUtils.show(mDevelopHostConnectionStatus);
            }
        };
        setOnClickListener(mViewOnClickListener);
    }
    
    void setStatusView(boolean isConnected) {
        mDevelopHostConnectionStatus = isConnected ? DevelopHostConnectionStatus.CONNECTED : DevelopHostConnectionStatus.DISCONNECTED ;
        // 获取vector drawable
        Drawable drawable = ContextCompat.getDrawable(mContext, (mDevelopHostConnectionStatus == DevelopHostConnectionStatus.CONNECTED) ? R.drawable.ic_dev_connected : R.drawable.ic_dev_disconnected);
        if (drawable!= null) {
            setImageDrawable(drawable);
        }
    }
}

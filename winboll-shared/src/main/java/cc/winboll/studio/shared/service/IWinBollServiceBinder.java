package cc.winboll.studio.shared.service;
import android.graphics.drawable.Drawable;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/12/08 23:40:05
 * @Describe WinBollService 服务 Binder。
 */
public interface IWinBollServiceBinder {
    
    public static final String TAG = "IWinBollServiceBinder";
    
    public WinBollService getService();
    
    public Drawable getCurrentStatusIconDrawable();
}

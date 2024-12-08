package cc.winboll.studio.app;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/12/08 15:10:51
 * @Describe 全局应用类
 */
import cc.winboll.studio.shared.app.WinBollApplication;

public class App extends WinBollApplication {
    
    public static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        setIsDebug(!BuildConfig.DEBUG);
    }
    
}

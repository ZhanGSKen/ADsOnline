package cc.winboll.studio.shared.service;
import cc.winboll.studio.shared.app.BaseBean;
import android.util.JsonReader;
import java.io.IOException;
import android.util.JsonWriter;
import android.content.Context;

/**
 * @Author ZhanGSKen@QQ.COM
 * @Date 2024/12/08 19:44:57
 * @Describe WinBollService 运行参数配置
 */
public class WinBollServiceBean extends BaseBean {

    public static final String TAG = "WinBollServiceBean";

    volatile boolean isEnable;

    public WinBollServiceBean() {
        isEnable = false;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public boolean isEnable() {
        return isEnable;
    }

    @Override
    public String getName() {
        return WinBollServiceBean.class.getName();
    }

    @Override
    public void writeThisToJsonWriter(JsonWriter jsonWriter) throws IOException {
        super.writeThisToJsonWriter(jsonWriter);
        WinBollServiceBean bean = this;
        jsonWriter.name("isEnable").value(bean.isEnable());
    }

    @Override
    public boolean initObjectsFromJsonReader(JsonReader jsonReader, String name) throws IOException {
        if (super.initObjectsFromJsonReader(jsonReader, name)) { return true; } else {
            if (name.equals("isEnable")) {
                setIsEnable(jsonReader.nextBoolean());
            } else {
                return false;
            }
        }
        return true;
    }

    @Override
    public BaseBean readBeanFromJsonReader(JsonReader jsonReader) throws IOException {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            String name = jsonReader.nextName();
            if (!initObjectsFromJsonReader(jsonReader, name)) {
                jsonReader.skipValue();
            }
        }
        // 结束 JSON 对象
        jsonReader.endObject();
        return this;
    }

    public static WinBollServiceBean loadWinBollServiceBean(Context context) {
        WinBollServiceBean bean = WinBollServiceBean.loadBean(context, WinBollServiceBean.class);
        return bean == null ? new WinBollServiceBean() : bean;
    }

    public static boolean saveWinBollServiceBean(WinBollService service, WinBollServiceBean bean) {
        return WinBollServiceBean.saveBean(service, bean);
    }
}

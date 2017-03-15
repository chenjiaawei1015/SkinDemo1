package cjw.skinlib.base;


import android.content.Context;
import android.view.View;

public abstract class AbsSkin {
    /**
     * 引入名字   background
     */
    protected String attrName = null;
    /**
     * id
     */
    protected int refId = 0;
    /**
     * resource  name 资源名字  比如 @drawble/app_icon
     */
    protected String attrValueName = null;

    /**
     * 类型名字比如   color  和  drawable
     */
    protected String attrType = null;

    public AbsSkin(String attrName, int refId, String attrValueName, String attrType) {
        this.attrName = attrName;
        this.refId = refId;
        this.attrValueName = attrValueName;
        this.attrType = attrType;
    }

    /**
     * 应用皮肤
     */
    public abstract void apply(Context context, View view);
}

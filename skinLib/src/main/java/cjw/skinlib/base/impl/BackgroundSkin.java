package cjw.skinlib.base.impl;


import android.content.Context;
import android.view.View;

import cjw.skinlib.SkinManager;
import cjw.skinlib.base.AbsSkin;

public class BackgroundSkin extends AbsSkin {

    public BackgroundSkin(String attrName, int refId, String attrValueName, String attrType) {
        super(attrName, refId, attrValueName, attrType);
    }

    @Override
    public void apply(Context context, View view) {
        SkinManager skinManager = SkinManager.getInstance();
        if ("color".equals(attrType)) {
            view.setBackgroundColor(skinManager.getColor(context, refId));
        } else if ("drawable".equals(attrType)) {
            view.setBackgroundDrawable(skinManager.getDrawable(context, refId));
        }
    }
}

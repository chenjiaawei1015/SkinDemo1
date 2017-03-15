package cjw.skinlib.base.impl;


import android.content.Context;
import android.view.View;
import android.widget.TextView;

import cjw.skinlib.SkinManager;
import cjw.skinlib.base.AbsSkin;

public class TextSkin extends AbsSkin {

    public TextSkin(String attrName, int refId, String attrValueName, String attrType) {
        super(attrName, refId, attrValueName, attrType);
    }

    @Override
    public void apply(Context context, View view) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            tv.setTextColor(SkinManager.getInstance().getColor(context, refId));
        }
    }
}

package cjw.skinlib;


import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.LayoutInflaterFactory;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cjw.skinlib.base.AbsSkin;
import cjw.skinlib.base.impl.BackgroundSkin;
import cjw.skinlib.base.impl.TextSkin;

public class SkinInflaterFactory implements LayoutInflaterFactory {

    /**
     * 系统控件的前缀名
     */
    private static final String[] PREFIX_LIST = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    //缓存整个页面需要换肤的View
    private Map<View, SkinItem> mSkinMap = new HashMap<>();

    /**
     * 创建View对象回调
     */
    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View view = null;

        // 1.查询View控件
        // 从系统控件中查询
        for (String prefix : PREFIX_LIST) {
            view = createView(context, attrs, prefix + name);
            if (view != null) {
                break;
            }
        }
        // 从自定义控件中查询
        if (view == null) {
            view = createView(context, attrs, name);
        }

        // 2.换肤操作
        if (view != null) {
            changeViewSkin(view, context, attrs);
        }

        return view;
    }

    /**
     * 换肤操作
     */
    private void changeViewSkin(View view, Context context, AttributeSet attrs) {
        List<AbsSkin> attrList = new ArrayList<>();

        Resources resources = context.getResources();

        for (int i = 0; i < attrs.getAttributeCount(); i++) {
            // 获取属性名称
            String attributeName = attrs.getAttributeName(i);
            // 获取属性值
            String attributeValue = attrs.getAttributeValue(i);

            //资源的id
            int id = -1;
            //入口名,例如text_color_selector
            String entryName;
            //类型名,例如color,drawable
            String typeName;
            AbsSkin absSkin = null;

            if ("background".equalsIgnoreCase(attributeName)) {
                id = Integer.parseInt(attributeValue.substring(1));//资源的id
                //入口名，例如text_color_selector
                entryName = context.getResources().getResourceEntryName(id);
                //类型名，例如color、drawable
                typeName = context.getResources().getResourceTypeName(id);
                absSkin = new BackgroundSkin(attributeName, id, entryName, typeName);

            } else if ("textColor".equalsIgnoreCase(attributeName)) {
                id = Integer.parseInt(attributeValue.substring(1));
                entryName = resources.getResourceEntryName(id);
                typeName = resources.getResourceTypeName(id);

                absSkin = new TextSkin(attributeName, id, entryName, typeName);

            }

            if (absSkin != null) {
                attrList.add(absSkin);
            }
        }

        if (!attrList.isEmpty()) {
            SkinItem skinItem = new SkinItem(view, attrList);
            mSkinMap.put(view, skinItem);
            // skinItem.apply(context);
        }
    }

    /**
     * 给外面进行调用 换肤
     */
    public void update(Context context) {
        for (View view : mSkinMap.keySet()) {
            if (view == null) {
                continue;
            }
            mSkinMap.get(view).apply(context);
        }
    }

    /**
     * 封装一个控件
     */
    private class SkinItem {
        public View view;
        public List<AbsSkin> attrList;

        public SkinItem(View view, List<AbsSkin> attrList) {
            this.view = view;
            this.attrList = attrList;
        }

        /**
         * 应用属性的方法
         */
        public void apply(Context context) {
            for (AbsSkin skinInterface : attrList) {
                skinInterface.apply(context, view);
            }
        }
    }

    /**
     * 获取View控件
     */
    private View createView(Context context, AttributeSet attrs, String packageName) {
        try {
            Class clazz = context.getClassLoader().loadClass(packageName);
            Constructor<? extends View> constructor = clazz.getConstructor(Context.class, AttributeSet.class);
            return constructor.newInstance(context, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

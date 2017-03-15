package cjw.skinlib;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

import java.lang.reflect.Method;

public class SkinManager {

    private static final SkinManager sSkinManager = new SkinManager();

    private Resources mSkinResource;
    private String mPackageName;

    private SkinManager() {
    }

    public static SkinManager getInstance() {
        return sSkinManager;
    }

    public void loadSkin(Context context, String path) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        mPackageName = packageInfo.packageName;

        // 构造Resource
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            // addAssetPath
            Method addAssetPathMtd = assetManager.getClass().getMethod("addAssetPath", String.class);
            ;
            addAssetPathMtd.invoke(assetManager, path);

            Resources resources = context.getResources();
            mSkinResource = new Resources(assetManager, resources.getDisplayMetrics(), resources.getConfiguration());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getColor(Context context, int resId) {
        if (mSkinResource == null) {
            return resId;
        }

        Resources resources = context.getResources();
        String resName = resources.getResourceEntryName(resId);

        // 获取插件apk颜色的id
        int colorId = mSkinResource.getIdentifier(resName, "color", mPackageName);
        if (colorId <= 0) {
            return resId;
        }
        return mSkinResource.getColor(colorId);
    }

    public Drawable getDrawable(Context context, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        if (mSkinResource == null) {
            return drawable;
        }

        Resources resources = context.getResources();
        String dwName = resources.getResourceEntryName(resId);
        int dwId = mSkinResource.getIdentifier(dwName, "drawable", mPackageName);
        if (dwId <= 0) {
            return drawable;
        }
        drawable = mSkinResource.getDrawable(dwId);
        return drawable;
    }

}

package cjw.skinlib;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterCompat;

public class SkinBaseActivity extends Activity {

    private static final String TAG = "SkinBaseActivity";

    private SkinInflaterFactory mFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFactory = new SkinInflaterFactory();
        LayoutInflaterCompat.setFactory(getLayoutInflater(), mFactory);
    }

    public void update(Context context) {
        mFactory.update(context);
    }
}

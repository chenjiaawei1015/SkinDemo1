package cjw.skindemo1;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;

import java.io.File;

import cjw.skinlib.SkinBaseActivity;
import cjw.skinlib.SkinManager;

public class MainActivity extends SkinBaseActivity implements View.OnClickListener {

    private View mTv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTv1 = findViewById(R.id.tv1);
        mTv1.setOnClickListener(this);
    }

    public void changeSkin(View view) {
        String path = new File(Environment.getExternalStorageDirectory(), "skin.apk").getAbsolutePath();
        SkinManager.getInstance().loadSkin(this, path);
        update(this);
    }

    @Override
    public void onClick(View v) {
        changeSkin(v);
    }
}

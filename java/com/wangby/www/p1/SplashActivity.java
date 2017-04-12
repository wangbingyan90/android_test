package com.wangby.www.p1;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by 王炳炎zer on 2017/4/12.
 */

public class SplashActivity extends Activity {
    TextView version;
    int mLocalVersionCode;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        initUI();
        initData();


    }

    /**
     *获取数据
     */
    private void initData() {
        version.setText("version"+getVersion());
        mLocalVersionCode = getVersionCode();
        
    }

    /**
     * 初始化UI组件
     */
    private void initUI() {
        version = (TextView) findViewById(R.id.version);

    }


    /**
     *
     * @return 版本信息
     */

    public String getVersion() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取本地版本号
     * @return版本号
     */
    public int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }


}

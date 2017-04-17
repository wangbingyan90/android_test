package com.wangby.www.p1;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wangby.www.p1.tool.DataTool;
import com.wangby.www.p1.tool.HttpTool;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 王炳炎zer on 2017/4/12.
 */

public class SplashActivity extends Activity {
    /**
     * 相关状态码
     */
    protected static final int JOSN_ERROR = 103;
    protected static final int URL_ERROR = 102;
    protected static final int ENTER_HOME = 101;
    protected static final  int UPDATE_VERSION = 100;

    ProgressDialog progressDialog;
    TextView version;
    RelativeLayout splash;
    int mLocalVersionCode;
    String versionDes;
    String downloadurl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        x.view().inject(this);
        setContentView(R.layout.splash);
        initUI();
        initData();

//        initAnimation();
    }


    /**
     * 添加淡入动画效果
     */
//    private void initAnimation() {
//        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
//        alphaAnimation.setDuration(5000);
//        splash.startAnimation(alphaAnimation);
//    }

    /**
     * 更新是否成功
     * 都要进入home
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        super.onStart();
        super.onResume();
        enterHome();
    }

    /**
     * 更新对话框，提示更新
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showUpdate() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.log);
        builder.setTitle("有新版本需更新");
        builder.setMessage(versionDes);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
                //下载更新
            }
        });
        builder.setNegativeButton("放弃",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                //即使用户点击取消,也需要让其进入应用程序主界面
                enterHome();
                dialog.dismiss();
            }
        });
        builder.show();
    }


    /**
     * 下载apk
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void downloadApk() {
        if (android.os.Build.VERSION.SDK_INT>=5) {
            downHight();
        } else {
            HttpTool.down(this);//低于6.0直接使用
        }
    }

    /**
     * 高版本apk下载
     * 需要动态授权
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void downHight() {
        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            HttpTool.down(this);
        } else {
            HttpTool.down(this);;
        }
    }

    /**
     * 进入主界面
     */
    private void enterHome() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }



    /**
     * 获取网络版本信息
     * 返回状态码
     */
    private void checkVersion() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                String josn = null;
                try {
//                    josn = HttpTool.urlToString("http://123.206.85.134/wang/version.html");
                    HttpURLConnection connection = (HttpURLConnection) new URL("http://123.206.85.134/wang/version.html").openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    if(connection.getResponseCode()==200){
                        InputStream is = connection.getInputStream();
                        josn = HttpTool.streamToString(is);
                    }
                    JSONObject jsonObject = new JSONObject(josn);
                    downloadurl = jsonObject.getString("downloadurl");
                    String versionCode = jsonObject.getString("versionCode");
                    versionDes = jsonObject.getString("versionDes");
                    String versionName = jsonObject.getString("versionName");

                    if(mLocalVersionCode<Integer.parseInt(versionCode)){
                        msg.what = UPDATE_VERSION;
                    }else {
                        msg.what = ENTER_HOME;
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JOSN_ERROR;
                }catch (IOException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;
                }finally {
                    long endTime = System.currentTimeMillis();
                    if(endTime-startTime<3000){
                        try {
                            Thread.sleep(3000-(endTime-startTime));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }){
        }.start();

    }



    /**
     *获取本地数据
     *
     */
    private void initData() {
        version.setText("version"+getVersion());
        mLocalVersionCode = getVersionCode();
        if(DataTool.getBooleanConfig(this,DataTool.UP_VERSION)){
            checkVersion();
        }else {
            getWindow().getDecorView().postDelayed(new Runnable() {
                public void run()
                {SplashActivity.this.enterHome();}
            }, 2000L);
        }
    }


    /**
     * 初始化UI组件
     */
    private void initUI() {
        version = (TextView) findViewById(R.id.version);
        splash = (RelativeLayout) findViewById(R.id.splash);
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



    /**
     * 检查版本更新
     * 并进行处理
     */
    private Handler mHandler = new Handler(){
        @RequiresApi(api = Build.VERSION_CODES.M)
        public void handleMessage(android.os.Message msg){
            switch (msg.what){
                case JOSN_ERROR:
                    Toast.makeText(SplashActivity.this,"资源内容异常",Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case URL_ERROR:
                    Toast.makeText(SplashActivity.this,"URL地址异常",Toast.LENGTH_SHORT).show();
                    enterHome();
                    break;
                case ENTER_HOME:
                    enterHome();
                    break;
                case UPDATE_VERSION:
                    showUpdate();
                    break;
            }
        };
    };



}

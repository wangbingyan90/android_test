package com.wangby.www.p1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 王炳炎zer on 2017/4/13.
 */
public class HttpTool {


    /**
     * 流转换成字符串
     * @param is 输入流
     * @return转变的字符串
     */
    public static String streamToString(InputStream is) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int temp = -1;
        try {
            while((temp = is.read(buffer))!=-1){
                bos.write(buffer,0,temp);
            }
            return bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                is.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解析网站正文
     * @param url 网站地址
     * @return 网站正文
     * @throws IOException 访问失败
     */
    public static String urlToString(String url) throws IOException {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://123.206.85.134/wang/version.html").openConnection();
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            if(connection.getResponseCode()==200){
                InputStream is = connection.getInputStream();
                return HttpTool.streamToString(is);
            }
        return new String("访问失败");
    }


    /**
     * 下载文件
     * @param mContext Activity上下文
     */
    public static void down(final Context mContext) {
        {
            final ProgressDialog progressDialog = new ProgressDialog(mContext);
            if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

                RequestParams params=new RequestParams("http://123.206.85.134/wang/updata.apk");
                params.setAutoResume(true);
                params.setSaveFilePath(Environment.getExternalStorageDirectory().getPath() + File.separator+ "updata.apk");
                x.http().get(params,  new Callback.ProgressCallback<File>(){
                    @Override
                    public void onWaiting() {
                    }

                    @Override
                    public void onStarted() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isDownloading) {
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.setMessage("亲，努力下载中。。。");
                        progressDialog.show();
                        progressDialog.setMax((int) total);
                        progressDialog.setProgress((int) current);
                    }

                    @Override
                    public void onSuccess(File result) {
                        Toast.makeText(mContext, "下载成功", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        //提示用户安装
                        installApk(result,mContext);
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        ex.printStackTrace();
                        Toast.makeText(mContext, "下载失败，请检查网络和SD卡", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });
            }else {
                Toast.makeText(mContext, "SD卡问题", Toast.LENGTH_SHORT).show();
            }


        }
    }

    /**
     * 安装apk
     * @param result apk文件
     */
    private static void installApk(File result,Context mContext) {
        //系统apk入口
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
//		//文件作为数据源
//		intent.setData(Uri.fromFile(result));
//		//设置安装的类型
//		intent.setType("application/vnd.android.package-archive");


        intent.setDataAndType(Uri.fromFile(result),"application/vnd.android.package-archive");
        mContext.startActivity(intent);
//       mContext.startActivityForResult(intent, 0);

    }


}

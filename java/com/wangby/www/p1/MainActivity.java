package com.wangby.www.p1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.wangby.www.p1.tool.DataTool;

public class  MainActivity extends AppCompatActivity {
    GridView mGridView;

    String[] strings;
    int[] ints;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        initData();


    }

    /**
     * 初始数据
     */
    private void initData() {
        strings = new String[]{
                "个人设置", "提供线索", "捡到物品", "帮助线索", "个人设置", "我的失物", "提供线索", "捡到物品", "帮助线索"
        };
        ints = new int[]{
                R.drawable.log,
                R.drawable.log,
                R.drawable.log,
                R.drawable.log,
                R.drawable.log,
                R.drawable.log,
                R.drawable.log,
                R.drawable.log,
                R.drawable.log,
        };
        mGridView.setAdapter(new MyAapter());
        //注册九宫格单个条目点击事件
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        Intent intent = new Intent(getApplicationContext(),Select_set.class);
                        startActivity(intent);
                        break;
                    case 1:
                        showDialog();
                        break;
                }
            }
        });
    }

    protected void showDialog() {
        //判断本地是否有存储密码(sp	字符串)
        String psd = DataTool.getStringConfig(this,"psd");
        if(TextUtils.isEmpty(psd)){
            //1,初始设置密码对话框
//            showSetPsdDialog();
        }else{
            //2,确认密码对话框
//            showConfirmPsdDialog();
        }
    }
//
//    /**
//     * 确认密码对话框
//     */
//    private void showConfirmPsdDialog() {
//
//        //因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
//        //view是由自己编写的xml转换成的view对象xml----->view
//        Builder builder = new AlertDialog.Builder(this);
//        final AlertDialog dialog = builder.create();
//
//        final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
//        //让对话框显示一个自己定义的对话框界面效果
//        dialog.setView(view);
//        dialog.show();
//
//        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
//        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
//
//        bt_submit.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);
//
//                String confirmPsd = et_confirm_psd.getText().toString();
//
//                if(!TextUtils.isEmpty(confirmPsd)){
//                    String psd = SpUtil.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");
//                    if(psd.equals(confirmPsd)){
//                        //进入应用手机防盗模块,开启一个新的activity
//                        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
//                        startActivity(intent);
//                        //跳转到新的界面以后需要去隐藏对话框
//                        dialog.dismiss();
//                    }else{
//                        ToastUtil.show(getApplicationContext(),"确认密码错误");
//                    }
//                }else{
//                    //提示用户密码输入有为空的情况
//                    ToastUtil.show(getApplicationContext(), "请输入密码");
//                }
//            }
//        });
//
//        bt_cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//    }
//    /**
//     * 设置密码对话框
//     */
//    private void showSetPsdDialog() {
//        //因为需要去自己定义对话框的展示样式,所以需要调用dialog.setView(view);
//        //view是由自己编写的xml转换成的view对象xml----->view
//        Builder builder = new AlertDialog.Builder(this);
//        final AlertDialog dialog = builder.create();
//
//        final View view = View.inflate(this, R.layout.dialog_set_psd, null);
//        //让对话框显示一个自己定义的对话框界面效果
//        dialog.setView(view);
//        dialog.show();
//
//        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
//        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
//
//        bt_submit.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_psd);
//                EditText et_confirm_psd = (EditText)view.findViewById(R.id.et_confirm_psd);
//
//                String psd = et_set_psd.getText().toString();
//                String confirmPsd = et_confirm_psd.getText().toString();
//
//                if(!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmPsd)){
//                    if(psd.equals(confirmPsd)){
//                        //进入应用手机防盗模块,开启一个新的activity
//                        Intent intent = new Intent(getApplicationContext(), TestActivity.class);
//                        startActivity(intent);
//                        //跳转到新的界面以后需要去隐藏对话框
//                        dialog.dismiss();
//
//                        SpUtil.putString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, psd);
//                    }else{
//                        ToastUtil.show(getApplicationContext(),"确认密码错误");
//                    }
//                }else{
//                    //提示用户密码输入有为空的情况
//                    ToastUtil.show(getApplicationContext(), "请输入密码");
//                }
//            }
//        });
//
//        bt_cancel.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//    }

    /**
     * 初始九宫格UI
     */
    private void initUI() {

        mGridView = (GridView) findViewById(R.id.grid);


    }


    /**
     * 九宫格数据配置器
     */
    private class MyAapter extends BaseAdapter {
        @Override
        public int getCount() {
            return strings.length;
        }

        @Override
        public Object getItem(int position) {
            return strings[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(), R.layout.gridview, null);
            TextView tv_title = (TextView) view.findViewById(R.id.title);
            ImageView iv_icon = (ImageView) view.findViewById(R.id.image);

            tv_title.setText(strings[position]);
            iv_icon.setBackgroundResource(ints[position]);
            return view;
        }
    }
}

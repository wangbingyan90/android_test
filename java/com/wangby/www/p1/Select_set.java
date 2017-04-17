package com.wangby.www.p1;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.wangby.www.p1.tool.DataTool;
import com.wangby.www.p1.view.SelectLayout;

/**
 * Created by 王炳炎zer on 2017/4/16.
 */
public class Select_set extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_set);

        iniUpate();

    }


    /**
     * 添加监视器
     * 用于修改设置配置配置
     */
    private void iniUpate() {
        final SelectLayout selectLayout = (SelectLayout) findViewById(R.id.upview);
        boolean upversion = DataTool.getBooleanConfig(this,DataTool.UP_VERSION);
        selectLayout.setCheck(upversion);
        selectLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                boolean check = selectLayout.isCheck();
                selectLayout.setCheck(!check);
                DataTool.saveBooleanConfig(getApplicationContext(),DataTool.UP_VERSION,!check);
            }
        });
    }

}

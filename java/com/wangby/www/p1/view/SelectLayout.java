package com.wangby.www.p1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wangby.www.p1.R;

import static com.wangby.www.p1.R.id.set2;

/**
 * 自定义布局
 * Created by 王炳炎zer on 2017/4/16.
 */

public class SelectLayout extends RelativeLayout{

    TextView viewById;
    TextView viewById1;
    CheckBox viewById2;

    private static final String NAMESPACE = "http://schemas.android.com/apk/res/com.wangby.www.p1";

    private String mDestitle;
    private String mDesoff;
    private String mDeson;

    public SelectLayout(Context context) {
        this(context,null);
    }

    public SelectLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SelectLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //添加局部布局
        View.inflate(context, R.layout.select_layout,this);
         viewById = (TextView) this.findViewById(R.id.set1);
         viewById1 = (TextView) this.findViewById(set2);
         viewById2 = (CheckBox) this.findViewById(R.id.cb_box);

        iniAttrs(attrs);
        viewById.setText(mDestitle);
    }

    /**
     * 初始化属性变量
     * @param attrs 属性文件
     */
    private void iniAttrs(AttributeSet attrs) {
        mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
        mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
        mDeson = attrs.getAttributeValue(NAMESPACE, "deson");

    }

    public boolean isCheck(){
        return viewById2.isChecked();
    }

    public void setCheck(boolean isCheck){
        viewById2.setChecked(isCheck);
        if(isCheck){
            viewById1.setText(mDeson);
        }else {
            viewById1.setText(mDesoff);
        }
    }



}

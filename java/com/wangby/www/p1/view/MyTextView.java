package com.wangby.www.p1.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 自定义控件
 * Created by 王炳炎zer on 2017/4/16.
 */

public class MyTextView extends TextView{
    //使用在通过java代码创建控件
    public MyTextView(Context context) {
        super(context);
    }

    //由系统调用(带属性+上下文环境构造方法)
    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //由系统调用(带属性+上下文环境构造方法+布局文件中定义样式文件构造方法)
    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //重写获取焦点的方法,由系统调用,调用的时候默认就能获取焦点
    @Override
    public boolean isFocused() {
        return true;
    }
}
